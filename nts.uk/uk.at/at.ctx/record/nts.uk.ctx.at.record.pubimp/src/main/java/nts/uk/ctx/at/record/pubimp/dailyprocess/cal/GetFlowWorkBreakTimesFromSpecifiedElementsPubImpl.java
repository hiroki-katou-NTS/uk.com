package nts.uk.ctx.at.record.pubimp.dailyprocess.cal;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.GetFlowWorkBreakTimesFromSpecifiedElements;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.requestlist.PrevisionalForImp;
import nts.uk.ctx.at.record.pub.dailyprocess.cal.GetFlowWorkBreakTimesFromSpecifiedElementsPub;
import nts.uk.ctx.at.record.pub.dailyprocess.cal.PrevisionalForImpImport;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.AffiliationInforState;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ReflectWorkInforDomainService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ICorrectionAttendanceRule;
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

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class GetFlowWorkBreakTimesFromSpecifiedElementsPubImpl implements GetFlowWorkBreakTimesFromSpecifiedElementsPub {
    @Inject
    private FixedWorkSettingRepository fixedWorkSettingRepo;

    @Inject
    private FlowWorkSettingRepository flowWorkSettingRepo;

    @Inject
    private FlexWorkSettingRepository flexWorkSettingRepo;

    @Inject
    private PredetemineTimeSettingRepository predetemineTimeSettingRepo;

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private WorkTimeSettingRepository workTimeRepo;

    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private DailyRecordShareFinder dailyRecordShareFinder;

    @Inject
    private ReflectWorkInforDomainService reflectWorkInforDomainServiceImpl;

    @Inject
    private ICorrectionAttendanceRule correctionAttendanceRule;

    @Override
    public List<BreakTimeSheet> getFlowWorkBreakTimes(PrevisionalForImpImport params) {
        PrevisionalForImp convertedParams = new PrevisionalForImp(
                params.getEmployeeId(),
                params.getTargetDate(),
                params.getTimeSheets(),
                params.getWorkTypeCode(),
                params.getWorkTimeCode(),
                params.getBreakTimeSheets(),
                params.getOutingTimeSheets(),
                params.getShortWorkingTimeSheets()
        );

        GetFlowWorkBreakTimesFromSpecifiedElements.Require require = new GetFlowWorkBreakTimesFromSpecifiedElements.Require() {
            @Override
            public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
                return fixedWorkSettingRepo.findByKey(AppContexts.user().companyId(), code.v()).orElse(null);
            }
            @Override
            public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
                return flowWorkSettingRepo.find(AppContexts.user().companyId(), code.v()).orElse(null);
            }
            @Override
            public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
                return flexWorkSettingRepo.find(AppContexts.user().companyId(), code.v()).orElse(null);
            }
            @Override
            public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
                return predetemineTimeSettingRepo.findByWorkTimeCode(AppContexts.user().companyId(), wktmCd.v()).orElse(null);
            }
            @Override
            public Optional<WorkType> getWorkType(String workTypeCd) {
                return workTypeRepo.findByPK(AppContexts.user().companyId(), workTypeCd);
            }
            @Override
            public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
                return workTimeRepo.findByCode(AppContexts.user().companyId(), workTimeCode);
            }
            @Override
            public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
                return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
            }
            @Override
            public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {
                return correctionAttendanceRule.process(domainDaily, changeAtt);
            }
            @Override
            public Optional<IntegrationOfDaily> findDailyRecord(String employeeId, GeneralDate ymd) {
                return dailyRecordShareFinder.find(employeeId, ymd);
            }
            @Override
            public AffiliationInforState createAffiliationInforOfDailyPerfor(String companyId, String employeeId, GeneralDate ymd, String empCalAndSumExecLogID) {
                return reflectWorkInforDomainServiceImpl.createAffiliationInforOfDailyPerfor(companyId, employeeId, ymd, empCalAndSumExecLogID);
            }
        };

        return GetFlowWorkBreakTimesFromSpecifiedElements.get(require, convertedParams);
    }
}
