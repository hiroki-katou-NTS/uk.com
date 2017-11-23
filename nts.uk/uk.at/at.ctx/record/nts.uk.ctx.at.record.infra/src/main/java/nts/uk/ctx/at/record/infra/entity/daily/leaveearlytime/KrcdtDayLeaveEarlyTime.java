package nts.uk.ctx.at.record.infra.entity.daily.leaveearlytime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_LEAVEEARLYTIME")
public class KrcdtDayLeaveEarlyTime  extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayLeaveEarlyTimePK krcdtDayLeaveEarlyTimePK;
	
	/*早退時間*/
	@Column(name = "LEAVEEARLY_TIME")
	public int leaveEarlyTime;
	/*計算早退時間*/
	@Column(name = "CALC_LEAVEEARLY_TIME")
	public int calcLeaveEarlyTime;
	/*早退控除時間*/
	@Column(name = "LEAVEEARLY_DEDCT_TIME")
	public int leaveEarlyDedctTime;
	/*計算早退控除時間*/
	@Column(name = "CALC_LEAVEEARLY_DEDCT_TIME")
	public int calcLeaveEarlyDedctTime;
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
		return this.krcdtDayLeaveEarlyTimePK;
	}
}
