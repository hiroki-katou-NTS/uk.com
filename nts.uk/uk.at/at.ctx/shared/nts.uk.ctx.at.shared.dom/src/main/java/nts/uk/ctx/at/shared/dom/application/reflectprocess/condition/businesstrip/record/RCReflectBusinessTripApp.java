package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripInfoShare;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectDirectBounceClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectStartEndWork;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectAttendance;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp.WorkInfoDto;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         出張申請の反映（勤務実績）
 */
public class RCReflectBusinessTripApp {

	public static Collection<Integer> reflect(Require require, BusinessTripShare bussinessTrip,
			DailyRecordOfApplication dailyApp, ReflectBusinessTripApp reflectWorkChange, GeneralDate date) {

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
		lstItemId.addAll(ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp, Optional.of(true),
				Optional.of(true)));

		// 始業終業の反映
		lstItemId.addAll(ReflectStartEndWork.reflect(dailyApp,
				businessTripInfo.get().getWorkingHours().isPresent() ? businessTripInfo.get().getWorkingHours().get()
						: new ArrayList<>(),
				bussinessTrip.getPrePostAtr()));

		// 該当の[出張勤務情報. 勤務時間帯]をチェック
		if (!businessTripInfo.get().getWorkingHours().isPresent()
				|| businessTripInfo.get().getWorkingHours().get().isEmpty()) {
			return lstItemId;
		}

		// 出退勤の反映
		lstItemId.addAll(ReflectAttendance.reflect(businessTripInfo.get().getWorkingHours().get(),
				ScheduleRecordClassifi.RECORD, dailyApp, Optional.of(true), Optional.of(true)));

		// 直行直帰区分の反映
		lstItemId.addAll(ReflectDirectBounceClassifi.reflect(dailyApp, NotUseAtr.USE, NotUseAtr.USE));
		return lstItemId;
	}

	public static interface Require extends ReflectWorkInformation.Require {

	}
}
