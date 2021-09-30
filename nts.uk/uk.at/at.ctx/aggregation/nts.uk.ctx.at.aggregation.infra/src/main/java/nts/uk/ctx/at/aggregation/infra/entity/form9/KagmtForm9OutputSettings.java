package nts.uk.ctx.at.aggregation.infra.entity.form9;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.var;
import nts.uk.ctx.at.aggregation.dom.form9.Form9DetailOutputSetting;
import nts.uk.ctx.at.aggregation.dom.form9.Form9TimeRoundingSetting;
import nts.uk.ctx.at.aggregation.dom.form9.RoundingUnit;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_RPT_FORM9_OUTPUT_SETTING")
public class KagmtForm9OutputSettings extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KagmtForm9OutputSettingsPk pk;

    /**
     * 10進数表示時の時間丸め 処理単位
     */
    @Column(name = "ROUNDING_UNIT")
    public int roundingUnit;

    /**
     * 10進数表示時の時間丸め 処理方法
     */
    @Column(name = "ROUNDING_METHOD")
    public int roundingMethod;

    /**
     * 勤務時間がない場合 属性を空白とするか
     */
    @Column(name = "ALLZERO_IS_ATTRIBUTE_BLANK")
    public boolean isAtrBlank;


    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static KagmtForm9OutputSettings toEntity(String companyId, Form9DetailOutputSetting detailOutputSetting) {
        var pk = new KagmtForm9OutputSettingsPk(companyId);
        return new KagmtForm9OutputSettings(
                pk,
                detailOutputSetting.getTimeRoundingSetting().getRoundingUnit().value,
                detailOutputSetting.getTimeRoundingSetting().getRoundingMethod().value,
                detailOutputSetting.isAllZeroIsAttributeBlank()
        );
    }


    public static Form9DetailOutputSetting toDomain(KagmtForm9OutputSettings entity) {
        return new Form9DetailOutputSetting(
                new Form9TimeRoundingSetting(RoundingUnit.of(entity.roundingUnit), Rounding.valueOf(entity.roundingMethod)),
                entity.isAtrBlank
        );
    }
}
