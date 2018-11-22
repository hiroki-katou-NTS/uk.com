package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
* 給与汎用パラメータ識別
*/
@Getter
public class SalGenParaIdentification extends AggregateRoot {
    
    /**
    * パラメータNo
    */
    private GenPurposeParamNo paraNo;
    
    /**
    * 会社ID
    */
    private String cID;
    
    /**
    * 名称
    */
    private GenPurposeParamName name;
    
    /**
    * 属性区分
    */
    private ParaAttributeType attributeType;
    
    /**
    * 履歴区分
    */
    private ParaHistoryAtr historyAtr;
    
    /**
    * 補足説明
    */
    private Optional<String> explanation;
    
    public SalGenParaIdentification(String paraNo, String cid, String name, int attributeType, int historyAtr, String explanation) {
        this.cID = cid;
        this.paraNo = new GenPurposeParamNo(paraNo);
        this.attributeType = EnumAdaptor.valueOf(attributeType, ParaAttributeType.class);
        this.name = new GenPurposeParamName(name) ;
        this.historyAtr = EnumAdaptor.valueOf(historyAtr, ParaHistoryAtr.class);
        this.explanation = Optional.ofNullable(explanation);
    }
    
}
