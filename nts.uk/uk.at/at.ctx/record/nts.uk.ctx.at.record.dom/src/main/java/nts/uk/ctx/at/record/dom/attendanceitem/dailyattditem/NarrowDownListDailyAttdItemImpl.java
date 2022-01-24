package nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem.NarrowDownListDailyAttdItem.Require;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodRepository;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.NarrowDownListDailyAttdItemPub;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.operationsettings.TaskOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkMntSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;

@Stateless
public class NarrowDownListDailyAttdItemImpl implements NarrowDownListDailyAttdItemPub{
	
	@Inject 
	private DivergenceReasonInputMethodRepository divergenceReasonInputMethodRepository;
	@Inject 
	private TaskOperationSettingRepository operationSettingRepository;
	@Inject 
	private SupportOperationSettingRepository supportOperationSettingRepository;
	@Inject 
	private OvertimeWorkFrameRepository overtimeWorkFrameRepository;
	@Inject 
	private WorkdayoffFrameRepository workdayoffFrameRepository;
	@Inject 
	private DivergenceTimeRepository divergenceTimeRepository;
	@Inject 
	private BPTimeItemRepository bPTimeItemRepository;
	@Inject 
	private OptionalItemRepository optionalItemRepository;
	@Inject 
	private PremiumItemRepository premiumItemRepository;
	@Inject 
	private OutManageRepository outManageRepository;
	@Inject 
	private AggDeformedLaborSettingRepository aggDeformedLaborSettingRepository;
	@Inject 
	private FlexWorkMntSetRepository flexWorkMntSetRepository;
	@Inject 
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;
	@Inject 
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	@Inject 
	private ComSubstVacationRepository comSubstVacationRepository;
	@Inject 
	private TimeSpecialLeaveMngSetRepository timeSpecialLeaveMngSetRepository;
	@Inject 
	private NursingLeaveSettingRepository nursingLeaveSettingRepository;
	@Inject 
	private WorkManagementMultipleRepository workManagementMultipleRepository;
	@Inject 
	private TemporaryWorkUseManageRepository temporaryWorkUseManageRepository;

	@Override
	public List<Integer> get(String companyId, List<Integer> listAttdId) {
		Require require = new NarrowDownDailyAttdItemImpl();
		return NarrowDownListDailyAttdItem.get(require, companyId, listAttdId);
	}
	
	private class NarrowDownDailyAttdItemImpl implements NarrowDownListDailyAttdItem.Require {
		
		
		@Override
		public OptionLicense getOptionLicense() {
			return AppContexts.optionLicense();
		}
		
		@Override
		public List<DivergenceReasonInputMethod> getDivergenceReasonInputMethod(String companyId) {
			return divergenceReasonInputMethodRepository.getAllDivTime(companyId);
		}

		@Override
		public Optional<TaskOperationSetting> getTasksOperationSetting(String companyId) {
			return operationSettingRepository.getTasksOperationSetting(companyId);
		}
		
		@Override
		public Optional<SupportOperationSetting> getSupportOperationSetting(String companyId) {
			return supportOperationSettingRepository.getSupportOperationSetting(companyId);
		}
		
		@Override
		public List<OvertimeWorkFrame> getAllOvertimeWorkFrame(String companyId) {
			return overtimeWorkFrameRepository.getAllOvertimeWorkFrame(companyId);
		}
		
		@Override
		public List<WorkdayoffFrame> getAllWorkdayoffFrame(String companyId) {
			return workdayoffFrameRepository.getAllWorkdayoffFrame(companyId);
		}
		
		@Override
		public List<DivergenceTimeRoot> getAllDivTime(String companyId) {
			return divergenceTimeRepository.getAllDivTime(companyId);
		}
		
		@Override
		public List<BonusPayTimeItem> getListBonusPayTimeItem(String companyId) {
			return bPTimeItemRepository.getListBonusPayTimeByCid(companyId);
		}
		
		@Override
		public List<OptionalItem> findAllOptionalItem(String companyId) {
			return optionalItemRepository.findAll(companyId);
		}
		
		@Override
		public List<PremiumItem> findPremiumItemByCompanyID(String companyID) {
			return premiumItemRepository.findByCompanyID(companyID);
		}
		
		@Override
		public Optional<OutManage> findOutManageByID(String companyID) {
			return outManageRepository.findByID(companyID);
		}
		
		@Override
		public Optional<AggDeformedLaborSetting> findAggDeformedLaborSettingByCid(String companyId) {
			return aggDeformedLaborSettingRepository.findByCid(companyId);
		}
		
		@Override
		public Optional<FlexWorkSet> findFlexWorkSet(String companyId) {
			return flexWorkMntSetRepository.find(companyId);
		}
		
		@Override
		public AnnualPaidLeaveSetting findByCompanyId(String companyId) {
			return annualPaidLeaveSettingRepository.findByCompanyId(companyId);
		}
		
		@Override
		public CompensatoryLeaveComSetting findCompensatoryLeaveComSetting(String companyId) {
			return compensLeaveComSetRepository.find(companyId);
		}
		
		@Override
		public Optional<ComSubstVacation> findComSubstVacationById(String companyId) {
			return comSubstVacationRepository.findById(companyId);
		}
		
		@Override
		public Optional<TimeSpecialLeaveManagementSetting> findTimeSpecialLeaveManagementSetting(String companyId) {
			return timeSpecialLeaveMngSetRepository.findByCompany(companyId);
		}
		
		@Override
		public List<NursingLeaveSetting> findNursingLeaveSetting(String companyId) {
			return nursingLeaveSettingRepository.findByCompanyId(companyId);
		}
		
		@Override
		public Optional<WorkManagementMultiple> findWorkManagementMultiple(String companyID) {
			return workManagementMultipleRepository.findByCode(companyID);
		}
		
		@Override
		public Optional<TemporaryWorkUseManage> findTemporaryWorkUseManage(String companyId) {
			return temporaryWorkUseManageRepository.findByCid(companyId);
		}
		
	}
}
