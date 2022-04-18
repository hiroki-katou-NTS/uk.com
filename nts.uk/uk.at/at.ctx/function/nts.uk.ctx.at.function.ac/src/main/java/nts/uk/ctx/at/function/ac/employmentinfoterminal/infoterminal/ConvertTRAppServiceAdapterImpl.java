package nts.uk.ctx.at.function.ac.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.ConvertTRAppServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AnnualHolidayReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppLateReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppOverTimeReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppStampReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppVacationReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppWorkChangeReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.AppWorkHolidayReceptionDataImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application.ApplicationReceptionDataImport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AnnualHolidayReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppLateReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppOverTimeReceptionDataExport.AppOverTimBuilder;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppStampReceptionDataExport.AppStampBuilder;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppVacationReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppWorkChangeReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppWorkHolidayReceptionDataExport.AppWorkHolidayBuilder;
import nts.uk.ctx.at.request.pub.application.infoterminal.ApplicationReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.ConvertTRAppServicePub;

@Stateless
public class ConvertTRAppServiceAdapterImpl implements ConvertTRAppServiceAdapter {

	@Inject
	private ConvertTRAppServicePub pub;

	@Override
	public Optional<AtomTask> converData(String empInfoTerCode, String contractCode,
			ApplicationReceptionDataImport recept) {

		return pub.converData(empInfoTerCode, contractCode, covertTo(recept));
	}

	public ApplicationReceptionDataExport covertTo(ApplicationReceptionDataImport data) {

		ApplicationReceptionDataExport appDom = new ApplicationReceptionDataExport(data.getIdNumber(),
				data.getApplicationCategory(), data.getYmd(), data.getTime());
		ApplicationCategory cate = ApplicationCategory.valueStringOf(data.getApplicationCategory());
		switch (cate) {

		// 打刻申請
		case STAMP:
			AppStampReceptionDataImport appStampData = (AppStampReceptionDataImport) data;
			return new AppStampBuilder(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(), data.getTime(),
					appStampData.getGoOutCategory(), appStampData.getTypeBeforeAfter())
							.appTime(appStampData.getAppTime()).appYMD(appStampData.getAppYMD())
							.stampType(appStampData.getStampType()).reason(appStampData.getReason()).build();

		// 残業申請
		case OVERTIME:
			// AppOverTime
			AppOverTimeReceptionDataImport overTimeData = (AppOverTimeReceptionDataImport) data;
			return new AppOverTimBuilder(overTimeData.getIdNumber(), overTimeData.getApplicationCategory(),
					data.getYmd(), data.getTime(), overTimeData.getOvertimeHour1(),
					overTimeData.getOvertimeNo1()).overtimeHour2(overTimeData.getOvertimeHour2())
							.overtimeNo2(overTimeData.getOvertimeNo2()).overtimeHour3(overTimeData.getOvertimeHour3())
							.overtimeNo3(overTimeData.getOvertimeNo3())
							.typeBeforeAfter(overTimeData.getTypeBeforeAfter()).appYMD(overTimeData.getAppYMD())
							.reason(overTimeData.getReason()).build();

		// 休暇申請
		case VACATION:
			// AppAbsence
			AppVacationReceptionDataImport appVacData = (AppVacationReceptionDataImport) data;
			return new AppVacationReceptionDataExport(
					new ApplicationReceptionDataExport(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(),
							data.getTime()),
					appVacData.getTypeBeforeAfter(), appVacData.getStartDate(), appVacData.getEndDate(),
					appVacData.getWorkType(), appVacData.getReason());

		// 勤務変更申請
		case WORK_CHANGE:
			AppWorkChangeReceptionDataImport appWorkData = (AppWorkChangeReceptionDataImport) data;
			return new AppWorkChangeReceptionDataExport(appDom, appWorkData.getTypeBeforeAfter(),
					appWorkData.getStartDate(), appWorkData.getEndDate(), appWorkData.getWorkTime(),
					appWorkData.getReason());

		// 休日出勤時間申請
		case WORK_HOLIDAY:
			AppWorkHolidayReceptionDataImport appWorkHol = (AppWorkHolidayReceptionDataImport) data;
			return new AppWorkHolidayBuilder(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(),
					data.getTime(), appWorkHol.getBreakTime1(), appWorkHol.getBreakNo1())
							.breakTime2(appWorkHol.getBreakTime2()).breakNo2(appWorkHol.getBreakNo2())
							.breakTime3(appWorkHol.getBreakTime3()).breakNo3(appWorkHol.getBreakNo3())
							.typeBeforeAfter(appWorkHol.getTypeBeforeAfter()).appYMD(appWorkHol.getAppYMD())
							.reason(appWorkHol.getReason()).build();

		// 遅刻早退取消申請
		case LATE:
			AppLateReceptionDataImport appLateData = (AppLateReceptionDataImport) data;
			return new AppLateReceptionDataExport(appDom, appLateData.getTypeBeforeAfter(), appLateData.getAppYMD(),
					appLateData.getReasonLeave(), appLateData.getReason());

		// 時間年休申請
		case ANNUAL:
			AnnualHolidayReceptionDataImport annualHoli = (AnnualHolidayReceptionDataImport) data;
			return new AnnualHolidayReceptionDataExport(
					new ApplicationReceptionDataExport(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(),
							data.getTime()),
					annualHoli.getAnnualHolidayType(), annualHoli.getAnnualHolidayTime(),
					annualHoli.getTypeBeforeAfter(), annualHoli.getAppYMD(), annualHoli.getReason());

		default:
			return null;
		}
	}

}
