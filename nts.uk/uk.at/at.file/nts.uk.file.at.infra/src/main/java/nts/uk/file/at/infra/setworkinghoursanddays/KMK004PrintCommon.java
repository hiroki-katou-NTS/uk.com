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
    		return TextResource.localize("KMK004_404");
    	case 1:
    		return TextResource.localize("KMK004_405");
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
				return I18NText.getText("Enum_DayOfWeek_Monday");
			case Tuesday:
				return I18NText.getText("Enum_DayOfWeek_Tuesday");
			case Wednesday:
				return I18NText.getText("Enum_DayOfWeek_Wednesday");
			case Thursday:
				return I18NText.getText("Enum_DayOfWeek_Thursday");
			case Friday:
				return I18NText.getText("Enum_DayOfWeek_Friday");
			case Saturday:
				return I18NText.getText("Enum_DayOfWeek_Saturday");
			case Sunday:
				return I18NText.getText("Enum_DayOfWeek_Sunday");
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
    		return TextResource.localize("KMK004_406");
    	case 1:
    		return TextResource.localize("KMK004_407");
    	default: 
    		return null;
    	}
    }
	
	public static String getFlexType(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 1:
    		return TextResource.localize("KMK004_408");
    	case 0:
    		return TextResource.localize("KMK004_409");
    	default: 
    		return null;
    	}
    }
	
	public static String getAggType(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_181");
    	case 1:
    		return TextResource.localize("KMK004_182");
    	default: 
    		return null;
    	}
    }
	

	public static String getAggTypeEmployee(int value){
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_410");
    	case 1:
    		return TextResource.localize("KMK004_411");
    	default: 
    		return null;
    	}
    }
	
	public static String getInclude(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_406");
    	case 1:
    		return TextResource.localize("KMK004_407");
    	default: 
    		return null;
    	}
    }
	
	public static String getLegal(Integer value) {
		if(value== null) return null;
		switch (value) {
		case 0:
			return TextResource.localize("KMK004_412");
		case 1:
			return TextResource.localize("KMK004_413");
		default:
			return null;
		}
	}
	
	public static String getShortageTime(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_76");
    	case 1:
    		return TextResource.localize("KMK004_77");
    	default: 
    		return null;
    	}
    }
	
	public static String getSettle(Integer value) {
		if(value== null) return null;
		switch (value){
    	case 0:
    		return TextResource.localize("KMK004_272");
    	case 1:
    		return TextResource.localize("KMK004_273");
    	default: 
    		return null;
    	}
	}
	
	public static String getWeeklySurcharge(Integer value){
		if(value== null) return null;
    	switch (value){
    	case 0:
    		return TextResource.localize("KMK004_404");
    	case 1:
    		return TextResource.localize("KMK004_405");
    	default: 
    		return null;
    	}
    }
}
