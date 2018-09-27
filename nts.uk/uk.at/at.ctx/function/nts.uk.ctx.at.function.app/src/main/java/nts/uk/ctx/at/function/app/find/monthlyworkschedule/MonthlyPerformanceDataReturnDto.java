package nts.uk.ctx.at.function.app.find.monthlyworkschedule;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author NWS-HoangNM
 */
@Data
public class MonthlyPerformanceDataReturnDto {
	
	/**
	 * フォーマット種類 - Format Type
	 *  権限 - Authority: 0
	 *  勤務種別 - Work Type: 1
	 *  
	 *  Unknown- -1
	 */
	private String settingUnitType;
	
	/**
	 * list of info items with code and name
	 */
	private List<MonthlyDataInforReturnDto> listItems;
}
