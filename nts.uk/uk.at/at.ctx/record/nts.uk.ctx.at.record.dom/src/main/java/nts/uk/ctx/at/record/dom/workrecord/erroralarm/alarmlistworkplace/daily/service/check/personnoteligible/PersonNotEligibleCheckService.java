package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.personnoteligible;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriodProc;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."5.年休未付与者（本年=0の人）をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class PersonNotEligibleCheckService {

    @Inject
    private RecordDomRequireService requireService;

    /**
     * 5.年休未付与者（本年=0の人）をチェック
     *
     * @param cid         会社ID
     * @param personInfos List＜個人社員基本情報＞
     * @param period      期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> check(String cid, List<PersonEmpBasicInfoImport> personInfos, DatePeriod period) {
        List<ExtractResultDto> results = new ArrayList<>();
        val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();
        GeneralDate criteriaDate = GeneralDate.today();
        // Input．List＜個人社員基本情報＞をループする
        for (PersonEmpBasicInfoImport personInfo : personInfos) {
            // 次回年休付与日を取得する
            Optional<AggrResultOfAnnualLeave> aggrResultOpt = GetAnnLeaRemNumWithinPeriodProc.algorithm(
                    require, cacheCarrier, cid, personInfo.getEmployeeId(),
                    period, InterimRemainMngMode.OTHER, criteriaDate,
                    false, false, Optional.of(false),
                    Optional.empty(), Optional.empty(), false,
                    Optional.empty(), Optional.empty(), Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty());

            if (!aggrResultOpt.isPresent()) continue;
            AggrResultOfAnnualLeave aggrResult = aggrResultOpt.get();

            GeneralDate ymd = aggrResult.getAsOfStartNextDayOfPeriodEnd().getYmd();
            Double endRemain = aggrResult.getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveNoMinus().getRemainingNumber().getTotalRemainingDays().v();
            Double periodRemain = aggrResult.getAsOfStartNextDayOfPeriodEnd().getRemainingNumber().getAnnualLeaveNoMinus().getRemainingNumber().getTotalRemainingDays().v();
            // 次回年休付与日をチェック //TODO Q&A 36682
            if (period.start().beforeOrEquals(personInfo.getRetirementDate()) &&
                    period.start().beforeOrEquals(ymd) &&
                    periodRemain == 0) {

                // 抽出結果を作成
                String message = TextResource.localize("KAL020_111", personInfo.getEmployeeCode() + "　" + personInfo.getBusinessName(),
                        String.valueOf(endRemain), String.valueOf(endRemain), ymd.toString("yyyy/MM/dd"));
                ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                        new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")), Optional.empty()),
                        null,
                        Optional.ofNullable(TextResource.localize("KAL020_121", ymd.toString("yyyy/MM/dd"))),
                        Optional.empty(),
                        null
                );

                // List＜抽出結果＞に作成した抽出結果を追加
                results.add(result);
            }
        }

        // List＜抽出結果＞を返す
        return results;
    }
}
