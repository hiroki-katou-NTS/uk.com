package nts.uk.screen.at.app.kaf021.query.a;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgreementTimeMonthDto {
    /**
     * 月度
     */
    private int yearMonth;

    /**
     * 管理期間の36協定時間
     */
    private AgreementTimeDto time;

    /**
     * 36協定時間一覧.月別実績の36協定上限時間
     */
    private AgreementTimeDto maxTime;
}