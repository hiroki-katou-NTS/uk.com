package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.validate.CompositeObjectValidationService;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Days;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Month;

/**
 * @author thanhpv
 * 終了日
 */

@Getter
@Setter
@NoArgsConstructor
public class EndDate extends DomainObject {

	/** 終了日の指定方法 */
	private EndSpecify endSpecify;

	/** 締め日指定の月数 */
	private Optional<Month> endMonth = Optional.empty();

	/** 日数指定の日 */
	private Optional<Days> endDays = Optional.empty(); // Number of days specified

	public EndDate(int endSpecify) {
		this.endSpecify = EnumAdaptor.valueOf(endSpecify, EndSpecify.class);
	}

	public void setEndDay(PreviousClassification monthPrevious, int day, boolean makeToDay) {
		this.endDays = Optional.of(new Days(monthPrevious, day, makeToDay));
	}

	public void setEndMonth(PreviousClassification monthPrevious, int month, boolean currentMonth) {
		this.endMonth = Optional.of(new Month(monthPrevious, month, currentMonth));
	}

    @Override
    public void validate() {
    	CompositeObjectValidationService.validate(this);
    	if (this.endSpecify == EndSpecify.MONTH && !this.endMonth.isPresent()) {
    		throw new RuntimeException("End month is required!");
    	}
    	if (this.endSpecify == EndSpecify.DAYS && !this.endDays.isPresent()) {
    		throw new RuntimeException("End days are required!");
    	}
    }
}
