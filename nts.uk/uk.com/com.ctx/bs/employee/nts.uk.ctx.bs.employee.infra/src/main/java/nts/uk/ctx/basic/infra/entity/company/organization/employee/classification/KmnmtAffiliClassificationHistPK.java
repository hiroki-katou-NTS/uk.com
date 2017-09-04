/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.classification;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtAffiliClassificationHistPK.
 */
@Getter
@Setter
@Embeddable
public class KmnmtAffiliClassificationHistPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	    
    /** The empId. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String empId;
    
    /** The clscd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLSCD")
    private String clscd;
    
    /** The str D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;

    /**
     * Instantiates a new kmnmt affili classification hist PK.
     */
    public KmnmtAffiliClassificationHistPK() {
    }
    
}
