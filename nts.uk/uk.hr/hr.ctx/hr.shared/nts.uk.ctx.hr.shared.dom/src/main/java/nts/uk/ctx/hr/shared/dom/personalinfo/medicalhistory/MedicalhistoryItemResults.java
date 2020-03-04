package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author THanhPV 受診履歴
 */
@Getter
@AllArgsConstructor
public class MedicalhistoryItemResults {

	/** 会社ID */
	private String companyID;

	/** 社員ID */
	private String employeeID;
	
	/** 開始日 */
	private GeneralDate startDate;

	/** 終了日 */
	private GeneralDate endDate;
	
	/** 総合結果  */
	private String evaluation;

}
