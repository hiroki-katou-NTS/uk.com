package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.reftimesetcfm;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicCheckName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アルゴリズム.マスタチェック(基本)の集計処理.基準時間確認
 *
 * @author Le Huu Dat
 */
@Stateless
public class RefTimeSetCfmService {

    @Inject
    private MonthlyWorkTimeSetRepo mnthlyWorkTimeSetRepo;

    /**
     * 基準時間確認
     *
     * @param cid            会社ID
     * @param name           アラーム項目名
     * @param displayMessage 表示するメッセージ.
     * @param ymPeriod       期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> confirm(String cid, BasicCheckName name, DisplayMessage displayMessage, YearMonthPeriod ymPeriod) {
        // 空欄のリスト「アラーム抽出結果（職場別）」を作成する。
        List<ExtractResultDto> results = new ArrayList<>();
        List<MonthlyWorkTimeSetCom> monthlySets = new ArrayList<>();
        monthlySets.addAll(mnthlyWorkTimeSetRepo.findCompanyByPeriod(cid, MonthlyWorkTimeSet.LaborWorkTypeAttr.REGULAR_LABOR, ymPeriod));
        monthlySets.addAll(mnthlyWorkTimeSetRepo.findCompanyByPeriod(cid, MonthlyWorkTimeSet.LaborWorkTypeAttr.DEFOR_LABOR, ymPeriod));
        monthlySets.addAll(mnthlyWorkTimeSetRepo.findCompanyByPeriod(cid, MonthlyWorkTimeSet.LaborWorkTypeAttr.FLEX, ymPeriod));
        YearMonth loopYm = YearMonth.of(ymPeriod.start().year(), ymPeriod.start().month());
        while (loopYm.lessThanOrEqualTo(ymPeriod.end())) {
            // ドメインモデル「会社別月単位労働時間」を取得する。
            final YearMonth finalLoopYm = loopYm;
            List<MonthlyWorkTimeSetCom> filterdSets = monthlySets.stream().filter(i -> i.getYm().equals(finalLoopYm)).collect(Collectors.toList());

            if (CollectionUtil.isEmpty(monthlySets)) {
                // 「アラーム値メッセージ」を作成します。
                String message = TextResource.localize("KAL020_6", String.valueOf(loopYm.year()),
                        AppContexts.user().companyCode());
                // ドメインオブジェクト「抽出結果」を作成します。
                ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                        new AlarmValueDate(loopYm.toString(), Optional.empty()),
                        name.v(),
                        Optional.ofNullable(TextResource.localize("KAL020_15")),
                        Optional.of(new MessageDisplay(displayMessage.v())),
                        null
                );

                // リスト「抽出結果」に作成した抽出結果を追加する。
                results.add(result);
            }

            loopYm = loopYm.addMonths(1);
        }

        // リスト「抽出結果」を返す。
        return results;
    }
}
