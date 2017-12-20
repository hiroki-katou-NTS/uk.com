package nts.uk.ctx.at.record.infra.entity.daily.legalworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_PRS_INCLD_TIME")
public class KrcdtDayPrsIncldTime extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayPrsIncldTimePK krcdtDayPrsIncldTimePK;
	/*就業時間*/
	@Column(name = "WORK_TIME")
	public int workTime;
	/*実働就業時間*/
	@Column(name = "ACT_WORK_TIME")
	public int actWorkTime;
	/*所定内割増時間*/
	@Column(name = "PRS_INCLD_PRMIM_TIME")
	public int prsIncldPrmimTime;
	/*所定内深夜時間*/
	@Column(name = "PRS_INCLD_MIDN_TIME")
	public int prsIncldMidnTime;
	/*休暇加算時間*/
	@Column(name = "VACTN_ADD_TIME")
	public int vactnAddTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayPrsIncldTimePK;
	}
}
