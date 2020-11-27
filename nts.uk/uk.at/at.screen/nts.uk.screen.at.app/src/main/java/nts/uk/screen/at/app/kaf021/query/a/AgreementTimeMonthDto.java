package nts.uk.screen.at.app.kaf021.query.a;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;

@Getter
public class AgreementTimeMonthDto {

    public AgreementTimeMonthDto(int yearMonth) {
        this.yearMonth = yearMonth;
        this.time = new AggreementTimeDto();
        this.maxTime = new AggreementTimeDto();
        this.status = null;
    }

    public AgreementTimeMonthDto(AgreementTimeOfManagePeriod domain) {
        this.yearMonth = domain.getYm().v();
        this.time = new AggreementTimeDto(domain.getAgreementTime());
        this.maxTime = new AggreementTimeDto(domain.getLegalMaxTime());
        this.status = domain.getStatus().value;
    }

    /**
     * 月度
     */
    private int yearMonth;

    /**
     * 36協定対象時間
     */
    private AggreementTimeDto time;

    /**
     * 法定上限対象時間
     */
    private AggreementTimeDto maxTime;

    /**
     * 状態
     */
    private Integer status;
}