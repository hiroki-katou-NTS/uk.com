package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.businesstrip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripInfoShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.ReflectAppDestination;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author thanh_nx
 *
 *         出張申請の反映
 */
@Getter
public class ReflectBusinessTripApp implements DomainAggregate {

	// 会社ID
	private String companyId;

	public ReflectBusinessTripApp(String companyId) {
		super();
		this.companyId = companyId;
	}

	// 出張申請の反映（勤務実績）
	public Collection<Integer> reflectRecord(Require require, BusinessTripInfoShare bussinessTrip,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstItemId = new ArrayList<>();
		// 勤務情報を反映する
		lstItemId.addAll(reflectWorkInfo(require, bussinessTrip, dailyApp, ReflectAppDestination.RECORD));

		// 始業終業時刻の反映
		lstItemId.addAll(reflectStartEndTime(require, bussinessTrip, dailyApp, ReflectAppDestination.SCHEDULE));

		bussinessTrip.getWorkingHours().forEach(workHour -> {
			// 出退勤の反映
			lstItemId.addAll(ReflectAttendance.reflect(require, companyId, Arrays.asList(workHour),
					ScheduleRecordClassifi.RECORD, dailyApp, Optional.of(workHour.getTimeZone().getStartTime() != null),
					Optional.of(workHour.getTimeZone().getEndTime() != null),
					Optional.of(TimeChangeMeans.DIRECT_BOUNCE_APPLICATION)));
		});
		
		return lstItemId;
	}

	// 出張申請の反映（勤務予定）
	public Collection<Integer> reflectSchedule(Require require, BusinessTripInfoShare bussinessTrip,
			DailyRecordOfApplication dailyApp) {

		List<Integer> lstItemId = new ArrayList<>();
		// 勤務情報を反映する
		lstItemId.addAll(reflectWorkInfo(require, bussinessTrip, dailyApp, ReflectAppDestination.SCHEDULE));

		// 始業終業時刻の反映
		lstItemId.addAll(reflectStartEndTime(require, bussinessTrip, dailyApp, ReflectAppDestination.RECORD));

		return lstItemId;
	}

	// 勤務情報を反映する
	private List<Integer> reflectWorkInfo(Require require, BusinessTripInfoShare bussinessTrip,
			DailyRecordOfApplication dailyApp, ReflectAppDestination destination) {
		List<Integer> itemIds = new ArrayList<>();
		// 該当の[出張勤務情報.勤務情報]を勤務情報DTOへセット
		WorkInformation recordInfo = dailyApp.getWorkInformation().getRecordInfo();
		WorkInfoDto workInfoDto = new WorkInfoDto(Optional.of(bussinessTrip.getWorkInformation().getWorkTypeCode()),
				bussinessTrip.getWorkInformation().getWorkTimeCodeNotNull());
		if (!recordInfo.getWorkTypeCode().v().equals(workInfoDto.getWorkTypeCode().get().v())
				|| (recordInfo.getWorkTimeCodeNotNull().isPresent() && !workInfoDto.getWorkTimeCode().isPresent())
				|| (!recordInfo.getWorkTimeCodeNotNull().isPresent() && workInfoDto.getWorkTimeCode().isPresent())
				|| (recordInfo.getWorkTimeCodeNotNull().isPresent() && workInfoDto.getWorkTimeCode().isPresent()
						&& !recordInfo.getWorkTimeCodeNotNull().get().v()
								.equals(workInfoDto.getWorkTimeCode().get().v()))) {
			itemIds.addAll(ReflectWorkInformation.reflectInfo(require, companyId, workInfoDto, dailyApp,
					Optional.of(true), Optional.of(true)));
		}

		return itemIds;
	}

	// 始業終業時刻を反映する
	private List<Integer> reflectStartEndTime(Require require, BusinessTripInfoShare bussinessTrip,
			DailyRecordOfApplication dailyApp, ReflectAppDestination destination) {
		List<Integer> itemIds = new ArrayList<>();
		Optional<WorkType> workTypeOpt = require.getWorkType(bussinessTrip.getWorkInformation().getWorkTimeCode().v());
		if (!workTypeOpt.isPresent() || !workTypeOpt.get().isHolidayWork())
			return itemIds;
		// 出退勤の反映
		bussinessTrip.getWorkingHours().forEach(workHour -> {
			itemIds.addAll(ReflectAttendance.reflect(require, companyId, Arrays.asList(workHour),
					EnumAdaptor.valueOf(destination.value, ScheduleRecordClassifi.class), dailyApp,
					Optional.of(workHour.getTimeZone().getStartTime() != null),
					Optional.of(workHour.getTimeZone().getEndTime() != null),
					Optional.of(TimeChangeMeans.APPLICATION)));
		});

		return itemIds;
	}

	public static interface Require
			extends ReflectWorkInformation.Require, ReflectAttendance.Require, ReflectStartEndWork.Require {

		/**
		 * 勤務種類を取得する
		 * 
		 * @param workTypeCd 就業時間帯コード
		 * @return
		 */
		Optional<WorkType> getWorkType(String workTypeCd);
	}

}
