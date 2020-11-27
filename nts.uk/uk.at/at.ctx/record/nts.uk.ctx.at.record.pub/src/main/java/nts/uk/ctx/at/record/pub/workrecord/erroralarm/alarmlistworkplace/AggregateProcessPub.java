package nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface AggregateProcessPub {

    /**
     * マスタチェック(基本)の集計処理
     *
     * @param cid             会社ID
     * @param period          期間
     * @param alarmCheckWkpId List＜職場のエラームチェックID＞
     * @param workplaceIds    List＜職場ID＞
     * @return List＜アラーム抽出結果＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckBasic(String cid, DatePeriod period,
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
     * @param period          期間
     * @param alarmCheckWkpId List＜職場のエラームチェックID＞
     * @param workplaceIds    List＜職場ID＞
     * @return List＜アラーム抽出結果＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckWorkplace(String cid, DatePeriod period,
                                                                             List<String> alarmCheckWkpId,
                                                                             List<String> workplaceIds);

    /**
     * スケジュール／日次の集計処理
     *
     * @param cid             会社ID
     * @param period          期間
     * @param alarmCheckWkpId List＜固定抽出条件ID＞
     * @param optionalIds     List＜任意抽出条件ID＞
     * @param workplaceIds    List＜職場ID＞
     * @return List＜アラーム抽出結果＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckSchedule(String cid, DatePeriod period,
                                                                            List<String> alarmCheckWkpId,
                                                                            List<String> optionalIds,
                                                                            List<String> workplaceIds);
}
