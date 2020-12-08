package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryHolidayQuota;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryHolidayWorkTimeApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         NRWeb照会休日出勤申請を取得
 */
public class GetNRWebQueryHolidayWorkAppDetail {
	// [S-1] プロセス
	public static List<NRQueryHolidayWorkTimeApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {
		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.HOLIDAY_WORK_APPLICATION);
		List<AppHolidayWork> lstAppHolidayWorkList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstAppHolidayWorkList.addAll(require.findAppHolidayWorkWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstAppHolidayWorkList.addAll(require.findAppHolidayWorkWithSidDateApptype(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstAppHolidayWorkList
					.addAll(require.findAppHolidayWorkWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}

		// $枠NOリスト
		val appHolidayWorkWorkFrNos = lstAppHolidayWorkList.stream()
				.flatMap(x -> x.getApplicationTime().getApplicationTime().stream().map(y -> y.getFrameNo().v()))
				.distinct().collect(Collectors.toList());
		// $休出枠
		List<WorkdayoffFrame> lstWorkFrame = require.findWorkdayoffFrame(param.getCid(), appHolidayWorkWorkFrNos);

		List<Integer> lstDivergenceReasonNo = lstAppHolidayWorkList.stream().flatMap(x -> {
			return x.getApplicationTime().getReasonDissociation().map(y -> y.stream().collect(Collectors.toList()))
					.map(z -> z.stream().map(t -> t.getDiviationTime()).collect(Collectors.toList()))
					.orElse(new ArrayList<>()).stream();
		}).collect(Collectors.toList());
		List<DivergenceReasonInputMethod> lstDivergenceReason = require.getDivergenceReason(param.getCid(),
				lstDivergenceReasonNo);

		return createHolidayWorkTime(require, param.getCid(), lstAppHolidayWorkList, lstWorkFrame, lstDivergenceReason);
	}

	// [pvt-1] 申請のデータを作る
	private static List<NRQueryHolidayWorkTimeApp> createHolidayWorkTime(Require require, String cid,
			List<AppHolidayWork> lstAppHolidayWorkList, List<WorkdayoffFrame> lstWorkFrame,
			List<DivergenceReasonInputMethod> lstDivergenceReason) {
		List<NRQueryHolidayWorkTimeApp> result = new ArrayList<>();

		List<Integer> lstHolWorkNo = lstAppHolidayWorkList.stream()
				.flatMap(x -> x.getApplicationTime().getApplicationTime().stream().map(y -> y.getFrameNo().v()))
				.distinct().collect(Collectors.toList());
		List<WorkdayoffFrame> workDayOff = require.findWorkdayoffFrame(cid, lstHolWorkNo);

		val lstDivergenceReasonNo = lstAppHolidayWorkList.stream().flatMap(x -> x.getApplicationTime()
				.getReasonDissociation().orElse(new ArrayList<>()).stream().map(y -> y.getDiviationTime())).distinct()
				.collect(Collectors.toList());
		List<DivergenceReasonInputMethod> lstDivergence = require.getDivergenceReason(cid, lstDivergenceReasonNo);

		lstAppHolidayWorkList.stream().forEach(app -> {
			String workTypeName = require.findWorkTypeByPK(cid, app.getWorkInformation().getWorkTypeCode().v())
					.map(x -> x.getAbbreviationName().v()).orElse("");
			String workTimeName = app.getWorkInformation().getWorkTimeCodeNotNull().flatMap(x -> require
					.findWorkTimeByCode(cid, x.v()).map(y -> y.getWorkTimeDisplayName().getWorkTimeName().v()))
					.orElse("");

			// $休出枠
			List<NRQueryHolidayQuota> holidayQuotaLst = app
					.getApplicationTime().getApplicationTime().stream().filter(
							y -> workDayOff.stream().anyMatch(
									x -> x.getWorkdayoffFrNo().v().intValue() == y.getFrameNo().v().intValue()))
					.map(y -> new NRQueryHolidayQuota(
							workDayOff.stream()
									.filter(x -> x.getWorkdayoffFrNo().v().intValue() == y.getFrameNo().v().intValue())
									.findFirst().get().getWorkdayoffFrName().v(),
							y.getApplicationTime().v().toString()))
					.collect(Collectors.toList());

			List<String> reasonDissocsLst = new ArrayList<>();
			// $乖離理由
			if (app.getApplicationTime().getReasonDissociation().isPresent()) {
				reasonDissocsLst.addAll(app.getApplicationTime().getReasonDissociation().get().stream().map(x -> {
					if (x.getReason() != null) {
						return x.getReason().v();
					} else {
						return lstDivergence.stream()
								.filter(y -> y.getDivergenceTimeNo() == x.getDiviationTime().intValue())
								.flatMap(y -> y.getReasons().stream())
								.filter(y -> y.getDivergenceReasonCode().v().equals(x.getReasonCode().v())).findFirst()
								.map(y -> y.getReason().v()).orElse("");
					}
				}).collect(Collectors.toList()));
			}

			for (ReflectionStatusOfDay state : app.getReflectionStatus().getListReflectionStatusOfDay()) {
				NRQueryApp appQuery = NRQueryApp.create(app, state);
				result.add(
						new NRQueryHolidayWorkTimeApp(appQuery, NotUseAtr.valueOf(app.getGoWorkAtr().value),
								NotUseAtr.valueOf(app.getBackHomeAtr().value), workTimeName, workTypeName,
								app.getApplicationTime().getOverTimeShiftNight().filter(x -> x.getOverTimeMidNight() != null)
										.map(x -> String.valueOf(x.getOverTimeMidNight().v())),
								holidayQuotaLst, reasonDissocsLst));
			}
		});
		return result;
	}

	public static interface Require {
		// [R-1] 申請者 と申請日から休日出勤申請を取得する
		public List<AppHolidayWork> findAppHolidayWorkWithSidDate(String companyId, String sid, GeneralDate date);

		// [R-2] 休日出勤申請を取得する
		public List<AppHolidayWork> findAppHolidayWorkWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から休日出勤申請を取得する
		public List<AppHolidayWork> findAppHolidayWorkWithSidDatePeriod(String companyId, String sid,
				DatePeriod period);

		// [R-4] 枠NOリストから休出枠を取得する
		List<WorkdayoffFrame> findWorkdayoffFrame(String companyId, List<Integer> workdayoffFrNos);

		// [R-5] 就業時間帯コードリストから就業時間帯の設定を取得する
		// WorkTimeSettingRepository.findByCode
		public Optional<WorkTimeSetting> findWorkTimeByCode(String companyId, String workTimeCode);

		// [R-6] 勤務種類コードリストから勤務種類を取得する
		// WorkTypeRepository.findByPK
		public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd);

		// [R-7] 乖離理由コードリストから乖離理由の選択肢を取得する
		// DivergenceReasonInputMethodI.getData
		public List<DivergenceReasonInputMethod> getDivergenceReason(String companyId, List<Integer> lstNo);
	}
}
