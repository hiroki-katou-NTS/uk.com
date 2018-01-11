package nts.uk.ctx.at.shared.app.util.attendanceitem.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface AttendanceItemLayout {

	String layout();
	
	String jpPropertyName();
	
	String needCheckIDWithField() default "";
	
	String needCheckIDWithMethod() default "";
	
	boolean needCheckIDWithIndex() default false;
	
	boolean isList() default false;
	
	int listMaxLength() default -1;
	
	boolean isOptional() default false;
	
	String[] setFieldWithIndex() default "";
	
	boolean isUpdate() default false;
}
