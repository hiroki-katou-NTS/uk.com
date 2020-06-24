package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の出退勤時刻
 * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_ATD_LVW_TIME")
public class KscdtSchAtdLvwTime extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KscdtSchAtdLvwTimePK pk;

	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	
	/** 出勤時刻 **/
	@Column(name = "ATD_CLOCK")
	public int atdClock;
	
	/** 退勤時刻**/
	@Column(name = "LWK_CLOCK")
	public int lwkClock;
	@Override
	protected Object getKey() {

		return this.pk;
	}
}
