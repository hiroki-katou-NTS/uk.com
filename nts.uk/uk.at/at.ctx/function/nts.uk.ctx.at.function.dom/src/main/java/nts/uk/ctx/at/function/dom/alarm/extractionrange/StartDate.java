package nts.uk.ctx.at.function.dom.alarm.extractionrange;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ClosingDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.NumberOfDays;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.SpecifyStartDate;

/**
 * @author thanhpv
 * 開始日
 */

@Getter
@Setter
public class StartDate {

	/**前・先区分*/
	private SpecifyStartDate specifyStartDate;
	
	/**Closing Date*/
	// 締め日指定
	private ClosingDate closingDate;
	
	/**Specify number of days*/
	// 日数指定
	private NumberOfDays numberOfDays;

	public StartDate(SpecifyStartDate specifyStartDate, ClosingDate closingDate, NumberOfDays numberOfDays) {
		super();
		this.specifyStartDate = specifyStartDate;
		this.closingDate = closingDate;
		this.numberOfDays = numberOfDays;
	}	
}
