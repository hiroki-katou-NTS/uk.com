package nts.uk.ctx.at.record.dom.manageemploymenthours;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

/**
 * AggregateRoot: 		雇用３６協定時間
 * Responsibility : 	全社の３６協定時間を管理する
 * @author chinh.hm
 */

@Getter
@NoArgsConstructor
public class Employmenthours extends AggregateRoot {

    /** The companyId.
     * 	会社ID
     * */
    private String  companyId;

    /** Employemeny code
     * 雇用コード
     * */
    private String employmentCD;

    /** 労働制 */

    private LaborSystemtAtr laborSystemtAtr;
    /** ３６協定基本設定	**/
    // TODO SẼ QUAY LẠI SAU.
    private BasicAgreementSetting basicAgreementSetting;

    /**
     * 	[C-0] 雇用３６協定時間 (会社ID,雇用コード,労働制,時間設定)
     * @param companyId
     * @param employmentCD
     * @param basicAgreementSetting
     */
    public Employmenthours(String  companyId,String employmentCD,LaborSystemtAtr laborSystemtAtr,BasicAgreementSetting basicAgreementSetting ){
        this.companyId = companyId;
        this.employmentCD = employmentCD;
        this.laborSystemtAtr = laborSystemtAtr;
        this.basicAgreementSetting = basicAgreementSetting;


    }

}
