package nts.uk.ctx.at.shared.infra.entity.remainingnumber.breakdayoff.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 暫定休出代休紐付け管理
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_INTERIM_HDWK_HD_COM")
public class KrcmtInterimBreakDayOff extends ContractUkJpaEntity implements Serializable{
	/**	 */
	@EmbeddedId
	public KrcmtInterimBreakDayOffPK breakDayOffKey;
	/**	休出管理データ区分 */
	@Column(name = "BREAK_MNG_ATR")
	public int breakMngAtr;
	/**	代休管理データ区分 */
	@Column(name = "DAYOFF_MNG_ATR")
	public int dayOffMngAtr;
	/**	使用時間数 */
	@Column(name = "USE_TIMES")
	public int userTimes;
	/**	使用日数 */
	@Column(name = "USE_DAYS")
	public Double userDays;
	/**	対象選択区分 */
	@Column(name = "SELECTED_ATR")
	public int selectedAtr;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return breakDayOffKey;
	}

}
