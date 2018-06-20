package nts.uk.ctx.at.record.infra.entity.monthly.vacation.absenceleave;

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
 * 振休月別残数データ
 * @author do_dt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KRCDT_MON_SUBOFHD_REMAIN")
public class KrcdtMonSubOfHdRemain extends UkJpaEntity implements Serializable{
	@EmbeddedId
	public KrcdtMonSubOfHdRemainPK pk;
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
	/**	使用日数 */
	@Column(name = "USED_DAYS")
	public Double usedDays;
	/**	残日数 */
	@Column(name = "REMAINING_DAYS")
	public Double remainingDays;
	/**	繰越日数 */
	@Column(name = "CARRYFORWARD_DAYS")
	public Double carryForWardDays;
	/**	未消化日数 */
	@Column(name = "UNUSED_DAYS")
	public Double unUsedDays;
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
