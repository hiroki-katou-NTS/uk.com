package nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReasonTimeChangeDto {
	
	//時刻変更手段
	private Integer timeChangeMeans;
	
	//打刻方法
	private Integer engravingMethod;

}
