package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.ParaAttributeType;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.ParaHistoryAtr;

/**
* 給与汎用パラメータ識別
*/
@Getter
public class SalGenParaIdentification extends AggregateRoot {
    
    /**
    * パラメータNo
    */
    private String paraNo;
    
    /**
    * 会社ID
    */
    private String cID;
    
    /**
    * 名称
    */
    private String name;
    
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
        this.paraNo =paraNo ;
        this.attributeType = EnumAdaptor.valueOf(attributeType, ParaAttributeType.class);
        this.name =name ;
        this.historyAtr = EnumAdaptor.valueOf(historyAtr, ParaHistoryAtr.class);
        this.explanation = Optional.ofNullable(explanation);
    }
    
}
