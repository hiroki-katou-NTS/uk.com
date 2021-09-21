package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;

/**
 * @author anhnm
 * 子の看護設定が存在するかチェック
 *
 */
@Stateless
public class ChildNursingSettingCheck {
    
    /**
     * @param require
     * @param employeeId 社員ID
     * @return boolean
     */
    public boolean check(Require require, String employeeId) {
        return require.getChildCareByEmpId(employeeId).isPresent();
    }

    /**
     *  [R-1] 子の看護休暇基本情報を取得
     */
    public static interface Require {
        Optional<ChildCareLeaveRemainingInfo> getChildCareByEmpId(String empId);
    }
}
