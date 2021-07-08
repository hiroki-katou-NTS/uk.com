package nts.uk.ctx.at.schedule.app.find.schedule.workplace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.app.command.schedule.workplace.ScheduleRegisterCommand;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SClsHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.createworkschedule.createschedulecommon.correctworkschedule.CorrectWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.CreateWorkScheduleService;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ResultOfRegisteringWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
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
public class ScheduleRegisterCommandHandler {
    
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
    
    @Inject
    private CreateWorkScheduleService createWorkScheduleService;
    
    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    public List<RegisterWorkScheduleOutput> register(ScheduleRegisterCommand command) {
        List<RegisterWorkScheduleOutput> outputs = new ArrayList<RegisterWorkScheduleOutput>();
        RequireImp requireImp = new RequireImp(workTypeRepo, workTimeSettingRepository, basicScheduleService, fixedWorkSettingRepository, flowWorkSettingRepository, flexWorkSettingRepository, predetemineTimeSettingRepository, employmentHisScheduleAdapter, sharedAffJobtitleHisAdapter, sharedAffWorkPlaceHisAdapter, syClassificationAdapter, businessTypeEmpService, workingConditionRepo, workScheduleRepo, shiftMasterRepository, correctWorkSchedule, interimRemainDataMngRegisterDateChange);
        
        // 1: 作る(Require, 社員ID, 年月日, シフトマスタ取り込みコード, boolean)
        List<ResultOfRegisteringWorkSchedule> resultOfRegisteringWorkSchedule = createWorkScheduleService.register(requireImp, command.toDomain());
        
        // 2: List<勤務予定の登録処理結果> : anyMatch $.エラーがあるか == true 社員IDを指定して社員を取得する(List<社員ID>)
        List<String> employeeIds = new ArrayList<String>();
        resultOfRegisteringWorkSchedule.stream().forEach(x -> {
            if (x.isHasError()) {
                employeeIds.add(x.getErrorInformation().get(0).getEmployeeId());
            }
        });
        List<EmployeeImport> employeeImports = empEmployeeAdapter.findByEmpId(employeeIds);
        employeeImports.stream().forEach(x -> {
            ResultOfRegisteringWorkSchedule result = resultOfRegisteringWorkSchedule.stream()
                    .filter(y -> y.isHasError() ? y.getErrorInformation().get(0).getEmployeeId().equals(x.getEmployeeId()) : y.isHasError()).findFirst().get();
            RegisterWorkScheduleOutput output = new RegisterWorkScheduleOutput(
                    x.getEmployeeCode(), 
                    x.getEmployeeName(), 
                    result.getErrorInformation().get(0).getDate().toString("yyyy/MM/dd"), 
                    result.getErrorInformation().get(0).getAttendanceItemId().isPresent() ? result.getErrorInformation().get(0).getAttendanceItemId().get() : 0, 
                    result.getErrorInformation().get(0).getErrorMessage());
            
            outputs.add(output);
        });
        
        if (outputs.size() > 0) {
            return outputs;
        }
        // 3: <<call>>
        resultOfRegisteringWorkSchedule.forEach(result -> {
            Optional<AtomTask> atomTaskOpt = result.getAtomTask();
            
            if (atomTaskOpt.isPresent()) {
                atomTaskOpt.get().run();
            }
        });
        
        return outputs;
    }
    
    @AllArgsConstructor
    private class RequireImp implements CreateWorkScheduleService.Require {
        
        private WorkTypeRepository workTypeRepo;
        
        private WorkTimeSettingRepository workTimeSettingRepository;
        
        private BasicScheduleService basicScheduleService;
        
        private FixedWorkSettingRepository fixedWorkSettingRepository;
        
        private FlowWorkSettingRepository flowWorkSettingRepository;
        
        private FlexWorkSettingRepository flexWorkSettingRepository;
        
        private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
        
        private EmploymentHisScheduleAdapter employmentHisScheduleAdapter;
        
        private SharedAffJobtitleHisAdapter sharedAffJobtitleHisAdapter;
        
        private SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;
        
        private SyClassificationAdapter syClassificationAdapter;
        
        private BusinessTypeEmpService businessTypeEmpService;
        
        private WorkingConditionRepository workingConditionRepo;
        
        private WorkScheduleRepository workScheduleRepo;
        
        private ShiftMasterRepository shiftMasterRepository;
        
        private CorrectWorkSchedule correctWorkSchedule;
        
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
            return shiftMasterRepository.getShiftMaster(AppContexts.user().companyId(), importCode);
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
