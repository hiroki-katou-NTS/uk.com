package nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting;

/**
 * 実績集計方法
 */
public class Achievements {


    /**
     *基準時間
     */
    // Reference time.
    private ReferenceTime referenceTime;

    /**
     *日別実績の集計
     */
    // Aggregation of daily results.
    private AchievementMethod dailyResults;

    /**
     *月別実績の集計
     */
    // Aggregation of monthly results.
    private AchievementMethod monthlyResults;
}
