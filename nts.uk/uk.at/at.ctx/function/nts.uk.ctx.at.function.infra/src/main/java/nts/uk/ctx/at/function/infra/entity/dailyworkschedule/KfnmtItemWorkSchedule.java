/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The Class KfnmtItemWorkSchedule.
 */
@Getter
@Setter
@Entity
@Table(name="KFNMT_ITEM_WORK_SCHEDULE")
@NoArgsConstructor
public class KfnmtItemWorkSchedule extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KfnmtItemWorkSchedulePK id;

	/** The item name. */
	@Column(name="ITEM_NAME")
	private String itemName;

	/** The work type name display. */
	@Column(name="WORKTYPE_NAME_DISPLAY")
	private BigDecimal workTypeNameDisplay;
	
	/** The remark input no. */
	@Column(name="REMARK_INPUT_NO")
	private BigDecimal remarkInputNo; 

	/** The lst kfnmt attendance display. */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumns({
			@JoinColumn(name="CID", referencedColumnName="CID"),
			@JoinColumn(name="ITEM_CD", referencedColumnName="ITEM_CD")
	})
	private List<KfnmtAttendanceDisplay> lstKfnmtAttendanceDisplay;
	
	/** The lst kfnmt print remark cont. */
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumns({
			@JoinColumn(name="CID", referencedColumnName="CID"),
			@JoinColumn(name="ITEM_CD", referencedColumnName="ITEM_CD")
	})
	private List<KfnmtPrintRemarkCont> lstKfnmtPrintRemarkCont;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}
}