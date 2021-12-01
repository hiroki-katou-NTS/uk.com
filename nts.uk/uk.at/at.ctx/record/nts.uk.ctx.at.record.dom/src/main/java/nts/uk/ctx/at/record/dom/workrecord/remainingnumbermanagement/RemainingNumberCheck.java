package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.TimeDigestionParam;

/**
 * @author anhnm
 * 各残数をチェックするか判断する
 *
 */
public interface RemainingNumberCheck {
    /**
     * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.アルゴリズム.Query.各残数をチェックするか判断する.各残数をチェックするか判断する
     * 各残数をチェックするか判断する
     * @param cId
     * @param workTypeCodes
     * @param timeDigest
     * @return
     */
    RemainNumberClassification determineCheckRemain(String cId, List<String> workTypeCodes, Optional<TimeDigestionParam> timeDigest);
}
