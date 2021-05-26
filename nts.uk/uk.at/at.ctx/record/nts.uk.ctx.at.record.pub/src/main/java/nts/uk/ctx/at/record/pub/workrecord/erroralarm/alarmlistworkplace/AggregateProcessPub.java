package nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;

import java.util.List;

public interface AggregateProcessPub {

    /**
     * マスタチェック(基本)の集計処理
     *
     * @param cid             会社ID
     * @param ymPeriod        期間
     * @param alarmCheckWkpId List＜職場のエラームチェックID＞
     * @param workplaceIds    List＜職場ID＞
     * @return List＜アラーム抽出結果＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckBasic(String cid, YearMonthPeriod ymPeriod,
                                                                         List<String> alarmCheckWkpId,
                                                                         List<String> workplaceIds);

    /**
     * マスタチェック(日別)の集計処理
     *
     * @param cid             会社ID
     * @param period          期間
     * @param alarmCheckWkpId List＜職場のエラームチェックID＞
     * @param workplaceIds    List＜職場ID＞
     * @return List＜アラーム抽出結果＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckDaily(String cid, DatePeriod period,
                                                                         List<String> alarmCheckWkpId,
                                                                         List<String> workplaceIds);

    /**
     * マスタチェック(日別)の集計処理
     *
     * @param cid             会社ID
     * @param ymPeriod        期間
     * @param alarmCheckWkpId List＜職場のエラームチェックID＞
     * @param workplaceIds    List＜職場ID＞
     * @return List＜アラーム抽出結果＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckWorkplace(String cid, YearMonthPeriod ymPeriod,
                                                                             List<String> alarmCheckWkpId,
                                                                             List<String> workplaceIds);

    /**
     * スケジュール／日次の集計処理
     *
     * @param cid                 会社ID
     * @param period              期間
     * @param fixedExtractCondIds List＜固定抽出条件ID＞
     * @param extractCondIds      List＜任意抽出条件ID＞
     * @param workplaceIds        List＜職場ID＞
     * @return List＜アラーム抽出結果＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processSchedule(String cid, DatePeriod period,
                                                                 List<String> fixedExtractCondIds,
                                                                 List<String> extractCondIds,
                                                                 List<String> workplaceIds);

    /**
     * 月次の集計処理
     *
     * @param cid                 会社ID
     * @param ym                  年月
     * @param fixedExtractCondIds List＜固定抽出条件ID＞
     * @param extractCondIds      List＜任意抽出条件ID＞
     * @param workplaceIds        List<職場ID＞
     * @return List＜アラームリスト抽出情報（職場）＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMonthly(String cid, YearMonth ym,
                                                                List<String> fixedExtractCondIds,
                                                                List<String> extractCondIds,
                                                                List<String> workplaceIds);

    /**
     * 申請承認の集計処理
     *
     * @param period              期間
     * @param fixedExtractCondIds List＜固定抽出条件ID＞
     * @param workplaceIds        List<職場ID＞
     * @return List＜アラームリスト抽出情報（職場）＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processAppApproval(DatePeriod period,
                                                                    List<String> fixedExtractCondIds,
                                                                    List<String> workplaceIds);
}
