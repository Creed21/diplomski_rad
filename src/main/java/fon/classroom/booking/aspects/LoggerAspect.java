//package fon.classroom.booking.aspects;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Aspect
//@Component
//public class LoggerAspect {
//
//    Logger logger = Logger.getLogger(LoggerAspect.class.getName());
//
//
//    @AfterThrowing(value = "execution(* fon.classroom.booking.service.*.*(..))", throwing = "ex")
//    public void logException(JoinPoint joinPoint,Exception ex) {
//            logger.log(Level.SEVERE, joinPoint.getSignature()+" "+ex.getMessage());
//    }
//
//    @AfterThrowing(value = "@annotation(fon.classroom.booking.aspect.LogAspect)", throwing = "ex")
//    public void logExceptionWithAnnotation(JoinPoint joinPoint,Exception ex) {
//        logger.log(Level.SEVERE, joinPoint.getSignature()+" "+ex.getMessage());
//    }
//}
