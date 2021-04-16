package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.beforeaggregate;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.ReAlarmWorkplaceAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.申請承認.アルゴリズム.申請承認の集計処理.申請承認の集計する前のデータを準備
 *
 * @author Le Huu Dat
 */
@Stateless
public class BeforeAggregateAppApproveService {

    @Inject
    private ReAlarmWorkplaceAdapter reAlarmWorkplaceAdapter;

    /**
     * 申請承認の集計する前のデータを準備
     *
     * @param period              期間
     * @param workplaceIds        List<職場ID＞
     * @return Map＜職場ID、List＜社員情報＞＞
     */
    public Map<String, List<EmployeeInfoImport>> prepareData(List<String> workplaceIds, DatePeriod period) {
        // 職場IDから社員情報を取得
        Map<String, List<EmployeeInfoImport>> empsByWpMap = reAlarmWorkplaceAdapter.advanceAcquisitionProcess(workplaceIds, period);

        // 取得したデータを返す
        return empsByWpMap;
    }
}
