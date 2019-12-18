package nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 定点退職予定者
 * 
 * @author sonnlb
 *
 */
@Data
@Builder
public class PlannedRetirementDto {
	// 個人ID
	private String pId;
	// 社員ID
	private String sId;
	// 社員コード
	private String scd;
	// ビジネスネーム
	private String businessName;
	// ビジネスネームカナ
	private String businessnameKana;
	// 誕生日
	private GeneralDate birthday;
	// 入社日
	private GeneralDate dateJoinComp;
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
	private GeneralDate retirementDate;
	// 公開日
	private GeneralDate releaseDate;
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
}
