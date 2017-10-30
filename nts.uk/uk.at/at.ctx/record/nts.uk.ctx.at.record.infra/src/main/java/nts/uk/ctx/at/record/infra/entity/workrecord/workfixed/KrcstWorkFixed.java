/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.workfixed;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_WORK_FIXED")
public class KrcstWorkFixed extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrcstWorkFixedPK krcstWorkFixedPK;
    
    @Size(max = 36)
    @Column(name = "CONFIRM_PID")
    private String confirmPid;
    
    @Column(name = "CONFIRM_CLS")
    private short confirmCls;
    
    @Column(name = "FIXED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fixedDate;
    
    @Column(name = "PROCESS_YM")
    private int processYm;

    public KrcstWorkFixed() {
    	super();
    }

    public KrcstWorkFixed(KrcstWorkFixedPK krcstWorkFixedPK) {
        this.krcstWorkFixedPK = krcstWorkFixedPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krcstWorkFixedPK != null ? krcstWorkFixedPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcstWorkFixed)) {
            return false;
        }
        KrcstWorkFixed other = (KrcstWorkFixed) object;
        if ((this.krcstWorkFixedPK == null && other.krcstWorkFixedPK != null) || (this.krcstWorkFixedPK != null && !this.krcstWorkFixedPK.equals(other.krcstWorkFixedPK))) {
            return false;
        }
        return true;
    }

	@Override
	protected Object getKey() {	
		return this.krcstWorkFixedPK;
	}
    
}
