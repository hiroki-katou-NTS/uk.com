package nts.uk.ctx.exio.app.command.exo.category;

import lombok.Value;

@Value
public class ExOutCtgCommand
{
    
    /**
    * 
    */
    private int categoryId;
    
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
    private boolean defaultValue;
    
    private Long version;

}
