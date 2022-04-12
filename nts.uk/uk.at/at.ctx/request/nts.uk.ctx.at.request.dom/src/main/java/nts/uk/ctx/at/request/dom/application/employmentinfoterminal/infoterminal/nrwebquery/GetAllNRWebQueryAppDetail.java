package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;

/**
 * @author thanh_nx
 *
 *         すべてのNRWeb照会申請を取得
 */
public class GetAllNRWebQueryAppDetail {

	// S-1] プロセス
	public static List<? extends NRQueryApp> getAll(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {
		List<NRQueryApp> lstResult = new ArrayList<>();
		if (!param.getNrWebQuery().getApplication().isPresent())
			return lstResult;
		// NRWeb照会残業申請を取得
		lstResult.addAll(GetNRWebQueryOvertimeAppDetail.process(require, param, period));
		// NRWeb照会休暇申請を取得
		lstResult.addAll(GetNRWebQueryVacationAppDetail.process(require, param, period));
		// NRWeb照会勤務変更申請を取得
		lstResult.addAll(GetNRWebQueryWorkChangeAppDetail.process(require, param, period));
		// NRWeb照会出張申請を取得
		lstResult.addAll(GetNRWebQueryBussinessTripAppDetail.process(require, param, period));
		// NRWeb照会直行直帰申請を取得
		lstResult.addAll(GetNRWebQueryGoBackDirectlyAppDetail.process(require, param, period));
		// NRWeb照会休日出勤申請を取得
		lstResult.addAll(GetNRWebQueryHolidayWorkAppDetail.process(require, param, period));
		// NRWeb照会打刻申請を取得
		lstResult.addAll(GetNRWebQueryStampAppDetail.process(require, param, period));
		// NRWeb照会時間年休申請を取得
		lstResult.addAll(GetNRWebQueryTimeLeaveAppDetail.process(require, param, period));
		// NRWeb照会遅早取消申請を取得
		lstResult.addAll(GetNRWebQueryLateCancelAppDetail.process(require, param, period));
		// NRWeb照会振休振出申請を取得
		lstResult.addAll(GetNRWebQueryFurikyuAppDetail.process(require, param, period));
		// NRWeb照会任意項目申請を取得
		lstResult.addAll(GetNRWebQueryAnyItemAppDetail.process(require, param, period));

		return lstResult;
	}

	// [S-2] 申請名称を作る
	public static String createAppName(ApplicationType appType) {
		switch (appType) {
		case OVER_TIME_APPLICATION:
			return "残業申請";
		case ABSENCE_APPLICATION:
			return "休暇申請";
		case WORK_CHANGE_APPLICATION:
			return "勤務変更申請";
		case BUSINESS_TRIP_APPLICATION:
			return "出張申請";
		case GO_RETURN_DIRECTLY_APPLICATION:
			return "直行直帰申請";
		case HOLIDAY_WORK_APPLICATION:
			return "休日出勤申請";
		case STAMP_APPLICATION:
			return "打刻申請";
		case ANNUAL_HOLIDAY_APPLICATION:
			return "時間年休申請";
		case EARLY_LEAVE_CANCEL_APPLICATION:
			return "遅早取消申請";
		case COMPLEMENT_LEAVE_APPLICATION:
			return "振休振出申請";
		case OPTIONAL_ITEM_APPLICATION:
			return "任意項目申請";

		default:
			return null;
		}
	}

	// [S-3] 承認状況を作る
	public static String createAppStatus(ReflectionStatusOfDay reflectionStatus) {
		if (reflectionStatus.getScheReflectStatus() == ReflectedState.REFLECTED
				&& reflectionStatus.getActualReflectStatus() == ReflectedState.REFLECTED) {
			return "反映済み";
		}

		if (reflectionStatus.getScheReflectStatus() == ReflectedState.WAITREFLECTION
				|| reflectionStatus.getActualReflectStatus() == ReflectedState.WAITREFLECTION) {
			return "反映待ち";
		}

		if (reflectionStatus.getScheReflectStatus() == ReflectedState.DENIAL
				|| reflectionStatus.getActualReflectStatus() == ReflectedState.DENIAL) {
			return "否認";
		}

		if (reflectionStatus.getScheReflectStatus() == ReflectedState.CANCELED
				|| reflectionStatus.getActualReflectStatus() == ReflectedState.CANCELED) {
			return "取消済み";
		}

		if (reflectionStatus.getScheReflectStatus() == ReflectedState.REMAND
				|| reflectionStatus.getActualReflectStatus() == ReflectedState.REMAND) {
			return "差し戻し";
		}

		return "未反映";
	}

	public static interface Require extends GetNRWebQueryOvertimeAppDetail.Require,
			GetNRWebQueryVacationAppDetail.Require, GetNRWebQueryWorkChangeAppDetail.Require,
			GetNRWebQueryBussinessTripAppDetail.Require, GetNRWebQueryGoBackDirectlyAppDetail.Require,
			GetNRWebQueryHolidayWorkAppDetail.Require, GetNRWebQueryStampAppDetail.Require,
			GetNRWebQueryTimeLeaveAppDetail.Require, GetNRWebQueryLateCancelAppDetail.Require,
			GetNRWebQueryFurikyuAppDetail.Require, GetNRWebQueryAnyItemAppDetail.Require {

	}

}
