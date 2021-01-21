package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface AttendanceItemRoot {
	
	String rootName() default "";
	
	boolean isContainer() default false;
	
	/** 0 is 日別実績、1　is 月別実績　*/
	AttendanceItemType itemType() default AttendanceItemType.DAILY_ITEM;
}
