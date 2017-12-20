/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.classification.affiliate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmnmtAffiliClassificationHist.
 */
@Getter
@Setter
@Entity
@Table(name = "KMNMT_AFFI_CLASS_HIST")
public class KmnmtAffiliClassificationHist extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kmnmt classification hist PK. */
    @EmbeddedId
    protected KmnmtAffiliClassificationHistPK kmnmtClassificationHistPK;
    
    /** The end D. */
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endD;

    /**
     * Instantiates a new kmnmt affili classification hist.
     */
    public KmnmtAffiliClassificationHist() {
    	super();
    }

    /**
     * Instantiates a new kmnmt affili classification hist.
     *
     * @param kmnmtClassificationHistPK the kmnmt classification hist PK
     */
    public KmnmtAffiliClassificationHist(KmnmtAffiliClassificationHistPK kmnmtClassificationHistPK) {
        this.kmnmtClassificationHistPK = kmnmtClassificationHistPK;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getKmnmtClassificationHistPK();
	}
    
}
