/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmnmtAffiliWorkplaceHist.
 */
@Getter
@Setter
@Entity
@Table(name = "KMNMT_AFFI_WORKPLACE_HIST")
@XmlRootElement
public class KmnmtAffiliWorkplaceHist extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kmnmt workplace hist PK. */
    @EmbeddedId
    protected KmnmtAffiliWorkplaceHistPK kmnmtAffiliWorkplaceHistPK;
  
    
    /** The end D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endD;

    /**
     * Instantiates a new kmnmt emp workplace hist.
     */
    public KmnmtAffiliWorkplaceHist() {
    }



	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getKmnmtAffiliWorkplaceHistPK();
	}
}
