package nts.uk.ctx.at.record.dom.manageclassificationagreementtime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

/**
 * AggregateRoot :分類３６協定時間
 */
@Getter
@NoArgsConstructor
public class Classification36AgreementTime extends AggregateRoot {
    /**
     * The companyId.
     * 会社ID
     */
    private String companyId;

    /**
     * classificationCode
     * 分類コード
     */
    private ClassificationCode classificationCode;

    /**
     * 労働制
     */

    private LaborSystemtAtr laborSystemtAtr;
    /**
     * ３６協定基本設定
     **/
    // TODO SẼ QUAY LẠI SAU.
    private BasicAgreementSetting basicAgreementSetting;

    public Classification36AgreementTime(String companyId, ClassificationCode classificationCode, LaborSystemtAtr laborSystemtAtr, BasicAgreementSetting basicAgreementSetting) {
        this.companyId = companyId;
        this.classificationCode = classificationCode;
        this.laborSystemtAtr = laborSystemtAtr;
        this.basicAgreementSetting = basicAgreementSetting;
    }
}