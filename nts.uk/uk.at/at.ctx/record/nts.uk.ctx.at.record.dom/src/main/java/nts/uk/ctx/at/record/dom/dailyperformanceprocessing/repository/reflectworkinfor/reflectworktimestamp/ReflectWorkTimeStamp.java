package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectworkinfor.reflectworktimestamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * 打刻の就業時間帯を反映する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectWorkTimeStamp {
	public void reflectStamp(WorkTimeCode worktimeCode,IntegrationOfDaily integrationOfDaily) {
		//就業時間帯の変更可能か確認する
		Optional<EditStateOfDailyAttd> editState = integrationOfDaily.getEditState().stream()
				.filter(c->c.getAttendanceItemId() == 29).findFirst();
		if(!editState.isPresent()) {
			//就業時間帯を反映する
			integrationOfDaily.getWorkInformation().getRecordInfo().setWorkTimeCode(worktimeCode);
			integrationOfDaily.getWorkInformation().getScheduleInfo().setWorkTimeCode(worktimeCode);
		}
	}

}
