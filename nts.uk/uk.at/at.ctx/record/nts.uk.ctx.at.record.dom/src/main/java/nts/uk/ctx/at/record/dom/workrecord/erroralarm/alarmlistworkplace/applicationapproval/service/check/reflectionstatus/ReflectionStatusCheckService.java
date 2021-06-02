package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.check.reflectionstatus;

import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.record.dom.adapter.request.application.approvalstatus.ApplicationDateImport;
import nts.uk.ctx.at.record.dom.adapter.request.application.approvalstatus.ReflectionStatusAdapter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.申請承認.アルゴリズム.申請承認の集計処理.固定抽出条件をチェック.反映状態をチェックする
 *
 * @author Le Huu Dat
 */
@Stateless
public class ReflectionStatusCheckService {

    @Inject
    private ReflectionStatusAdapter reflectionStatusAdapter;

    /**
     * 反映状態をチェックする
     *
     * @param employeeInfos List＜社員情報＞
     * @param period        期間
     * @param reflectState  反映状態
     * @return 抽出結果
     */
    public ExtractResultDto check(List<EmployeeInfoImport> employeeInfos, DatePeriod period, int reflectState) {

        List<String> employeeIds = employeeInfos.stream().map(x -> x.getSid()).collect(Collectors.toList());
        // [No.690]社員（List）,期間に一致する申請を取得する
        List<ApplicationDateImport> appDates = reflectionStatusAdapter.getAppByEmployeeDate(employeeIds, period)
                .entrySet().stream().flatMap(x -> x.getValue().stream())
                .collect(Collectors.toList());

        // 実績反映状態＝【INPUT.反映状態】の件数をチェックする
        // (Check số record có reflectStatus thực tế = 【INPUT.reflect】)
        List<ApplicationDateImport> appDateFilter = appDates.stream().filter(x -> x.getApplication().getReflectionStatus()
                .getListReflectionStatusOfDayExport().stream().anyMatch(c -> c.getActualReflectStatus() == reflectState))
                .collect(Collectors.toList());
        int count = appDateFilter.size();
        if (count == 0) {
            return null;
        }

        AlarmValueDate alarmValueDate = new AlarmValueDate(period.start().toString("yyyyMMdd"),
                Optional.of(period.end().toString("yyyyMMdd")));
        String message = "";
        switch (reflectState) {
            case 0:
                message = TextResource.localize("KAL020_500", String.valueOf(count));
                break;
            case 5:
                message = TextResource.localize("KAL020_502", String.valueOf(count));
                break;
            case 1:
                message = TextResource.localize("KAL020_503", String.valueOf(count));
                break;
        }
        return new ExtractResultDto(new AlarmValueMessage(message),
                alarmValueDate,
                null,
                Optional.ofNullable(message),
                Optional.empty(),
                null
        );
    }
}
