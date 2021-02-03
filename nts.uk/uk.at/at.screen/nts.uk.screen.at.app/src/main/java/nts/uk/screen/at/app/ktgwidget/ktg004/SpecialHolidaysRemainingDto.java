package nts.uk.screen.at.app.ktgwidget.ktg004;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanhpv
 * @name 対象社員の特休残数情報
 */
@AllArgsConstructor
@Getter
public class SpecialHolidaysRemainingDto {

	//特休残数
	private RemainingDaysAndTimeDto specialResidualNumber = new RemainingDaysAndTimeDto();
	
	//特別休暇コード
	private Integer code;
	
	//特別休暇名称
	private String name;
}
