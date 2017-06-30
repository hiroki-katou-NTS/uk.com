/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.classification;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmnmtAffiliClassificationHistPK.
 */
@Getter
@Setter
@Embeddable
public class KmnmtAffiliClassificationHistPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The hist id. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "HIST_ID")
    private String histId;
    
    /** The sid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_ID")
    private String empId;
    
    /** The clscd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLSCD")
    private String clscd;

    /**
     * Instantiates a new kmnmt affili classification hist PK.
     */
    public KmnmtAffiliClassificationHistPK() {
    }

    /**
     * Instantiates a new kmnmt affili classification hist PK.
     *
     * @param histId the hist id
     * @param sid the sid
     * @param clscd the clscd
     */
    public KmnmtAffiliClassificationHistPK(String histId, String sid, String clscd) {
        this.histId = histId;
        this.empId = sid;
        this.clscd = clscd;
    }
    
}
