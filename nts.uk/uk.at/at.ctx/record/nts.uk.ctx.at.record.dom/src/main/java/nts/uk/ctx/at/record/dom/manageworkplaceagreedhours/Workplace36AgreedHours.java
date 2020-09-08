package nts.uk.ctx.at.record.dom.manageworkplaceagreedhours;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

/**
 * AggregateRoot	 職場３６協定時間
 * 	Responsibility	`職場每の３６協定時間を管理する
 */
@Getter
@NoArgsConstructor
public class Workplace36AgreedHours extends AggregateRoot {
    /** The workplaceId. */
    // 職場ID
    private String  workplaceId;
    /** 労働制 */

    private LaborSystemtAtr laborSystemtAtr;

    /** ３６協定基本設定	**/
    // TODO SẼ QUAY LẠI SAU.
    private BasicAgreementSetting basicAgreementSetting;

    /**
     * 	[C-0] 職場３６協定時間 (職場ID,労働制,時間設定)
     * @param workplaceId
     * @param laborSystemtAtr
     *
     */
    // TODO 時間設定 LÀ ?????
    public Workplace36AgreedHours(String  workplaceId, LaborSystemtAtr laborSystemtAtr, BasicAgreementSetting basicAgreementSetting){
            this.workplaceId = workplaceId;
            this.laborSystemtAtr = laborSystemtAtr;
            this.basicAgreementSetting = basicAgreementSetting;
    }
}
