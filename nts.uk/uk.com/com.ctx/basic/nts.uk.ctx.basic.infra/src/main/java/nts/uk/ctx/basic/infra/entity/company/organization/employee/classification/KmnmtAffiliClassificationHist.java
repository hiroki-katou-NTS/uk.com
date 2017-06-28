/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.classification;

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
 * The Class KmnmtAffiliClassificationHist.
 */
@Getter
@Setter
@Entity
@Table(name = "KMNMT_AFFILI_CLASSIFICATION_HIST")
@XmlRootElement
public class KmnmtAffiliClassificationHist extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kmnmt classification hist PK. */
    @EmbeddedId
    protected KmnmtAffiliClassificationHistPK kmnmtClassificationHistPK;
    
    /** The str D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;
    
    /** The end D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endD;

    public KmnmtAffiliClassificationHist() {
    }

    public KmnmtAffiliClassificationHist(KmnmtAffiliClassificationHistPK kmnmtClassificationHistPK) {
        this.kmnmtClassificationHistPK = kmnmtClassificationHistPK;
    }

    public KmnmtAffiliClassificationHist(String histId, String sid, String clscd) {
        this.kmnmtClassificationHistPK = new KmnmtAffiliClassificationHistPK(histId, sid, clscd);
    }

    public KmnmtAffiliClassificationHistPK getKmnmtClassificationHistPK() {
        return kmnmtClassificationHistPK;
    }

    public void setKmnmtClassificationHistPK(KmnmtAffiliClassificationHistPK kmnmtClassificationHistPK) {
        this.kmnmtClassificationHistPK = kmnmtClassificationHistPK;
    }

	@Override
	protected Object getKey() {
		return this.getKmnmtClassificationHistPK();
	}
    
}
