package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClosureCdNameDto {
	/** The closure id. */
	// 締めＩＤ
	private int closureIdMain;

	/** The closure name. */
	// 名称: 締め名称
	private String closureName;
	
	
	public static ClosureCdNameDto fromDomain(ClosureHistory domain){
		return new  ClosureCdNameDto(
					domain.getClosureId().value,
					domain.getClosureName().v()
				);
	}
}
