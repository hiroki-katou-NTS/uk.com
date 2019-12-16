package nts.uk.ctx.hr.develop.dom.databeforereflecting.retirementinformation;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.RetirementCategory;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.Status;

/**
 * 定年退職者情報
 * 
 * @author sonnlb
 * 
 */
@Builder
@Data
public class RetirementInformation {
	// 履歴ID
	private String historyID;
	// 希望勤務コース_CD
	private String selectRetirementCourseCd;
	// 退職日
	private GeneralDateTime retirementDate;
	// 公開日
	private GeneralDateTime releaseDate;
	// 社員ID
	private String sId;
	// 継続雇用フラグ
	private Retension extendEmploymentFlg;
	// 社員名
	private String employeeName;
	// 社員コード
	private String scd;
	// 会社ID
	private String companyId;
	// 業務名称
	private String workName;
	// ステータス
	private Status status;
	// 希望勤務コース_ID
	private Long selectRetirementCourseId;
	// 保留フラグ
	private Retension pendingFlag;
	// 会社コード
	private String companyCode;
	// 希望勤務コース_名称
	private String selectRetirementCourseName;
	// 契約コード
	private String contractCode;
	// 業務ID
	private Integer workId;
	// 入力日
	private GeneralDate inputDate;
	// 退職区分
	private RetirementCategory retirementCategory;
	// 届出区分
	private Retension notificationCategory;
	// 退職理由区分1
	private String resignmentReasonDivision1;
}
