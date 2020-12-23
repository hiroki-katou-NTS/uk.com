package nts.uk.ctx.at.request.dom.application.timeleaveapplication.service;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeDigestAppType;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.*;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.*;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
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

    @Override
    public LeaveRemainingInfo getLeaveRemainingInfo(String companyId, String employeeId, GeneralDate baseDate) {
        // 時間休暇の管理区分を取得する
        // 10-1.年休の設定を取得する
        RemainNumberTempRequireService.Require require = requireService.createRequire();
        AnnualHolidaySetOutput annualHolidaySetOutput = AbsenceTenProcess.getSettingForAnnualHoliday(require, companyId);
        TimeAnnualLeaveMng timeAnnualLeaveMng = new TimeAnnualLeaveMng(annualHolidaySetOutput.getTimeYearRest(), annualHolidaySetOutput.isSuspensionTimeYearFlg());

        // 10-2.代休の設定を取得する
        CacheCarrier cache = new CacheCarrier();
        SubstitutionHolidayOutput substituationHoliday =  AbsenceTenProcess.getSettingForSubstituteHoliday(require, cache, companyId, employeeId, baseDate);
        TimeSubstituteLeaveMng timeSubstituteLeaveMng = new TimeSubstituteLeaveMng(substituationHoliday.getDigestiveUnit(), substituationHoliday.isTimeOfPeriodFlg());

        // ドメインモデル「60H超休管理設定」を取得する
        Com60HourVacation com60HourVacation = com60HourVacationRepo.findById(companyId).orElse(null);
        Super60HLeaveMng super60HLeaveMng = new Super60HLeaveMng(
                com60HourVacation == null ? null : com60HourVacation.getSetting().getDigestiveUnit().value,
                com60HourVacation == null ? null : com60HourVacation.isManaged()
        );

        // ドメインモデル「介護看護休暇設定」を取得する
        List<NursingLeaveSetting> nursingLeaveSettingList = nursingLeaveSettingRepo.findByCompanyId(companyId);
        NursingLeaveSetting careNursingLeaveSetting = nursingLeaveSettingList.stream().filter(i -> i.getNursingCategory() == NursingCategory.Nursing).findFirst().orElse(null);
        NursingLeaveSetting childCareNursingLeaveSetting = nursingLeaveSettingList.stream().filter(i -> i.getNursingCategory() == NursingCategory.ChildNursing).findFirst().orElse(null);
        NursingLeaveMng nursingLeaveMng = new NursingLeaveMng(
                null,
                careNursingLeaveSetting == null ? null : careNursingLeaveSetting.isManaged(),
                null,
                childCareNursingLeaveSetting == null ? null : childCareNursingLeaveSetting.isManaged()
        );

        // ドメインモデル「時間特別休暇の管理設定」を取得する
        TimeSpecialLeaveManagementSetting timeSpecialLeaveManagementSetting = timeSpecialLeaveMngSetRepo.findByCompany(companyId).orElse(null);
        TimeSpecialLeaveMng timeSpecialLeaveMng = new TimeSpecialLeaveMng(
                timeSpecialLeaveManagementSetting == null ? null : timeSpecialLeaveManagementSetting.getTimeDigestiveUnit().value,
                timeSpecialLeaveManagementSetting == null ? null : timeSpecialLeaveManagementSetting.getManageType() == ManageDistinct.YES,
                new ArrayList<>()
        );
        if (timeSpecialLeaveMng.getTimeSpecialLeaveMngAtr() == Boolean.TRUE) {
            // ドメインモデル「特別休暇枠」を取得する
            List<SpecialHolidayFrame> listSpecialFrame = specialHolidayFrameRepo.findAll(companyId).stream().filter(i -> i.getDeprecateSpecialHd() != DeprecateClassification.NotDeprecated).collect(Collectors.toList());
            timeSpecialLeaveMng.getListSpecialFrame().addAll(listSpecialFrame);
        }

        TimeLeaveManagement timeLeaveManagement =  new TimeLeaveManagement(
                timeAnnualLeaveMng,
                timeSubstituteLeaveMng,
                super60HLeaveMng,
                nursingLeaveMng,
                timeSpecialLeaveMng
        );

        // 各時間休暇の残数を取得する
        TimeLeaveRemaining timeLeaveRemaining = new TimeLeaveRemaining();
        if (timeLeaveManagement.getTimeAnnualLeaveMng().getTimeAnnualLeaveMngAtr() != Boolean.TRUE
                && timeLeaveManagement.getTimeSubstituteLeaveMng().getTimeSubstituteLeaveMngAtr() != Boolean.TRUE
                && timeLeaveManagement.getSuper60HLeaveMng().getSuper60HLeaveMngAtr() != Boolean.TRUE
                && timeLeaveManagement.getNursingLeaveMng().getTimeCareLeaveMngAtr() != Boolean.TRUE
                && timeLeaveManagement.getNursingLeaveMng().getTimeChildCareLeaveMngAtr() != Boolean.TRUE) {
            return new LeaveRemainingInfo(timeLeaveManagement, timeLeaveRemaining);
        }

        // 社員に対応する締め期間を取得する
        DatePeriod closingPeriod = ClosureService.findClosurePeriod(require, cache, employeeId, baseDate);

        if (timeLeaveManagement.getTimeAnnualLeaveMng().getTimeAnnualLeaveMngAtr() == Boolean.TRUE) {
            // 基準日時点の年休残数を取得する
            ReNumAnnLeaReferenceDateImport reNumAnnLeave = leaveAdapter.getReferDateAnnualLeaveRemainNumber(employeeId, baseDate);
            timeLeaveRemaining.setAnnualTimeLeaveRemainingDays(reNumAnnLeave.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantDay());
            timeLeaveRemaining.setAnnualTimeLeaveRemainingTime(reNumAnnLeave.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantTime());
        }

        if (timeLeaveManagement.getTimeSubstituteLeaveMng().getTimeSubstituteLeaveMngAtr() == Boolean.TRUE) {
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

        if (timeLeaveManagement.getSuper60HLeaveMng().getSuper60HLeaveMngAtr() == Boolean.TRUE) {
            // [RQ677]期間中の60H超休残数を取得する
//            GetHolidayOver60hRemNumWithinPeriod.RequireM1 require60h = new GetHolidayOver60hRemNumWithinPeriodImpl.GetHolidayOver60hRemNumWithinPeriodRequireM1();
//            CacheCarrier cacheCarrier1 = new CacheCarrier();
//            AggrResultOfHolidayOver60h aggrResultOfHolidayOver60h = this.getHolidayOver60hRemNumWithinPeriod.algorithm(require60h
//                    , cacheCarrier1
//                    , companyId
//                    , employeeId
//                    , this.getDatePeroid(closingPeriod.start())
//                    , InterimRemainMngMode.MONTHLY
//                    , inputDate
//                    , Optional.empty()
//                    , Optional.empty()
//                    , Optional.empty());
        }

        // TODO: chưa xong

        timeLeaveRemaining.setRemainingPeriod(new DatePeriod(closingPeriod.start(), closingPeriod.end().addYears(1).addDays(-1)));

        return new LeaveRemainingInfo(timeLeaveManagement, timeLeaveRemaining);
    }

    /**
     * 時間休暇申請登録前チェック
     */
    @Override
    public void checkBeforeRigister(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication, TimeLeaveApplicationOutput output) {

        //特別休暇枠をチェックする
        if (timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> !x.getTimeDigestApplication().getSpecialHdFrameNo().isPresent()
            && x.getTimeDigestApplication().getTimeSpecialVacation().v() > 0).collect(Collectors.toList()).size() > 0) {
            throw new BusinessException("Msg_1983");
        }

        //時間休暇優先順位チェック
        timeVacationCheck(timeDigestAppType, timeLeaveApplication, output.getTimeVacationRemainingOutput(), output.getTimeVacationManagementOutput());

        //時間休暇残数チェック
        remainingTimeVacationCheck(timeDigestAppType, timeLeaveApplication);

        //時間休暇の消化単位チェック

        //TODO call algorithm
//        //60H超休消化単位
//        Optional<Integer> super60HDigestion = Optional.of(output.getTimeVacationManagementOutput().getSupHolidayManagement().getSuper60HDigestion().value);
//        Optional<Integer> timeBaseRestingUnit = Optional.of(output.getTimeVacationManagementOutput().getTimeAllowanceManagement().getTimeBaseRestingUnit().value);
//        Optional<Integer> timeAnnualLeaveUnit = Optional.of(output.getTimeVacationManagementOutput().getTimeAnnualLeaveManagement().getTimeAnnualLeaveUnit().value);
//        Optional<Integer> timeChildNursing = Optional.of(output.getTimeVacationManagementOutput().getChildNursingManagement().getTimeChildDigestiveUnit().value);
//
//        //INPUT．「時間消化申請」をチェックする
//        if (timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x ->
//            x.getTimeDigestApplication().getHoursOfSubHoliday().v() == 0 &&
//                x.getTimeDigestApplication().getSixtyHOvertime().v() == 0 &&
//                x.getTimeDigestApplication().getHoursOfHoliday().v() == 0 &&
//                x.getTimeDigestApplication().getChildNursingTime().v() == 0 &&
//                x.getTimeDigestApplication().getNursingTime().v() == 0 &&
//                x.getTimeDigestApplication().getTimeSpecialVacation().v() == 0
//        ).collect(Collectors.toList()).size() > 0) {
//            throw new BusinessException("Msg_511");
//        }
//
//        if (timeDigestAppType == TimeDigestAppType.SIXTY_H_OVERTIME.value) {
//            //TODO INPUT．「時間消化申請．60H超休」/ INPUT．「60H超休消化単位」の余りが0
//            if (totalSixtyOvertime > super60HDigestion.orElse(1)) {
//                throw new BusinessException("Msg_478",super60HDigestion.orElse(1).toString());
//            }
//        } else if (timeDigestAppType == TimeDigestAppType.HOURS_OF_SUB_HOLIDAY.value){
//            if (totalAnualSubHoliday > timeBaseRestingUnit.orElse(1)){
//                throw new BusinessException("Msg_477",timeBaseRestingUnit.orElse(1).toString());
//            }
//        } else if (timeDigestAppType == TimeDigestAppType.HOURS_OF_HOLIDAY.value){
//            if (totalAnualHoliday > timeAnnualLeaveUnit.orElse(1)){
//                throw new BusinessException("Msg_476",timeAnnualLeaveUnit.orElse(1).toString());
//            }
//        }else if (timeDigestAppType == TimeDigestAppType.CHILD_NURSING_TIME.value){
//            if (totalChildNursingTime > timeChildNursing.orElse(1)){
//                throw new BusinessException("Msg_1686",totalChildNursingTime.toString(), timeBaseRestingUnit.orElse(1).toString());
//            }
//        }else if (timeDigestAppType == TimeDigestAppType.NURSING_TIME.value){
//            if (totalNursingTime> timeChildNursing.orElse(1)){
//                throw new BusinessException("Msg_1686",totalChildNursingTime.toString(), timeBaseRestingUnit.orElse(1).toString());
//            }
//        }

        //契約時間をチェック
        checkContractTime(timeDigestAppType, timeLeaveApplication, output.getWorkingConditionItemOutput());

    }

    /**
     * 時間休暇優先順位チェック
     */
    private void timeVacationCheck(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication,
                                   TimeVacationRemainingOutput timeVacationRemainingOutput,
                                   TimeVacationManagementOutput timeVacationManagementOutput) {
        if (timeDigestAppType == TimeDigestAppType.HOURS_OF_HOLIDAY.value || timeDigestAppType == TimeDigestAppType.USE_COMBINATION.value &&
            timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> x.getTimeDigestApplication().getHoursOfHoliday().v() > 0).collect(Collectors.toList()).size() > 0) {

            //ドメインモデル「休暇の取得ルール」を取得する
            Optional<AcquisitionRule> acquisitionRule = acquisitionRuleRepository.findById(AppContexts.user().companyId());

            //時間休暇の優先順をチェックする

            //60H超休残時間
            int subOfHoliday = timeVacationRemainingOutput.getSubOfHoliday();
            //時間代休残数
            int timeOfTimeOff = timeVacationRemainingOutput.getTimeOfTimeOff();
            //代休の時間管理区分
            boolean timeBaseManagementClass = timeVacationManagementOutput.getTimeAllowanceManagement().isTimeBaseManagementClass();
            //60H超休の時間管理区分
            boolean overrest60HManagement = timeVacationManagementOutput.getSupHolidayManagement().isOverrest60HManagement();

            //時間消化申請
            List<TimeDigestApplication> timeDigestApplications = timeLeaveApplication.getLeaveApplicationDetails().stream().map(TimeLeaveApplicationDetail::getTimeDigestApplication).collect(Collectors.toList());

            Integer totalAnualHoliday = timeDigestApplications.stream().reduce(0, (a, b) -> a + b.getHoursOfHoliday().v(), Integer::sum);
            Integer totalSixtyOvertime = timeDigestApplications.stream().reduce(0, (a, b) -> a + b.getSixtyHOvertime().v(), Integer::sum);
            Integer totalAnualSubHoliday = timeDigestApplications.stream().reduce(0, (a, b) -> a + b.getHoursOfSubHoliday().v(), Integer::sum);

            //「@取得する順番をチェックする」を確認する
            if (acquisitionRule.isPresent() && acquisitionRule.get().getCategory() == SettingDistinct.YES) {
                //「@年休より優先する休暇．60H超休を優先」　と　INPUT「60H超休の時間管理区分」を確認する
                if (acquisitionRule.get().getAnnualHoliday().isSixtyHoursOverrideHoliday() && overrest60HManagement) {
                    //INPUT「時間消化申請」とINPUT「60H超休残数」を確認する

                    if (totalAnualHoliday > 0 && timeVacationRemainingOutput.getSubOfHoliday() > 0 && (subOfHoliday - totalSixtyOvertime) > 0) {
                        //TODO
                        throw new BusinessException("Msg_1687", "", "", "");
                    }
                } else {
                    //「@年休より優先する休暇．代休を優先」　と　INPUT「代休の時間管理区分」を確認する
                    if (acquisitionRule.get().getAnnualHoliday().isPriorityPause() && timeBaseManagementClass) {

                        //INPUT「時間消化申請」とINPUT「時間代休残数」を確認する
                        if (totalAnualHoliday > 0 && timeOfTimeOff > 0 && (timeOfTimeOff - totalAnualSubHoliday) > 0) {
                            //TODO
                            throw new BusinessException("Msg_1687", "", "", "");
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
            Collections.emptyList(), Collections.emptyList(), getAppData(timeLeaveApplication.getApplication()), timeDigestAppType == TimeDigestAppType.HOURS_OF_SUB_HOLIDAY.value,
            false, timeDigestAppType == TimeDigestAppType.HOURS_OF_HOLIDAY.value, false, false,
            false, timeDigestAppType == TimeDigestAppType.SIXTY_H_OVERTIME.value);

        boolean ChkSixtyHOvertime = timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> x.getTimeDigestApplication().getSixtyHOvertime().v() > 0).collect(Collectors.toList()).size() > 0;
        boolean chkHoursOfHoliday = timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> x.getTimeDigestApplication().getHoursOfHoliday().v() > 0).collect(Collectors.toList()).size() > 0;
        boolean chkHoursOfSubHoliday = timeLeaveApplication.getLeaveApplicationDetails().stream().filter(x -> x.getTimeDigestApplication().getHoursOfSubHoliday().v() > 0).collect(Collectors.toList()).size() > 0;

        if (timeDigestAppType == TimeDigestAppType.USE_COMBINATION.value) {
            inputParam.setChkSubHoliday(chkHoursOfSubHoliday);

            inputParam.setChkAnnual(chkHoursOfHoliday);
            inputParam.setChkSuperBreak(ChkSixtyHOvertime);
        }

        EarchInterimRemainCheck remainCheck = interimRemainDataMngCheckRegister.checkRegister(inputParam);

        if (remainCheck.isChkSubHoliday() || remainCheck.isChkPause() || remainCheck.isChkAnnual() ||
            remainCheck.isChkFundingAnnual() || remainCheck.isChkSpecial()) {
            //TODO
            throw new BusinessException("Msg_1409", "", "");
        }
    }

    /**
     * 契約時間をチェック
     */
    private void checkContractTime(int timeDigestAppType, TimeLeaveApplication timeLeaveApplication, WorkingConditionItem workingConditionItem) {

        //1日の契約時間をチェックする
        Integer totalAnualHoliday = 0;

        if (timeDigestAppType == TimeDigestAppType.SIXTY_H_OVERTIME.value) {
            totalAnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getSixtyHOvertime().v(), Integer::sum);
        } else if (timeDigestAppType == TimeDigestAppType.HOURS_OF_SUB_HOLIDAY.value) {
            totalAnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getHoursOfSubHoliday().v(), Integer::sum);

        } else if (timeDigestAppType == TimeDigestAppType.HOURS_OF_HOLIDAY.value) {
            totalAnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getHoursOfHoliday().v(), Integer::sum);

        } else if (timeDigestAppType == TimeDigestAppType.CHILD_NURSING_TIME.value) {
            totalAnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getChildNursingTime().v(), Integer::sum);

        } else if (timeDigestAppType == TimeDigestAppType.NURSING_TIME.value) {
            totalAnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getNursingTime().v(), Integer::sum);

        } else if (timeDigestAppType == TimeDigestAppType.TIME_SPECIAL_VACATION.value) {
            totalAnualHoliday += timeLeaveApplication.getLeaveApplicationDetails().stream().reduce(0, (a, b) -> a + b.getTimeDigestApplication().getTimeSpecialVacation().v(), Integer::sum);
        }

        if (totalAnualHoliday > workingConditionItem.getContractTime().v()) {
            throw new BusinessException("Msg_1760", "", "", "");
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
