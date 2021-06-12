package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SClsHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.createworkschedule.createschedulecommon.correctworkschedule.CorrectWorkSchedule;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobtitleHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpService;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterImportCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 *
 */
@Stateless
public class CreateWorkScheduleService {
    
    @Inject
    private WorkTypeRepository workTypeRepo;
    
    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;
    
    @Inject
    private BasicScheduleService basicScheduleService;
    
    @Inject
    private FixedWorkSettingRepository fixedWorkSettingRepository;
    
    @Inject
    private FlowWorkSettingRepository flowWorkSettingRepository;
    
    @Inject
    private FlexWorkSettingRepository flexWorkSettingRepository;
    
    @Inject
    private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
    
    @Inject
    private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
    
    @Inject
    private SharedAffJobtitleHisAdapter sharedAffJobtitleHisAdapter;
    
    @Inject
    private SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;
    
    @Inject
    private SyClassificationAdapter syClassificationAdapter;
    
    @Inject
    private BusinessTypeEmpService businessTypeEmpService;
    
    @Inject
    private WorkingConditionRepository workingConditionRepo;
    
    @Inject
    private WorkScheduleRepository workScheduleRepo;
    
    @Inject
    private ShiftMasterRepository shiftMasterRepository;
    
    @Inject
    private CorrectWorkSchedule correctWorkSchedule;
    
    @Inject
    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
    
    public List<ResultOfRegisteringWorkSchedule> register(ScheduleRegister param) {
        RequireImp require = new RequireImp(workTypeRepo, workTimeSettingRepository, basicScheduleService, fixedWorkSettingRepository, flowWorkSettingRepository, flexWorkSettingRepository, predetemineTimeSettingRepository, employmentHisScheduleAdapter, sharedAffJobtitleHisAdapter, sharedAffWorkPlaceHisAdapter, syClassificationAdapter, businessTypeEmpService, workingConditionRepo, workScheduleRepo, shiftMasterRepository, correctWorkSchedule, interimRemainDataMngRegisterDateChange);
        
        List<ResultOfRegisteringWorkSchedule> resultOfRegisteringWorkSchedule = new ArrayList<ResultOfRegisteringWorkSchedule>();
        // 作る(Require, 社員ID, 年月日, シフトマスタ取り込みコード, boolean)
        param.getTargets().forEach(x -> {
            resultOfRegisteringWorkSchedule.add(CreateWorkScheduleByImportCode.create(
                    require, 
                    x.getEmployeeId(), 
                    x.getDate(), 
                    x.getShiftmasterCode(), 
                    param.isOverWrite()));
        });
        
        return resultOfRegisteringWorkSchedule;
    }
    
    @AllArgsConstructor
    private class RequireImp implements CreateWorkScheduleByImportCode.Require {
        
        @Inject
        private WorkTypeRepository workTypeRepo;
        
        @Inject
        private WorkTimeSettingRepository workTimeSettingRepository;
        
        @Inject
        private BasicScheduleService basicScheduleService;
        
        @Inject
        private FixedWorkSettingRepository fixedWorkSettingRepository;
        
        @Inject
        private FlowWorkSettingRepository flowWorkSettingRepository;
        
        @Inject
        private FlexWorkSettingRepository flexWorkSettingRepository;
        
        @Inject
        private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
        
        @Inject
        private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
        
        @Inject
        private SharedAffJobtitleHisAdapter sharedAffJobtitleHisAdapter;
        
        @Inject
        private SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;
        
        @Inject
        private SyClassificationAdapter syClassificationAdapter;
        
        @Inject
        private BusinessTypeEmpService businessTypeEmpService;
        
        @Inject
        private WorkingConditionRepository workingConditionRepo;
        
        @Inject
        private WorkScheduleRepository workScheduleRepo;
        
        @Inject
        private ShiftMasterRepository shiftMasterRepository;
        
        @Inject
        private CorrectWorkSchedule correctWorkSchedule;
        
        @Inject
        private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

        @Override
        public Optional<WorkType> getWorkType(String workTypeCd) {
            return workTypeRepo.findByPK(AppContexts.user().companyId(), workTypeCd);
        }

        @Override
        public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
            return workTimeSettingRepository.findByCode(AppContexts.user().companyId(), workTimeCode);
        }

