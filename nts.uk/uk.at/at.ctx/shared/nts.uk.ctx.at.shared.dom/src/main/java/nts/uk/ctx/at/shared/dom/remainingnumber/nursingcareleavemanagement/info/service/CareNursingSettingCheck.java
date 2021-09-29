package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;

/**
 * @author anhnm
 * 介護設定が存在するかチェック
 *
 */
@Stateless
public class CareNursingSettingCheck {
    
    /**
     * @param require
     * @param employeeId 社員ID
     * @return boolean
     */
    public static boolean check(Require require, String employeeId) {
        return require.getCareByEmpId(employeeId).isPresent();
    }
    
    /**
     *  [R-1] 介護基本情報を取得
     */
    public static interface Require {
        Optional<CareLeaveRemainingInfo> getCareByEmpId(String empId);
    }

}
