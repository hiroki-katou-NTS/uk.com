package nts.uk.screen.at.app.dailyperformance.correction.flex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcFlexDto {
     private String employeeId;
     private GeneralDate date;
     private int closureId;
     private String histId;
     private boolean flag;
     
     public static CalcFlexDto createCalcFlexDto(String employeeId, GeneralDate date){
    	 return new CalcFlexDto(employeeId, date);
     }

	public CalcFlexDto(String employeeId, GeneralDate date) {
		this.employeeId = employeeId;
		this.date = date;
	}
     
     
}