        @Override
        public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
            return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
        }

        @Override
        public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
            return fixedWorkSettingRepository.findByKey(AppContexts.user().companyId(), code.v()).get();
        }

        @Override
        public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
            return flowWorkSettingRepository.find(AppContexts.user().companyId(), code.v()).get();
        }

        @Override
        public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
            return flexWorkSettingRepository.find(AppContexts.user().companyId(), code.v()).get();
        }

        @Override
        public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
            return predetemineTimeSettingRepository.findByWorkTimeCode(AppContexts.user().companyId(), wktmCd.v()).get();
        }

        @Override
        public SharedSyEmploymentImport getAffEmploymentHistory(String employeeId, GeneralDate standardDate) {
            List<EmploymentPeriodImported> listEmpHist = employmentHisScheduleAdapter
                    .getEmploymentPeriod(Arrays.asList(employeeId), new DatePeriod(standardDate, standardDate));
            if(listEmpHist.isEmpty())
                return null;
            return new SharedSyEmploymentImport(listEmpHist.get(0).getEmpID(), listEmpHist.get(0).getEmploymentCd(), "",
                    listEmpHist.get(0).getDatePeriod());
        }

        @Override
        public SharedAffJobTitleHisImport getAffJobTitleHistory(String employeeId, GeneralDate standardDate) {
            List<SharedAffJobTitleHisImport> listAffJobTitleHis =  sharedAffJobtitleHisAdapter.findAffJobTitleHisByListSid(Arrays.asList(employeeId), standardDate);
            if(listAffJobTitleHis.isEmpty())
                return null;
            return listAffJobTitleHis.get(0);
        }

        @Override
        public SharedAffWorkPlaceHisImport getAffWorkplaceHistory(String employeeId, GeneralDate standardDate) {
            Optional<SharedAffWorkPlaceHisImport> rs = sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHis(employeeId, standardDate);
            return rs.isPresent() ? rs.get() : null;
        }

        @Override
        public SClsHistImport getClassificationHistory(String employeeId, GeneralDate standardDate) {
            Optional<SClsHistImported> imported = syClassificationAdapter.findSClsHistBySid(AppContexts.user().companyId(), employeeId, standardDate);
            if (!imported.isPresent()) {
                return null;
            }
            return new SClsHistImport(imported.get().getPeriod(), imported.get().getEmployeeId(),
                    imported.get().getClassificationCode(), imported.get().getClassificationName());
        }

        @Override
        public Optional<BusinessTypeOfEmployee> getBusinessType(String employeeId, GeneralDate standardDate) {
            List<BusinessTypeOfEmployee> list = businessTypeEmpService.getData(employeeId, standardDate);
            if (list.isEmpty()) 
                return Optional.empty();
            return Optional.of(list.get(0));
        }

        @Override
        public Optional<WorkingConditionItem> getWorkingConditionHistory(String employeeId, GeneralDate standardDate) {
            Optional<WorkingConditionItem> rs = workingConditionRepo.getWorkingConditionItemByEmpIDAndDate(AppContexts.user().companyId(), standardDate, employeeId);
            return rs;
        }

        @Override
        public String getLoginEmployeeId() {
            return AppContexts.user().employeeId();
        }

        @Override
        public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
            Optional<WorkSchedule> rs = workScheduleRepo.get(employeeId, date);
            return rs;
        }

        @Override
        public Optional<ShiftMaster> getShiftMaster(ShiftMasterImportCode importCode) {
            return shiftMasterRepository.getByShiftMaterCd(AppContexts.user().companyId(), importCode.v());
        }

        @Override
        public WorkSchedule correctWorkSchedule(WorkSchedule workSchedule) {
            WorkSchedule rs = correctWorkSchedule.correctWorkSchedule(workSchedule, workSchedule.getEmployeeID(), workSchedule.getYmd());
            return rs;
        }

        @Override
        public void insertWorkSchedule(WorkSchedule workSchedule) {
            workScheduleRepo.insert(workSchedule);
        }

        @Override
        public void updateWorkSchedule(WorkSchedule workSchedule) {
            workScheduleRepo.update(workSchedule);
        }

        @Override
        public void registerTemporaryData(String employeeId, GeneralDate date) {
            interimRemainDataMngRegisterDateChange.registerDateChange(AppContexts.user().companyId(), employeeId, Arrays.asList(date));
        }
        
    }
}
