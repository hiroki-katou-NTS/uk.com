package nts.uk.ctx.at.record.dom.daily.dailyperformance.classification;

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
    		list.add(new EnumCodeName(String.valueOf(ReasonGoOut.values()[i].value), ReasonGoOut.values()[i].description));
    	}
    	return list;
    }
    
    public static List<EnumCodeName> getDowork(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< DoWork.values().length; i++){ 
    		list.add(new EnumCodeName(String.valueOf(DoWork.values()[i].value), DoWork.values()[i].description));
    	}
    	return list;
    }
    
    public static List<EnumCodeName> getCalcHours(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< AutomaticCalcAfterHours.values().length; i++){
    		list.add(new EnumCodeName(String.valueOf(AutomaticCalcAfterHours.values()[i].value), AutomaticCalcAfterHours.values()[i].description));
    	}
    	return list;
    }
    
    public static List<EnumCodeName> getCalcCompact(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< AutomaticCalcCompact.values().length; i++){
    		list.add(new EnumCodeName(String.valueOf(AutomaticCalcCompact.values()[i].value), AutomaticCalcCompact.values()[i].description));
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
