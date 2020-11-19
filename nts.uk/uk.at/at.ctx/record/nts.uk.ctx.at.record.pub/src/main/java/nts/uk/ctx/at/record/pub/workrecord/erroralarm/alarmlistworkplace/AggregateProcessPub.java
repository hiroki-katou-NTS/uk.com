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
     * @param workPlaceInfos  List＜職場情報＞
     * @return List＜アラーム抽出結果＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckBasic(String cid, DatePeriod period,
                                                                         List<String> alarmCheckWkpId,
                                                                         List<String> workplaceIds,
                                                                         List<AlWorkPlaceInforExport> workPlaceInfos);

    /**
     * マスタチェック(日別)の集計処理
     *
     * @param cid             会社ID
     * @param period          期間
     * @param alarmCheckWkpId List＜職場のエラームチェックID＞
     * @param workplaceIds    List＜職場ID＞
     * @param workPlaceInfos  List＜職場情報＞
     * @return List＜アラーム抽出結果＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckDaily(String cid, DatePeriod period,
                                                                         List<String> alarmCheckWkpId,
                                                                         List<String> workplaceIds,
                                                                         List<AlWorkPlaceInforExport> workPlaceInfos);
}
