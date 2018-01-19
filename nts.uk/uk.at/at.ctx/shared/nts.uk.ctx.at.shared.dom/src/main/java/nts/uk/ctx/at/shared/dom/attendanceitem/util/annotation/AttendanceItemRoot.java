package nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface AttendanceItemRoot {
	
	String rootName() default "";
	
	boolean isContainer() default false;
}
