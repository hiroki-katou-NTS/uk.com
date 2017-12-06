/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate;

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
 * The Class KmnmtAffiliJobTitleHist.
 */
@Getter
@Setter
@Entity
@Table(name = "KMNMT_AFFI_JOB_TITLE_HIST")
public class KmnmtAffiliJobTitleHist extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kmnmt job title hist PK. */
    @EmbeddedId
    protected KmnmtAffiliJobTitleHistPK kmnmtJobTitleHistPK;
    
    /** The end D. */
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endD;

    /**
     * Instantiates a new kmnmt affili job title hist.
     */
    public KmnmtAffiliJobTitleHist() {
    	super();
    }

    /**
     * Instantiates a new kmnmt affili job title hist.
     *
     * @param kmnmtJobTitleHistPK the kmnmt job title hist PK
     */
    public KmnmtAffiliJobTitleHist(KmnmtAffiliJobTitleHistPK kmnmtJobTitleHistPK) {
        this.kmnmtJobTitleHistPK = kmnmtJobTitleHistPK;
    }
    
	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kmnmtJobTitleHistPK;
	}
    
}
