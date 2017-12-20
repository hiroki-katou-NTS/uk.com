package nts.uk.ctx.at.record.infra.entity.daily.latetime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_LATETIME")
public class KrcdtDayLateTime extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayLateTimePK krcdtDayLateTimePK;
	
	/*遅刻時間*/
	@Column(name = "LATE_TIME")
	public int lateTime;
	/*計算遅刻時間*/
	@Column(name = "CALC_LATE_TIME")
	public int calcLateTime;
	/*遅刻控除時間*/
	@Column(name = "LATE_DEDCT_TIME")
	public int lateDedctTime;
	/*計算遅刻控除時間*/
	@Column(name = "CALC_LATE_DEDCT_TIME")
	public int calcLateDedctTime;
	/*時間年休使用時間*/
	@Column(name = "TIME_ANALLV_USE_TIME")
	public int timeAnallvUseTime;
	/*時間代休使用時間*/
	@Column(name = "TIME_CMPNSTLV_USE_TIME")
	public int timeCmpnstlvUseTime;
	/*超過有休使用時間*/
	@Column(name = "OVER_PAY_VACTN_USE_TIME")
	public int overPayVactnUseTime;
	/*特別休暇使用時間*/
	@Column(name = "SP_VACTN_USE_TIME")
	public int spVactnUseTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayLateTimePK;
	}
	
}
