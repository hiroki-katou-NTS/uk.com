package nts.uk.screen.at.app.kaf021.query.a;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;

@Getter
public class AgreementTimeYearDto {

    public AgreementTimeYearDto(int year){
        this.year = year;
        this.limitTime = 0;
        this.time = 0;
        this.status = null;
        this.error = 0;
        this.alarm = 0;
    }

    public AgreementTimeYearDto(int year, AgreementTimeYear domain) {
        this.year = year;
        AgreementTimeOfYear time = domain.getRecordTime();
        this.limitTime = time.getThreshold().getUpperLimit().v();
        this.time = time.getTargetTime().v();
        this.status = domain.getStatus().value;
        this.error = time.getThreshold().getErAlTime().getError().v();
        this.alarm = time.getThreshold().getErAlTime().getAlarm().v();
    }

    /**
     * 年間
     */
    private int year;

    /**
     * 限度時間
     */
    private Integer limitTime;
    /**
     * 実績時間
     */
    private Integer time;
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