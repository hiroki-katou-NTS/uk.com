package nts.uk.ctx.at.shared.app.find.pattern.monthly.setting;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class HistoryDto.
 */
@Getter
@Setter
public class HistoryDto {
	
	/** The history id. */
	public String historyId;
	
	/** The period. */
	public Period  period;
	
	/**
	 * Instantiates a new history dto.
	 *
	 * @param historyId the history id
	 * @param period the period
	 */
	public HistoryDto(String historyId, Period  period){
		this.historyId = historyId;
		this.period = period;
	}
}
