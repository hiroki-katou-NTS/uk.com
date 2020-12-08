package nts.uk.ctx.at.request.pubimp.application.infoterminal.nrwebquery;

import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryAnyItemApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryBussinessTripApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryFurikyuFurishutsuApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryGoBackDirectlyApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryHolidayWorkTimeApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryLateCancelApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryOvertimeApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryStampApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryTimeLeaveApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryVacationApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryWorkChangeApp;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQueryAppParameter;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQueryParameter;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryAnyItemAppDetailExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryAnyItemAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryBussinessTripAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryFurikyuFurishutsuAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryGoBackDirectlyAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryHolidayQuotaExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryHolidayWorkTimeAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryLateCancelAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryOvertimeAppAnyItemExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryOvertimeAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryOvertimeQuotaExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryStampAppDetailExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryStampAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryTimeDigesLeavNameExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryTimeLeaveAppDetailExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryTimeLeaveAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryVacationAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryWorkChangeAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRWebQuerySidDateParameterExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.TripAppTimeZoneExport;

public class NRQueryAppConvert {

	public static NRWebQuerySidDateParameter toDomain(NRWebQuerySidDateParameterExport export) {
		Optional<NRWebQueryAppParameter> application = export.getNrWebQuery().getApplication()
				.map(x -> new NRWebQueryAppParameter(x.getDate(),
						x.getKbn().map(y -> EnumAdaptor.valueOf(y, ApplicationType.class)),
						x.getJikbn().map(y -> EnumAdaptor.valueOf(y, PrePostAtr.class)), x.getNdate()));
		NRWebQueryParameter nrWebQuery = new NRWebQueryParameter(export.getNrWebQuery().getContractCode(),
				export.getNrWebQuery().getCno(), export.getNrWebQuery().getVer(), export.getNrWebQuery().getType(),
				export.getNrWebQuery().getYm(), application);
		return new NRWebQuerySidDateParameter(export.getCid(), export.getSid(), nrWebQuery);
	}

