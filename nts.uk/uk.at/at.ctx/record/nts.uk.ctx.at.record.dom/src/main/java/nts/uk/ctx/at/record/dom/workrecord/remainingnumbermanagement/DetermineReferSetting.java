package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcessCommon;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.GetSettingCompensaLeave;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SixtyHourSettingOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CheckDateForManageCmpLeaveService;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.DetermineChildCareManage;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.DetermineLongTermManage;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.アルゴリズム.Query.各残数をチェックするか判断する.設定を参照して、判断する.設定を参照して、判断する
 *
 */
@Stateless
public class DetermineReferSetting {
    
    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;
    
    @Inject
    private EmpSubstVacationRepository empSubstVacationRepository;
    
    @Inject
    private ComSubstVacationRepository comSubstVacationRepository;
    
    @Inject
    private RemainNumberTempRequireService requireService;
    
    @Inject
    private AbsenceTenProcessCommon absenceTenProcessCommon;
    
    @Inject
    private DetermineChildCareManage determineChildCareManage;
    
    @Inject
    private DetermineLongTermManage determineLongTermManage;
    
    @Inject
    private PublicHolidaySettingRepository publicHolidaySettingRepository;

    public RemainNumberClassification algorithm(String companyId, String employeeId, RemainNumberClassification remainNumberClassification) {
        RequireImpl requireImpl = new RequireImpl();
        val require = requireService.createRequire();
        
        // 代休チェック区分＝true
        if (remainNumberClassification.isChkSubHoliday()) {
            // 代休を管理する年月日かどうかを判断する
            boolean checkSubholiday = CheckDateForManageCmpLeaveService.check(require, companyId, employeeId, GeneralDate.today());
            
            // OUTPUT残数チェック区分.代休チェック区分＝取得した「代休を管理する」
            remainNumberClassification.setChkSubHoliday(checkSubholiday);
        }
        
        // 振休チェック区分＝true
        if (remainNumberClassification.isChkPause()) {
            // 10-3.振休の設定を取得する
            LeaveSetOutput leaveSetOutput = GetSettingCompensaLeave.process(requireImpl, companyId, employeeId, GeneralDate.today());
            
            // OUTPUT残数チェック区分.振休チェック区分＝取得した「振休管理区分」
            remainNumberClassification.setChkPause(leaveSetOutput.isSubManageFlag());
        }
        
        // 積休チェック区分＝true
        if (remainNumberClassification.isChkFundingAnnual()) {
            // 10-4.積立年休の設定を取得する
            boolean checkFundingAnnual = AbsenceTenProcess.getSetForYearlyReserved(require, new CacheCarrier(), companyId, employeeId, GeneralDate.today());
            
            // OUTPUT残数チェック区分.積休チェック区分＝取得した「積立年休管理区分」
            remainNumberClassification.setChkFundingAnnual(checkFundingAnnual);
        }
        
        // 超休チェック区分＝true
        if (remainNumberClassification.isChkSuperBreak()) {
            // 10-5.60H超休の設定を取得する
            SixtyHourSettingOutput sixtyHourSettingOutput = absenceTenProcessCommon.getSixtyHourSetting(companyId, employeeId, GeneralDate.today());
            
            // OUTPUT残数チェック区分.超休チェック区分＝取得した「60H超休管理区分」
            remainNumberClassification.setChkFundingAnnual(sixtyHourSettingOutput.isSixtyHourOvertimeMngDistinction());
        }
        
        // 年休チェック区分＝true
        if (remainNumberClassification.isChkAnnual()) {
            // 10-1.年休の設定を取得する
            AnnualHolidaySetOutput annualHolidaySetOutput = AbsenceTenProcess.getSettingForAnnualHoliday(require, companyId);
            
            // OUTPUT残数チェック区分.年休チェック区分＝取得した「年休管理区分」
            remainNumberClassification.setChkAnnual(annualHolidaySetOutput.isYearHolidayManagerFlg());
        }
        
        // 子の看護チェック区分＝true
        if (remainNumberClassification.isChkChildNursing()) {
            // 子の看護管理するかどうか判断する
            boolean checkChildCare = determineChildCareManage.algorithm(companyId, employeeId);
            
            // OUTPUT残数チェック区分.子の看護チェック区分＝取得した「子の看護を管理する」
            remainNumberClassification.setChkChildNursing(checkChildCare);
        }
        
        // 介護チェック区分＝true
        if (remainNumberClassification.isChkLongTermCare()) {
            // 介護管理するかどうか判断する
            boolean checkLongTerm = determineLongTermManage.algorithm(companyId, employeeId);
            
            // OUTPUT残数チェック区分.介護チェック区分＝取得した「介護を管理する」
            remainNumberClassification.setChkLongTermCare(checkLongTerm);
        }
        
        // 公休チェック区分＝true
        if (remainNumberClassification.isChkPublicHoliday()) {
            // ドメイン「公休設定」を取得する
            Optional<PublicHolidaySetting> publicHolidaySetting = publicHolidaySettingRepository.get(companyId);
            
            if (publicHolidaySetting.isPresent()) {
                // 公休チェック区分を再セットする
                remainNumberClassification.setChkPublicHoliday(publicHolidaySetting.get().isManagePublicHoliday());
            }
        }
        
        // OUTPUT残数チェック区分を渡す
        return remainNumberClassification;
    }
    
    public class RequireImpl implements GetSettingCompensaLeave.Require {

        @Override
        public Optional<BsEmploymentHistoryImport> findEmploymentHistory(String companyId, String employeeId,
                GeneralDate baseDate) {
            return shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, baseDate);
        }

        @Override
        public Optional<EmpSubstVacation> findEmpById(String companyId, String contractTypeCode) {
            return empSubstVacationRepository.findById(companyId, contractTypeCode);
        }

        @Override
        public Optional<ComSubstVacation> findComById(String companyId) {
            return comSubstVacationRepository.findById(companyId);
        }
    }
}
