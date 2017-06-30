/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmnmtAffiliWorkplaceHistPK.
 */
@Getter
@Setter
@Embeddable
public class KmnmtAffiliWorkplaceHistPK implements Serializable {
	
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
    @Column(name = "EMP_ID")
    private String empId;
    
    /** The wpl id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WPL_ID")
    private String wplId;

    /**
     * Instantiates a new kmnmt affili workplace hist PK.
     */
    public KmnmtAffiliWorkplaceHistPK() {
    }

    /**
     * Instantiates a new kmnmt affili workplace hist PK.
     *
     * @param histId the hist id
     * @param empId the empId
     * @param wplId the wpl id
     */
    public KmnmtAffiliWorkplaceHistPK(String histId, String empId, String wplId) {
        this.histId = histId;
        this.empId = empId;
        this.wplId = wplId;
    }    
}
