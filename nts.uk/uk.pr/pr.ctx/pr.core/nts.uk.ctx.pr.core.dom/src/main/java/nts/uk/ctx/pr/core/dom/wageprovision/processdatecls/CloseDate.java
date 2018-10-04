package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 勤怠締め年月日
 */
@AllArgsConstructor
@Getter
public class CloseDate extends DomainObject {

	/**
	 * 勤怠締め日
	 */
	private TimeCloseDateClassification timeCloseDate;

	/**
	 * 基準月
	 */
	private Optional<SocialInsuColleMonth> closeDateBaseMonth;

	/**
	 * 基準年
	 */
	private Optional<YearSelectClassification> closeDateBaseYear;

	/**
	 * 基準日
	 */
	private Optional<DateSelectClassification> closeDateRefeDate;

	public CloseDate(int timeCloseDate, Integer closeDateBaseMonth, Integer closeDateBaseYear,
			Integer closeDateRefeDate) {
		this.timeCloseDate = EnumAdaptor.valueOf(timeCloseDate,TimeCloseDateClassification.class);


		if (closeDateBaseMonth == null)
			this.closeDateBaseMonth = Optional.empty();
		else
			this.closeDateBaseMonth = Optional.of(EnumAdaptor.valueOf(closeDateBaseMonth, SocialInsuColleMonth.class));

		if (closeDateBaseYear == null)
			this.closeDateBaseYear = Optional.empty();
		else
			this.closeDateBaseYear = Optional
					.of(EnumAdaptor.valueOf(closeDateBaseYear, YearSelectClassification.class));

		if (closeDateRefeDate == null)
			this.closeDateRefeDate = Optional.empty();
		else
			this.closeDateRefeDate = Optional
					.of(EnumAdaptor.valueOf(closeDateRefeDate, DateSelectClassification.class));
	}

}
