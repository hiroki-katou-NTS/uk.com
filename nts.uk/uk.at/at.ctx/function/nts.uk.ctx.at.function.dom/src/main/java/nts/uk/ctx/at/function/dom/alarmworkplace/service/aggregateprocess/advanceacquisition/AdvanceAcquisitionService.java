package nts.uk.ctx.at.function.dom.alarmworkplace.service.aggregateprocess.advanceacquisition;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム_職場別.アルゴリズム.集計処理.集計処理.事前取得処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class AdvanceAcquisitionService {

    @Inject
    private WorkplaceAdapter workplaceAdapter;

    /**
     * 事前取得処理
     *
     * @param workplaceIds List＜職場ID＞
     * @param period       期間
     * @return Map＜職場ID、List＜社員情報＞＞
     */
    public Map<String, List<EmployeeInfoImported>> process(List<String> workplaceIds, DatePeriod period) {
        Map<String, List<EmployeeInfoImported>> empsByWp = new HashMap<>();
        // Input．List＜職場ID＞をループする
        for (String workplaceId : workplaceIds) {
            // [No.597]職場の所属社員を取得する
            List<EmployeeInfoImported> employees = workplaceAdapter.getLstEmpByWorkplaceIdsAndPeriod(Arrays.asList(workplaceId), period);
            // Map＜職場ID、List＜社員ID＞＞を作成
            empsByWp.put(workplaceId, employees);
        }
        return empsByWp;
    }
}