	public static NRQueryAppExport fromDomain(NRQueryApp domain) {
		NRQueryAppExport base = new NRQueryAppExport(domain.getAppDate(), domain.getInputDate(), domain.getAppName(),
				domain.getAppType(), domain.getBeforeAfterType(), domain.getApprovalStatus());
		switch (EnumAdaptor.valueOf(domain.getAppType(), ApplicationType.class)) {
		// 残業申請
		case OVER_TIME_APPLICATION:
			NRQueryOvertimeApp appOvertime = (NRQueryOvertimeApp) domain;
			return new NRQueryOvertimeAppExport(base, appOvertime.getMidnightIimeOutside(),
					appOvertime.getFlexOvertime(), appOvertime.getOverTimeWoktypeName(),
					appOvertime.getOverTimeZoneName(),
					appOvertime.getAnyItemLst().stream()
							.map(x -> new NRQueryOvertimeAppAnyItemExport(x.getName(), x.getValue()))
							.collect(Collectors.toList()), // NRQueryOvertimeAppAnyItem
					appOvertime.getReasonDissocsLst(),
					appOvertime.getOvertimeQuotalst().stream()
							.map(x -> new NRQueryOvertimeQuotaExport(x.getOvertimeQuotaName(), x.getOvertimeHours()))
							.collect(Collectors.toList()));
		// 1: 休暇申請
		case ABSENCE_APPLICATION:
			NRQueryVacationApp absenceApp = (NRQueryVacationApp) domain;
			return new NRQueryVacationAppExport(base, absenceApp.getVacationType(), absenceApp.getWorkTypeName(),
					absenceApp.getWorkTimeName(),
					absenceApp.getTimeDigestLeavNameLst().stream()
							.map(x -> new NRQueryTimeDigesLeavNameExport(x.getTimeDigestName(), x.getTimeDigestHours()))
							.collect(Collectors.toList()));
		// 2: 勤務変更申請
		case WORK_CHANGE_APPLICATION:
			NRQueryWorkChangeApp workChangeApp = (NRQueryWorkChangeApp) domain;
			return new NRQueryWorkChangeAppExport(base, workChangeApp.getGoOutAtr(), workChangeApp.getComeBackAtr(),
					workChangeApp.getWorkTimeName(), workChangeApp.getWorkTypeName(), workChangeApp.getHourMinuteLst());
		// 3: 出張申請
		case BUSINESS_TRIP_APPLICATION:
			NRQueryBussinessTripApp bussinessApp = (NRQueryBussinessTripApp) domain;
			return new NRQueryBussinessTripAppExport(base, bussinessApp.getWorkTimeName(),
					bussinessApp.getWorkTypeName(),
					bussinessApp.getPeriod().stream()
							.map(x -> new TripAppTimeZoneExport(x.getStartTime(), x.getEndTime()))
							.collect(Collectors.toList()));
		// 4: 直行直帰申請
		case GO_RETURN_DIRECTLY_APPLICATION:
			NRQueryGoBackDirectlyApp gobackApp = (NRQueryGoBackDirectlyApp) domain;
			return new NRQueryGoBackDirectlyAppExport(base, gobackApp.getGoOutAtr(), gobackApp.getComeBackAtr(),
					gobackApp.getWorkTimeName(), gobackApp.getWorkTypeName());
		// 6: 休出時間申請
		case HOLIDAY_WORK_APPLICATION:
			NRQueryHolidayWorkTimeApp holidayApp = (NRQueryHolidayWorkTimeApp) domain;
			return new NRQueryHolidayWorkTimeAppExport(base, holidayApp.getGoOutAtr(), holidayApp.getComeBackAtr(),
					holidayApp.getWorkTimeName(), holidayApp.getWorkTypeName(), holidayApp.getMidnightIimeOutside(),
					holidayApp.getHolidayQuotaLst().stream()
							.map(x -> new NRQueryHolidayQuotaExport(x.getHolidayQuotaName(), x.getHolidayQuotaTime()))
							.collect(Collectors.toList()),
					holidayApp.getReasonDissocsLst());
		// 7: 打刻申請
		case STAMP_APPLICATION:
			NRQueryStampApp stampApp = (NRQueryStampApp) domain;
			return new NRQueryStampAppExport(base,
					stampApp.getAppDetailLst().stream()
							.map(x -> new NRQueryStampAppDetailExport(x.getStampTypeName(), x.getContents()))
							.collect(Collectors.toList()));
		// 8: 時間休暇申請
		case ANNUAL_HOLIDAY_APPLICATION:
			NRQueryTimeLeaveApp annualApp = (NRQueryTimeLeaveApp) domain;
			return new NRQueryTimeLeaveAppExport(base,
					annualApp.getTimeLeavDetail().stream()
							.map(x -> new NRQueryTimeLeaveAppDetailExport(x.getReflectDest(), x.getTime()))
							.collect(Collectors.toList()));
		// 9: 遅刻早退取消申請
		case EARLY_LEAVE_CANCEL_APPLICATION:
			NRQueryLateCancelApp earlyLeavApp = (NRQueryLateCancelApp) domain;
			return new NRQueryLateCancelAppExport(base, earlyLeavApp.getLateCancelAppDetailLst());
		// 10: 振休振出申請
		case COMPLEMENT_LEAVE_APPLICATION:
			NRQueryFurikyuFurishutsuApp furikyuApp = (NRQueryFurikyuFurishutsuApp) domain;
			return new NRQueryFurikyuFurishutsuAppExport(base, furikyuApp.getWorkTypeName(),
					furikyuApp.getWorkTimeName(), furikyuApp.isFurikyu());
		// 15: 任意項目申請
		case OPTIONAL_ITEM_APPLICATION:
			NRQueryAnyItemApp optionalApp = (NRQueryAnyItemApp) domain;
			return new NRQueryAnyItemAppExport(base,
					optionalApp.getAnyItemDetailLst().stream()
							.map(x -> new NRQueryAnyItemAppDetailExport(x.getAnyItemName(), x.getValue()))
							.collect(Collectors.toList()));
		}

		return base;
	}
}
