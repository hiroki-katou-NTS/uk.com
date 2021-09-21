package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.businesstrip;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripInfoShare;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectDirectBounceClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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

	//出張申請の反映（勤務実績）
	public Collection<Integer> reflectRecord(Require require, BusinessTripShare bussinessTrip,
			DailyRecordOfApplication dailyApp, GeneralDate date) {

		List<Integer> lstItemId = new ArrayList<>();
		// 該当する出張勤務情報を取得
		Optional<BusinessTripInfoShare> businessTripInfo = bussinessTrip.getInfos().stream()
				.filter(x -> x.getDate().equals(date)).findFirst();

		if (!businessTripInfo.isPresent()) {
			return lstItemId;
		}

		// 該当の[出張勤務情報.勤務情報]を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(
				Optional.of(businessTripInfo.get().getWorkInformation().getWorkTypeCode()),
				businessTripInfo.get().getWorkInformation().getWorkTimeCodeNotNull());

		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, companyId, workInfoDto, dailyApp, Optional.of(true),
				Optional.of(true)));

		// 該当の[出張勤務情報. 勤務時間帯]をチェック
		if (!businessTripInfo.get().getWorkingHours().isPresent()
				|| businessTripInfo.get().getWorkingHours().get().isEmpty()) {
			return lstItemId;
		}

		// 出退勤の反映
		lstItemId.addAll(ReflectAttendance.reflect(require, companyId, businessTripInfo.get().getWorkingHours().get(),
				ScheduleRecordClassifi.RECORD, dailyApp, Optional.of(true), Optional.of(true), Optional.of(TimeChangeMeans.DIRECT_BOUNCE_APPLICATION)));

		// 直行直帰区分の反映
		lstItemId.addAll(ReflectDirectBounceClassifi.reflect(dailyApp, NotUseAtr.USE, NotUseAtr.USE));
		return lstItemId;
	}

	//出張申請の反映（勤務予定）
	public Collection<Integer> reflectSchedule(Require require, BusinessTripShare bussinessTrip,
			DailyRecordOfApplication dailyApp, GeneralDate date) {

		List<Integer> lstItemId = new ArrayList<>();
		// 該当する出張勤務情報を取得
		Optional<BusinessTripInfoShare> businessTripInfo = bussinessTrip.getInfos().stream()
				.filter(x -> x.getDate().equals(date)).findFirst();

		if (!businessTripInfo.isPresent()) {
			return lstItemId;
		}

		// 該当の[出張勤務情報.勤務情報]を勤務情報DTOへセット
		WorkInfoDto workInfoDto = new WorkInfoDto(
				Optional.of(businessTripInfo.get().getWorkInformation().getWorkTypeCode()),
				businessTripInfo.get().getWorkInformation().getWorkTimeCodeNotNull());

		// 勤務情報の反映
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, companyId, workInfoDto, dailyApp,
				Optional.of(true), Optional.of(true)));

		// 該当の[出張勤務情報. 勤務時間帯]をチェック
		if (!businessTripInfo.get().getWorkingHours().isPresent()
				|| businessTripInfo.get().getWorkingHours().get().isEmpty()) {
			return lstItemId;
		}

		// 出退勤の反映
		lstItemId.addAll(ReflectAttendance.reflect(require, companyId, businessTripInfo.get().getWorkingHours().get(),
				ScheduleRecordClassifi.RECORD, dailyApp, Optional.of(true), Optional.of(true), Optional.of(TimeChangeMeans.APPLICATION)));

		// 直行直帰区分の反映
		lstItemId.addAll(ReflectDirectBounceClassifi.reflect(dailyApp, NotUseAtr.USE, NotUseAtr.USE));
		return lstItemId;
	}

	public static interface Require
			extends ReflectWorkInformation.Require, ReflectAttendance.Require, ReflectStartEndWork.Require {

	}
	
}
