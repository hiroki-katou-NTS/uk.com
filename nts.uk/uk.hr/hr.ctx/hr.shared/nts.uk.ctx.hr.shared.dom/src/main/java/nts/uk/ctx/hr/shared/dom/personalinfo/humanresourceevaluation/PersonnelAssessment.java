package nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author anhdt 人事評価
 */
@Getter
@Builder
public class PersonnelAssessment extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 履歴ID
	 */
	private String historyID;

	/**
	 * 社員ID
	 */
	private String employeeID;

	/**
	 * 総合評価
	 */
	private String comprehensiveEvaluation;

}
