package nts.uk.screen.at.app.kaf021.query.a;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;

@Getter
public class AgreementTimeMonthDto {

    public AgreementTimeMonthDto(int yearMonth){
        this.yearMonth = yearMonth;
        this.time = 0;
        this.maxTime = 0;
        this.status = null;
        this.error = 0;
        this.alarm = 0;
    }

    public AgreementTimeMonthDto(AgreementTimeOfManagePeriod domain) {
        AgreementTimeOfMonthly time = domain.getAgreementTime();
        AgreementTimeOfMonthly maxTime = domain.getLegalMaxTime();
        this.yearMonth = domain.getYm().v();
        this.time = time.getAgreementTime().v();
        this.maxTime = maxTime.getAgreementTime().v();
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