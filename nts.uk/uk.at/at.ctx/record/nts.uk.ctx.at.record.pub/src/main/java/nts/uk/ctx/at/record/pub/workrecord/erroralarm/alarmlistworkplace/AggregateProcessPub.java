package nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface AggregateProcessPub {

    /**
     * マスタチェック(基本)の集計処理
     *
     * @param cid                    会社ID
     * @param period                 期間
     * @param workplaceErrorCheckIds List＜職場のエラームチェックID＞
     * @param workplaceIds           List＜職場ID＞
     */
    List<AlarmListExtractionInfoWorkplaceExport> processMasterCheckBasic(String cid, DatePeriod period, List<String> workplaceErrorCheckIds, List<String> workplaceIds);
}
