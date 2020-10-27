package nts.uk.ctx.pereg.infra.entity.person.info.ctg;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_CTG_COMMON")
public class PpemtCtgCommon extends UkJpaEntity implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    public PpemtCtgCommonPK ppemtCtgCommonPK;
    
    @Basic(optional = false)
    @Column(name = "CATEGORY_PARENT_CD")
    public String categoryParentCd;
    
    @Basic(optional = false)
    @Column(name = "CATEGORY_TYPE")
    public int categoryType;
    
    @Basic(optional = false)
    @Column(name = "PERSON_EMPLOYEE_TYPE")
    public int personEmployeeType;
    
    @Basic(optional = false)
    @Column(name = "FIXED_ATR")
    public int fixedAtr;
    
    @Basic(optional = false)
    @Column(name = "ADD_ITEM_OBJ_ATR")
    public int addItemObjCls;
    
    @Basic(optional = false)
    @Column(name = "INIT_VAL_MASTER_OBJ_ATR")
    public int initValMasterObjCls;
    
   
    @Basic(optional = false)
    @Column(name = "SALARY_USE_ATR")
    public int salaryUseAtr;
    
    @Basic(optional = false)
    @Column(name = "PERSONNEL_USE_ATR")
    public int personnelUseAtr;
    
    @Basic(optional = false)
    @Column(name = "EMPLOYMENT_USE_ATR")
    public int employmentUseAtr;
    
	@Basic(optional = false)
	@Column(name = "CAN_ABOLITION")
	public int canAbolition;

    
	@Override
	protected Object getKey() {
		return ppemtCtgCommonPK;
	}
}
