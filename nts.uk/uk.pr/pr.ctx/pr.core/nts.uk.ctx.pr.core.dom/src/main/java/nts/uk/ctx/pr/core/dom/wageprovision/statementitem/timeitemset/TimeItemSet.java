package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.AverageWageAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;
import nts.uk.shr.com.primitive.Memo;

/**
 * @author thanh.tq 勤怠項目設定
 */
@Getter
public class TimeItemSet extends AggregateRoot {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * カテゴリ区分
     */
    private CategoryAtr categoryAtr;

    /**
     * 項目名コード
     */
    private ItemNameCode itemNameCode;

    /**
     * 時間回数区分
     */
    private TimeCountAtr timeCountAtr;

    /**
     * 所定労働日数区分
     */
    private Optional<ClassifiedWorkingDaysPerYear> workingDaysPerYear;

    /**
     * 平均賃金区分
     */
    private Optional<AverageWageAtr> averageWageAtr;

    /**
     * 勤怠エラー範囲設定
     */
    private DetailTimeErrorAlarmRangeSetting errorRangeSetting;

    /**
     * 勤怠アラーム範囲設定
     */
    private DetailTimeErrorAlarmRangeSetting alarmRangeSetting;

    /**
     * 備考
     */
    private Optional<Memo> note;

    public TimeItemSet(String cid, int categoryAtr, String itemNameCode,
                       int timeCountAtr, Integer workingDaysPerYear, Integer averageWageAtr,
                       int errorUpperLimitSetAtr, Integer errorUpRangeValTime, BigDecimal errorUpRangeValNum,
                       int errorLowerLimitSetAtr, Integer errorLoRangeValTime, BigDecimal errorLoRangeValNum,
                       int alarmUpperLimitSetAtr, Integer alarmUpRangeValTime, BigDecimal alarmUpRangeValNum,
                       int alarmLowerLimitSetAtr, Integer alarmLoRangeValTime, BigDecimal alarmLoRangeValNum,
                       String note) {
        super();
        this.cid = cid;
        this.categoryAtr = EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class);
        this.itemNameCode = new ItemNameCode(itemNameCode);
        this.timeCountAtr = EnumAdaptor.valueOf(timeCountAtr, TimeCountAtr.class);
        this.workingDaysPerYear = Optional
                .ofNullable(EnumAdaptor.valueOf(workingDaysPerYear, ClassifiedWorkingDaysPerYear.class));
        this.averageWageAtr = Optional.of(EnumAdaptor.valueOf(averageWageAtr, AverageWageAtr.class));
        this.errorRangeSetting = new DetailTimeErrorAlarmRangeSetting(errorUpperLimitSetAtr, errorUpRangeValNum, errorUpRangeValTime,
                errorLowerLimitSetAtr, errorLoRangeValNum, errorLoRangeValTime);
        this.alarmRangeSetting = new DetailTimeErrorAlarmRangeSetting(alarmUpperLimitSetAtr, alarmUpRangeValNum, alarmUpRangeValTime,
                alarmLowerLimitSetAtr, alarmLoRangeValNum, alarmLoRangeValTime);
        this.note = note == null ? Optional.empty() : Optional.of(new Memo(note));
    }

}
