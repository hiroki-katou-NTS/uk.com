package nts.uk.screen.at.app.kaf021.find;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;

@Getter
public class AgreementTimeDto {
    public AgreementTimeDto(AgreementTimeOfManagePeriod domain) {
        AgreementTimeOfMonthly time = domain.getAgreementTime();
        this.time = time.getAgreementTime().v();
        this.maxTime = time.getThreshold().getUpperLimit().v();
        this.status = domain.getStatus().value;
        this.error = time.getThreshold().getErAlTime().getError().v();
        this.alarm = time.getThreshold().getErAlTime().getAlarm().v();
    }

    /*public AgreementTimeDto(AgreMaxTimeOfMonthly domain) {
        this.time = domain.getAgreementTime().v();
        this.maxTime = domain.getMaxTime().v();
        this.status = domain.getStatus().value;
    }*/

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
    /**
     * エラー時間
     */
    private Integer error;
    /**
     * アラーム時間
     */
    private Integer alarm;
}
