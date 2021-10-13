package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.アルゴリズム.Query.各残数をチェックするか判断する.介護看護がチェックするか判断する.介護看護がチェックするか判断する
 *
 */
@Stateless
public class DetermineCareNursingCheck {
    
    @Inject
    private NursingLeaveSettingRepository nursingLeaveSettingRepo;

    /**
     * @param cId 会社ID
     * @param absenceFrameNo 欠勤枠NO(Optional)
     * @param specialHolidayFrame 特別休暇枠NO(Optional)
     * @return 子の看護チェック区分/介護チェック区分
     */
    public ChildCareNurseCheck determineCareNursingCheck(String cId, List<Integer> absenceFrameNo, List<Integer> specialHolidayFrame) {
        // 子の看護チェック区分
        boolean childCareCheck = false;
        // 介護チェック区分
        boolean nursingCheck = false;
        
        // ドメイン「介護看護休暇設定」を取得する
        NursingLeaveSetting childNursingLeaveSettings = nursingLeaveSettingRepo
                .findByCompanyIdAndNursingCategory(cId, NursingCategory.ChildNursing.value);
        NursingLeaveSetting nursingLeaveSettings = nursingLeaveSettingRepo
                .findByCompanyIdAndNursingCategory(cId, NursingCategory.Nursing.value);
        
        // 子の看護チェック区分を判断する
        // falseの場合
        if (childNursingLeaveSettings.getSpecialHolidayFrame().isPresent() && !specialHolidayFrame.isEmpty() 
                && specialHolidayFrame.stream().filter(x -> x.equals(childNursingLeaveSettings.getSpecialHolidayFrame().get())).findFirst().isPresent()) {
            childCareCheck = true;
        }
        if (childNursingLeaveSettings.getWorkAbsence().isPresent() && !absenceFrameNo.isEmpty() 
                && absenceFrameNo.stream().filter(x -> x.equals(childNursingLeaveSettings.getWorkAbsence().get())).findFirst().isPresent()) {
            childCareCheck = true;
        }
        
        // 介護チェック区分を判断する
        if (nursingLeaveSettings.getSpecialHolidayFrame().isPresent() && !specialHolidayFrame.isEmpty() 
                && specialHolidayFrame.stream().filter(x -> x.equals(nursingLeaveSettings.getSpecialHolidayFrame().get())).findFirst().isPresent()) {
                nursingCheck = true;
        }
        if (nursingLeaveSettings.getWorkAbsence().isPresent() && !absenceFrameNo.isEmpty() 
                && absenceFrameNo.stream().filter(x -> x.equals(nursingLeaveSettings.getWorkAbsence().get())).findFirst().isPresent()) {
                nursingCheck = true;
        }
        
        // 「子の看護チェック区分・介護チェック区分」を渡す
        return new ChildCareNurseCheck(childCareCheck, nursingCheck);
    }
}
