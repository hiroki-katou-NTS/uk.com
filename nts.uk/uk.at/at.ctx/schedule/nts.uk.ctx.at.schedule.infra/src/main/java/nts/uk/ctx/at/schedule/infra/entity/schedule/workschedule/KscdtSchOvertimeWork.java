package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.OverTimeFrameTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の残業時間
 * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author HieuLt
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
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchTime kscdtSchTime;
	
	public static KscdtSchOvertimeWork toEntity (OverTimeFrameTime overTimeFrameTime, String cid , String sid , GeneralDate ymd ){
		KscdtSchOvertimeWorkPK pk = new KscdtSchOvertimeWorkPK(sid, ymd, overTimeFrameTime.getOverWorkFrameNo().v());		
		return new  KscdtSchOvertimeWork(pk,
				cid,
				overTimeFrameTime.getOverTimeWork().getTime().v(),
				overTimeFrameTime.getTransferTime().getTime().v(),
				overTimeFrameTime.getBeforeApplicationTime().v()
				);
	}

	public KscdtSchOvertimeWork(KscdtSchOvertimeWorkPK pk, String cid, int overtimeWorkTime, int overtimeWorkTimeTrans,
			int overtimeWorkTimePreApp) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.overtimeWorkTime = overtimeWorkTime;
		this.overtimeWorkTimeTrans = overtimeWorkTimeTrans;
		this.overtimeWorkTimePreApp = overtimeWorkTimePreApp;
	}
	


}
