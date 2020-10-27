package nts.uk.screen.at.app.kaf021.query.a;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;

@Getter
public class AgreementTimeMonthDto {

    public AgreementTimeMonthDto(AgreementTimeOfManagePeriod domain) {
        AgreementTimeOfMonthly time = domain.getAgreementTime();
        this.yearMonth = domain.getYm().v();
        this.time = time.getAgreementTime().v();
        this.maxTime = time.getThreshold().getUpperLimit().v();
        this.status = domain.getStatus().value;
        this.error = time.getThreshold().getErAlTime().getError().v();
        this.alarm = time.getThreshold().getErAlTime().getAlarm().v();
    }

    /**
     * 月度
     */
    private int yearMonth;

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