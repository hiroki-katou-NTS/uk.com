/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KscmtWorkMonthSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_WORK_MONTH_SET")
public class KscmtWorkMonthSet extends JpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** The kwmmt work month set PK. */
    @EmbeddedId
    protected KscmtWorkMonthSetPK kscmtWorkMonthSetPK;
    
    /** The work type cd. */
    @Column(name = "WORK_TYPE_CD")
    private String workTypeCd;
    
    /** The working cd. */
    @Column(name = "WORKING_CD")
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
