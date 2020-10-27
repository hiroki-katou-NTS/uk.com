package nts.uk.ctx.at.shared.infra.entity.remainingnumber.breakdayoff.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 暫定休出管理データ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_INTERIM_BREAK_MNG")
public class KrcmtInterimBreakMng extends ContractUkJpaEntity implements Serializable{
	/**暫定休出管理データID	 */
	@Id
	@Column(name = "BREAK_MNG_ID")
	public String breakMngId;
	/**	１日相当時間 */
	@Column(name = "ONEDAY_EQUIVALENT_TIME")
	public int oneDayEquivalentTime;
	/**	使用期限日 */
	@Column(name = "EXPIRATION_DAYS")
	public GeneralDate expirationDate;
	/**	発生時間数 */
	@Column(name = "OCCURRENCE_TIMES")
	public int occurrenceTimes;
	/**	発生日数 */
	@Column(name = "OCCURRENCE_DAYS")
	public Double occurrenceDays;
	/**	半日相当時間 */
	@Column(name = "HAFTDAY_EQUI_TIME")
	public int haftDayEquiTime;
	/**	未使用時間数 */
	@Column(name = "UNUSED_TIMES")
	public int unUsedTimes;
	/**	未使用日数 */
	@Column(name = "UNUSED_DAYS")
	public Double unUsedDays;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return breakMngId;
	}
	

}
