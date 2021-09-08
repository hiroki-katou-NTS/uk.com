package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseRequireImplFactory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.付与せず上限で管理する休暇.子の看護・介護休暇管理.Export.Query.[RQ726]基準日時点の介護残数を取得する.[RQ726]基準日時点の介護残数を取得する
 *
 */
@Stateless
public class GetRemainingNumberNursing {
    
    @Inject
    private NursingLeaveSettingRepository nursingLeaveSettingRepo;
    
    @Inject
    private ChildCareNurseRequireImplFactory childCareNurseRequireImplFactory;
    
    @Inject
    private GetRemainingNumberCareService getRemainingNumberCareService;
    
    /**
     * [RQ726]基準日時点の介護残数を取得する
     * @param cId 会社ID
     * @param sId 社員ID
     * @param date 基準日
     * @return 子の看護介護休暇集計結果
     */
    public AggrResultOfChildCareNurse getRemainingNumberNursing(String cId, String sId, GeneralDate date) {
        
        // 「介護看護休暇設定」を取得する
        NursingLeaveSetting nursingLeaveSettings = nursingLeaveSettingRepo
                .findByCompanyIdAndNursingCategory(cId, NursingCategory.Nursing.value);
        
        // 残数の集計期間を求める
        DatePeriod period = nursingLeaveSettings.findPeriodForRemainNumber(date);
        
        // [NO.207]期間中の介護休暇残数を取得
        AggrResultOfChildCareNurse nursePeriod = getRemainingNumberCareService.getCareRemNumWithinPeriod(
                cId, 
                sId, 
                period, 
                InterimRemainMngMode.OTHER, 
                date, 
                Optional.of(false), 
                new ArrayList<TempCareManagement>(), 
                Optional.empty(), 
                Optional.empty(), 
                Optional.empty(), 
                new CacheCarrier(), 
                childCareNurseRequireImplFactory.createRequireImpl());

        return nursePeriod;
    }

}
