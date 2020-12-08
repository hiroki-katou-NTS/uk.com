package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.application;

import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryAnyItemAppDetailImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryAnyItemAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryBussinessTripAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryFurikyuFurishutsuAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryGoBackDirectlyAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryHolidayQuotaImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryHolidayWorkTimeAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryLateCancelAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryOvertimeAppAnyItemImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryOvertimeAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryOvertimeQuotaImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryStampAppDetailImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryStampAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryTimeDigesLeavNameImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryTimeLeaveAppDetailImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryTimeLeaveAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryVacationAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryWorkChangeAppImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.TripAppTimeZoneImport;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryAnyItemAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryBussinessTripAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryFurikyuFurishutsuAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryGoBackDirectlyAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryHolidayWorkTimeAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryLateCancelAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryOvertimeAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryStampAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryTimeLeaveAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryVacationAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryWorkChangeAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRWebQueryAppParameterExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRWebQueryParameterExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRWebQuerySidDateParameterExport;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;

public class NRQueryAppConvertFunc {

	public static NRWebQuerySidDateParameterExport toDomain(NRWebQuerySidDateParameter Import) {
		Optional<NRWebQueryAppParameterExport> application = Import.getNrWebQuery().getApplication()
				.map(x -> new NRWebQueryAppParameterExport(x.getDate(), x.getKbn(), x.getJikbn(), x.getNdate()));
		NRWebQueryParameterExport nrWebQuery = new NRWebQueryParameterExport(Import.getNrWebQuery().getContractCode(),
				Import.getNrWebQuery().getCno(), Import.getNrWebQuery().getVer(), Import.getNrWebQuery().getType(),
				Import.getNrWebQuery().getYm(), application);
		return new NRWebQuerySidDateParameterExport(Import.getCid(), Import.getSid(), nrWebQuery);
	}

