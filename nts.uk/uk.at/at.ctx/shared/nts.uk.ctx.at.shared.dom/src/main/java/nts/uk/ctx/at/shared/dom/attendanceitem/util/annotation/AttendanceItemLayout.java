package nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface AttendanceItemLayout {
	
	int index() default -1;

	String layout();
	
	String jpPropertyName();
	
//	String needCheckIDWithField() default "";
	
	String needCheckIDWithMethod() default "";
	
	boolean needCheckIDWithIndex() default false;
	
//	boolean isList() default false;
	
	boolean listNoIndex() default false;
	
	int listMaxLength() default -1;
	
	boolean isOptional() default false;
	
	String indexField() default "";
	
	String methodForEnumValues() default "";
	
	String enumField() default "";
}
