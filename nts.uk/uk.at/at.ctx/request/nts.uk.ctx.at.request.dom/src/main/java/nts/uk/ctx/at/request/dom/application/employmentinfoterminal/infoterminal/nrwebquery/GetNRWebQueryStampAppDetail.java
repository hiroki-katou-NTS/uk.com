package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.ApplicationArgument;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryStampApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryStampAppDetail;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeApp;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeZoneApp;
import nts.uk.ctx.at.request.dom.application.stamp.StartEndClassification;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.com.time.TimeZone;

/**
 * @author thanh_nx
 *
 *         NRWeb照会打刻申請を取得
 */
public class GetNRWebQueryStampAppDetail {
	// [S-1] プロセス
	public static List<NRQueryStampApp> process(Require require, NRWebQuerySidDateParameter param, DatePeriod period) {

		// $タイプ
		ApplicationArgument type = param.getNrWebQuery().getApplication().get()
				.argumentCheck(ApplicationType.STAMP_APPLICATION);
		List<AppStamp> lstAppStampList = new ArrayList<>();
		List<AppRecordImage> lstAppRecoder = new ArrayList<>();
		List<NRQueryStampApp> result = new ArrayList<>();
		if (type == ApplicationArgument.PT1) {
			lstAppStampList.addAll(require.findAppStampWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));

			lstAppRecoder.addAll(require.findAppTimeRecordWithSidDate(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get()));

		} else if (type == ApplicationArgument.PT2) {
			lstAppStampList.addAll(require.findAppStampWithSidDateApptype(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));

			lstAppRecoder.addAll(require.findAppTimeRecordWithSidDateApptype(param.getCid(), param.getSid(),
					param.getNrWebQuery().getApplication().get().getDate().get(),
					param.getNrWebQuery().getApplication().get().getDateTime().get(),
					param.getNrWebQuery().getApplication().get().getJikbn().get()));
		} else if (type == ApplicationArgument.PT3) {
			lstAppStampList.addAll(require.findAppStampWithSidDatePeriod(param.getCid(), param.getSid(), period));

			lstAppRecoder.addAll(require.findAppTimeRecordWithSidDatePeriod(param.getCid(), param.getSid(), period));
		} else {
			return new ArrayList<>();
		}

