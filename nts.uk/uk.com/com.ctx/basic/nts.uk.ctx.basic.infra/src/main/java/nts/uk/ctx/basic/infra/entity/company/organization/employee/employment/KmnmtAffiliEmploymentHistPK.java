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
    
    /** The sid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_ID")
    private String empId;
    
    /** The emptcd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPT_CD")
    private String emptcd;

    public KmnmtAffiliEmploymentHistPK() {
    }

}
