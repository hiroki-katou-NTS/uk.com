package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.EmployeeMonthlyPerError;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.アルゴリズム.Query.休暇残数エラーの取得.子の看護残数のチェック.子の看護残数のチェック
 * 
 */
public interface RemainChildCareCheck {
    
    /**
     * 子の看護残数のチェック
     * @param param
     * @return
     */
    List<EmployeeMonthlyPerError> checkRemainChildCare(RemainChildCareCheckParam param);
}
