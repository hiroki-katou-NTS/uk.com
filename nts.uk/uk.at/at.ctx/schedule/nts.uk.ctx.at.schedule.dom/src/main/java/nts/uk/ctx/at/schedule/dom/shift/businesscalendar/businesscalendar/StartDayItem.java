package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.businesscalendar;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 会社開始曜日
 *
 */
@Getter
public class StartDayItem extends AggregateRoot {
	private String companyId;
	private StartDay startDay;

	/**
	 * 
	 * @param companyId
	 * @param startDay
	 */
	public StartDayItem(String companyId, StartDay startDay) {
		this.companyId = companyId;
		this.startDay = startDay;
	}

	/**
	 * 
	 * @param companyId
	 * @param startDay
	 * @return
	 */
	public static StartDayItem createFromJavaType(String companyId, int startDay) {
		return new StartDayItem(companyId, EnumAdaptor.valueOf(startDay, StartDay.class));
	}

}
