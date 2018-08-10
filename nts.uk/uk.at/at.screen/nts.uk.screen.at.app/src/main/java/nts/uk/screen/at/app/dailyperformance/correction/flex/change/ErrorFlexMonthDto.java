package nts.uk.screen.at.app.dailyperformance.correction.flex.change;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorFlexMonthDto {

	/**
	 * フレックス: フレックスエラー
	 */
	public Integer flex;
	/**
	 * 年休: 年休エラー
	 */
	public Integer annualHoliday;
	/**
	 * 積立年休: 積立年休エラー
	 */
	public Integer yearlyReserved;
	
	public String messageError(){
		if(flex != null){
			return "";
		}else
		return "";
	}
}
