package nts.uk.ctx.at.shared.dom.workrule.closure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.calendar.Day;

/**
 * 締め開始日締め日
 * @author hayata_maekawa
 *
 */

@AllArgsConstructor
@Getter
public class ClosureStartEndOutput {

	//締め開始日
	private Day start;
	//締め日
	private Day closure;
	
}
