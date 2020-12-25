package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.service.itemchecksystemunique;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.スケジュール／日次.アルゴリズム.システム固有のチェック項目.スケジュール未確定
 *
 * @author Le Huu Dat
 */
@Stateless
public class ScheduleUndecidedService {

    public int count(List<String> employeeIds, List<WorkScheduleWorkInforImport> workScheduleWorkInfos) {
        int count = 0;
        // Input．List＜社員情報＞をループする
        for (String employeeId : employeeIds) {
            // 勤務予定を絞り込む
            List<WorkScheduleWorkInforImport> workInfos = workScheduleWorkInfos.stream()
                    .filter(x -> x.getEmployeeId().equals(employeeId) && x.getConfirmedATR() == 0).collect(Collectors.toList());
            // 絞り込んだ勤務予定をチェック
            if (CollectionUtil.isEmpty(workInfos)) continue;
            count++;
        }

        return count;
    }
}
