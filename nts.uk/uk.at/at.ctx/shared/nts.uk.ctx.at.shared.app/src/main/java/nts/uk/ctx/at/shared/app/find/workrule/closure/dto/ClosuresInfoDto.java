package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

@AllArgsConstructor
@Getter
public class ClosuresInfoDto {
	/** The closure id. */
	// 締めＩＤ
	private int closureId;

	/** The closure name. */
	private int useAtr;
	
	/** The closure date. */
	private int closuresMonth;
	
	public static ClosuresInfoDto fromDomain(Closure domain) {
		return new ClosuresInfoDto(domain.getClosureId().value, domain.getUseClassification().value, domain.getClosureMonth().getProcessingYm().v());
	}
}
