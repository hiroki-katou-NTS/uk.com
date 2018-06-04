package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
public class ClosureEmploymentDto {
	/** The company id. */
	// 会社ID
	private String companyId;

	/** Employemeny code */
	// 雇用コード
	private String employmentCD;

	/** The closure id. */
	// 締めＩＤ
	private int closureId;

	public static ClosureEmploymentDto convertToDto(ClosureEmployment closureEmployment) {
		return new ClosureEmploymentDto(closureEmployment.getCompanyId(), closureEmployment.getEmploymentCD(),
				closureEmployment.getClosureId());
	}
}
