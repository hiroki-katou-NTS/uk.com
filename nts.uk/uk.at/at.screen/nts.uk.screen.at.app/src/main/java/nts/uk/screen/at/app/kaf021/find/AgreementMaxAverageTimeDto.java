package nts.uk.screen.at.app.kaf021.find;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTime;

@Getter
public class AgreementMaxAverageTimeDto {
    public AgreementMaxAverageTimeDto(AgreMaxAverageTime domain) {
        this.totalTime = domain.getTotalTime().v();
        this.time = domain.getAverageTime().v();
        this.status = domain.getStatus().value;
    }

    /**
     * 合計時間
     */
    private Integer totalTime;
    /**
     * 平均時間
     */
    private Integer time;
    /**
     * 状態
     */
    private Integer status;
}
