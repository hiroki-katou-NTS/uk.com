package nts.uk.ctx.at.shared.dom.workrule.closure;

import nts.uk.shr.com.time.calendar.Day;

/**
 * 締め開始日締め日OUTPUT
 * @author hayata_maekawa
 *
 */
public class ClosureStartEndOutput {

	//締め開始日
	public Day start;
	//締め日
	public Day closure;
	
	
	public ClosureStartEndOutput(Day start, Day Closure){
		this.start = start;
		this.closure = Closure;
	}
}
