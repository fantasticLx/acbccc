package io.kimmking.aop;


import com.alibaba.fastjson.JSONObject;
import eu.bitwalker.useragentutils.UserAgent;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 通过aop切面打印请求访问日志
 */
@Aspect
@Component
public class WebLogAspect {
    public static boolean hasRes=false;
    private static final String USER="anonymousUser";
    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.xx.controller..*.*(..))")
    public void webLog() {

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = new Object();
        String name = "佚名用户";
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            //- 鉴权
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            if (auth != null && !Objects.equals(USER, auth.getPrincipal())) {
                name = (String) auth.getPrincipal();
            }
            if (request != null) {
                Date startTime = new Date();
                String url = request.getRequestURL().toString();
                StringBuffer stringBuffer = new StringBuffer();
                String ip = request.getRemoteAddr();
                stringBuffer.append("{userName:" + name + ",url:" + url + ",parmas:{");
                Signature signature = joinPoint.getSignature();
                MethodSignature methodSignature = (MethodSignature) signature;
                String[] strings = methodSignature.getParameterNames();
                Object[] objects = joinPoint.getArgs();
                for (int i = 0; i < objects.length; i++) {
                    stringBuffer.append(strings[i] + ":" + objects[i] + ",");
                }
                stringBuffer.replace(stringBuffer.length() - 1, stringBuffer.length(), "}}");
                String logs = stringBuffer.toString();

                //获取请求头中的User-Agent
                UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
                logger.info("请求开始时间：{}", LocalDateTime.now());
                logger.info("请求Url : {}", request.getRequestURL().toString());
                logger.info("请求方式 : {}", request.getMethod());
                logger.info("请求ip : {}", request.getRemoteAddr());
                logger.info("请求方法 : ", joinPoint.getSignature().getDeclaringTypeName() + "." +
                                            joinPoint.getSignature().getName());
                logger.info("请求参数 : {}", Arrays.toString(joinPoint.getArgs()));
                // 系统信息
                logger.info("浏览器：{}", userAgent.getBrowser().toString());
                logger.info("浏览器版本：{}", userAgent.getBrowserVersion());
                logger.info("操作系统: {}", userAgent.getOperatingSystem().toString());

                try {
                    logger.info(logs);
                    result = joinPoint.proceed();
                    if(hasRes){
                        String response = JSONObject.toJSONString(result);
                        logger.info("请求结束===返回值={}:" + response);
                    }
                    Date endTime = new Date();
//                    logger.info("请求开始时间："+ DateTimeUtil.date2LongString(startTime)+";请求结束时间："+
//                        DateTimeUtil.date2LongString(endTime));
                } catch (Exception e) {
//                    result = new BaseResult();
//                    BaseResult tmp = ((BaseResult) result);
//                    tmp.setStatus(0);
//                    tmp.setMsg("系统异常");
                    logger.error(logs, e);
                }
            }
        } else {
            result = joinPoint.proceed();
        }
        return result;
    }
}