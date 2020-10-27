/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Instantiates a new krcmt actual lock PK.
 *
 * @param cid the cid
 * @param closureId the closure id
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class KrcdtAtdActualLockPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The closure id. */
    @Column(name = "CLOSURE_ID")
    private int closureId;

}

