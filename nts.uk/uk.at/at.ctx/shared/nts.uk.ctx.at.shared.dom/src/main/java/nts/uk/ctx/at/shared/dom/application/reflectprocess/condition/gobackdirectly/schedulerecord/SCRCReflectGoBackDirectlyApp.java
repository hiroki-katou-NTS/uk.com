package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.schedulerecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import nts.uk.ctx.at.shared.dom.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectDirectBounceClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ReflectWorkInformation;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.ApplicationStatusShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.ReflectGoBackDirectly;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         直行直帰申請の反映
 */
public class SCRCReflectGoBackDirectlyApp {
	public static Collection<Integer> reflect(Require require, String companyId, GoBackDirectlyShare appGoback,
			DailyRecordOfApplication dailyApp, ReflectGoBackDirectly reflectGoback) {

		Set<Integer> lstResult = new HashSet<>();
		// [勤務情報を反映する]をチェック
		if (reflectGoback.getReflectWorkInfo() == ApplicationStatusShare.DO_REFLECT) {
			// 1:反映する
			lstResult.addAll(processWorkType(require, companyId, appGoback, dailyApp, reflectGoback));

		}

		if (reflectGoback.getReflectWorkInfo() == ApplicationStatusShare.DO_NOT_REFLECT_1
				|| reflectGoback.getReflectWorkInfo() == ApplicationStatusShare.DO_REFLECT_1) {
			// 2：申請時に決める、3：申請時に決める
			if (appGoback.getIsChangedWork().isPresent() && appGoback.getIsChangedWork().get() == NotUseAtr.USE) {
				lstResult.addAll(processWorkType(require, companyId, appGoback, dailyApp, reflectGoback));
			}
		}

		// 直行直帰区分の反映
		lstResult.addAll(ReflectDirectBounceClassifi.reflect(dailyApp, appGoback.getStraightDistinction(),
				appGoback.getStraightLine()));
		return lstResult;
	}

	private static List<Integer> processWorkType(Require require, String companyId, GoBackDirectlyShare appGoback,
			DailyRecordOfApplication dailyApp, ReflectGoBackDirectly reflectGoback) {
		// 勤務種類を取得する
		Optional<WorkType> workTypeOpt = require.findByPK(companyId,
				dailyApp.getWorkInformation().getRecordInfo().getWorkTypeCode().v());
		if (!checkWorkType(workTypeOpt)) {
			// [input. 直行直帰申請. 勤務情報]を勤務情報DTOへセット
			// [勤務種類コード、就業時間帯コード]を勤務情報DTOへセット
			WorkInfoDto workInfoDto = appGoback.getDataWork().map(x -> {
				return new WorkInfoDto(Optional.ofNullable(x.getWorkTypeCode()), x.getWorkTimeCodeNotNull());
			}).orElse(new WorkInfoDto(Optional.empty(), Optional.empty()));
			// 勤務情報の反映
			return ReflectWorkInformation.reflectInfo(require, workInfoDto, dailyApp, Optional.of(true),
					Optional.of(true));
		}
		return new ArrayList<>();
	}

	private static boolean checkWorkType(Optional<WorkType> workTypeOpt) {

		if (!workTypeOpt.isPresent()) {
			return true;
		}

		if (workTypeOpt.get().getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay && (workTypeOpt.get()
				.getDailyWork().getClassification() == WorkTypeClassification.Shooting
				|| workTypeOpt.get().getDailyWork().getClassification() == WorkTypeClassification.HolidayWork)) {
			return true;
		}

		if (workTypeOpt.get().getDailyWork().getWorkTypeUnit() != WorkTypeUnit.OneDay
				&& workTypeOpt.get().getDailyWork().getClassification() == WorkTypeClassification.Shooting) {

			return true;
		}
		return false;
	}

	public static interface Require extends ReflectWorkInformation.Require {

		// WorkTypeRepository
		Optional<WorkType> findByPK(String companyId, String workTypeCd);
	}
}
