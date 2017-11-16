package nts.uk.ctx.at.shared.dom.worktime.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
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
	private String companyID;
	/**
	 * 職場ID
	 */
	private String workplaceID;
	/**
	 * 利用就業時間帯
	 */
	private String workTimeID;
	
	public static WorkTimeWorkplace createFromJavaType(String companyID, String workplaceID, String workTimeID) {
		return new WorkTimeWorkplace(companyID,workplaceID,workTimeID);
	}
}