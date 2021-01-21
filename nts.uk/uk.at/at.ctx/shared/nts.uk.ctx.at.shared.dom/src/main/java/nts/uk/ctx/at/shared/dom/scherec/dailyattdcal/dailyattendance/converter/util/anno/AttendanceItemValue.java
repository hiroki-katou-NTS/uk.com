package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface AttendanceItemValue {
	
	ValueType type() default ValueType.CODE;
	
	String getTypeWith() default "";
	
	String setValueWith() default "";
}
