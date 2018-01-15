package nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnumCodeName {
    private int code;
    private String name;
    public static List<EnumCodeName> getReasonGoOut(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< ReasonGoOut.values().length; i++){
    		list.add(new EnumCodeName(ReasonGoOut.values()[i].code, ReasonGoOut.values()[i].name));
    	}
    	return list;
    }
    
    public static List<EnumCodeName> getDowork(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< DoWork.values().length; i++){
    		list.add(new EnumCodeName(DoWork.values()[i].code, DoWork.values()[i].name));
    	}
    	return list;
    }
    
    public static List<EnumCodeName> getCalcHours(){
    	List<EnumCodeName> list = new ArrayList<>();
    	for(int i =0; i< AutomaticCalcAfterHours.values().length; i++){
    		list.add(new EnumCodeName(AutomaticCalcAfterHours.values()[i].code, AutomaticCalcAfterHours.values()[i].name));
    	}
    	return list;
    }
}
