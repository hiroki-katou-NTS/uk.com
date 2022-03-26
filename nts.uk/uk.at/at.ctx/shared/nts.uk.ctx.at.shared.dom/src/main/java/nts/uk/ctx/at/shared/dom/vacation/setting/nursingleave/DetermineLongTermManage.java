package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.休暇.子の看護休暇.アルゴリズム.介護管理するかどうか判断する.介護管理するかどうか判断する
 *
 */
@Stateless
public class DetermineLongTermManage {
    
    @Inject
    private NursingLeaveSettingRepository nursingLeaveSettingRepo;
    
    @Inject
    private CareLeaveRemainingInfoRepository careLeaveRemainingInfoRepository;
    
    public boolean algorithm(String companyId, String employeeId) {
        // ドメイン「介護看護休暇設定」を取得する
        NursingLeaveSetting nursingLeaveSettings = nursingLeaveSettingRepo
                .findByCompanyIdAndNursingCategory(companyId, NursingCategory.Nursing.value);
        
        // データがある　AND　管理区分　＝　管理する
        if (nursingLeaveSettings != null && nursingLeaveSettings.isManaged()) {
            // ドメイン「介護休暇基本情報」を取得する
            Optional<CareLeaveRemainingInfo> careLeaveRemainingInfo = careLeaveRemainingInfoRepository.getCareByEmpId(employeeId);
            
            // データがある　AND　利用区分　＝　true
            if (careLeaveRemainingInfo.isPresent() && careLeaveRemainingInfo.get().isUseClassification()) {
                // 「true」を渡す
                return true;
            }
        }
        
        // 「false」を渡す
        return false;
    }
}
