package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import java.util.Optional;

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
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutk
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_GOING_OUT_TS")
@Getter
public class KscdtSchGoingOutTs extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KscdtSchGoingOutTsPK pk;

	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	
	/** 外出理由**/
	@Column(name = "REASON_ATR")
	public int reasonAtr;
	
	/** 外出．時刻 :時刻 */
	@Column(name = "GOING_OUT_CLOCK")
	public int goingOutClock;
	
	/** 戻り．時刻 :時刻 */
	@Column(name = "GOING_BACK_CLOCK")
	public int goingBackClock;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchBasicInfo kscdtSchBasicInfo;


	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KscdtSchGoingOutTs toEntity(String sid, GeneralDate ymd,String cid,OutingTimeSheet outingTimeSheet) {
		KscdtSchGoingOutTs entity =  new KscdtSchGoingOutTs(
				new KscdtSchGoingOutTsPK(sid, ymd, outingTimeSheet.getOutingFrameNo().v()), 
				cid, 
				outingTimeSheet.getReasonForGoOut().value,
				outingTimeSheet.getGoOut().get().getTimeDay().getTimeWithDay().get().v(),
				outingTimeSheet.getComeBack().get().getTimeDay().getTimeWithDay().get().v());
		return entity;
	}
	
	public OutingTimeSheet toDomain() {
		WorkStamp workStampGoOut = new WorkStamp(
				new WorkTimeInformation(
						new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, Optional.empty()), 
						new TimeWithDayAttr(this.goingOutClock)), 
				Optional.empty());
		
		WorkStamp workStampComeBack = new WorkStamp(
				new WorkTimeInformation(
						new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, Optional.empty()),
						new TimeWithDayAttr(this.goingBackClock)),
				Optional.empty());
		
		OutingTimeSheet domain = new OutingTimeSheet(
				new OutingFrameNo(this.pk.frameNo), 
				Optional.of(workStampGoOut), 
				GoingOutReason.valueOf(this.reasonAtr), 
				Optional.of(workStampComeBack));
			return domain;
	}



	public KscdtSchGoingOutTs(KscdtSchGoingOutTsPK pk, String cid, int reasonAtr, int goingOutClock, int goingBackClock) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.reasonAtr = reasonAtr;
		this.goingOutClock = goingOutClock;
		this.goingBackClock = goingBackClock;
	}
	
}