		result.addAll(
				lstAppStampList.stream().flatMap(x -> createFromAppStamp(x).stream()).collect(Collectors.toList()));
		result.addAll(
				lstAppRecoder.stream().flatMap(x -> createFromAppStampRecord(x).stream()).collect(Collectors.toList()));
		return result;
	}

	// [pvt-1] 打刻申請を作る
	private static List<NRQueryStampApp> createFromAppStamp(AppStamp appStamp) {
		List<NRQueryStampAppDetail> resultDetail = new ArrayList<>();
		List<NRQueryStampApp> result = new ArrayList<>();
		// 打刻申請.時間帯
		if (!appStamp.getListTimeStampAppOther().isEmpty()) {
			resultDetail.addAll(appStamp.getListTimeStampAppOther().stream().flatMap(
					x -> createStampDestInforTimeZone(x.getDestinationTimeZoneApp(), Optional.of(x.getTimeZone()))
							.stream())
					.collect(Collectors.toList()));
		}

		// 時間帯の取消
		if (!appStamp.getListDestinationTimeZoneApp().isEmpty()) {
			resultDetail.addAll(appStamp.getListDestinationTimeZoneApp().stream()
					.flatMap(x -> createStampDestInforTimeZone(x, Optional.empty()).stream())
					.collect(Collectors.toList()));
		}

		// 時刻
		if (!appStamp.getListTimeStampApp().isEmpty()) {
			resultDetail.addAll(appStamp.getListTimeStampApp().stream().flatMap(
					x -> createStampDestInforTime(x.getDestinationTimeApp(), Optional.of(x.getTimeOfDay())).stream())
					.collect(Collectors.toList()));
		}

		// 時刻の取消
		if (!appStamp.getListDestinationTimeApp().isEmpty()) {
			resultDetail.addAll(appStamp.getListDestinationTimeApp().stream()
					.flatMap(x -> createStampDestInforTime(x, Optional.empty()).stream()).collect(Collectors.toList()));
		}

		for (ReflectionStatusOfDay state : appStamp.getReflectionStatus().getListReflectionStatusOfDay()) {
			NRQueryApp appQuery = NRQueryApp.create(appStamp, state);
			result.add(new NRQueryStampApp(appQuery, resultDetail));
		}

		return result;
	}

	// [pvt-２] レコーダイメージ申請を作る
	private static List<NRQueryStampApp> createFromAppStampRecord(AppRecordImage appRecoder) {
		List<NRQueryStampApp> result = new ArrayList<>();
		List<NRQueryStampAppDetail> resultDetail = new ArrayList<>();
		Function<String, NRQueryStampAppDetail> createDetail = new Function<String, NRQueryStampAppDetail>() {
			@Override
			public NRQueryStampAppDetail apply(String t) {
				return new NRQueryStampAppDetail(t, NRQueryApp.createValueFormatTimeAtr(appRecoder.getAttendanceTime().toString()));
			}
		};
		for (ReflectionStatusOfDay state : appRecoder.getReflectionStatus().getListReflectionStatusOfDay()) {
			NRQueryApp appQuery = NRQueryApp.create(appRecoder, state);
			switch (appRecoder.getAppStampCombinationAtr()) {
			case ATTENDANCE:
				resultDetail.add(createDetail.apply(TextResource.localize("Com_WorkIn")));
				break;
			case OFFICE_WORK:
				resultDetail.add(createDetail.apply(TextResource.localize("Com_WorkOut")));
				break;
			case OVERTIME:
				resultDetail.add(createDetail.apply(TextResource.localize("Com_WorkOut")));
				break;
			case GO_OUT:
				resultDetail.add(createDetail.apply(TextResource.localize("Com_Out")));
				break;
			case RETURN:
				resultDetail.add(createDetail.apply(TextResource.localize("Com_In")));
				break;
			case EARLY:
				resultDetail.add(createDetail.apply(TextResource.localize("早出")));
				break;
			case HOLIDAY:
				resultDetail.add(createDetail.apply(TextResource.localize("休出")));
				break;
			default:
				break;
			}
			result.add(new NRQueryStampApp(appQuery, resultDetail));
		}
		return result;
	}

	// [pvt-3] 時間帯申請の反映先情報の打刻種類名を作る
	private static String cancelMessage = "実績取り消し";

	private static List<NRQueryStampAppDetail> createStampDestInforTimeZone(DestinationTimeZoneApp destictTimeZone,
			Optional<TimeZone> timeZones) {
		List<NRQueryStampAppDetail> detailResult = new ArrayList<>();
		Function<String, NRQueryStampAppDetail> createDetail = new Function<String, NRQueryStampAppDetail>() {
			@Override
			public NRQueryStampAppDetail apply(String t) {
				return new NRQueryStampAppDetail(t + destictTimeZone.getEngraveFrameNo(),
						timeZones.map(x -> NRQueryApp.createValueFormatTimeAtr(x.getStartTime().toString())).orElse(cancelMessage));
			}
		};
		switch (destictTimeZone.getTimeZoneStampClassification()) {
		case PARENT:
			detailResult.add(createDetail.apply("育児外出"));
			detailResult.add(createDetail.apply("育児戻り"));
			break;
		case NURSE:
			detailResult.add(createDetail.apply("介護外出"));
			detailResult.add(createDetail.apply("介護戻り"));
			break;
		case BREAK:
			detailResult.add(createDetail.apply("休憩開始"));
			detailResult.add(createDetail.apply("休憩終了"));
			break;
		}
		return detailResult;
	}

	// [pvt-4]時刻申請の反映先情報の打刻種類名を作る
	private static List<NRQueryStampAppDetail> createStampDestInforTime(DestinationTimeApp destinationTimeApp,
			Optional<TimeWithDayAttr> timeOfDay) {

		List<NRQueryStampAppDetail> detailResult = new ArrayList<>();
		BiFunction<String, String, NRQueryStampAppDetail> createDetail = new BiFunction<String, String, NRQueryStampAppDetail>() {
			@Override
			public NRQueryStampAppDetail apply(String in, String out) {
				return new NRQueryStampAppDetail(
						(destinationTimeApp.getStartEndClassification() == StartEndClassification.START ? in : out)
								+ destinationTimeApp.getEngraveFrameNo(),
						timeOfDay.map(x -> NRQueryApp.createValueFormatTimeAtr(x.v().toString())).orElse(cancelMessage));
			}
		};
		switch (destinationTimeApp.getTimeStampAppEnum()) {
		case ATTEENDENCE_OR_RETIREMENT:
			detailResult
					.add(createDetail.apply(TextResource.localize("Com_WorkIn"), TextResource.localize("Com_WorkOut")));
			break;
		case EXTRAORDINARY:
			detailResult.add(
					createDetail.apply(TextResource.localize("Com_ExtraIn"), TextResource.localize("Com_ExtraOut")));
			break;
		case GOOUT_RETURNING:
			detailResult.add(createDetail.apply(TextResource.localize("Com_In"), TextResource.localize("Com_Out")));
			break;
		case CHEERING:
			detailResult.add(createDetail.apply("応援入", "応援出"));
			break;
		}
		return detailResult;
	}

	public static interface Require {

		// [R-1] 申請者 と申請日から打刻申請を取得する
		public List<AppStamp> findAppStampWithSidDate(String companyId, String sid, GeneralDate date);

		// [R-2] 打刻申請を取得する
		public List<AppStamp> findAppStampWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-3] 申請者 と期間から打刻申請を取得する
		public List<AppStamp> findAppStampWithSidDatePeriod(String companyId, String sid, DatePeriod period);

		// [R-4] 申請者 と申請日からレコーダイメージ申請を取得する
		public List<AppRecordImage> findAppTimeRecordWithSidDate(String companyId, String sid, GeneralDate date);

		// [R-5] レコーダイメージ申請を取得する
		public List<AppRecordImage> findAppTimeRecordWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr);

		// [R-6] 申請者 と期間からレコーダイメージ申請を取得する
		public List<AppRecordImage> findAppTimeRecordWithSidDatePeriod(String companyId, String sid, DatePeriod period);

	}
}
