package nts.uk.ctx.exio.dom.exo.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 外部出力カテゴリ
*/
@AllArgsConstructor
@Getter
public class ExOutCtg extends AggregateRoot
{
    
    /**
    * 
    */
    private String categoryId;
    
    /**
    * 
    */
    private int officeHelperSysAtr;
    
    /**
    * 
    */
    private String categoryName;
    
    /**
    * 
    */
    private int categorySet;
    
    /**
    * 
    */
    private int personSysAtr;
    
    /**
    * 
    */
    private int attendanceSysAtr;
    
    /**
    * 
    */
    private int payrollSysAtr;
    
    /**
    * 
    */
    private int functionNo;
    
    /**
    * 
    */
    private String functionName;
    
    /**
    * 
    */
    private String explanation;
    
    /**
    * 
    */
    private int displayOrder;
    
    /**
    * 
    */
    private int defaultValue;
    
    
}
