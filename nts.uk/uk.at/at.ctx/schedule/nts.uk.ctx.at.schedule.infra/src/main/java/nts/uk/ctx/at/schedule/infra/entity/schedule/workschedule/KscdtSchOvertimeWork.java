package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の残業時間
 * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author kingo
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_OVERTIME_WORK")
public class KscdtSchOvertimeWork extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KscdtSchOvertimeWorkPK pk;
	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	/** 残業時間 **/
	@Column(name = "OVERTIME_WORK_TIME")
	public int overtimeWorkTime;
	/** 振替時間 **/
	@Column(name = "OVERTIME_WORK_TIME_TRANS")
	public int overtimeWorkTimeTrans;
	/** 事前申請時間 **/
	@Column(name = "OVERTIME_WORK_TIME_PREAPP")
	public int overtimeWorkTimePreApp;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
