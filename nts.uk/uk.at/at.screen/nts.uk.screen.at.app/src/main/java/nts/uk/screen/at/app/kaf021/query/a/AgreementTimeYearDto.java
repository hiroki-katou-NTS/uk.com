package nts.uk.screen.at.app.kaf021.query.a;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;

@Getter
public class AgreementTimeYearDto {

    public AgreementTimeYearDto(int year){
        this.year = year;
        this.time = new AggreementTimeDto();
        this.maxTime = new AggreementTimeDto();
        this.status = null;
    }

    public AgreementTimeYearDto(int year, AgreementTimeYear domain) {
        this.year = year;
        this.time = new AggreementTimeDto(domain.getRecordTime());
        this.maxTime = new AggreementTimeDto(domain.getLimitTime());
        this.status = domain.getStatus().value;
    }

    /**
     * 年間
     */
    private int year;

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