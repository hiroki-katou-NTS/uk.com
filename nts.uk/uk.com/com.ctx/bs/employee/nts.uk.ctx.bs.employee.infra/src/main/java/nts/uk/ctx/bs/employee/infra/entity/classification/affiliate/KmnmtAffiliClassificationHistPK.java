/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.classification.affiliate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

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
    @Column(name = "SID")
    private String empId;
    
    /** The clscd. */
    @Column(name = "CLSCD")
    private String clscd;
    
    /** The str D. */
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;

    /**
     * Instantiates a new kmnmt affili classification hist PK.
     */
    public KmnmtAffiliClassificationHistPK() {
    	super();
    }
    
}
