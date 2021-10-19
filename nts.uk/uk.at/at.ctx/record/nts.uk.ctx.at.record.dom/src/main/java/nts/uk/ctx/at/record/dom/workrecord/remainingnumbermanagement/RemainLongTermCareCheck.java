package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.List;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNurseErrors;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.アルゴリズム.Query.休暇残数エラーの取得.介護残数のチェック.介護残数のチェック
 */
public interface RemainLongTermCareCheck {

    /**
     * 介護残数のチェック
     * @param param
     * @return
     */
    List<ChildCareNurseErrors> checkRemainLongTermCare(RemainLongTermCareCheckParam param);
}
