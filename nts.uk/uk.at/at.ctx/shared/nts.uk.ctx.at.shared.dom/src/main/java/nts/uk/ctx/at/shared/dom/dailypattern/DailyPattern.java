package nts.uk.ctx.at.shared.dom.dailypattern;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

// TODO: Auto-generated Javadoc
/**
 * Gets the calendar setting.
 *
 * @return the calendar setting
 */
@Getter
public class DailyPattern extends AggregateRoot{
	
	/** The company id. */
	private String companyId;
	
	/** The pattern code. */
	private String patternCode;
	
	/** The pattern name. */
	private String patternName;
	
	/** The work type codes. */
	private List<String> workTypeCodes;
	
	/** The work house codes. */
	private List<String> workHouseCodes;
	
	/** The calendar setting. */
	private DailyPatternSetting calendarSetting;

	/**
	 * Instantiates a new pattern calendar.
	 */
	public DailyPattern(){
		
	}
	
	/**
	 * Instantiates a new pattern calendar.
	 *
	 * @param memento the memento
	 */
	public DailyPattern(DailyPatternGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.patternCode = memento.getPatternCode();
		this.patternName = memento.getPatternName();
		this.workTypeCodes = memento.getWorkTypeCodes();
		this.workHouseCodes = memento.getWorkHouseCodes();
		this.calendarSetting = memento.getCalendarSetting();
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
		memento.setWorkTypeCodes(this.workTypeCodes);
		memento.setWorkHouseCodes(this.workHouseCodes);
		memento.setCalendarSetting(new DailyPatternSetting(new DailyPatternSettingGetMemento() {
			
			@Override
			public NumberDayDaily getNumberDayCalendar() {
				return new NumberDayDaily(1);
			}
		}));
	}
	
}
