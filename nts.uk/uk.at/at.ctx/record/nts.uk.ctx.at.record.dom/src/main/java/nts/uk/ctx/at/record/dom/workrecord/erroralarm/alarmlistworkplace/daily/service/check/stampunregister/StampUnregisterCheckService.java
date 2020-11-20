package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.stampunregister;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."9.カード未登録打刻をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class StampUnregisterCheckService {

    /**
     * 9.カード未登録打刻をチェック
     *
     * @param unregistedStampCards List＜打刻日、未登録打刻カード＞
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> check(List<Object> unregistedStampCards) {
        List<ExtractResultDto> results = new ArrayList<>();
        // Input．List＜打刻日、未登録打刻カード＞をチェック
        if (CollectionUtil.isEmpty(unregistedStampCards)) return results;

        // Input．List＜打刻日、未登録打刻カード＞をループする
        for (Object unregistedStampCard : unregistedStampCards) {
            // 抽出結果を作成
            String message = TextResource.localize("KAL020_104", "card no"); //TODO
            ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                    new AlarmValueDate(Integer.valueOf("0000"), Optional.empty()), //TODO
                    null,
                    Optional.ofNullable(TextResource.localize("KAL020_119", "card no")), //TODO
                    Optional.empty(),
                    null
            );

            // List＜抽出結果＞に作成した抽出結果を追加
            results.add(result);
        }

        // List＜抽出結果＞を返す
        return results;
    }
}
