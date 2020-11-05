package nts.uk.screen.at.app.kaf021.query.a;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;

/**
 * ３６協定運用設定 Dto
 */
@Getter
public class AgreementOperationSettingDto {
    public AgreementOperationSettingDto(AgreementOperationSetting domain) {
        this.startingMonth = domain.getStartingMonth().getMonth();
        this.useSpecical = domain.isSpecicalConditionApplicationUse();
        this.useYear = domain.isYearSpecicalConditionApplicationUse();
    }

    /** ３６協定起算月 **/
    private Integer startingMonth;

    /** 特別条項申請を使用する **/
    private boolean useSpecical;

    /** 年間の特別条項申請を使用する **/
    private boolean useYear;
}
