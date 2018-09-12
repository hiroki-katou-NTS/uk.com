package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.Optional;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 厚生年金保険区分
 */
@Getter
public class WelfarePensionInsuranceClassification extends AggregateRoot {

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 厚生年金基金加入区分
     */
    private FundClassification fundClassification;

    public WelfarePensionInsuranceClassification(String historyId, int fundClassification) {
        this.historyId = historyId;
        this.fundClassification = EnumAdaptor.valueOf(fundClassification, FundClassification.class);
    }
}
