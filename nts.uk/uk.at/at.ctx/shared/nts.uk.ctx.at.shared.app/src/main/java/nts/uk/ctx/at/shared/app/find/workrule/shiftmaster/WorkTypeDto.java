package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author anhdt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeDto {
	private String code;
	private String name;
	
	public WorkTypeDto(WorkType domain) {
		this.code = domain.getWorkTypeCode().v();
		this.name = domain.getAbbreviationName() != null ? domain.getAbbreviationName().v() : "";
	}
} 
