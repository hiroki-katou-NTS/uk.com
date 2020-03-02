package nts.uk.ctx.hr.shared.dom.databeforereflecting.retirementinformation;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.OnHoldFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.RequestFlag;
import nts.uk.ctx.hr.shared.dom.personalinfo.retirementinfo.RetirementCategory;

//個人情報反映前データ_定年退職者情報
@Data
@Builder
public class RetirementInformation_New extends AggregateRoot {
	// 履歴ID
	private String historyId;
	// 希望勤務コース_CD
	private String desiredWorkingCourseCd;
	// 退職日
	private GeneralDateTime retirementDate;
	// 公開日
	private GeneralDateTime releaseDate;
	// 社員ID
	private String sId;
	// 継続雇用フラグ
	private ResignmentDivision extendEmploymentFlg;
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
	// 反映先_履歴ID
	private String dst_HistId;
	// 希望勤務コース_ID
	private BigInteger desiredWorkingCourseId;
	// 保留フラグ
	private OnHoldFlag pendingFlag;
	// 会社コード
	private String companyCode;
	// 希望勤務コース_名称
	private String desiredWorkingCourseName;
	// 契約コード
	private String contractCode;
	// 業務ID
	private Integer workId;
	// 個人名
	private String PersonName;
	// 入力日
	private GeneralDate inputDate;
	// 退職区分
	private RetirementCategory retirementCategory;
	// 届出区分
	private RequestFlag notificationCategory;
	// 退職理由区分1_ID
	private BigInteger retirementReasonCtgID1;
	// 退職理由区分1_CD
	private String retirementReasonCtgCd1;
	// 退職理由区分1_名称
	private String retirementReasonCtgName1;
}
