package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.休暇.子の看護休暇.アルゴリズム.子の看護管理するかどうか判断する.子の看護管理するかどうか判断する
 *
 */
@Stateless
public class DetermineChildCareManage {
    
    @Inject
    private NursingLeaveSettingRepository nursingLeaveSettingRepo;
    
    @Inject
    private ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepository;
    
    public boolean algorithm(String companyId, String employeeId) {
        // ドメイン「介護看護休暇設定」を取得する
        NursingLeaveSetting childcareLeaveSettings = nursingLeaveSettingRepo
                .findByCompanyIdAndNursingCategory(companyId, NursingCategory.ChildNursing.value);
        
        // データがある　AND　管理区分　＝　管理する
        if (childcareLeaveSettings != null && childcareLeaveSettings.isManaged()) {
            // ドメイン「子の看護休暇基本情報」を取得する
            Optional<ChildCareLeaveRemainingInfo> childCareLeaveRemainingInfo = childCareLeaveRemInfoRepository.getChildCareByEmpId(employeeId);
            
            // データがある　AND　利用区分　＝　true
            if (childCareLeaveRemainingInfo.isPresent() && childCareLeaveRemainingInfo.get().isUseClassification()) {
                // 「true」を渡す
                return true;
            }
        }
        
        // 「false」を渡す
        return false;
    }
}
