package nts.uk.ctx.at.request.pubimp.application.infoterminal.nrwebquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.GetAllNRWebQueryAppDetail;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain.NRQueryApp;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.holidayshipment.ApplicationForHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethodI;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.infra.repository.application.FindAppCommonForNR;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.GetAllNRWebQueryAppDetailPub;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRQueryAppExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.NRWebQuerySidDateParameterExport;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class GetAllNRWebQueryAppDetailPubImpl implements GetAllNRWebQueryAppDetailPub {

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private OptionalItemRepository optionalItemRepository;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private DivergenceReasonInputMethodI divergenceReasonInputMethodI;

	@Inject
	private FindAppCommonForNR<AppOverTime> appOverTimeRepository;

	@Inject
	private OvertimeWorkFrameRepository overtimeWorkFrameRepository;

	@Inject
	private FindAppCommonForNR<ApplyForLeave> applyForLeaveRepository;

	@Inject
	private FindAppCommonForNR<AppWorkChange> appWorkChangeRepository;

	@Inject
	private FindAppCommonForNR<BusinessTrip> businessTripRepository;

	@Inject
	private FindAppCommonForNR<GoBackDirectly> goBackDirectlyRepository;

	@Inject
	private FindAppCommonForNR<AppHolidayWork> appHolidayWorkRepository;

	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;

	@Inject
	private FindAppCommonForNR<AppStamp> appStampRepository;

	@Inject
	private FindAppCommonForNR<AppRecordImage> appRecordImageRepository;

	@Inject
	private FindAppCommonForNR<TimeLeaveApplication> timeLeaveApplicationRepository;

	@Inject
	private FindAppCommonForNR<ArrivedLateLeaveEarly> arrivedLateLeaveEarlyRepository;

	@Inject
	private FindAppCommonForNR<OptionalItemApplication> optionalItemApplicationRepository;
	
	@Inject
	private FindAppCommonForNR<RecruitmentApp> recruitmentAppRepository;
	
	@Inject
	private FindAppCommonForNR<AbsenceLeaveApp> absenceLeaveAppRepository;
	
	@Override
	public List<? extends NRQueryAppExport> getAll(NRWebQuerySidDateParameterExport param, DatePeriod period) {
		RequireImpl impl = new RequireImpl();
		List<? extends NRQueryApp> queryApp = GetAllNRWebQueryAppDetail.getAll(impl, NRQueryAppConvert.toDomain(param),
				period);
		return queryApp.stream().map(x -> NRQueryAppConvert.fromDomain(x)).collect(Collectors.toList());
	}

	@AllArgsConstructor
	public class RequireImpl implements GetAllNRWebQueryAppDetail.Require {

		@Override
		public List<AppOverTime> findOvertimeWithSidDate(String companyId, String sid, GeneralDate date) {
			return appOverTimeRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<AppOverTime> findOvertimeWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return appOverTimeRepository.findWithSidDateApptype(companyId, sid, date, inputDate, prePostAtr);
		}

		@Override
		public List<AppOverTime> findOvertimeWithSidDatePeriod(String companyId, String sid, DatePeriod period) {
			return appOverTimeRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<OvertimeWorkFrame> getOvertimeWorkFrameByFrameNos(String companyId,
				List<Integer> overtimeWorkFrNos) {
			return overtimeWorkFrameRepository.getOvertimeWorkFrameByFrameNos(companyId, overtimeWorkFrNos);
		}

		@Override
		public List<OptionalItem> findOptionalItemByListNos(String companyId, List<Integer> optionalitemNos) {
			return optionalItemRepository.findByListNos(companyId, optionalitemNos);
		}

		@Override
		public Optional<WorkTimeSetting> findWorkTimeByCode(String companyId, String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public Optional<WorkType> findWorkTypeByPK(String companyId, String workTypeCd) {
			return workTypeRepository.findByPK(companyId, workTypeCd);
		}

		@Override
		public List<DivergenceReasonInputMethod> getDivergenceReason(String companyId, List<Integer> lstNo) {
			return divergenceReasonInputMethodI.getData(companyId, lstNo);
		}

		@Override
		public List<ApplyForLeave> findForLeaveWithSidDate(String companyId, String sid, GeneralDate date) {
			return applyForLeaveRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<ApplyForLeave> findForLeaveWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return applyForLeaveRepository.findWithSidDateApptype(companyId, sid, date, inputDate, prePostAtr);
		}

		@Override
		public List<ApplyForLeave> findForLeaveWithSidDatePeriod(String companyId, String sid, DatePeriod period) {
			return applyForLeaveRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<AppWorkChange> findAppWorkChangeWithSidDate(String companyId, String sid, GeneralDate date) {
			return appWorkChangeRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<AppWorkChange> findAppWorkChangeWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return appWorkChangeRepository.findWithSidDateApptype(companyId, sid, date, inputDate,
					prePostAtr);
		}

		@Override
		public List<AppWorkChange> findAppWorkChangeWithSidDatePeriod(String companyId, String sid, DatePeriod period) {
			return appWorkChangeRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<BusinessTrip> findBussinessTripWithSidDate(String companyId, String sid, GeneralDate date) {
			return businessTripRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<BusinessTrip> findBussinessTripWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return businessTripRepository.findWithSidDateApptype(companyId, sid, date, inputDate,
					prePostAtr);
		}

		@Override
		public List<BusinessTrip> findBussinessTripWithSidDatePeriod(String companyId, String sid, DatePeriod period) {
			return businessTripRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<GoBackDirectly> findGoBackDirectlyWithSidDate(String companyId, String sid, GeneralDate date) {
			return goBackDirectlyRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<GoBackDirectly> findGoBackDirectlyWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return goBackDirectlyRepository.findWithSidDateApptype(companyId, sid, date, inputDate,
					prePostAtr);
		}

		@Override
		public List<GoBackDirectly> findGoBackDirectlyWithSidDatePeriod(String companyId, String sid,
				DatePeriod period) {
			return goBackDirectlyRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<AppHolidayWork> findAppHolidayWorkWithSidDate(String companyId, String sid, GeneralDate date) {
			return appHolidayWorkRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<AppHolidayWork> findAppHolidayWorkWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return appHolidayWorkRepository.findWithSidDateApptype(companyId, sid, date, inputDate,
					prePostAtr);
		}

		@Override
		public List<AppHolidayWork> findAppHolidayWorkWithSidDatePeriod(String companyId, String sid,
				DatePeriod period) {
			return appHolidayWorkRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<WorkdayoffFrame> findWorkdayoffFrame(String companyId, List<Integer> workdayoffFrNos) {
			return workdayoffFrameRepository.getWorkdayoffFrameBy(companyId, workdayoffFrNos);
		}

		@Override
		public List<AppStamp> findAppStampWithSidDate(String companyId, String sid, GeneralDate date) {
			return appStampRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<AppStamp> findAppStampWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return appStampRepository.findWithSidDateApptype(companyId, sid, date, inputDate, prePostAtr);
		}

		@Override
		public List<AppStamp> findAppStampWithSidDatePeriod(String companyId, String sid, DatePeriod period) {
			return appStampRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<AppRecordImage> findAppTimeRecordWithSidDate(String companyId, String sid, GeneralDate date) {
			return appRecordImageRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<AppRecordImage> findAppTimeRecordWithSidDateApptype(String companyId, String sid, GeneralDate date,
				GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return appRecordImageRepository.findWithSidDateApptype(companyId, sid, date, inputDate,
					prePostAtr);
		}

		@Override
		public List<AppRecordImage> findAppTimeRecordWithSidDatePeriod(String companyId, String sid,
				DatePeriod period) {
			return appRecordImageRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<TimeLeaveApplication> findTimeLeaveWithSidDate(String companyId, String sid, GeneralDate date) {
			return timeLeaveApplicationRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<TimeLeaveApplication> findTimeLeaveWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return timeLeaveApplicationRepository.findWithSidDateApptype(companyId, sid, date, inputDate,
					prePostAtr);
		}

		@Override
		public List<TimeLeaveApplication> findTimeLeaveWithSidDatePeriod(String companyId, String sid,
				DatePeriod period) {
			return timeLeaveApplicationRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<ArrivedLateLeaveEarly> findArrivedLateLeaveEarlyWithSidDate(String companyId, String sid,
				GeneralDate date) {
			return arrivedLateLeaveEarlyRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<ArrivedLateLeaveEarly> findArrivedLateLeaveEarlyWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return arrivedLateLeaveEarlyRepository.findWithSidDateApptype(companyId, sid, date,
					inputDate, prePostAtr);
		}

		@Override
		public List<ArrivedLateLeaveEarly> findArrivedLateLeaveEarlyWithSidDatePeriod(String companyId, String sid,
				DatePeriod period) {
			return arrivedLateLeaveEarlyRepository.findWithSidDatePeriod(companyId, sid, period);
		}

		@Override
		public List<ApplicationForHolidays> findApplicationForHolidaysWithSidDate(String companyId, String sid,
				GeneralDate date) {
			List<ApplicationForHolidays> result = new ArrayList<>();
			result.addAll(recruitmentAppRepository.findWithSidDate(companyId, sid, date));
			result.addAll(absenceLeaveAppRepository.findWithSidDate(companyId, sid, date));
			return result;
		}

		@Override
		public List<ApplicationForHolidays> findApplicationForHolidaysWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			List<ApplicationForHolidays> result = new ArrayList<>();
			result.addAll(recruitmentAppRepository.findWithSidDateApptype(companyId, sid, date, inputDate, prePostAtr));
			result.addAll(absenceLeaveAppRepository.findWithSidDateApptype(companyId, sid, date, inputDate, prePostAtr));
			return result;
		}

		@Override
		public List<ApplicationForHolidays> findApplicationForHolidaysWithSidDatePeriod(String companyId, String sid,
				DatePeriod period) {
			List<ApplicationForHolidays> result = new ArrayList<>();
			result.addAll(recruitmentAppRepository.findWithSidDatePeriod(companyId, sid, period));
			result.addAll(absenceLeaveAppRepository.findWithSidDatePeriod(companyId, sid, period));
			return result;
		}

		@Override
		public List<OptionalItemApplication> findOptionalItemWithSidDate(String companyId, String sid,
				GeneralDate date) {
			return optionalItemApplicationRepository.findWithSidDate(companyId, sid, date);
		}

		@Override
		public List<OptionalItemApplication> findOptionalItemWithSidDateApptype(String companyId, String sid,
				GeneralDate date, GeneralDateTime inputDate, PrePostAtr prePostAtr) {
			return optionalItemApplicationRepository.findWithSidDateApptype(companyId, sid, date, inputDate, prePostAtr);
		}

		@Override
		public List<OptionalItemApplication> findOptionalItemWithSidDatePeriod(String companyId, String sid,
				DatePeriod period) {
			return optionalItemApplicationRepository.findWithSidDatePeriod(companyId, sid, period);
		}

	}
}
