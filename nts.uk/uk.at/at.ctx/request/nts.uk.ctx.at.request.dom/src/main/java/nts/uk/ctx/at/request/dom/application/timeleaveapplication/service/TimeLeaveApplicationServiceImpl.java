package nts.uk.ctx.at.request.dom.application.timeleaveapplication.service;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.*;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
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
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
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
}
