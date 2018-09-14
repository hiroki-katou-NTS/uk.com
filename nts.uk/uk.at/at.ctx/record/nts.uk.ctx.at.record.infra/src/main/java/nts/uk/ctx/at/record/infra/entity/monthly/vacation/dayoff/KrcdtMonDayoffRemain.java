package nts.uk.ctx.at.record.infra.entity.monthly.vacation.dayoff;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * テーブル：　代休月別残数データ
 * @author do_dt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KRCDT_MON_DAYOFF_REMAIN")
public class KrcdtMonDayoffRemain extends UkJpaEntity implements Serializable{

	/**	 */
	@EmbeddedId
	public KrcdtMonDayoffRemainPK pk;
	/**	締め処理状態 */
	@Column(name = "CLOSURE_STATUS")
	public int closureStatus;
	/**	開始年月日 */
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	/**	終了年月日 */
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	/**	発生日数 */
	@Column(name = "OCCURRED_DAYS")
	public Double occurredDays;
	/**	発生時間 */
	@Column(name = "OCCURRED_TIMES")
	public Integer occurredTimes;
	/**	使用日数 */
	@Column(name = "USED_DAYS")
	public Double usedDays;
	/**使用時間	 */
	@Column(name = "USED_MINUTES")
	public Integer usedTimes;
	/**	残日数 */
	@Column(name = "REMAINING_DAYS")
	public Double remainingDays;
	/**	残時間 */
	@Column(name = "REMAINING_MINUTES")
	public Integer remainingTimes;
	/**	繰越日数 */
	@Column(name = "CARRYFORWARD_DAYS")
	public Double carryforwardDays;
	/**	繰越時間 */
	@Column(name = "CARRYFORWARD_MINUTES")
	public Integer carryforwardTimes;
	/**	未消化日数 */
	@Column(name = "UNUSED_DAYS")
	public Double unUsedDays;
	/**	未消化時間 */
	@Column(name = "UNUSED_TIMES")
	public Integer unUsedTimes;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}

}
