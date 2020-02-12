package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Builder
@Data
public class RetirementInformationDto {
	// 個人ID
	private String pId;
	// 社員ID
	private String employeeId;

	// 社員コード
	private String employeeCode;

	// ビジネスネーム
	private String businessName;

	// ビジネスネームカナ
	private String businessNameKana;

	private GeneralDate birthday;

	private GeneralDate dateJoinComp;

	// 部門ID

	private String departmentId;

	// 部門コード

	private String departmentCode;

	// 部門名称

	private String departmentName;

	// 職位ID
	private String positionId;

	// 職位コード
	private String positionCode;

	// 職位名称
	private String positionName;

	// 雇用コード
	private String employmentCode;

	// 雇用名称
	private String employmentName;

}
