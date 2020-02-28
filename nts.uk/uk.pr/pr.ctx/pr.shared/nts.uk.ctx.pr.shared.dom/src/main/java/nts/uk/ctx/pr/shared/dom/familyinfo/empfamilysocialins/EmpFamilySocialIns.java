package nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
* 社員家族社会保険情報
*/
@Getter
public class EmpFamilySocialIns extends AggregateRoot {
    
    /**
    * 介護保険適用区分
    */
    private Distinction insCareDivision;
    
    /**
    * 履歴ID
    */
    private String historyId;
	
	/**
    * 社会保険適用区分
    */
	private Distinction socialInsuranceCls;

    /**
     * 家族基礎年金番号
     */
	private String fmBsPenNum;

    public EmpFamilySocialIns(int insCareDivision, String historyId, int socialInsuranceCls, String fmBsPenNum) {
        this.insCareDivision = EnumAdaptor.valueOf(insCareDivision, Distinction.class);
        this.historyId = historyId;
        this.socialInsuranceCls = EnumAdaptor.valueOf(socialInsuranceCls, Distinction.class);
        this.fmBsPenNum = fmBsPenNum;
    }
}
