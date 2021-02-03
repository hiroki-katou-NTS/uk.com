package nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author quytb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_RPT_SCHEDULE_ITEM")
public class KagmtRptScheduleItem extends ContractUkJpaEntity implements Serializable{

	private static final long serialVersionUID = -2457141042108913902L;
	
	@EmbeddedId
	public KagmtRptScheduleItemPk pk;

	@Column(name = "PERSONAL_ITEM")
	public Integer personalItem;
	
	@Column(name = "ADDITIONAL_PERSONAL_ITEM")
	public Integer additionalPersonalItem;
	
	@Column(name = "ATTENDANCE_ITEM")
	public Integer attendanceItem;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
