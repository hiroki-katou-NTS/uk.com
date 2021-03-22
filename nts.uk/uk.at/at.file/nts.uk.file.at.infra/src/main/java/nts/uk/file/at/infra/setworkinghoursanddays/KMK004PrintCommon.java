package nts.uk.file.at.infra.setworkinghoursanddays;

import java.util.Optional;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 
 * @author sonnlb
 *
 */
public class KMK004PrintCommon {
	
	private static String KMK004_404 = TextResource.localize("KMK004_404");
	private static String KMK004_405 = TextResource.localize("KMK004_405");
	private static String KMK004_406 = TextResource.localize("KMK004_406");
	private static String KMK004_407 = TextResource.localize("KMK004_407");
	private static String KMK004_408 = TextResource.localize("KMK004_408");
	private static String KMK004_409 = TextResource.localize("KMK004_409");
	private static String KMK004_410 = TextResource.localize("KMK004_410");
	private static String KMK004_411 = TextResource.localize("KMK004_411");
	private static String KMK004_412 = TextResource.localize("KMK004_412");
	private static String KMK004_413 = TextResource.localize("KMK004_413");
	private static String KMK004_181 = TextResource.localize("KMK004_181");
	private static String KMK004_182 = TextResource.localize("KMK004_182");
	private static String KMK004_76 = TextResource.localize("KMK004_76");
	private static String KMK004_77 = TextResource.localize("KMK004_77");
	private static String KMK004_272 = TextResource.localize("KMK004_272");
	private static String KMK004_273 = TextResource.localize("KMK004_273");
	private static String Monday = I18NText.getText("Enum_DayOfWeek_Monday");
	private static String Tuesday = I18NText.getText("Enum_DayOfWeek_Tuesday");
	private static String Wednesday = I18NText.getText("Enum_DayOfWeek_Wednesday");
	private static String Thursday = I18NText.getText("Enum_DayOfWeek_Thursday");
	private static String Friday = I18NText.getText("Enum_DayOfWeek_Friday");
	private static String Saturday = I18NText.getText("Enum_DayOfWeek_Saturday");
	private static String Sunday = I18NText.getText("Enum_DayOfWeek_Sunday");
	
	
	public static String convertTime(Integer pTime) {
		if (pTime == null) {
			return null;
		}
		String time = String.format("%d:%02d", pTime / 60, pTime % 60);
		return time;
	}
	
	public static String getExtraType(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return KMK004_404;
    	case 1:
    		return KMK004_405;
    	default: 
    		return null;
    	}
    }
	public static String getWeekStart(Optional<WeekRuleManagement> startOfWeek) {
		if (!startOfWeek.isPresent()) {
			return null;
		}
		
		switch (startOfWeek.get().getWeekStart()) {
			case Monday:
				return Monday;
			case Tuesday:
				return Tuesday;
			case Wednesday:
				return Wednesday;
			case Thursday:
				return Thursday;
			case Friday:
				return Friday;
			case Saturday:
				return Saturday;
			case Sunday:
				return Sunday;
			case TighteningStartDate:
				return "締め開始日";
			default:
				return null;
		}
	}
	
	
	
	public static String getLegalType(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return KMK004_406;
    	case 1:
    		return KMK004_407;
    	default: 
    		return null;
    	}
    }
	
	public static String getFlexType(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 1:
    		return KMK004_408;
    	case 0:
    		return KMK004_409;
    	default: 
    		return null;
    	}
    }
	
	public static String getAggType(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return KMK004_181;
    	case 1:
    		return KMK004_182;
    	default: 
    		return null;
    	}
    }
	

	public static String getAggTypeEmployee(int value){
    	switch (value){
    	case 0:
    		return KMK004_410;
    	case 1:
    		return KMK004_411;
    	default: 
    		return null;
    	}
    }
	
	public static String getInclude(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return KMK004_406;
    	case 1:
    		return KMK004_407;
    	default: 
    		return null;
    	}
    }
	
	public static String getLegal(Integer value) {
		if(value== null) return null;
		switch (value) {
		case 0:
			return KMK004_412;
		case 1:
			return KMK004_413;
		default:
			return null;
		}
	}
	
	public static String getShortageTime(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return KMK004_76;
    	case 1:
    		return KMK004_77;
    	default: 
    		return null;
    	}
    }
	
	public static String getSettle(Integer value) {
		if(value== null) return null;
		switch (value){
    	case 0:
    		return KMK004_272;
    	case 1:
    		return KMK004_273;
    	default: 
    		return null;
    	}
	}
	
	public static String getWeeklySurcharge(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return KMK004_404;
    	case 1:
    		return KMK004_405;
    	default: 
    		return null;
    	}
    }
}
