package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
/**
 * @author Hieult
 */
/** 時間年休一日の時間 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeAnnualLeaveTimeDay {
	
	/* 一日の時間年休時間参照場所 */
	private DayTimeAnnualLeave timeOfDayReference;
	
	/* 全社一律の時間 : 労働契約時間 */
	private Optional<LaborContractTime>  uniformTime;
	
	/* 契約時間丸め */
	private Optional<ContractTimeRound> contractTimeRound;
	
}
