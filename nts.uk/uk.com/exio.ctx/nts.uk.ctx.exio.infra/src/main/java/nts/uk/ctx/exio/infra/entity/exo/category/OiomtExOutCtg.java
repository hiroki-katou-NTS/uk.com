package nts.uk.ctx.exio.infra.entity.exo.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 外部出力カテゴリ
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_CTG")
public class OiomtExOutCtg extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtExOutCtgPk exOutCtgPk;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "OFFICE_HELPER_SYS_ATR")
    public int officeHelperSysAtr;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_NAME")
    public String categoryName;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_SET")
    public int categorySet;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "PERSON_SYS_ATR")
    public int personSysAtr;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "ATTENDANCE_SYS_ATR")
    public int attendanceSysAtr;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "PAYROLL_SYS_ATR")
    public int payrollSysAtr;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "FUNCTION_NO")
    public int functionNo;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "FUNCTION_NAME")
    public String functionName;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "EXPLANATION")
    public String explanation;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "DISPLAY_ORDER")
    public int displayOrder;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "DEFAULT_VALUE")
    public int defaultValue;
    
    @Override
    protected Object getKey()
    {
        return exOutCtgPk;
    }

    public ExOutCtg toDomain() {
        return new ExOutCtg(this.exOutCtgPk.categoryId, this.officeHelperSysAtr, this.categoryName, this.categorySet, this.personSysAtr, this.attendanceSysAtr, this.payrollSysAtr, this.functionNo, this.functionName, this.explanation, this.displayOrder, this.defaultValue);
    }
    public static OiomtExOutCtg toEntity(ExOutCtg domain) {
        return new OiomtExOutCtg(new OiomtExOutCtgPk(domain.getCategoryId()), domain.getOfficeHelperSysAtr(), domain.getCategoryName(), domain.getCategorySet(), domain.getPersonSysAtr(), domain.getAttendanceSysAtr(), domain.getPayrollSysAtr(), domain.getFunctionNo(), domain.getFunctionName(), domain.getExplanation(), domain.getDisplayOrder(), domain.getDefaultValue());
    }

}
