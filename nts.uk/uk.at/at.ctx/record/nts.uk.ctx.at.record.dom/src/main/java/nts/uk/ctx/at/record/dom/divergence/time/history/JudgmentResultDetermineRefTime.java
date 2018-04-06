package nts.uk.ctx.at.record.dom.divergence.time.history;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.divergence.time.JudgmentResult;


/**
 * The Class JudgmentResultDetermineRefTime.
 */
@Getter
@Setter
@AllArgsConstructor
public class JudgmentResultDetermineRefTime {

	/** The determine reaf time. */
	private DetermineReferenceTime determineReafTime;

	/** The judgment result. */
	private JudgmentResult judgmentResult;
	
	/**
	 * Instantiates a new judgment result determine ref time.
	 */
	public JudgmentResultDetermineRefTime(){
		
	}
}
