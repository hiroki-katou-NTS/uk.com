package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

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
 * @author thanh.tq 支給項目設定
 */
@Getter
public class PaymentItemSet extends AggregateRoot {

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
     * 課税区分
     */
    private TaxAtr taxAtr;

    /**
     * 限度金額設定
     */
    private LimitAmountSetting limitAmountSetting;

    /**
     * 社会保険区分
     */
    private SocialInsuranceCategory socialInsuranceCategory;

    /**
     * 労働保険区分
     */
    private LaborInsuranceCategory laborInsuranceCategory;

    /**
     * 固定的賃金の設定
     */
    private FixedWage fixedWage;

    /**
     * 平均賃金区分
     */
    private AverageWageAtr averageWageAtr;

    /**
     * 内訳項目利用区分
     */
    private BreakdownItemUseAtr breakdownItemUseAtr;

    /**
     * 支給エラー範囲設定
     */
    private DetailAmountErrorAlarmRangeSetting errorRangeSetting;

    /**
     * 支給アラーム範囲設定
     */
    private DetailAmountErrorAlarmRangeSetting alarmRangeSetting;

    /**
     * 備考
     */
    private Optional<Memo> note;

    public PaymentItemSet(String cid, int categoryAtr, String itemNameCode, int taxAtr,
                          Integer taxableAmountAtr, Long limitAmount, Integer limitAmountAtr, String taxLimitAmountCode,
                          int socialInsuranceCategory, int laborInsuranceCategory,
                          int settingAtr, Integer everyoneEqualSet, Integer monthlySalary, Integer hourlyPay,
                          Integer dayPayee, Integer monthlySalaryPerday,
                          int averageWageAtr,
                          int breakdownItemUseAtr,
                          int errorUpperLimitSetAtr, BigDecimal errorUpRangeVal,
                          int errorLowerLimitSetAtr, BigDecimal errorLoRangeVal,
                          int alarmUpperLimitSetAtr, BigDecimal alarmUpRangeVal,
                          int alarmLowerLimitSetAtr, BigDecimal alarmLoRangeVal,
                          String note) {
        super();
        this.cid = cid;
        this.categoryAtr = EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class);
        this.itemNameCode = new ItemNameCode(itemNameCode);
        this.taxAtr = EnumAdaptor.valueOf(taxAtr, TaxAtr.class);
        this.limitAmountSetting = new LimitAmountSetting(taxableAmountAtr, limitAmount, limitAmountAtr,
                taxLimitAmountCode);
        this.socialInsuranceCategory = EnumAdaptor.valueOf(socialInsuranceCategory, SocialInsuranceCategory.class);
        this.laborInsuranceCategory = EnumAdaptor.valueOf(laborInsuranceCategory, LaborInsuranceCategory.class);
        this.fixedWage = new FixedWage(settingAtr, everyoneEqualSet, monthlySalary, hourlyPay, dayPayee,
                monthlySalaryPerday);
        this.averageWageAtr = EnumAdaptor.valueOf(averageWageAtr, AverageWageAtr.class);
        this.breakdownItemUseAtr = EnumAdaptor.valueOf(breakdownItemUseAtr, BreakdownItemUseAtr.class);
        this.errorRangeSetting = new DetailAmountErrorAlarmRangeSetting(errorUpperLimitSetAtr, errorUpRangeVal,
                errorLowerLimitSetAtr, errorLoRangeVal);
        this.alarmRangeSetting = new DetailAmountErrorAlarmRangeSetting(alarmUpperLimitSetAtr, alarmUpRangeVal,
                alarmLowerLimitSetAtr, alarmLoRangeVal);
        this.note = note == null ? Optional.empty() : Optional.of(new Memo(note));
    }
}
