package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting;

import java.util.List;
import java.util.Optional;

public interface AnyPeriodCorrectionFormatSettingRepository {
    /**
     * [1]  Insert(任意期間修正のフォーマット設定)
     * @param setting
     */
    void insert(AnyPeriodCorrectionFormatSetting setting);

    /**
     * [2]  Update(任意期間修正のフォーマット設定)
     * @param setting
     */
    void update(AnyPeriodCorrectionFormatSetting setting);

    /**
     * [3]  Delete(会社ID,コード)
     * @param companyId
     * @param code
     */
    void delete(String companyId, String code);

    /**
     * [4]  Get*
     * @param companyId
     * @return
     */
    List<AnyPeriodCorrectionFormatSetting> getAll(String companyId);

    /**
     * [5]  Get
     * @param companyId
     * @param code
     * @return
     */
    Optional<AnyPeriodCorrectionFormatSetting> get(String companyId, String code);
}
