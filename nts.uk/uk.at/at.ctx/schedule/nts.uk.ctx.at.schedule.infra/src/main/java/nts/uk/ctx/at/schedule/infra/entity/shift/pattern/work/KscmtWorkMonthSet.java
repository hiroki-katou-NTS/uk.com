/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscmtWorkMonthSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_MONTHLY_PATTERN_DTL")
public class KscmtWorkMonthSet extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** The kwmmt work month set PK. */
    @EmbeddedId
    protected KscmtWorkMonthSetPK kscmtWorkMonthSetPK;
    
    /** The work type cd. */
    @Column(name = "WKTP_CD")
    private String workTypeCd;
    
    /** The working cd. */
    @Column(name = "WKTM_CD")
    private String workingCd;
    /**
     * Instantiates a new kscmt work month set.
     */
    public KscmtWorkMonthSet() {
    	super();
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtWorkMonthSetPK;
	}
    
}
