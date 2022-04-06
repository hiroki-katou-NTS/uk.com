package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

import java.util.Optional;

public interface SupportWorkAggregationSettingRepository {
    /**
     * [1] Insert(応援勤務集計設定)
     * @param domain
     */
    void insert(SupportWorkAggregationSetting domain);

    /**
     * [2] Update(応援勤務集計設定)
     * @param domain
     */
    void update(SupportWorkAggregationSetting domain);

    /**
     * [3] Get
     * 応援勤務集計設定を取得する
     * @param companyId
     * @return 応援勤務集計設定
     */
    Optional<SupportWorkAggregationSetting> get(String companyId);
}
