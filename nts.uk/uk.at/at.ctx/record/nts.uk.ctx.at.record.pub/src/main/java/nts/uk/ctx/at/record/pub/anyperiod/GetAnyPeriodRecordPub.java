package nts.uk.ctx.at.record.pub.anyperiod;


import java.util.List;
import java.util.Map;

public interface GetAnyPeriodRecordPub {
    /**
     * 任意期間別実績の値を取得する
     * @param employeeIds
     * @param frameCode
     * @param itemIds
     * @return 任意期間別実績データ値マップ　（社員ID、任意期間別実績データ値リスト）
     */
    Map<String, AnyPeriodRecordValuesExport> getRecordValues(
            List<String> employeeIds,
            String frameCode,
            List<Integer> itemIds
    );
}
