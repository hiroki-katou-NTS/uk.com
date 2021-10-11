package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectworkinfor.reflectworktimestamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.calculationsetting.ReflectWorkingTimeClass;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 打刻の就業時間帯を反映する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectWorkTimeStamp {
	@Inject
	private StampReflectionManagementRepository stampReflectionManagementRepo;
	
	public void reflectStamp(WorkTimeCode worktimeCode,IntegrationOfDaily integrationOfDaily) {
		//就業時間帯反映区分を確認する
		Optional<StampReflectionManagement> optData = stampReflectionManagementRepo.findByCid(AppContexts.user().companyId());
		//打刻から就業時間帯を反映する
		if(optData.isPresent() && optData.get().getReflectWorkingTimeClass() == ReflectWorkingTimeClass.REFLECT ) {
			//就業時間帯の変更可能か確認する
			Optional<EditStateOfDailyAttd> editState = integrationOfDaily.getEditState().stream()
					.filter(c->c.getAttendanceItemId() == 29).findFirst();
			if(!editState.isPresent()) {
				//就業時間帯を反映する
				integrationOfDaily.getWorkInformation().getRecordInfo().setWorkTimeCode(worktimeCode);
	//			integrationOfDaily.getWorkInformation().getScheduleInfo().setWorkTimeCode(worktimeCode);
				//日別勤怠の編集状態を追加する
				integrationOfDaily.getEditState().add(new EditStateOfDailyAttd(29, EditStateSetting.IMPRINT));
			}
		}
	}

}
