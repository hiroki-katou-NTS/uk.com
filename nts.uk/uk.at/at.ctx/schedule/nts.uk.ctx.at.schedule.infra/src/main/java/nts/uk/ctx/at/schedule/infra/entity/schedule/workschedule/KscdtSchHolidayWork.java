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
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 勤務予定の休出時間
 * * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_HOLIDAY_WORK")
public class KscdtSchHolidayWork extends ContractUkJpaEntity{

	@EmbeddedId
	public KscdtSchHolidayWorkPK pk;
	
	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	
	
	/** 休出開始時刻 **/
	@Column(name = "HOLIDAY_WORK_TS_START")
	public int holidayWorkTsStart;
	
	
	/** 休出終了時刻 **/
	@Column(name = "HOLIDAY_WORK_TS_END")
	public int holidayWorkTsEnd;
	
	
	/** 休出時間 **/
	@Column(name = "HOLIDAY_WORK_TIME")
	public int holidayWorkTime;
	
	/** 振替時間**/
	@Column(name = "HOLIDAY_WORK_TIME_TRANS")
	public int holidayWorkTimeTrans;
	
	/** 事前申請時間**/
	@Column(name = "HOLIDAY_WORK_TIME_PREAPP")
	public int holidayWorkTimePreApp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchTime kscdtSchTime;
	
	public static KscdtSchHolidayWork toEntity(HolidayWorkFrameTimeSheet timeSheet,HolidayWorkFrameTime time, String sID, GeneralDate yMD, String cID){
		KscdtSchHolidayWorkPK pk = new KscdtSchHolidayWorkPK(sID, yMD, timeSheet.getHolidayWorkTimeSheetNo().v());
		return new KscdtSchHolidayWork(pk, cID, 
				timeSheet.getTimeSheet().getStart().v(),
				timeSheet.getTimeSheet().getEnd().v(),
				time.getHolidayWorkTime().get().getTime().v(), 
				time.getTransferTime().get().getTime().v(),
				time.getBeforeApplicationTime().get().v());
	}
	
/*	public HolidayWorkTimeOfDaily toDomain(){
		return HolidayWorkTimeOfDaily();
	}*/
	
	@Override
	protected Object getKey() {
		return this.pk;
	}


	public KscdtSchHolidayWork(KscdtSchHolidayWorkPK pk, String cid, int holidayWorkTsStart, int holidayWorkTsEnd,
			int holidayWorkTime, int holidayWorkTimeTrans, int holidayWorkTimePreApp) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.holidayWorkTsStart = holidayWorkTsStart;
		this.holidayWorkTsEnd = holidayWorkTsEnd;
		this.holidayWorkTime = holidayWorkTime;
		this.holidayWorkTimeTrans = holidayWorkTimeTrans;
		this.holidayWorkTimePreApp = holidayWorkTimePreApp;
	}

}
