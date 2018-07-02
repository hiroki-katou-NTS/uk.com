package nts.uk.ctx.exio.app.find.exo.category;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;

/**
* 外部出力カテゴリ
*/
@AllArgsConstructor
@Value
public class ExOutCtgDto
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
    
    
    public static ExOutCtgDto fromDomain(ExOutCtg domain)
    {
        return new ExOutCtgDto(domain.getCategoryId(), domain.getOfficeHelperSysAtr(), domain.getCategoryName(), domain.getCategorySet(), domain.getPersonSysAtr(), domain.getAttendanceSysAtr(), domain.getPayrollSysAtr(), domain.getFunctionNo(), domain.getFunctionName(), domain.getExplanation(), domain.getDisplayOrder(), domain.getDefaultValue());
    }
    
}
