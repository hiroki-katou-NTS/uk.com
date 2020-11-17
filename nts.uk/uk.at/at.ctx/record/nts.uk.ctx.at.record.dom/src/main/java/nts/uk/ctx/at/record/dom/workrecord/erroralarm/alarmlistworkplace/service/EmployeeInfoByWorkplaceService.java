package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.アルゴリズム.職場ID一覧から社員情報を取得する。
 *
 * @author Le Huu Dat
 */
@Stateless
public class EmployeeInfoByWorkplaceService {

    @Inject
    private SyWorkplaceAdapter syWorkplaceAdapter;

    /**
     * 職場ID一覧から社員情報を取得する。
     *
     * @param workplaceIds 職場ID
     * @param period       期間
     * @return Map＜職場ID、List＜社員情報＞＞
     */
    public Map<String, List<EmployeeInfoImported>> get(List<String> workplaceIds, DatePeriod period) {
        Map<String, List<EmployeeInfoImported>> empInfoMap = new HashMap<>();
        for (String workplaceId : workplaceIds) {
            // [No.597]職場の所属社員を取得する
            List<EmployeeInfoImported> empInfos = syWorkplaceAdapter.getLstEmpByWorkplaceIdsAndPeriod(
                    Collections.singletonList(workplaceId), period);
            // Map＜職場ID、List＜社員情報＞＞を作成する。
            empInfoMap.put(workplaceId, empInfos);
        }

        // List＜社員ID＞とMap＜職場ID、List＜社員ID＞＞を返す。
        return empInfoMap;
    }
}
