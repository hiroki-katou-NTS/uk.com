package nts.uk.ctx.at.schedule.dom.adapter.employmentstatus;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 在職情報
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Getter
public class EmploymentInfoImported {
	
	/** 年月日 */
	private GeneralDate standardDate;

	/** 在職状態 */
	private int employmentState;

	/** 休職休業枠NO */
	private Optional<Integer> tempAbsenceFrNo;
}
