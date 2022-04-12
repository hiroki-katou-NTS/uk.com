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
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryOvertimeApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryOvertimeAppAnyItem;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryOvertimeQuota;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author thanh_nx
 *
 *         NRWeb照会残業申請を取得
 */
public class GetNRWebQueryOvertimeAppDetail {

	// [S-1] プロセス
	public static List<NRQueryOvertimeApp> process(Require require, NRWebQuerySidDateParameter param,
			DatePeriod period) {
		if (!param.getNrWebQuery().getApplication().isPresent()) {
			return new ArrayList<>();
		}
		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.OVER_TIME_APPLICATION);
		List<AppOverTime> lstAppOverTimeList = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstAppOverTimeList.addAll(require.findOvertimeWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));
		} else if (type == ApplicationArgument.PT2) {
			lstAppOverTimeList.addAll(require.findOvertimeWithSidDateApptype(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstAppOverTimeList.addAll(require.findOvertimeWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}

		// $枠NOリスト
		val overtimeWorkFrNos = lstAppOverTimeList.stream()
				.flatMap(x -> x.getApplicationTime().getApplicationTime().stream().map(y -> y.getFrameNo().v()))
				.distinct().collect(Collectors.toList());
		// $残業枠
		List<OvertimeWorkFrame> lstWorkFrame = require.getOvertimeWorkFrameByFrameNos(param.getCid(),
				overtimeWorkFrNos);

		val lstOptionalItemNo = lstAppOverTimeList.stream().flatMap(
				x -> x.getApplicationTime().getAnyItem().orElse(new ArrayList<>()).stream().map(y -> y.getItemNo().v()))
				.distinct().collect(Collectors.toList());
		// $任意項目
		List<OptionalItem> lstOptionalItem = require.findOptionalItemByListNos(param.getCid(), lstOptionalItemNo);

		val lstDivergenceReasonNo = lstAppOverTimeList.stream().flatMap(x -> x.getApplicationTime()
				.getReasonDissociation().orElse(new ArrayList<>()).stream().map(y -> y.getDiviationTime())).distinct()
				.collect(Collectors.toList());
		List<DivergenceReasonInputMethod> lstDivergence = require.getDivergenceReason(param.getCid(),
				lstDivergenceReasonNo);
		return createAppData(require, param.getCid(), lstAppOverTimeList, lstWorkFrame, lstOptionalItem, lstDivergence);
	}

	// [pvt-1] 申請のデータを作る
	private static List<NRQueryOvertimeApp> createAppData(Require require, String cid, List<AppOverTime> lstAppOverTime,
			List<OvertimeWorkFrame> lstWorkFrame, List<OptionalItem> lstOptionalItem,
			List<DivergenceReasonInputMethod> lstDivergence) {
		List<NRQueryOvertimeApp> result = new ArrayList<>();
		lstAppOverTime.forEach(app -> {

			// 外深夜時間
			Optional<String> midnightIimeOutside = app.getApplicationTime().getOverTimeShiftNight().map(data -> {
				return NRQueryApp.createValueFormatTime(String.valueOf(data.getOverTimeMidNight().v()));
			});

			// フレックス超過時間
			Optional<String> flexOvertime = app.getApplicationTime().getFlexOverTime().map(data -> {
				return NRQueryApp.createValueFormatTime(String.valueOf(data.v()));
			});

			// 勤務種類名
			Optional<String> overTimeWoktypeName = Optional.empty();

			// 就業時間帯名
			Optional<String> overTimeZoneName = Optional.empty();

			if (app.getWorkInfoOp().isPresent()) {
				if (app.getWorkInfoOp().get().getWorkTimeCodeNotNull().isPresent())
					overTimeWoktypeName = require
							.findWorkTimeByCode(cid, app.getWorkInfoOp().get().getWorkTimeCode().v())
							.map(x -> x.getWorkTimeDisplayName().getWorkTimeAbName().v());
				overTimeZoneName = require.findWorkTypeByPK(cid, app.getWorkInfoOp().get().getWorkTimeCode().v())
						.map(x -> x.getAbbreviationName().v());
			}

			// $残業枠
			List<NRQueryOvertimeQuota> overtimeQuotalst = createOvertimeQuotalst(app, lstWorkFrame);

			// $乖離理由
			List<String> reasonDissocsLst = createReasonDissocsLst(app, lstDivergence);

			// $任意項目
			List<NRQueryOvertimeAppAnyItem> anyItemLst = createAnyItem(app, lstOptionalItem);

			for (ReflectionStatusOfDay state : app.getReflectionStatus().getListReflectionStatusOfDay()) {
				NRQueryApp appQuery = NRQueryApp.create(app, state);
				result.add(new NRQueryOvertimeApp(appQuery, midnightIimeOutside, flexOvertime, overTimeWoktypeName,
						overTimeZoneName, anyItemLst, reasonDissocsLst, overtimeQuotalst));
			}
		});

		return result;
	}

	// 残業枠を作成
	public static List<NRQueryOvertimeQuota> createOvertimeQuotalst(AppOverTime app,
			List<OvertimeWorkFrame> lstWorkFrame) {
		return app.getApplicationTime().getApplicationTime().stream()
				.filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME && lstWorkFrame.stream()
						.filter(y -> y.getOvertimeWorkFrNo().v().intValue() == x.getFrameNo().v()).findAny()
						.isPresent())
				.map(x -> {
					return new NRQueryOvertimeQuota(
							lstWorkFrame.stream()
									.filter(y -> y.getOvertimeWorkFrNo().v().intValue() == x.getFrameNo().v())
									.findFirst().get().getOvertimeWorkFrName().v(),
									NRQueryApp.createValueFormatTime(String.valueOf(x.getApplicationTime().v())));
				}).collect(Collectors.toList());
	}

	// 乖離理由を作成
	private static List<String> createReasonDissocsLst(AppOverTime app,
			List<DivergenceReasonInputMethod> lstDivergence) {
		if (!app.getApplicationTime().getReasonDissociation().isPresent()) {
			return new ArrayList<>();
		}
		return app.getApplicationTime().getReasonDissociation().get().stream().map(x -> {
			if (x.getReason() != null) {
				return x.getReason().v();
			}
			return lstDivergence.stream().filter(y -> y.getDivergenceTimeNo() == x.getDiviationTime())
					.flatMap(y -> y.getReasons().stream())
					.filter(y -> y.getDivergenceReasonCode().v().equals(x.getReasonCode().v())).findFirst()
					.map(y -> y.getReason().v()).orElse("");
		}).collect(Collectors.toList());
	}

	// 任意項目を作成
	private static List<NRQueryOvertimeAppAnyItem> createAnyItem(AppOverTime app, List<OptionalItem> lstOptionalItem) {
		List<NRQueryOvertimeAppAnyItem> anyItemLst = new ArrayList<>();
		app.getApplicationTime().getAnyItem().ifPresent(anyApp -> {
			val item = anyApp.stream().map(anyAppDetail -> {
				val no = lstOptionalItem.stream().filter(z -> z.getOptionalItemNo().v() == anyAppDetail.getItemNo().v())
						.findFirst();
				if (no.isPresent()) {
					String value = "";
					if (no.get().getOptionalItemAtr() == OptionalItemAtr.TIME) {
						value = anyAppDetail.getTime().map(x -> NRQueryApp.createValueFormatTime(String.valueOf(x.v()))).orElse("");
					} else if (no.get().getOptionalItemAtr() == OptionalItemAtr.NUMBER) {
						value = anyAppDetail.getTimes().map(x -> String.valueOf(x.v())).orElse("");
					} else {
						value = anyAppDetail.getAmount().map(x -> NRQueryApp.createValueFormatMoney(String.valueOf(x.v()))).orElse("");
					}

					return new NRQueryOvertimeAppAnyItem(no.get().getOptionalItemName().v(), value);
				} else {
					return null;
				}
			}).filter(y -> y != null).collect(Collectors.toList());
			anyItemLst.addAll(item);
		});
		return anyItemLst;
	}

	public static interface Require {

		// [R-1] 申請者 と申請日から残業申請を取得する
		public List<AppOverTime> findOvertimeWithSidDate(String companyId, String sid, GeneralDate date);

		// [R-2] 残業申請を取得する
		public List<AppOverTime> findOvertimeWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から残業申請を取得する
		public List<AppOverTime> findOvertimeWithSidDatePeriod(String companyId, String sid, DatePeriod period);

		// [R-４] 枠NOリストから残業枠を取得する
		// OvertimeWorkFrameRepository
		public List<OvertimeWorkFrame> getOvertimeWorkFrameByFrameNos(String companyId,
				List<Integer> overtimeWorkFrNos);

		// [R-５] 任意項目NOリストから任意項目を取得する
		// OptionalItemRepository.findByListNos
		public List<OptionalItem> findOptionalItemByListNos(String companyId, List<Integer> optionalitemNos);

		// [R-6] 就業時間帯コードリストから就業時間帯の設定を取得する
		// WorkTimeSettingRepository.findByCode
		public Optional<WorkTimeSetting> findWorkTimeByCode(String companyId, String workTimeCode);

		// [R-7] 勤務種類コードリストから勤務種類を取得する
		// WorkTypeRepository.findByPK
		public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd);

		// [R-8] 乖離理由コードリストから乖離理由の選択肢を取得する
		// DivergenceReasonInputMethodI.getData
		public List<DivergenceReasonInputMethod> getDivergenceReason(String companyId, List<Integer> lstNo);

	}
}
