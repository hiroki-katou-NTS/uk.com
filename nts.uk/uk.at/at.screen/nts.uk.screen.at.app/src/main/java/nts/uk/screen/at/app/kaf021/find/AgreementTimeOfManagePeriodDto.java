package nts.uk.screen.at.app.kaf021.find;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgreementTimeOfManagePeriodDto {
    /**
     * 月度
     */
    private int yearMonth;

    /**
     * 36協定対象時間
     */
    private Integer agreementTime;

    /**
     * 法定上限対象時間
     */
    private Integer agreementMaxTime;

    /**
     * 状態
     */
    private Integer status;
}
