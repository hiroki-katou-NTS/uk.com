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
	private KrcdtMonDayoffRemainPK pk;
	/**	締め処理状態 */
	@Column(name = "CLOSURE_STATUS")
	private int closureStatus;
	/**	開始年月日 */
	@Column(name = "START_DATE")
	private GeneralDate startDate;
	/**	終了年月日 */
	@Column(name = "END_DATE")
	private GeneralDate endDate;
	/**	発生日数 */
	@Column(name = "OCCURRED_DAYS")
	private int occurredDays;
	/**	発生時間 */
	@Column(name = "OCCURRED_TIMES")
	private int occurredTimes;
	/**	使用日数 */
	@Column(name = "USED_DAYS")
	private int usedDays;
	/**使用時間	 */
	@Column(name = "USED_MINUTES")
	private int usedTimes;
	/**	残日数 */
	@Column(name = "REMAINING_DAYS")
	private int remainingDays;
	/**	残時間 */
	@Column(name = "REMAINING_MINUTES")
	private int remainingTims;
	/**	繰越日数 */
	@Column(name = "CARRYFORWARD_DAYS")
	private int carryforwardDays;
	/**	繰越時間 */
	@Column(name = "CARRYFORWARD_MINUTES")
	private int carryforwardTimes;
	/**	未消化日数 */
	@Column(name = "UNUSED_DAYS")
	private int unUsedDays;
	/**	未消化時間 */
	@Column(name = "UNUSED_TIMES")
	private int unUsedTimes;
	
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
