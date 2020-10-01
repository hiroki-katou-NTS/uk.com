package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 前日と翌日の勤務
 * @author daiki_ichioka
 *
 */
@Getter
@AllArgsConstructor
public class PreviousAndNextDaily {

	/** 前日の勤務種類 */
	private WorkType previousWorkType;
	
	/** 翌日の勤務種類 */
	private WorkType nextWorkType;
	
	/** 前日の勤務情報 */
	private Optional<WorkInformation> previousInfo;
	
	/** 翌日の勤務情報 */
	private Optional<WorkInformation> nextInfo;
}
