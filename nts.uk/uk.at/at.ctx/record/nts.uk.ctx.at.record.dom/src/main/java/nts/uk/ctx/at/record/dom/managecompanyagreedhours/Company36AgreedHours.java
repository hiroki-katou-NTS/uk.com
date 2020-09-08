package nts.uk.ctx.at.record.dom.managecompanyagreedhours;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

/**
 * AggregateRoot: 		会社３６協定時間
 * Responsibility : 	全社の３６協定時間を管理する
 * @author chinh.hm
 */
@Getter
@NoArgsConstructor
public class Company36AgreedHours extends AggregateRoot {
    /**
     * The companyId.
     * 会社ID
     */
    private String companyId;

    /**
     * 労働制
     */
    private LaborSystemtAtr laborSystemtAtr;
    /**
     * ３６協定基本設定
     **/
    // TODO SẼ QUAY LẠI SAU.
    private BasicAgreementSetting basicAgreementSetting;

    public Company36AgreedHours(String companyId, ClassificationCode classificationCode, LaborSystemtAtr laborSystemtAtr, BasicAgreementSetting basicAgreementSetting) {
        this.companyId = companyId;
        this.laborSystemtAtr = laborSystemtAtr;
        this.basicAgreementSetting = basicAgreementSetting;
    }
}
