package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicCalculationForm;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.BasicCalculationFormula;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* かんたん計算式
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_BASIC_CAL_STD_AMOU")
public class QpbmtBasicCalculationStandardAmount extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @EmbeddedId
    public QpbmtBasicCalculationStandardAmountPk basicCalStdAmountPk;

    @Override
    protected Object getKey()
    {
        return basicCalStdAmountPk;
    }


    public static List<QpbmtBasicCalculationStandardAmount> toEntity(BasicCalculationFormula domain) {
        if (!domain.getBasicCalculationForm().isPresent()) return Collections.emptyList();
        return domain.getBasicCalculationForm().get().getBasicCalculationStandardAmount().getTargetItemCodeList().stream().map(item -> new QpbmtBasicCalculationStandardAmount(new QpbmtBasicCalculationStandardAmountPk(AppContexts.user().companyId(), domain.getFormulaCode().v(), domain.getHistoryID(), domain.getMasterUseCode().v(), item.v()))).collect(Collectors.toList());
    }
}
