package nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 定点退職予定者
 * 
 * @author sonnlb
 *
 */
@Data
@Builder
public class PlannedRetirement {
	// 個人ID
	public String pId;
	// 社員ID
	public String sId;
	// 社員コード
	public String scd;
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
	// 退職日
	public GeneralDateTime retirementDate;
	// 公開日
	public GeneralDateTime releaseDate;
	// 人事評価1
	// 人事評価n
	// 健康評価1
	// 健康評価n
	// ストレス評価1
	// ストレス評価n
}
