package nts.uk.ctx.hr.shared.dom.employment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmploymentInfoImport {

	/** The company id. */
	// 会社ID.
	private String companyId;

	/** The employment code. */
	// 雇用コード.
	private String employmentCode;

	/** The employment name. */
	// 雇用名称.
	private String employmentName;

	/** The emp external code. */
	// 雇用外部コード.
	private String empExternalCode;

	/** The memo. */
	// メモ.
	private String memo;
	// グループ会社共通マスタID
	public String empCommonMasterId;
	// グループ会社共通マスタ項目ID
	public String empCommonMasterItemId;
}
