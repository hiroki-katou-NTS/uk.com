package nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "KAGMT_RPT_SCHEDULE")
public class KagmtRptSchedule extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1851791487062083957L;
	
	@EmbeddedId
	public KagmtRptSchedulePk pk;
	
	@Column(name ="NAME")
	public String name;
	
	@Column(name = "ADDITIONAL_COLUM_USE_ATR")
	public Integer additionalColumUseAtr;
	
	@Column(name = "SHIFT_BACKCOLOR_USE_ATR")
	public Integer shiftBackColorUseAtr;
	
	@Column(name = "RECORD_DISP_ATR")
	public Integer recordDispAtr;

	@Override
	protected Object getKey() {	
		return this.pk;
	}
}
