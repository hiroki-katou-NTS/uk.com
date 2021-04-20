package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageNumberOfDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageNumberOfTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageTime;
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
     * 平均日数
     */
    private Optional<AverageNumberOfDays> averageNumberOfDays;

    /**
     * 平均回数
     */
    private Optional<AverageNumberOfTimes> averageNumberOfTimes;

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
     * @param checkTarget          チェック対象
     * @param averageNumberOfDays  平均日数
     * @param averageNumberOfTimes 平均回数
     * @param averageTime          平均時間
     * @param averageRatio         平均比率
     */
    public static AverageValueItem create(String checkTarget,
                                          Integer averageNumberOfDays,
                                          Integer averageNumberOfTimes,
                                          Integer averageTime,
                                          Integer averageRatio) {

        Optional<BonusPaySettingCode> checkTargetOpt = checkTarget != null ? Optional.of(new BonusPaySettingCode(checkTarget)) : Optional.empty();

        Optional<AverageNumberOfDays> averageNumberOfDaysOpt = averageNumberOfDays != null ? Optional.of(EnumAdaptor.valueOf(averageNumberOfDays, AverageNumberOfDays.class)) : Optional.empty();

        Optional<AverageNumberOfTimes> averageNumberOfTimesOpt = averageNumberOfTimes != null ? Optional.of(EnumAdaptor.valueOf(averageNumberOfTimes, AverageNumberOfTimes.class)) : Optional.empty();

        Optional<AverageTime> averageTimeOpt = averageTime != null ? Optional.of(EnumAdaptor.valueOf(averageTime, AverageTime.class)) : Optional.empty();

        Optional<AverageRatio> averageRatioOpt = averageRatio != null ? Optional.of(EnumAdaptor.valueOf(averageRatio, AverageRatio.class)) : Optional.empty();

        return new AverageValueItem(checkTargetOpt, averageNumberOfDaysOpt, averageNumberOfTimesOpt, averageTimeOpt, averageRatioOpt);
    }
}
