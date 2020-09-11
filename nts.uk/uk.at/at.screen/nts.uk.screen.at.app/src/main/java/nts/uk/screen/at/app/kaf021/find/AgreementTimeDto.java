package nts.uk.screen.at.app.kaf021.find;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreMaxTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;

@Getter
public class AgreementTimeDto {
    public AgreementTimeDto(AgreementTimeOfManagePeriod domain) {
        this.time = domain.getAgreementTime().getAgreementTime().getAgreementTime().v();
        this.maxTime = domain.getAgreementMaxTime().getAgreementTime().getMaxTime().v();
        this.status = domain.getAgreementTime().getAgreementTime().getStatus().value;
    }

    public AgreementTimeDto(AgreMaxTimeOfMonthly domain) {
        this.time = domain.getAgreementTime().v();
        this.maxTime = domain.getMaxTime().v();
        this.status = domain.getStatus().value;
    }

    /**
     * 36協定時間
     */
    private Integer time;

    /**
     * 上限時間
     */
    private Integer maxTime;

    /**
     * 状態
     */
    private Integer status;
}
