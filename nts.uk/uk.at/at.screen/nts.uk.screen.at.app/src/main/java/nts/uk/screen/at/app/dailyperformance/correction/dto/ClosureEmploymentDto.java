package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosureEmploymentDto {
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** Employemeny code */
	// 雇用コード
	private String employmentCD;
	
	/** The closure id. */
	// 締めＩＤ
	private Integer closureId;
}
