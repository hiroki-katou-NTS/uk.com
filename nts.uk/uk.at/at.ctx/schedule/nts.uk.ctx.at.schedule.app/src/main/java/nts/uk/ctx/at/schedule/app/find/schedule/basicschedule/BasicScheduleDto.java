package nts.uk.ctx.at.schedule.app.find.schedule.basicschedule;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class BasicScheduleDto {
	private String sId;
	private GeneralDate date;
	private String workTypeCd;
	private String siftCd;

	public static BasicScheduleDto fromDomain(BasicSchedule domain) {
		return new BasicScheduleDto(domain.getSId(), domain.getDate(), domain.getWorkTypeCd().v(),
				domain.getSiftCd().v());
	}
}
