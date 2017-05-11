package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DetailEmployee {
      private String personID;
      private String personName;
      List<DataRowComparingSalaryBonus> lstData;
      List<DataRowComparingSalaryBonusDto> lstDataDto;
      
      public static  List<DataRowComparingSalaryBonusDto> convertDataRowComparingSalaryBonusDto( List<DataRowComparingSalaryBonus> lstData){
    	  List<DataRowComparingSalaryBonusDto> lstDto= lstData.stream().map(c ->{
    		
    		return new DataRowComparingSalaryBonusDto(c.getItemName(), String.valueOf(c.getMonth1()),
    				String.valueOf(c.getMonth2()), String.valueOf(c.getDifferentSalary()),
    				c.getRegistrationStatus1(), c.getRegistrationStatus2(),
    				c.getReason(), c.getConfirmed());
    		  
    	  }).collect(Collectors.toList());
    	  return lstDto;
    	
      }
}
