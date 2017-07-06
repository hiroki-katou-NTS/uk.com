/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.employment;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmnmtAffiliEmploymentHistPK.
 */
@Getter
@Setter
@Embeddable
public class KmnmtAffiliEmploymentHistPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The hist id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIST_ID")
    private String histId;
    
    /** The empId. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String empId;
    
    
    /** The emptcd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPCD")
    private String emptcd;

    public KmnmtAffiliEmploymentHistPK() {
    }

    public KmnmtAffiliEmploymentHistPK(String histId, String empId, String emptcd) {
        this.histId = histId;
        this.empId = empId;
        this.emptcd = emptcd;
    }
}
