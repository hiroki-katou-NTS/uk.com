package nts.uk.screen.at.app.dailyperformance.correction.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosureSidDto {

	private String sid;
	
	private Closure closure;
}
