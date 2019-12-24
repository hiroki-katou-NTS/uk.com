package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.lifeinsurance;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsurance;

@Value
public class LifeInsuranceCommand {
    /**
     * コード
     */
    private String lifeInsuranceCode;

    /**
     * 名称
     */
    private String lifeInsuranceName;

    public LifeInsurance toDomain(String cid, String lifeInsuranceCode, String lifeInsuranceName) {
        return new LifeInsurance(cid, lifeInsuranceCode, lifeInsuranceName);
    }
}