	public static NRQueryAppImport fromDomain(NRQueryAppExport domain) {
		NRQueryAppImport base = new NRQueryAppImport(domain.getAppDate(), domain.getInputDate(), domain.getAppName(),
				domain.getAppType(), domain.getBeforeAfterType(), domain.getApprovalStatus()) {
			@Override
			public String createXml() {
				return "";
			}

			@Override
			public String createHtml() {
				return "";
			}

		};
		switch (EnumAdaptor.valueOf(domain.getAppType(), ApplicationTypeShare.class)) {
		// 残業申請
		case OVER_TIME_APPLICATION:
			NRQueryOvertimeAppExport appOvertime = (NRQueryOvertimeAppExport) domain;
			return new NRQueryOvertimeAppImport(base, appOvertime.getMidnightIimeOutside(),
					appOvertime.getFlexOvertime(), appOvertime.getOverTimeWoktypeName(),
					appOvertime.getOverTimeZoneName(),
					appOvertime.getAnyItemLst().stream()
							.map(x -> new NRQueryOvertimeAppAnyItemImport(x.getName(), x.getValue()))
							.collect(Collectors.toList()), // NRQueryOvertimeAppAnyItem
					appOvertime.getReasonDissocsLst(),
					appOvertime.getOvertimeQuotalst().stream()
							.map(x -> new NRQueryOvertimeQuotaImport(x.getOvertimeQuotaName(), x.getOvertimeHours()))
							.collect(Collectors.toList()));
		// 1: 休暇申請
		case ABSENCE_APPLICATION:
			NRQueryVacationAppExport absenceApp = (NRQueryVacationAppExport) domain;
			return new NRQueryVacationAppImport(base, absenceApp.getVacationType(), absenceApp.getWorkTypeName(),
					absenceApp.getWorkTimeName(),
					absenceApp.getTimeDigestLeavNameLst().stream()
							.map(x -> new NRQueryTimeDigesLeavNameImport(x.getTimeDigestName(), x.getTimeDigestHours()))
							.collect(Collectors.toList()));
		// 2: 勤務変更申請
		case WORK_CHANGE_APPLICATION:
			NRQueryWorkChangeAppExport workChangeApp = (NRQueryWorkChangeAppExport) domain;
			return new NRQueryWorkChangeAppImport(base, workChangeApp.getGoOutAtr(), workChangeApp.getComeBackAtr(),
					workChangeApp.getWorkTimeName(), workChangeApp.getWorkTypeName(), workChangeApp.getHourMinuteLst());
		// 3: 出張申請
		case BUSINESS_TRIP_APPLICATION:
			NRQueryBussinessTripAppExport bussinessApp = (NRQueryBussinessTripAppExport) domain;
			return new NRQueryBussinessTripAppImport(base, bussinessApp.getWorkTimeName(),
					bussinessApp.getWorkTypeName(),
					bussinessApp.getPeriod().stream()
							.map(x -> new TripAppTimeZoneImport(x.getStartTime(), x.getEndTime()))
							.collect(Collectors.toList()));
		// 4: 直行直帰申請
		case GO_RETURN_DIRECTLY_APPLICATION:
			NRQueryGoBackDirectlyAppExport gobackApp = (NRQueryGoBackDirectlyAppExport) domain;
			return new NRQueryGoBackDirectlyAppImport(base, gobackApp.getGoOutAtr(), gobackApp.getComeBackAtr(),
					gobackApp.getWorkTimeName(), gobackApp.getWorkTypeName());
		// 6: 休出時間申請
		case HOLIDAY_WORK_APPLICATION:
			NRQueryHolidayWorkTimeAppExport holidayApp = (NRQueryHolidayWorkTimeAppExport) domain;
			return new NRQueryHolidayWorkTimeAppImport(base, holidayApp.getGoOutAtr(), holidayApp.getComeBackAtr(),
					holidayApp.getWorkTimeName(), holidayApp.getWorkTypeName(), holidayApp.getMidnightIimeOutside(),
					holidayApp.getHolidayQuotaLst().stream()
							.map(x -> new NRQueryHolidayQuotaImport(x.getHolidayQuotaName(), x.getHolidayQuotaTime()))
							.collect(Collectors.toList()),
					holidayApp.getReasonDissocsLst());
		// 7: 打刻申請
		case STAMP_APPLICATION:
			NRQueryStampAppExport stampApp = (NRQueryStampAppExport) domain;
			return new NRQueryStampAppImport(base,
					stampApp.getAppDetailLst().stream()
							.map(x -> new NRQueryStampAppDetailImport(x.getStampTypeName(), x.getContents()))
							.collect(Collectors.toList()));
		// 8: 時間休暇申請
		case ANNUAL_HOLIDAY_APPLICATION:
			NRQueryTimeLeaveAppExport annualApp = (NRQueryTimeLeaveAppExport) domain;
			return new NRQueryTimeLeaveAppImport(base,
					annualApp.getTimeLeavDetail().stream()
							.map(x -> new NRQueryTimeLeaveAppDetailImport(x.getReflectDest(), x.getTime()))
							.collect(Collectors.toList()));
		// 9: 遅刻早退取消申請
		case EARLY_LEAVE_CANCEL_APPLICATION:
			NRQueryLateCancelAppExport earlyLeavApp = (NRQueryLateCancelAppExport) domain;
			return new NRQueryLateCancelAppImport(base, earlyLeavApp.getLateCancelAppDetailLst());
		// 10: 振休振出申請
		case COMPLEMENT_LEAVE_APPLICATION:
			NRQueryFurikyuFurishutsuAppExport furikyuApp = (NRQueryFurikyuFurishutsuAppExport) domain;
			return new NRQueryFurikyuFurishutsuAppImport(base, furikyuApp.getWorkTypeName(),
					furikyuApp.getWorkTimeName(), furikyuApp.isFurikyu());
		// 15: 任意項目申請
		case OPTIONAL_ITEM_APPLICATION:
			NRQueryAnyItemAppExport optionalApp = (NRQueryAnyItemAppExport) domain;
			return new NRQueryAnyItemAppImport(base,
					optionalApp.getAnyItemDetailLst().stream()
							.map(x -> new NRQueryAnyItemAppDetailImport(x.getAnyItemName(), x.getValue()))
							.collect(Collectors.toList()));
		}

		return base;
	}
}
