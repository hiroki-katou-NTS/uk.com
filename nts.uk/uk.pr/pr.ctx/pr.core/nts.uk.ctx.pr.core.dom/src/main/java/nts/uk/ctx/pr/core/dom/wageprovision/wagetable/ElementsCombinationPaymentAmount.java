package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
 * 要素の組み合わせで支払う金額
 */
@AllArgsConstructor
@Getter
public class ElementsCombinationPaymentAmount extends DomainObject {

    /**
     * 賃金テーブル支給金額
     */
    private WageTablePaymentAmount wageTablePaymentAmount;

    /**
     * 要素属性
     */
    private ContentElementAttribute elementAttribute;

    public ElementsCombinationPaymentAmount(BigDecimal wageTablePaymentAmount, String firstMasterCode, Integer firstFrameNumber, Integer firstFrameLowerLimit, Integer firstFrameUpperLimit,
                                            String secondMasterCode, Integer secondFrameNumber, Integer secondFrameLowerLimit, Integer secondFrameUpperLimit,
                                            String thirdMasterCode, Integer thirdFrameNumber, Integer thirdFrameLowerLimit, Integer thirdFrameUpperLimit) {
        this.wageTablePaymentAmount = new WageTablePaymentAmount(wageTablePaymentAmount);
        this.elementAttribute = new ContentElementAttribute(firstMasterCode, firstFrameNumber, firstFrameLowerLimit, firstFrameUpperLimit,
                secondMasterCode, secondFrameNumber, secondFrameLowerLimit, secondFrameUpperLimit,
                thirdMasterCode, thirdFrameNumber, thirdFrameLowerLimit, thirdFrameUpperLimit);
    }

}
