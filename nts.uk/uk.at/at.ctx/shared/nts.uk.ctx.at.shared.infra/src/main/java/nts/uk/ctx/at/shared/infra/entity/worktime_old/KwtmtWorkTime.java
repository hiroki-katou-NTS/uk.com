package nts.uk.ctx.at.shared.infra.entity.worktime_old;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KWTMT_WORK_TIME")
public class KwtmtWorkTime extends UkJpaEntity{
	
	@EmbeddedId
	public KwtmpWorkTimePK kwtmpWorkTimePK;
	
	@Column(name="NOTE")
	public String note;
	
	@Column(name="DAILY_WORK_ATR")
	public int workTimeDailyAtr;
	
	@Column(name="METHOD_ATR")
	public int workTimeMethodSet;
	
	@Column(name="DISP_ATR")
	public int displayAtr;

	@Column(name="NAME")
	public String workTimeName;

	@Column(name="AB_NAME")
	public String workTimeAbName;

	@Column(name="SYMBOL")
	public String workTimeSymbol;
	
	@Override
	protected Object getKey() {
		return kwtmpWorkTimePK;
	}
}
