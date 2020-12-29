package nts.uk.ctx.at.schedule.infra.entity.budget.premium;


import lombok.*;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "KSCMT_PER_COST_CALC")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPerCostCal extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KscmtPerCostCalPk pk;

    @Column(name = "START_DATE")
    public GeneralDate startDate;

    @Column(name = "END_DATE")
    public GeneralDate endDate;
    //人件費計算設定.単価
    @Column(name = "UNITPRICE_ATR")
    public Integer unitPriceAtr;

    //人件費計算設定.備考
    @Column(name = "MEMO")
    public String memo;

    // 人件費計算設定.人件費丸め設定.人件費単価丸め
    @Column(name = "UNIT_PRICE_ROUNDING")
    public int unitPriceRounding;
    // 人件費計算設定 >単位->端数処理位置
    @Column(name = "COST_UNIT")
    public int costUnit;
    // 人件費計算設定 -単価＊時間の丸め-端数処理
    @Column(name = "COST_ROUNDING")
    public int costRounding;
    // 人件費計算設定->単価の設定方法
    @Column(name = "UNITPRICE_SETTING_METHOD")
    public int unitPriceSettingMethod;
    // 人件費計算設定->就業時間単価
    @Column(name = "WORKING_HOURS_UNITPRICE_ATR")
    public int wokkingHoursUnitPriceAtr;

    @Override
    protected Object getKey() {
        return null;
    }

    public KscmtPerCostCal update(DateHistoryItem domain) {
        this.endDate = domain.end();
        this.startDate = domain.start();
        return this;
    }

    public static KscmtPerCostCal toEntity(PersonCostCalculation domain, GeneralDate startDate, GeneralDate endDate, String histId) {
        val unitPrice = domain.getUnitPrice();

        return new KscmtPerCostCal(
                new KscmtPerCostCalPk(domain.getCompanyID(), histId),
                startDate,
                endDate,
                unitPrice.isPresent() ? unitPrice.get().value : null,
                domain.getRemark().v(),
                domain.getRoundingSetting().getRoundingOfPremium().getPriceRounding().value,
                domain.getRoundingSetting().getAmountRoundingSetting().getUnit().asAmountEnum(),
                domain.getRoundingSetting().getAmountRoundingSetting().getRounding().value,
                domain.getHowToSetUnitPrice().value,
                domain.getWorkingHoursUnitPrice().v()
        );
    }
}
