package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.AverageNumberOfDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.AverageNumberOfTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.monthly.enums.AverageTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;

import java.util.Optional;

/**
 * Class: 平均値項目
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class AverageValueItem {
    /**
     * チェック対象
     */
    private Optional<BonusPaySettingCode> checkTarget;

    /**
     * 平均回数
     */
    private Optional<AverageNumberOfTimes> averageNumberOfTimes;

    /**
     * 平均日数
     */
    private Optional<AverageNumberOfDays> averageNumberOfDays;

    /**
     * 平均時間
     */
    private Optional<AverageTime> averageTime;

    /**
     * 平均比率
     */
    private Optional<AverageRatio> averageRatio;

    /**
     * 作成する
     *
     * @param checkTarget          Optional<チェック対象>
     * @param averageNumberOfTimes Optional<平均回数>
     * @param averageNumberOfDays  Optional<平均日数>
     * @param averageTime          Optional<平均時間>
     * @param averageRatio         Optional<平均比率>
     */
    public static AverageValueItem create(Optional<BonusPaySettingCode> checkTarget,
                                          Optional<AverageNumberOfTimes> averageNumberOfTimes,
                                          Optional<AverageNumberOfDays> averageNumberOfDays,
                                          Optional<AverageTime> averageTime,
                                          Optional<AverageRatio> averageRatio) {

        return new AverageValueItem(checkTarget, averageNumberOfTimes, averageNumberOfDays, averageTime, averageRatio);
    }
}
