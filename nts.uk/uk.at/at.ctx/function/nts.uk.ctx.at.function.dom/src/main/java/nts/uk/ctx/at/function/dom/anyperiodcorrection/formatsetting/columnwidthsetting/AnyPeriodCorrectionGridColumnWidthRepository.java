package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting;

import java.util.Optional;

public interface AnyPeriodCorrectionGridColumnWidthRepository {
    /**
     * [1]  Insert(任意期間修正のグリッド列幅)
     * @param setting
     */
    void insert(AnyPeriodCorrectionGridColumnWidth setting);

    /**
     * [2]  Update(任意期間修正のグリッド列幅)
     * @param setting
     */
    void update(AnyPeriodCorrectionGridColumnWidth setting);

    /**
     * [3]  Delete(社員ID)
     * @param employeeId
     */
    void delete(String employeeId);

    /**
     * [4]  Get
     * @param employeeId
     * @return
     */
    Optional<AnyPeriodCorrectionGridColumnWidth> get(String employeeId);
}
