package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の休憩時間帯
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_BREAK_TS")
@Getter
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchBasicInfo kscdtSchBasicInfo;
	
	public static KscdtSchBreakTs toEntity(BreakTimeSheet timeSheet, String sID, GeneralDate yMD, String cID) {
		KscdtSchBreakTsPK pk = new KscdtSchBreakTsPK(sID, yMD, timeSheet.getBreakFrameNo().v());
		KscdtSchBreakTs kscdtSchBreakTs = new KscdtSchBreakTs(pk, cID, timeSheet.getStartTime().v(), timeSheet.getEndTime().v());
		return kscdtSchBreakTs;
	}
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KscdtSchBreakTs(KscdtSchBreakTsPK pk, String cid, int breakTsStart, int breakTsEnd) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.breakTsStart = breakTsStart;
		this.breakTsEnd = breakTsEnd;
	}

}
