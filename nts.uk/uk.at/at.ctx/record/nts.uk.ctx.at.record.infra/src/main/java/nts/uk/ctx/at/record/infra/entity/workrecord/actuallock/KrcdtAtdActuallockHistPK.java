/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;


/**
 * The Class KrcdtAtdActuallockHistPK.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class KrcdtAtdActuallockHistPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
    @Column(name = "CID")
    private String cid;
	
    /** The closure id. */
    @Column(name = "CLOSURE_ID")
    private int closureId;
    
    /** The lock date. */
    @Column(name = "LOCK_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private GeneralDateTime lockDate;
    
}