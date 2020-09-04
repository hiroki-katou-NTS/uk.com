package nts.uk.ctx.at.record.dom.agreedtime;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

/**
 * AggregateRoot: 職場３６協定時間.
 * Responsibility : 職場每の３６協定時間を管理する
 * @author chinh.hm
 */

@Getter
public class Employmenthours extends AggregateRoot {

    /** The workplaceId.
     * 職場ID
     * */
    private String  workplaceId;

    /**
     *   enum
     *  労働制
     *
     * */
    private LaborSystemtAtr workingSystem;

    /** ３６協定基本設定	**/
    // TODO SẼ QUAY LẠI SAU.
    private BasicAgreementSetting basicAgreementSetting;

    /**
     * 	[C-0] 雇用３６協定時間 (会社ID,雇用コード,労働制,時間設定)
     * @param workplaceId
     * @param workingSystem
     * @param basicAgreementSetting
     */
    public Employmenthours(String  workplaceId,LaborSystemtAtr workingSystem,BasicAgreementSetting basicAgreementSetting){
        this.workplaceId = workplaceId;
        this.workingSystem = workingSystem;
        this.basicAgreementSetting = basicAgreementSetting;

    }
}
