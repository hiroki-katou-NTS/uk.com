/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.infra.repository.dailyworkschedule.JpaOutputItemDailyWorkScheduleGetMemento;
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

	/** The zone name. */
	@Column(name="ZONE_NAME")
	private BigDecimal zoneName;

	/** The lst kfnmt attendance display. */
	@OneToMany
	@JoinColumns({
			@JoinColumn(name="CID", referencedColumnName="CID"),
			@JoinColumn(name="ITEM_CODE", referencedColumnName="ITEM_CODE")
	})
	private List<KfnmtAttendanceDisplay> lstKfnmtAttendanceDisplay;
	
	/** The lst kfnmt print remark cont. */
	@OneToMany
	@JoinColumns({
			@JoinColumn(name="CID", referencedColumnName="CID"),
			@JoinColumn(name="ITEM_CODE", referencedColumnName="ITEM_CODE")
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