package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import java.util.List;
import java.util.Optional;

public interface SupportWorkListOutputSettingRepository {
    /**
     * [1] Insert(応援勤務一覧表の出力設定)
     * @param domain
     */
    void insert(SupportWorkListOutputSetting domain);

    /**
     * [2] Update(応援勤務一覧表の出力設定)
     * @param domain
     */
    void update(SupportWorkListOutputSetting domain);

    /**
     * [3] Delete(会社ID,コード)
     * @param companyId
     * @param code
     */
    void delete(String companyId, SupportWorkOutputCode code);

    /**
     * 	[4] Get
     * @param companyId 会社ID
     * @return 出力設定一覧
     */
    List<SupportWorkListOutputSetting> get(String companyId);

    /**
     * [5] Get
     * @param companyId 会社ID
     * @param code 応援勤務表コード
     * @return 出力設定
     */
    Optional<SupportWorkListOutputSetting> get(String companyId, SupportWorkOutputCode code);
}
