package nts.uk.ctx.at.shared.dom.dailypattern;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

// TODO: Auto-generated Javadoc
/**
 * The Class DailyPatternVal.
 */

/**
 * Gets the days.
 *
 * @return the days
 */
@Getter
public class DailyPatternVal extends DomainObject{
	
	/** The cid. */
	private String cid;
	
    /** The pattern cd. */
    private String patternCd;
    
    /** The disp order. */
    private Integer dispOrder;
    
    /** The work type set cd. */
    private String workTypeSetCd;
    
    /** The working hours cd. */
    private String workingHoursCd;
    
    /** The days. */
    private Integer days;

	/**
	 * Instantiates a new daily pattern val.
	 */
	public DailyPatternVal() {
	}

	/**
	 * Instantiates a new daily pattern val.
	 *
	 * @param cid the cid
	 * @param patternCd the pattern cd
	 * @param dispOrder the disp order
	 * @param workTypeSetCd the work type set cd
	 * @param workingHoursCd the working hours cd
	 * @param days the days
	 */
	public DailyPatternVal(String cid, String patternCd, Integer dispOrder, String workTypeSetCd, String workingHoursCd,
			Integer days) {
		this.cid = cid;
		this.patternCd = patternCd;
		this.dispOrder = dispOrder;
		this.workTypeSetCd = workTypeSetCd;
		this.workingHoursCd = workingHoursCd;
		this.days = days;
	}
    
	
	/**
	 * Instantiates a new daily pattern val.
	 *
	 * @param memento the memento
	 */
	public DailyPatternVal(DailyPatternValGetMemento memento) {
		this.cid = memento.getCompanyId();
		this.patternCd = memento.getPatternCode();
		this.dispOrder = memento.getDispOrder();
		this.workTypeSetCd = memento.getWorkTypeSetCd();
		this.workingHoursCd = memento.getWorkingHoursCd();
		this.days = memento.getDays();
	}
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DailyPatternValSetMemento memento) {
		memento.setCompanyId(this.cid);
		memento.setPatternCode(this.patternCd);
		memento.setDispOrder(this.dispOrder);
		memento.setWorkTypeCodes(this.workTypeSetCd);
		memento.setWorkHouseCodes(this.workingHoursCd);
		memento.setDays(this.days);
	}
    
}
