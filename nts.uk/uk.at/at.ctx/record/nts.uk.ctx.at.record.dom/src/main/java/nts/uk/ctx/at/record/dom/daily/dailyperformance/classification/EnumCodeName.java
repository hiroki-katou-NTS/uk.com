package nts.uk.ctx.at.record.dom.daily.dailyperformance.classification;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.shr.com.i18n.TextResource;

@Data
@AllArgsConstructor
public class EnumCodeName {
    private Integer code;
    private String name;
    public static List<EnumCodeName> getReasonGoOut(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< ReasonGoOut.values().length; i++){
    		list.add(new EnumCodeName(ReasonGoOut.values()[i].value, ReasonGoOut.values()[i].description));
    	}
    	return list;
    }

    public static List<EnumCodeName> getDowork(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< DoWork.values().length; i++){
    		list.add(new EnumCodeName(DoWork.values()[i].value, DoWork.values()[i].description));
    	}
    	return list;
    }

    public static List<EnumCodeName> getCalcHours(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< AutomaticCalcAfterHours.values().length; i++){
    		list.add(new EnumCodeName(AutomaticCalcAfterHours.values()[i].value, AutomaticCalcAfterHours.values()[i].description));
    	}
    	return list;
    }

    public static List<EnumCodeName> getCalcCompact(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< AutomaticCalcCompact.values().length; i++){
    		list.add(new EnumCodeName(AutomaticCalcCompact.values()[i].value, AutomaticCalcCompact.values()[i].description));
    	}
    	return list;
    }

    public static List<EnumCodeName> getComboTimeLimit(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< TimeLimitUpperLimitSetting.values().length; i++){
    		list.add(new EnumCodeName(TimeLimitUpperLimitSetting.values()[i].value, TimeLimitUpperLimitSetting.values()[i].description));
    	}
    	return list;
    }

    public static List<EnumCodeName> getNursingLicenseCls(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< LicenseClassification.values().length; i++){
    		list.add(new EnumCodeName(LicenseClassification.values()[i].value, TextResource.localize(LicenseClassification.values()[i].nameId)));
    	}
    	return list;
    }
}
