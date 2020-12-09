package nts.uk.ctx.at.shared.infra.entity.remainingnumber.breakdayoff.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 暫定代休管理データ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_INTERIM_HD_COM_MNG")
public class KrcmtInterimDayOffMng extends ContractUkJpaEntity implements Serializable{
	/**	暫定代休管理データID */
	@Id
	@Column(name = "DAYOFF_MNG_ID")	
	public String dayOffMngId;
	/**	必要時間数 */
	@Column(name = "REQUIRED_TIMES")	
	public int requiredTimes;
	/**	必要日数 */
	@Column(name = "REQUEIRED_DAYS")
	public Double requiredDays;
	/**	未相殺時間数 */
	@Column(name = "UNOFFSET_TIMES")
	public int unOffSetTimes;
	/**	未相殺日数 */
	@Column(name = "UNOFFSET_DAYS")
	public Double unOffsetDays;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return dayOffMngId;
	}

}
