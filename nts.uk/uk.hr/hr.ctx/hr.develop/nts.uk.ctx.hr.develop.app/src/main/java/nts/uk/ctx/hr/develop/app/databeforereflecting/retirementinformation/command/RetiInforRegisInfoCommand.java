package nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.command;

import java.math.BigInteger;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class RetiInforRegisInfoCommand {

	// 個人ID
	private String pId;
	// 社員ID
	private String sId;
	// 社員コード
	private String scd;
	// 個人名
	private String PersonName;
	// ビジネスネーム
	private String businessName;
	// ビジネスネームカナ
	private String businessnameKana;
	// 誕生日
	private String birthday;
	// 入社日
	private String dateJoinComp;
	// 部門ID
	private String departmentId;
	// 部門コード
	private String departmentCode;
	// 部門名
	private String departmentName;
	// 職位ID
	private String jobTitleId;
	// 職位コード
	private String jobTitleCd;
	// 職位名
	private String jobTitleName;
	// 雇用コード
	private String employmentCode;
	// 雇用名
	private String employmentName;
	// 年齢
	private Integer age;
	// 退職日
	private String retirementDate;
	// 公開日
	private String releaseDate;
	// 人事評価1
	private String hrEvaluation1;
	// 人事評価2
	private String hrEvaluation2;
	// 人事評価3
	private String hrEvaluation3;
	// 健康評価1
	private String healthStatus1;
	// 健康評価2
	private String healthStatus2;
	// 健康評価3
	private String healthStatus3;
	// ストレス評価1
	private String stressStatus1;
	// ストレス評価2
	private String stressStatus2;
	// ストレス評価3
	private String stressStatus3;
	// 保留フラグ
	private int pendingFlag;
	// ステータス
	private int status;
	// 反映先_履歴ID
	private String dst_HistId;
	// 入力日
	public String inputDate;
	// 履歴ID
	private String historyId;

	// 希望勤務コース_CD
	private String desiredWorkingCourseCd;
	// 継続雇用フラグ
	private int extendEmploymentFlg;
	// 会社ID
	private String companyId;
	// 業務名称
	private String workName;
	// 希望勤務コース_ID
	private BigInteger desiredWorkingCourseId;
	// 会社コード
	private String companyCode;
	// 希望勤務コース_名称
	private String desiredWorkingCourseName;
	// 契約コード
	private String contractCode;
	// 業務ID
	private Integer workId;
	// 退職区分
	private Integer retirementCategory;
	// 届出区分
	private int notificationCategory;
	// 退職理由区分1_ID
	private BigInteger retirementReasonCtgID1;
	// 退職理由区分1_CD
	private String retirementReasonCtgCd1;
	// 退職理由区分1_名称
	private String retirementReasonCtgName1;
	
	public GeneralDate getInputDate() {
		return convertToGenDate(this.inputDate);
	}

	public GeneralDate getReleaseDate() {
		return convertToGenDate(this.releaseDate);
	}

	public GeneralDate getRetirementDate() {
		return convertToGenDate(this.retirementDate);
	}

	private GeneralDate convertToGenDate(String date) {
		return GeneralDate.fromString(date, "yyyy/MM/dd");
	}

}
