package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の加給時間
 * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_BONUSPAY")
public class KscdtSchBonusPay extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KscdtSchBonusPayPK pK;
	
	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	
	/** 加給時間 **/
	@Column(name = "PREMIUM_TIME")
	public int premiumTime;
	
	/** 所定内加給時間 **/
	@Column(name = "PREMIUM_TIME_WITHIN")
	public int premiumTimeWithIn;
	
	/** 所定外加給時間 **/
	@Column(name = "PREMIUM_TIME_WITHOUT")
	public int premiumTimeWithOut;
	
	@Override
	protected Object getKey() {
		return this.pK;
	}

}
