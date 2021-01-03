package nts.uk.ctx.at.request.dom.application.timeleaveapplication.service;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeDigestAppType;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.*;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.*;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class TimeLeaveApplicationServiceImpl implements TimeLeaveApplicationService {
    @Inject
    private TimeLeaveAppReflectRepository timeLeaveAppReflectRepo;

    @Inject
    private RemainNumberTempRequireService requireService;

    @Inject
    private Com60HourVacationRepository com60HourVacationRepo;

    @Inject
    private NursingLeaveSettingRepository nursingLeaveSettingRepo;

    @Inject
    private TimeSpecialLeaveMngSetRepository timeSpecialLeaveMngSetRepo;

    @Inject
    private SpecialHolidayFrameRepository specialHolidayFrameRepo;

    @Inject
    private AnnLeaveRemainNumberAdapter leaveAdapter;

    @Inject
    private AcquisitionRuleRepository acquisitionRuleRepository;

    @Inject
    private OtherCommonAlgorithm otherCommonAlgorithm;

    @Inject
    private InterimRemainDataMngCheckRegister interimRemainDataMngCheckRegister;

    @Inject
    private SpecialHolidayRepository specialHolidayRepository;

    @Inject
    private CommonAlgorithm commonAlgorithm;

    @Inject
    private DetailBeforeUpdate detailBeforeProcessRegisterService;

    @Inject
    private SpecialLeaveGrantRepository specialLeaveGrantRepo;

    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;

    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    @Inject
    private GrantDateTblRepository grantDateTblRepo;

    @Inject
    private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;

    @Inject
    private InterimSpecialHolidayMngRepository interimSpecialHolidayMngRepo;

    @Inject
    private InterimRemainRepository interimRemainRepo;

    @Inject
    private SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepo;

    @Override
    public TimeLeaveApplicationReflect getTimeLeaveAppReflectSetting(String companyId) {
        TimeLeaveApplicationReflect reflectSetting = timeLeaveAppReflectRepo.findByCompany(companyId).orElse(null);
        if (reflectSetting == null
                || (reflectSetting.getCondition().getSuperHoliday60H() == NotUseAtr.NOT_USE
                && reflectSetting.getCondition().getAnnualVacationTime() == NotUseAtr.NOT_USE
                && reflectSetting.getCondition().getChildNursing() == NotUseAtr.NOT_USE
                && reflectSetting.getCondition().getNursing() == NotUseAtr.NOT_USE
                && reflectSetting.getCondition().getSpecialVacationTime() == NotUseAtr.NOT_USE
                && reflectSetting.getCondition().getSubstituteLeaveTime() == NotUseAtr.NOT_USE)
                ) {
            throw new BusinessException("Msg_474");
        }
        return reflectSetting;
    }

    /**
     * 時間休暇の管理区分を取得する
     * @param companyId
     * @param employeeId
     * @param baseDate
     * @return
     */
    @Override
    public TimeVacationManagementOutput getTimeLeaveManagement(String companyId, String employeeId, GeneralDate baseDate) {
        RemainNumberTempRequireService.Require require = requireService.createRequire();
        CacheCarrier cache = new CacheCarrier();

        // 10-1.年休の設定を取得する
        AnnualHolidaySetOutput annualHolidaySetOutput = AbsenceTenProcess.getSettingForAnnualHoliday(require, companyId);
        TimeAnnualLeaveManagement timeAnnualLeaveMng = new TimeAnnualLeaveManagement(
                EnumAdaptor.valueOf(annualHolidaySetOutput.getTimeYearRest(), TimeDigestiveUnit.class),
                annualHolidaySetOutput.isSuspensionTimeYearFlg()
        );

        // 10-2.代休の設定を取得する
        SubstitutionHolidayOutput substituationHoliday =  AbsenceTenProcess.getSettingForSubstituteHoliday(require, cache, companyId, employeeId, baseDate);
        TimeAllowanceManagement timeSubstituteLeaveMng = new TimeAllowanceManagement(
                EnumAdaptor.valueOf(substituationHoliday.getDigestiveUnit(), TimeDigestiveUnit.class),
                substituationHoliday.isTimeOfPeriodFlg()
        );

        // ドメインモデル「60H超休管理設定」を取得する
        Com60HourVacation com60HourVacation = com60HourVacationRepo.findById(companyId).orElse(null);
        SupHolidayManagement super60HLeaveMng = new SupHolidayManagement(
                com60HourVacation == null ? null : com60HourVacation.getSetting().getDigestiveUnit(),
                com60HourVacation != null && com60HourVacation.isManaged()
        );

        // ドメインモデル「介護看護休暇設定」を取得する
        List<NursingLeaveSetting> nursingLeaveSettingList = nursingLeaveSettingRepo.findByCompanyId(companyId);
        NursingLeaveSetting careNursingLeaveSetting = nursingLeaveSettingList.stream().filter(i -> i.getNursingCategory() == NursingCategory.Nursing).findFirst().orElse(null);
        NursingLeaveSetting childCareNursingLeaveSetting = nursingLeaveSettingList.stream().filter(i -> i.getNursingCategory() == NursingCategory.ChildNursing).findFirst().orElse(null);
        ChildNursingManagement nursingLeaveMng = new ChildNursingManagement(
                careNursingLeaveSetting == null ? null : careNursingLeaveSetting.getTimeCareNursingSetting().getTimeDigestiveUnit(),
                careNursingLeaveSetting != null && careNursingLeaveSetting.isManaged() && careNursingLeaveSetting.getTimeCareNursingSetting().getManageDistinct() == ManageDistinct.YES,
                childCareNursingLeaveSetting == null ? null : childCareNursingLeaveSetting.getTimeCareNursingSetting().getTimeDigestiveUnit(),
                childCareNursingLeaveSetting != null && childCareNursingLeaveSetting.isManaged() && childCareNursingLeaveSetting.getTimeCareNursingSetting().getManageDistinct() == ManageDistinct.YES
        );

        // ドメインモデル「時間特別休暇の管理設定」を取得する
        TimeSpecialLeaveManagementSetting timeSpecialLeaveManagementSetting = timeSpecialLeaveMngSetRepo.findByCompany(companyId).orElse(null);
        TimeSpecialLeaveManagement timeSpecialLeaveMng = new TimeSpecialLeaveManagement(
                timeSpecialLeaveManagementSetting == null ? null : timeSpecialLeaveManagementSetting.getTimeDigestiveUnit(),
                timeSpecialLeaveManagementSetting != null && timeSpecialLeaveManagementSetting.getManageType() == ManageDistinct.YES,
                new ArrayList<>()
        );
        if (timeSpecialLeaveMng.isTimeSpecialLeaveManagement()) {
            // ドメインモデル「特別休暇枠」を取得する
            List<SpecialHolidayFrame> listSpecialFrame = specialHolidayFrameRepo.findAll(companyId).stream().filter(i -> i.getDeprecateSpecialHd() != DeprecateClassification.NotDeprecated).collect(Collectors.toList());
            timeSpecialLeaveMng.getListSpecialFrame().addAll(listSpecialFrame);
        }

        return  new TimeVacationManagementOutput(
                super60HLeaveMng,
                nursingLeaveMng,
                timeSubstituteLeaveMng,
                timeAnnualLeaveMng,
                timeSpecialLeaveMng
        );
    }

    /**
     * 各時間休暇の残数を取得する
     * @param companyId
     * @param employeeId
     * @param baseDate
     * @param timeLeaveManagement
     * @return
     */
    @Override
    public TimeVacationRemainingOutput getTimeLeaveRemaining(String companyId, String employeeId, GeneralDate baseDate, TimeVacationManagementOutput timeLeaveManagement) {
        RemainNumberTempRequireService.Require require = requireService.createRequire();
        CacheCarrier cache = new CacheCarrier();

        // 各時間休暇の残数を取得する
        TimeVacationRemainingOutput timeLeaveRemaining = new TimeVacationRemainingOutput();
        if (!timeLeaveManagement.getTimeAnnualLeaveManagement().isTimeAnnualManagement()
                && !timeLeaveManagement.getTimeAllowanceManagement().isTimeBaseManagementClass()
                && !timeLeaveManagement.getSupHolidayManagement().isOverrest60HManagement()
                && !timeLeaveManagement.getChildNursingManagement().isTimeChildManagementClass()
                && !timeLeaveManagement.getChildNursingManagement().isTimeManagementClass()) {
            return timeLeaveRemaining;
        }

        // 社員に対応する締め期間を取得する
        DatePeriod closingPeriod = ClosureService.findClosurePeriod(require, cache, employeeId, baseDate);

        if (timeLeaveManagement.getTimeAnnualLeaveManagement().isTimeAnnualManagement()) {
            // 基準日時点の年休残数を取得する
            ReNumAnnLeaReferenceDateImport reNumAnnLeave = leaveAdapter.getReferDateAnnualLeaveRemainNumber(employeeId, baseDate);
            timeLeaveRemaining.setAnnualTimeLeaveRemainingDays(reNumAnnLeave.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantDay());
            timeLeaveRemaining.setAnnualTimeLeaveRemainingTime(reNumAnnLeave.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantTime());
        }

        if (timeLeaveManagement.getTimeAllowanceManagement().isTimeBaseManagementClass()) {
            // 期間内の休出代休残数を取得する
            BreakDayOffRemainMngParam remainParam = new BreakDayOffRemainMngParam(
                    companyId,
                    employeeId,
                    new DatePeriod(closingPeriod.start(), closingPeriod.end().addYears(1).addDays(-1)),
                    false,
                    baseDate,
                    false,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty());
            BreakDayOffRemainMngOfInPeriod dataCheck = BreakDayOffMngInPeriodQuery.getBreakDayOffMngInPeriod(require, cache, remainParam);
            timeLeaveRemaining.setSubTimeLeaveRemainingTime(dataCheck.getRemainTimes());
        }

        if (timeLeaveManagement.getSupHolidayManagement().isOverrest60HManagement()) {
            // [RQ677]期間中の60H超休残数を取得する
//            GetHolidayOver60hRemNumWithinPeriod.RequireM1 require60h = new GetHolidayOver60hRemNumWithinPeriodImpl.GetHolidayOver60hRemNumWithinPeriodRequireM1();
//            AggrResultOfHolidayOver60h aggrResultOfHolidayOver60h = this.getHolidayOver60hRemNumWithinPeriod.algorithm(require60h
//                    , cache
//                    , companyId
//                    , employeeId
//                    , new DatePeriod(closingPeriod.start(), closingPeriod.end().addYears(1).addDays(-1))
//                    , InterimRemainMngMode.MONTHLY
//                    , baseDate
//                    , Optional.empty()
//                    , Optional.empty()
//                    , Optional.empty());
        }

        if (timeLeaveManagement.getChildNursingManagement().isTimeChildManagementClass()) {
            // [NO.206]期間中の子の看護休暇残数を取得
//            AggrResultOfChildCareNurse resultOfChildCareNurse = this.getRemainingNumberChildCareSevice.getChildCareRemNumWithinPeriod(
//                    employeeId,
//                    new DatePeriod(closingPeriod.start(), closingPeriod.end().addYears(1).addDays(-1)),
//                    InterimRemainMngMode.OTHER,
//                    baseDate,
//                    Optional.empty(),
//                    Optional.empty(),
//                    Optional.empty(),
//                    Optional.empty(),
//                    Optional.empty()
//            );
        }

        if (timeLeaveManagement.getChildNursingManagement().isTimeManagementClass()) {
            // [NO.207]期間中の介護休暇残数を取得
        }

        timeLeaveRemaining.setRemainingPeriod(new DatePeriod(closingPeriod.start(), closingPeriod.end().addYears(1).addDays(-1)));
        return timeLeaveRemaining;
    }

    /**
     * 特別休暇残数情報を取得する
     * @param companyId
     * @param specialFrameNo
     * @param timeLeaveAppOutput
     * @return
     */
    @Override
    public TimeLeaveApplicationOutput getSpecialLeaveRemainingInfo(String companyId, Optional<Integer> specialFrameNo, TimeLeaveApplicationOutput timeLeaveAppOutput) {
        if (specialFrameNo.isPresent()) {
            // ドメインモデル「特別休暇」を取得する
            List<SpecialHoliday> specialHolidayList = specialHolidayRepository.findByCompanyId(companyId);
            // TODO: don't know get one or many
            Optional<SpecialHoliday> specialHoliday = specialHolidayList.stream().filter(i -> i.getTargetItem().getFrameNo().contains(specialFrameNo.get())).findFirst();
            if (!specialHoliday.isPresent())
                return timeLeaveAppOutput;

            // [NO.273]期間中の特別休暇残数を取得
            ComplileInPeriodOfSpecialLeaveParam param = new ComplileInPeriodOfSpecialLeaveParam(
                    companyId,
                    timeLeaveAppOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
                    timeLeaveAppOutput.getTimeVacationRemaining().getRemainingPeriod(),
                    false,
                    timeLeaveAppOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getBaseDate(),
                    specialHoliday.get().getSpecialHolidayCode().v(),
                    false,
                    false,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    Optional.empty()
            );
            InPeriodOfSpecialLeave specialLeave = SpecialLeaveManagementService
                    .complileInPeriodOfSpecialLeave(
                            SpecialLeaveManagementService.createRequireM5(specialLeaveGrantRepo, shareEmploymentAdapter,
                                    empEmployeeAdapter, grantDateTblRepo, annLeaEmpBasicInfoRepo, specialHolidayRepository,
                                    interimSpecialHolidayMngRepo, interimRemainRepo, specialLeaveBasicInfoRepo),
                            new CacheCarrier(), param)
                    .getAggSpecialLeaveResult();
            // TODO: cannot map with design
        }
        return timeLeaveAppOutput;
    }

    /**
     * 時間休暇申請登録前チェック
     */
    @Override
    public void checkBeforeRigister(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication, TimeLeaveApplicationOutput output) {

        //特別休暇枠をチェックする
        if (timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> !x.getTimeDigestApplication().getSpecialVacationFrameNO().isPresent()
            && x.getTimeDigestApplication().getTimeSpecialVacation().v() > 0).collect(Collectors.toList()).size() > 0) {
            throw new BusinessException("Msg_1983");
        }

        //時間休暇優先順位チェック
        timeVacationCheck(timeDigestAppType, timeLeaveApplication, output.getTimeVacationRemaining(), output.getTimeVacationManagement());

        //時間休暇残数チェック
        remainingTimeVacationCheck(timeDigestAppType, timeLeaveApplication);

        //時間休暇の消化単位チェック
        Optional<TimeDigestiveUnit> super60HDigestion = Optional.of(output.getTimeVacationManagement().getSupHolidayManagement().getSuper60HDigestion());
        Optional<TimeDigestiveUnit> timeBaseRestingUnit = Optional.of(output.getTimeVacationManagement().getTimeAllowanceManagement().getTimeBaseRestingUnit());
        Optional<TimeDigestiveUnit> timeAnnualLeaveUnit = Optional.of(output.getTimeVacationManagement().getTimeAnnualLeaveManagement().getTimeAnnualLeaveUnit());
        Optional<TimeDigestiveUnit> timeChildNursing = Optional.of(output.getTimeVacationManagement().getChildNursingManagement().getTimeChildDigestiveUnit());
        Optional<TimeDigestiveUnit> timeNursing = Optional.of(output.getTimeVacationManagement().getChildNursingManagement().getTimeDigestiveUnit());
        Optional<TimeDigestiveUnit> pendingUnit = Optional.of(output.getTimeVacationManagement().getTimeSpecialLeaveMng().getTimeSpecialLeaveUnit());
        timeLeaveApplication.getLeaveApplicationDetails().forEach(x -> {
            commonAlgorithm.vacationDigestionUnitCheck(x.getTimeDigestApplication(),
                super60HDigestion,
                timeBaseRestingUnit,
                timeAnnualLeaveUnit,
                timeChildNursing,
                timeNursing,
                pendingUnit
            );
        });

        //契約時間をチェック
        checkContractTime(timeDigestAppType, timeLeaveApplication, output.getWorkingConditionItem());

    }

    /**
     * 登録前チェック
     */
    @Override
    public void checkBeforeUpdate(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication, TimeLeaveApplicationOutput output) {

        // 4-1.詳細画面登録前の処理
        this.detailBeforeProcessRegisterService.processBeforeDetailScreenRegistration(
            AppContexts.user().companyId(),
            timeLeaveApplication.getApplication().getEmployeeID(),
            timeLeaveApplication.getApplication().getAppDate().getApplicationDate(),
            EmploymentRootAtr.APPLICATION.value,
            timeLeaveApplication.getApplication().getAppID(),
            timeLeaveApplication.getApplication().getPrePostAtr(),
            timeLeaveApplication.getApplication().getVersion(),
            null,
            null,
            output.getAppDispInfoStartup()
        );

        this.checkBeforeRigister(timeDigestAppType,timeLeaveApplication,output);

    }

    @Override
    public TimeLeaveApplicationOutput getSpecialVacation(Optional<Integer> specialHdFrameNo, TimeLeaveApplicationOutput timeLeaveApplicationOutput) {

        if (specialHdFrameNo.isPresent()) {
            //ドメインモデル「特別休暇」を取得する
            List<Integer> byAbsframeNo = specialHolidayRepository.findByAbsframeNo(AppContexts.user().companyId(), specialHdFrameNo.get());

            // [NO.273]期間中の特別休暇残数を取得 TODO QA 38719
//            SpecialVacationImported specialVacationImported = specialLeaveAdapter.complileInPeriodOfSpecialLeave(AppContexts.user().companyId(),
//                timeLeaveApplicationOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
//                timeLeaveApplicationOutput.getTimeVacationRemainingOutput().getDatePeriod(),
//                false,timeLeaveApplicationOutput.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getBaseDate(), byAbsframeNo.get(0), false);

//            if (specialVacationImported != null){
//                TimeSpecialVacationRemaining timeSpecial = timeLeaveApplicationOutput.getTimeVacationRemainingOutput().getTimeSpecialVacationRemaining();
//                timeSpecial.setDayOfSpecialLeave(specialHdFrameNo.get()); //TODO
//                timeSpecial.setTimeOfSpecialLeave(specialHdFrameNo.get()); //TODO
//                timeSpecial.setSpecialFrameNo(specialHdFrameNo.get());
//            }
        }

        return timeLeaveApplicationOutput;
    }

    /**
     * 時間休暇優先順位チェック
     */
    private void timeVacationCheck(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication,
                                   TimeVacationRemainingOutput timeVacationRemainingOutput,
                                   TimeVacationManagementOutput timeVacationManagementOutput) {
        if (timeDigestAppType == TimeDigestAppType.TIME_ANNUAL_LEAVE.value || timeDigestAppType == TimeDigestAppType.USE_COMBINATION.value &&
            timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> x.getTimeDigestApplication().getTimeAnualLeave().v() > 0).collect(Collectors.toList()).size() > 0) {

            //ドメインモデル「休暇の取得ルール」を取得する
            Optional<AcquisitionRule> acquisitionRule = acquisitionRuleRepository.findById(AppContexts.user().companyId());

            //時間休暇の優先順をチェックする


            //代休の時間管理区分
            boolean timeBaseManagementClass = timeVacationManagementOutput.getTimeAllowanceManagement().isTimeBaseManagementClass();
            //60H超休の時間管理区分
            boolean overrest60HManagement = timeVacationManagementOutput.getSupHolidayManagement().isOverrest60HManagement();

            //時間消化申請
            List<TimeDigestApplication> timeDigestApplications = timeLeaveApplication.getLeaveApplicationDetails().stream().map(TimeLeaveApplicationDetail::getTimeDigestApplication).collect(Collectors.toList());

            Integer totalTimeAnnualLeave = timeDigestApplications.stream().reduce(0, (a, b) -> a + b.getTimeAnualLeave().v(), Integer::sum);
            Integer totalSixtyOvertime = timeDigestApplications.stream().reduce(0, (a, b) -> a + b.getOvertime60H().v(), Integer::sum);
            Integer totalTimeOff = timeDigestApplications.stream().reduce(0, (a, b) -> a + b.getTimeOff().v(), Integer::sum);

            //「@取得する順番をチェックする」を確認する
            if (acquisitionRule.isPresent() && acquisitionRule.get().getCategory() == SettingDistinct.YES) {
                //「@年休より優先する休暇．60H超休を優先」　と　INPUT「60H超休の時間管理区分」を確認する
                if (acquisitionRule.get().getAnnualHoliday().isSixtyHoursOverrideHoliday() && overrest60HManagement) {
                    //60H超休残時間
                    int super60HRemainingTime = timeVacationRemainingOutput.getSuper60HRemainingTime();

                    //INPUT「時間消化申請」とINPUT「60H超休残数」を確認する
                    if (totalTimeAnnualLeave > 0 && super60HRemainingTime > 0 && (super60HRemainingTime - totalSixtyOvertime) > 0) {
                        throw new BusinessException("Msg_1687", "#Com_ExsessHoliday", "#Com_PaidHoliday", "#Com_ExsessHoliday");
                    }
                } else {
                    //「@年休より優先する休暇．代休を優先」　と　INPUT「代休の時間管理区分」を確認する
                    if (acquisitionRule.get().getAnnualHoliday().isPriorityPause() && timeBaseManagementClass) {
                        //時間代休残数
                        int timeOfTimeOff = timeVacationRemainingOutput.getSubTimeLeaveRemainingTime();

                        //INPUT「時間消化申請」とINPUT「時間代休残数」を確認する
                        if (totalTimeAnnualLeave > 0 && timeOfTimeOff > 0 && (timeOfTimeOff - totalTimeOff) > 0) {
                            throw new BusinessException("Msg_1687", "#Com_ExsessHoliday", "#Com_PaidHoliday", "#Com_ExsessHoliday");
                        }
                    }
                }
            }
        }
    }

    /**
     * 時間休暇残数チェック
     */
    private void remainingTimeVacationCheck(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication) {
        //4.社員の当月の期間を算出する
        PeriodCurrentMonth periodCurrentMonth =
            otherCommonAlgorithm.employeePeriodCurrentMonthCalculate(AppContexts.user().companyId(), timeLeaveApplication.getEmployeeID(), GeneralDate.today());

        //登録時の残数チェック
        InterimRemainCheckInputParam inputParam = new InterimRemainCheckInputParam(AppContexts.user().companyId(), timeLeaveApplication.getEmployeeID(),
            new DatePeriod(periodCurrentMonth.getStartDate(), periodCurrentMonth.getStartDate().addYears(1).addDays(-1)), false,
            timeLeaveApplication.getAppDate().getApplicationDate(),
            new DatePeriod(timeLeaveApplication.getOpAppStartDate().isPresent() ? timeLeaveApplication.getOpAppStartDate().get().getApplicationDate() : null,
                timeLeaveApplication.getOpAppEndDate().isPresent() ? timeLeaveApplication.getOpAppEndDate().get().getApplicationDate() : null), true,
            Collections.emptyList(), Collections.emptyList(), getAppData(timeLeaveApplication.getApplication()), timeDigestAppType == TimeDigestAppType.TIME_OFF.value,
            false, timeDigestAppType == TimeDigestAppType.TIME_ANNUAL_LEAVE.value, false, false,
            false, timeDigestAppType == TimeDigestAppType.SIXTY_H_OVERTIME.value);

        boolean ChkSixtyHOvertime = timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> x.getTimeDigestApplication().getOvertime60H().v() > 0).collect(Collectors.toList()).size() > 0;
        boolean chkHoursOfHoliday = timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> x.getTimeDigestApplication().getTimeAnualLeave().v() > 0).collect(Collectors.toList()).size() > 0;
        boolean chkHoursOfSubHoliday = timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> x.getTimeDigestApplication().getTimeOff().v() > 0).collect(Collectors.toList()).size() > 0;

        if (timeDigestAppType == TimeDigestAppType.USE_COMBINATION.value) {
            inputParam.setChkSubHoliday(chkHoursOfSubHoliday);

            inputParam.setChkAnnual(chkHoursOfHoliday);
            inputParam.setChkSuperBreak(ChkSixtyHOvertime);
        }

        EarchInterimRemainCheck remainCheck = interimRemainDataMngCheckRegister.checkRegister(inputParam);

        if (!remainCheck.isChkPublicHoliday() && !remainCheck.isChkSuperBreak()) {
            //TODO
            throw new BusinessException("Msg_1409", "", "");
        }
    }

    /**
     * 契約時間をチェック
     */
    private void checkContractTime(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication, WorkingConditionItem workingConditionItem) {

        //1日の契約時間をチェックする
        Integer totalAnnualHoliday = 0;

        if (timeDigestAppType == TimeDigestAppType.SIXTY_H_OVERTIME.value) {
            totalAnnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getOvertime60H().v(), Integer::sum);
        } else if (timeDigestAppType == TimeDigestAppType.TIME_OFF.value) {
            totalAnnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getTimeOff().v(), Integer::sum);

        } else if (timeDigestAppType == TimeDigestAppType.TIME_ANNUAL_LEAVE.value) {
            totalAnnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getTimeAnualLeave().v(), Integer::sum);

        } else if (timeDigestAppType == TimeDigestAppType.CHILD_NURSING_TIME.value) {
            totalAnnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getChildTime().v(), Integer::sum);

        } else if (timeDigestAppType == TimeDigestAppType.NURSING_TIME.value) {
            totalAnnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getNursingTime().v(), Integer::sum);

        } else if (timeDigestAppType == TimeDigestAppType.TIME_SPECIAL_VACATION.value) {
            totalAnnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getTimeSpecialVacation().v(), Integer::sum);
        }

        if (totalAnnualHoliday > workingConditionItem.getContractTime().v()) {
            throw new BusinessException("Msg_1760");
        }
    }

    private List<AppRemainCreateInfor> getAppData(Application application) {
        List<AppRemainCreateInfor> apps = new ArrayList<>();
        apps.add(new AppRemainCreateInfor(
            application.getEmployeeID(),
            application.getAppID(),
            application.getInputDate(),
            application.getAppDate().getApplicationDate(),
            EnumAdaptor.valueOf(application.getPrePostAtr().value, PrePostAtr.class),
            EnumAdaptor.valueOf(application.getAppType().value, ApplicationType.class),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            application.getOpAppStartDate().isPresent() ? Optional.of(application.getOpAppStartDate().get().getApplicationDate()) : Optional.empty(),
            application.getOpAppEndDate().isPresent() ? Optional.of(application.getOpAppEndDate().get().getApplicationDate()) : Optional.empty(),
            Collections.emptyList()
        ));

        return apps;
    }
}
