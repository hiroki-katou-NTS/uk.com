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
 * The Class KmnmtEmpWorkplace.
 */
@Getter
@Setter
@Entity
@Table(name = "KMNMT_EMP_WORKPLACE")
@XmlRootElement
public class KmnmtEmpWorkplace extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kmnmt emp workplace PK. */
    @EmbeddedId
    protected KmnmtEmpWorkplacePK kmnmtEmpWorkplacePK;

    /** The str D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate strD;
    
    /** The end D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endD;

    public KmnmtEmpWorkplace() {
    }

    public KmnmtEmpWorkplace(KmnmtEmpWorkplacePK kmnmtEmpWorkplacePK) {
        this.kmnmtEmpWorkplacePK = kmnmtEmpWorkplacePK;
    }


	@Override
	protected Object getKey() {
		return this.kmnmtEmpWorkplacePK;
	}
    
}
