package nts.uk.ctx.at.shared.dom.worktime.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

import java.util.List;

/**
 * 職場割り当て就業時間帯
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
public class WorkTimeWorkplace extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private final String companyID;

	/**
	 * 職場ID
	 */
	private final String workplaceID;

	/**
	 * 利用就業時間帯
	 */
	private List<WorkTimeCode> workTimeCodes;
	
	public static WorkTimeWorkplace create(String companyID, String workplaceID, List<WorkTimeCode> workTimeCodes) {
		return new WorkTimeWorkplace(companyID,workplaceID,workTimeCodes);
	}
}