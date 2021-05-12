package nts.uk.ctx.at.shared.infra.entity.remainingnumber.breakdayoff.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "KSHDT_INTERIM_HDWK")
public class KrcdtInterimHdwkMng extends ContractUkJpaEntity implements Serializable{
	
	@EmbeddedId
	public KrcdtInterimHdwkMngPk pk;
	
	/**残数管理データID	 */
	@Column(name = "REMAIN_MNG_ID")
	public String remainMngId;
	
	/** 作成元区分	 */
	@Column(name ="CREATOR_ATR")
	public int createAtr;
	
	/**	使用期限日 */
	@Column(name = "EXPIRATION_DAYS")
	public GeneralDate expirationDate;
	
	/**	発生日数 */
	@Column(name = "OCCURRENCE_DAYS")
	public Double occurrenceDays;
	
	/**	発生時間数 */
	@Column(name = "OCCURRENCE_TIMES")
	public int occurrenceTimes;
	
	/**	未使用日数 */
	@Column(name = "UNUSED_DAYS")
	public Double unUsedDays;
	
	/**	未使用時間数 */
	@Column(name = "UNUSED_TIMES")
	public int unUsedTimes;
	
	/**	１日相当時間 */
	@Column(name = "ONEDAY_EQUIVALENT_TIME")
	public int oneDayEquivalentTime;
	
	/**	半日相当時間 */
	@Column(name = "HAFTDAY_EQUI_TIME")
	public int haftDayEquiTime;
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected Object getKey() {
		return pk;
	}
	

}
