package nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author THanhPV 人事評価結果
 */
@Getter
@AllArgsConstructor
public class PersonnelAssessmentResults {

	/** 会社ID */
	private String companyID;

	/** 社員ID */
	private String employeeID;
	
	/** 開始日 */
	private GeneralDate startDate;

	/** 終了日 */
	private GeneralDate endDate;
	
	/** 総合評価  */
	private String evaluation;

}
