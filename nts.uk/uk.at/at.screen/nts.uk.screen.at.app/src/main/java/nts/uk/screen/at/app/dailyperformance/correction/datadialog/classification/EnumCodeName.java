package nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;

@Data
@AllArgsConstructor
public class EnumCodeName {
    private String code;
    private String name;
    public static List<EnumCodeName> getReasonGoOut(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< ReasonGoOut.values().length; i++){
    		list.add(new EnumCodeName(String.valueOf(ReasonGoOut.values()[i].code), ReasonGoOut.values()[i].name));
    	}
    	return list;
    }
    
    public static List<EnumCodeName> getDowork(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< DoWork.values().length; i++){
    		list.add(new EnumCodeName(String.valueOf(DoWork.values()[i].code), DoWork.values()[i].name));
    	}
    	return list;
    }
    
    public static List<EnumCodeName> getCalcHours(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< AutomaticCalcAfterHours.values().length; i++){
    		list.add(new EnumCodeName(String.valueOf(AutomaticCalcAfterHours.values()[i].code), AutomaticCalcAfterHours.values()[i].name));
    	}
    	return list;
    }
    
    public static List<EnumCodeName> getCalcCompact(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< AutomaticCalcCompact.values().length; i++){
    		list.add(new EnumCodeName(String.valueOf(AutomaticCalcCompact.values()[i].code), AutomaticCalcCompact.values()[i].name));
    	}
    	return list;
    }
    
    public static List<EnumCodeName> getComboTimeLimit(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< TimeLimitUpperLimitSetting.values().length; i++){
    		list.add(new EnumCodeName(String.valueOf(TimeLimitUpperLimitSetting.values()[i].value), TimeLimitUpperLimitSetting.values()[i].description));
    	}
    	return list;
    }
}
