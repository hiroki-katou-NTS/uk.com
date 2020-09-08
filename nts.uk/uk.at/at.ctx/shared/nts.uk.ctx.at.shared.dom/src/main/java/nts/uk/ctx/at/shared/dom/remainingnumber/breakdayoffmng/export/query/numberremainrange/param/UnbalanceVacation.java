package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;

/**
 * @author ThanhNX
 *
 *         休出の未相殺
 */
@Getter
public class UnbalanceVacation extends LeaveOccurrDetail {

	/**
	 * 1日相当時間
	 */
	private AttendanceTime timeOneDay;

	/**
	 * 半日相当時間
	 */
	private AttendanceTime timeHalfDay;

	public UnbalanceVacation(GeneralDate deadline, DigestionAtr digestionCate, Optional<GeneralDate> extinctionDate,
			AccumulationAbsenceDetail detail, AttendanceTime timeOneDay, AttendanceTime timeHalfDay) {
		super(detail, deadline, digestionCate, extinctionDate);
		this.timeOneDay = timeOneDay;
		this.timeHalfDay = timeHalfDay;
	}

}
