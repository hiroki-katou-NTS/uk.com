/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The Class KrcmtActualLockHistPK.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class KrcmtActualLockHistPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
	
    /** The closure id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLOSURE_ID")
    private short closureId;
    
    /** The lock date. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOCK_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockDate;
    
}