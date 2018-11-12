package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;


/**
* 給与汎用パラメータ選択肢
*/
@Getter
public class SalGenParamOptions extends AggregateRoot {
    
    /**
    * パラメータNo
    */
    private String paraNo;
    
    /**
    * 会社ID
    */
    private String cID;
    
    /**
    * 選択肢No
    */
    private int optionNo;
    
    /**
    * 選択肢名称
    */
    private String optionName;
    
    public SalGenParamOptions(String paraNo, String cid, int optionNo, String optionName) {
        this.cID = cid;
        this.paraNo = paraNo;
        this.optionNo = optionNo;
        this.optionName = optionName;
    }
    
}
