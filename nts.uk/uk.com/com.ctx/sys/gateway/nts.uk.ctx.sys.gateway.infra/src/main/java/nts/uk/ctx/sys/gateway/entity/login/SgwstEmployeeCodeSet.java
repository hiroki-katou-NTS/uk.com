/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.entity.login;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Getter
@Setter
@Entity
@Table(name = "SGWST_EMPLOYEE_CODE_SET")
@NoArgsConstructor
public class SgwstEmployeeCodeSet extends UkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMBER_DIGIT")
    private long numberDigit;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "EDIT_TYPE")
    private short editType;

    public SgwstEmployeeCodeSet(String cid) {
        this.cid = cid;
    }

    public SgwstEmployeeCodeSet(String cid, int exclusVer, long numberDigit, short editType) {
        this.cid = cid;
        this.numberDigit = numberDigit;
        this.editType = editType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgwstEmployeeCodeSet)) {
            return false;
        }
        SgwstEmployeeCodeSet other = (SgwstEmployeeCodeSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

	@Override
	protected Object getKey() {
		return this.cid;
	}
    
}
