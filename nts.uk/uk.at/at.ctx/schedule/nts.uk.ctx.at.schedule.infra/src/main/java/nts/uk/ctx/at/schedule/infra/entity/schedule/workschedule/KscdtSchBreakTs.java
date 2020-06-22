package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の休憩時間帯
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_BREAK_TS")
public class KscdtSchBreakTs extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KscdtSchBreakTsPK pk;
	
	/** 会社ID **/								
	@Column(name = "CID")
	public String cid;
	
	/** 休憩開始時刻 **/
	@Column(name = "BREAK_TS_START")
	public int breakTsStart;
	
	/** 休憩終了時刻 **/
	@Column(name = "BREAK_TS_END")
	public int breakTsEnd;
	
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
