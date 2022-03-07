package nts.uk.ctx.at.record.pubimp.dailyprocess.attendancetime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.DateHistoryCache;
import nts.arc.layer.app.cache.KeyDateHistoryCache;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyHolidayWorkPub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyHolidayWorkPubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyHolidayWorkPubImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.HolidayWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeSheet;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;

@Stateless
public class DailyHolidayWorkPubImpl implements DailyHolidayWorkPub{
	
	/** 社員雇用履歴 */
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	/** 代休管理設定（会社別） */
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepo;
	/** 雇用の代休管理設定 */
	@Inject
	private CompensLeaveEmSetRepository compensLeaveEmSetRepo;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private FixedWorkSettingRepository fixedWorkSetRepo;
	
	@Inject
	private FlowWorkSettingRepository flowWorkSetRepo;
	
	@Inject
	private FlexWorkSettingRepository flexWorkSetRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;
	
	@Override
	public DailyHolidayWorkPubExport calcHolidayWorkTransTime(DailyHolidayWorkPubImport imp) {
		
		OverTimeSheet.Require require = new RequireImpl();
		
		List<HolidayWorkFrameTime> result = HolidayWorkTimeSheet.transProcess(
				require, imp.getEmployeeId(), imp.getYmd(),
				imp.getWorkType(), imp.getAfterCalcUpperTimeList(), imp.getEachWorkTimeSet(),
				imp.getEachCompanyTimeSet());
		return DailyHolidayWorkPubExport.create(result);
	}
	
	private class RequireImpl implements OverTimeSheet.Require {
		
		private final KeyDateHistoryCache<String, SEmpHistoryImport> historyCache =
				KeyDateHistoryCache.incremental((employeeId, date) ->
				sysEmploymentHisAdapter.findSEmpHistBySid(AppContexts.user().companyId(), employeeId, date)
				.map(h -> DateHistoryCache.Entry.of(h.getPeriod(), h)));
	
		public RequireImpl(){
			
		}
		
		@Override
		public Optional<CompensatoryLeaveComSetting> compensatoryLeaveComSetting(String companyId) {
			return Optional.ofNullable(compensLeaveComSetRepo.find(companyId));
		}
		
		@Override
		public Optional<CompensatoryLeaveEmSetting> compensatoryLeaveEmSetting(String companyId,
				String employmentCode) {
			return Optional.ofNullable(compensLeaveEmSetRepo.find(companyId, employmentCode));
		}
		
		@Override
		public Optional<SEmpHistoryImport> getSEmpHistoryImport(String employeeId, GeneralDate baseDate) {
			return this.historyCache.get(employeeId, baseDate);
		}
		
		//private CheckDateForManageCmpLeaveService checkDateForManageCmpLeaveService;
			//this.checkDateForManageCmpLeaveService = checkDateForManageCmpLeaveService;
		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return workTimeSettingRepo.findByCode(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return fixedWorkSetRepo.findByKey(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flowWorkSetRepo.find(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flexWorkSetRepo.find(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepository.findByPK(companyId, workTypeCode.v());
		}

		@Override
		public OptionLicense getOptionLicense() {
			return AppContexts.optionLicense();
		}
	}
}
