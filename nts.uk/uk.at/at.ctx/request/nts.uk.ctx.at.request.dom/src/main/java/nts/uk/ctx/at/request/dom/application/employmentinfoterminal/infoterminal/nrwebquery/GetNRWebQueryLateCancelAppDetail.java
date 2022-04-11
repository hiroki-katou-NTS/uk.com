package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryLateCancelApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;

/**
 * @author thanh_nx
 *
 *         NRWeb照会遅早取消申請を取得
 */
public class GetNRWebQueryLateCancelAppDetail {

	// [S-1] プロセス
	public static List<NRQueryLateCancelApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {
		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION);
		List<ArrivedLateLeaveEarly> lstArrivedLateLeaveEarlyList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstArrivedLateLeaveEarlyList.addAll(require.findArrivedLateLeaveEarlyWithSidDate(param.getCid(),
					param.getSid(), param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstArrivedLateLeaveEarlyList.addAll(require.findArrivedLateLeaveEarlyWithSidDateApptype(param.getCid(),
					param.getSid(), param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstArrivedLateLeaveEarlyList
					.addAll(require.findArrivedLateLeaveEarlyWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}
		return lstArrivedLateLeaveEarlyList.stream().flatMap(x -> createListAppResult(x).stream())
				.collect(Collectors.toList());
	}

	// [pvt-1] 申請のデータを作る
	private static List<NRQueryLateCancelApp> createListAppResult(ArrivedLateLeaveEarly app) {
		List<String> lateCancelAppDetailLst = new ArrayList<>();
		List<NRQueryLateCancelApp> result = new ArrayList<>();
		// 申請.取消
		lateCancelAppDetailLst.addAll(app.getLateCancelation().stream().map(x -> {
			if (x.getLateOrEarlyClassification() == LateOrEarlyAtr.LATE) {
				return "遅刻取り消し";
			}
			return "早退取り消し";
		}).collect(Collectors.toList()));

		// 申請.時刻報告:
		lateCancelAppDetailLst.addAll(app.getLateOrLeaveEarlies().stream().map(x -> {
			if (x.getLateOrEarlyClassification() == LateOrEarlyAtr.LATE) {
				return "遅刻";
			}
			return "早退";
		}).collect(Collectors.toList()));

		for (ReflectionStatusOfDay state : app.getReflectionStatus().getListReflectionStatusOfDay()) {
			NRQueryApp appQuery = NRQueryApp.create(app, state);
			result.add(new NRQueryLateCancelApp(appQuery, lateCancelAppDetailLst));
		}
		return result;
	}

	public static interface Require {
		// [R-1] 申請者 と申請日から遅刻早退取消申請を取得する
		public List<ArrivedLateLeaveEarly> findArrivedLateLeaveEarlyWithSidDate(String companyId, String sid,
				GeneralDate date);

		// [R-2] 遅刻早退取消申請を取得する
		public List<ArrivedLateLeaveEarly> findArrivedLateLeaveEarlyWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から遅刻早退取消申請を取得する
		public List<ArrivedLateLeaveEarly> findArrivedLateLeaveEarlyWithSidDatePeriod(String companyId, String sid,
				DatePeriod period);
	}
}
