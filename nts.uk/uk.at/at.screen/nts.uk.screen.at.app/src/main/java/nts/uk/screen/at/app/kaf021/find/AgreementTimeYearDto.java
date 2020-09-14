package nts.uk.screen.at.app.kaf021.find;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;

@Getter
public class AgreementTimeYearDto {

    public AgreementTimeYearDto(AgreementTimeYear domain) {
        this.limitTime = domain.getLimitTime().v();
        this.time = domain.getRecordTime().v();
        this.status = domain.getStatus().value;
    }

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
}