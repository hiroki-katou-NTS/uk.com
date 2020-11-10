package nts.uk.screen.at.app.ktgwidget.ktg004;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanhpv
 * @name 対象社員の残数情報
 */
@Getter
@NoArgsConstructor
@Setter
public class RemainingNumberInforDto {

	//積立年休残日数
	private double numberAccumulatedAnnualLeave;
	
	//代休残数
	private RemainingDaysAndTimeDto numberOfSubstituteHoliday;
	
	//年休残数
	private RemainingDaysAndTimeDto numberOfAnnualLeaveRemain;
	
	//振休残日数
	private double remainingHolidays;
	
	//子の看護残数
	private RemainingDaysAndTimeDto nursingRemainingNumberOfChildren;
	
	//介護残数
	private RemainingDaysAndTimeDto longTermCareRemainingNumber;
	
	//特休残数情報
	private List<SpecialHolidaysRemainingDto> specialHolidaysRemainings;
}
