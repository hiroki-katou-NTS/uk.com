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
	private int timeCloseDate;

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

	public CloseDate(int timeCloseDate, int closeDateBaseMonth, int closeDateBaseYear, int closeDateRefeDate) {
		this.timeCloseDate = timeCloseDate;
		this.closeDateBaseMonth = Optional
				.ofNullable(EnumAdaptor.valueOf(closeDateBaseMonth, SocialInsuColleMonth.class));
		this.closeDateBaseYear = Optional
				.ofNullable(EnumAdaptor.valueOf(closeDateBaseYear, YearSelectClassification.class));
		this.closeDateRefeDate = Optional
				.ofNullable(EnumAdaptor.valueOf(closeDateRefeDate, DateSelectClassification.class));
	}

}
