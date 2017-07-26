package nts.uk.ctx.at.shared.dom.dailypattern;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

// TODO: Auto-generated Javadoc
/**
 * The Class DailyPattern.
 */

/**
 * Gets the days.
 *
 * @return the days
 */
@Getter
public class DailyPattern extends AggregateRoot{
	
	/** The company id. */
	private String companyId;
	
	/** The pattern code. */
	private String patternCode;
	
	/** The pattern name. */
	private String patternName;
	
	private List<DailyPatternVal> listDailyPatternVal;

	/**
	 * Instantiates a new daily pattern.
	 */
	public DailyPattern(){
		
	}
	
	/**
	 * Instantiates a new daily pattern.
	 *
	 * @param memento the memento
	 */
	public DailyPattern(DailyPatternGetMemento memento ) {
		this.companyId = memento.getCompanyId();
		this.patternCode = memento.getPatternCode();
		this.patternName = memento.getPatternName();
		this.listDailyPatternVal = memento.getListDailyPatternVal();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DailyPatternSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setPatternCode(this.patternCode);
		memento.setPatternName(this.patternName);
		memento.setListDailyPatternVal(this.listDailyPatternVal);
	}
	
}
