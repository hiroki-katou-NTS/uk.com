package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.beforecheck.unregistedstamp;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.StampRepository;
import nts.uk.ctx.at.shared.dom.adapter.workplace.affiliate.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.affiliate.SharedAffWorkplaceHistoryItemAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック前の取得するデータ.職場IDから未登録打刻カードを取得
 *
 * @author Le Huu Dat
 */
@Stateless
public class UnregistedStampCardService {

    @Inject
    private StampRepository stampRepository;
    @Inject
    private SharedAffWorkplaceHistoryItemAdapter sharedAffWorkplaceHistoryItemAdapter;

    /**
     * 職場IDから未登録打刻カードを取得
     *
     * @param workplaceIds List＜職場ID＞
     * @param period       期間（YMD)
     * @return Map＜職場ID、List＜打刻日、未登録打刻カード＞＞
     */
    public Map<String, List<Object>> getUnregistedStampCard(List<String> workplaceIds, DatePeriod period) {
        // 期間と職場一覧から所属職場履歴項目を取得する
        List<AffWorkplaceHistoryItemImport> affWpHistItems = sharedAffWorkplaceHistoryItemAdapter
                .getListAffWkpHistItem(period, workplaceIds);

        // ドメインモデル「打刻」を取得
        // TODO Q&A 36536

        // ドメインモデル「打刻カード」を取得
        // TODO Q&A 36537

        // 未登録打刻カードをチェック

        // Map＜職場ID、List＜打刻日、未登録打刻カード＞＞を作成

        // Map＜職場ID、List＜未登録打刻カード＞＞を渡す
        return workplaceIds.stream().collect(Collectors.toMap(x -> x, y -> new ArrayList<>()));
    }
}
