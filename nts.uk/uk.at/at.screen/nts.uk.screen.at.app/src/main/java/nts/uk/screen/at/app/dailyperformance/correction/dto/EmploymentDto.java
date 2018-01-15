package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDto {
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
}
