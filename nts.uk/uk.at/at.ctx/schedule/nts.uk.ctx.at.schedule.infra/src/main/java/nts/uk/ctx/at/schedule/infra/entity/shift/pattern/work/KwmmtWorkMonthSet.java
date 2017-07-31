/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KwmmtWorkMonthSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_WORK_MONTH_SET")
public class KwmmtWorkMonthSet extends JpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** The kwmmt work month set PK. */
    @EmbeddedId
    protected KwmmtWorkMonthSetPK kwmmtWorkMonthSetPK;
    
    /** The work type cd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WORK_TYPE_CD")
    private String workTypeCd;
    
    /** The working cd. */
    @Column(name = "WORKING_CD")
    private String workingCd;

    public KwmmtWorkMonthSet() {
    }


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kwmmtWorkMonthSetPK;
	}
    
}
