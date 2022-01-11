package nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem;

import java.util.List;
import java.util.Optional;

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

public class NarrowDownListDailyAttdItemImpl implements NarrowDownListDailyAttdItemPub{
	

	@Override
	public List<Integer> get(String companyId, List<Integer> listAttdId) {
		Require require = new NarrowDownDailyAttdItemImpl();
		return NarrowDownListDailyAttdItem.get(require, companyId, listAttdId);
	}
	
	private static class NarrowDownDailyAttdItemImpl implements NarrowDownListDailyAttdItem.Require {
		
		
		@Override
		public OptionLicense getOptionLicense() {
			return AppContexts.optionLicense();
		}
		
		@Inject 
		private DivergenceReasonInputMethodRepository divergenceReasonInputMethodRepository;

		@Override
		public List<DivergenceReasonInputMethod> getDivergenceReasonInputMethod(String companyId) {
			return divergenceReasonInputMethodRepository.getAllDivTime(companyId);
		}
		
		@Inject 
		private TaskOperationSettingRepository operationSettingRepository;

		@Override
		public Optional<TaskOperationSetting> getTasksOperationSetting(String companyId) {
			return operationSettingRepository.getTasksOperationSetting(companyId);
		}

		@Inject 
		private SupportOperationSettingRepository supportOperationSettingRepository;
		@Override
		public Optional<SupportOperationSetting> getSupportOperationSetting(String companyId) {
			// TODO Auto-generated method stub
			return supportOperationSettingRepository.getSupportOperationSetting(companyId);
		}

		@Inject 
		private OvertimeWorkFrameRepository overtimeWorkFrameRepository;
		@Override
		public List<OvertimeWorkFrame> getAllOvertimeWorkFrame(String companyId) {
			// TODO Auto-generated method stub
			return overtimeWorkFrameRepository.getAllOvertimeWorkFrame(companyId);
		}

		@Inject 
		private WorkdayoffFrameRepository workdayoffFrameRepository;
		@Override
		public List<WorkdayoffFrame> getAllWorkdayoffFrame(String companyId) {
			// TODO Auto-generated method stub
			return workdayoffFrameRepository.getAllWorkdayoffFrame(companyId);
		}

		@Inject 
		private DivergenceTimeRepository divergenceTimeRepository;
		@Override
		public List<DivergenceTimeRoot> getAllDivTime(String companyId) {
			// TODO Auto-generated method stub
			return divergenceTimeRepository.getAllDivTime(companyId);
		}

		@Inject 
		private BPTimeItemRepository bPTimeItemRepository;
		@Override
		public List<BonusPayTimeItem> getListBonusPayTimeItem(String companyId) {
			// TODO Auto-generated method stub
			return bPTimeItemRepository.getListBonusPayTimeItem(companyId);
		}

		@Inject 
		private OptionalItemRepository optionalItemRepository;
		@Override
		public List<OptionalItem> findAllOptionalItem(String companyId) {
			// TODO Auto-generated method stub
			return optionalItemRepository.findAll(companyId);
		}

		@Inject 
		private PremiumItemRepository premiumItemRepository;
		@Override
		public List<PremiumItem> findPremiumItemByCompanyID(String companyID) {
			// TODO Auto-generated method stub
			return premiumItemRepository.findAllIsUse(companyID);
		}

		@Inject 
		private OutManageRepository outManageRepository;
		@Override
		public Optional<OutManage> findOutManageByID(String companyID) {
			// TODO Auto-generated method stub
			return outManageRepository.findByID(companyID);
		}

		@Inject 
		private AggDeformedLaborSettingRepository aggDeformedLaborSettingRepository;
		@Override
		public Optional<AggDeformedLaborSetting> findAggDeformedLaborSettingByCid(String companyId) {
			// TODO Auto-generated method stub
			return aggDeformedLaborSettingRepository.findByCid(companyId);
		}

		@Inject 
		private FlexWorkMntSetRepository flexWorkMntSetRepository;
		@Override
		public Optional<FlexWorkSet> findFlexWorkSet(String companyId) {
			// TODO Auto-generated method stub
			return flexWorkMntSetRepository.find(companyId);
		}

		@Inject 
		private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;
		@Override
		public AnnualPaidLeaveSetting findByCompanyId(String companyId) {
			// TODO Auto-generated method stub
			return annualPaidLeaveSettingRepository.findByCompanyId(companyId);
		}

		@Inject 
		private CompensLeaveComSetRepository compensLeaveComSetRepository;
		@Override
		public CompensatoryLeaveComSetting findCompensatoryLeaveComSetting(String companyId) {
			// TODO Auto-generated method stub
			return compensLeaveComSetRepository.find(companyId);
		}

		@Inject 
		private ComSubstVacationRepository comSubstVacationRepository;
		@Override
		public Optional<ComSubstVacation> findComSubstVacationById(String companyId) {
			// TODO Auto-generated method stub
			return comSubstVacationRepository.findById(companyId);
		}

		@Inject 
		private TimeSpecialLeaveMngSetRepository timeSpecialLeaveMngSetRepository;
		@Override
		public Optional<TimeSpecialLeaveManagementSetting> findTimeSpecialLeaveManagementSetting(String companyId) {
			// TODO Auto-generated method stub
			return timeSpecialLeaveMngSetRepository.findByCompany(companyId);
		}

		@Inject 
		private NursingLeaveSettingRepository nursingLeaveSettingRepository;
		@Override
		public List<NursingLeaveSetting> findNursingLeaveSetting(String companyId) {
			// TODO Auto-generated method stub
			return nursingLeaveSettingRepository.findByCompanyId(companyId);
		}

		@Inject 
		private WorkManagementMultipleRepository workManagementMultipleRepository;
		@Override
		public Optional<WorkManagementMultiple> findWorkManagementMultiple(String companyID) {
			// TODO Auto-generated method stub
			return workManagementMultipleRepository.findByCode(companyID);
		}

		@Inject 
		private TemporaryWorkUseManageRepository temporaryWorkUseManageRepository;
		@Override
		public Optional<TemporaryWorkUseManage> findTemporaryWorkUseManage(String companyId) {
			// TODO Auto-generated method stub
			return temporaryWorkUseManageRepository.findByCid(companyId);
		}
		
	}
}
