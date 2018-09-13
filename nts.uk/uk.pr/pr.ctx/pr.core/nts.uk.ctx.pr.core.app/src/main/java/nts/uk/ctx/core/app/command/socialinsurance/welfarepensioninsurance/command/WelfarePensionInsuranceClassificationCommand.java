package nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.FundClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;

/**
 * 厚生年金保険区分
 */
@AllArgsConstructor
@Data
public class WelfarePensionInsuranceClassificationCommand {
	/**
     * 履歴ID
     */
    private String historyId;

    /**
     * 厚生年金基金加入区分
     */
    private int fundClassification;

    public WelfarePensionInsuranceClassification fromCommandToDomain() {
        return new WelfarePensionInsuranceClassification(this.historyId, this.fundClassification);
    }
}
