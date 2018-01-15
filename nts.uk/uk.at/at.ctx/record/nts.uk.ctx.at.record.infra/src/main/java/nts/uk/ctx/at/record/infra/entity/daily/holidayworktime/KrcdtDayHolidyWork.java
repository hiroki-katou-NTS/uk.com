package nts.uk.ctx.at.record.infra.entity.daily.holidayworktime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkMidNightTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_HOLIDYWORK")
public class KrcdtDayHolidyWork extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayHolidyWorkPK krcdtDayHolidyWorkPK;
	/*休日出勤時間1*/
	@Column(name = "HOLI_WORK_TIME_1")
	public int holiWorkTime1;
	/*休日出勤時間2*/
	@Column(name = "HOLI_WORK_TIME_2")
	public int holiWorkTime2;
	/*休日出勤時間3*/
	@Column(name = "HOLI_WORK_TIME_3")
	public int holiWorkTime3;
	/*休日出勤時間4*/
	@Column(name = "HOLI_WORK_TIME_4")
	public int holiWorkTime4;
	/*休日出勤時間5*/
	@Column(name = "HOLI_WORK_TIME_5")
	public int holiWorkTime5;
	/*休日出勤時間6*/
	@Column(name = "HOLI_WORK_TIME_6")
	public int holiWorkTime6;
	/*休日出勤時間7*/
	@Column(name = "HOLI_WORK_TIME_7")
	public int holiWorkTime7;
	/*休日出勤時間8*/
	@Column(name = "HOLI_WORK_TIME_8")
	public int holiWorkTime8;
	/*休日出勤時間9*/
	@Column(name = "HOLI_WORK_TIME_9")
	public int holiWorkTime9;
	/*休日出勤時間10*/
	@Column(name = "HOLI_WORK_TIME_10")
	public int holiWorkTime10;
	/*振替時間1*/
	@Column(name = "TRANS_TIME_1")
	public int transTime1;
	/*振替時間2*/
	@Column(name = "TRANS_TIME_2")
	public int transTime2;
	/*振替時間3*/
	@Column(name = "TRANS_TIME_3")
	public int transTime3;
	/*振替時間4*/
	@Column(name = "TRANS_TIME_4")
	public int transTime4;
	/*振替時間5*/
	@Column(name = "TRANS_TIME_5")
	public int transTime5;
	/*振替時間6*/
	@Column(name = "TRANS_TIME_6")
	public int transTime6;
	/*振替時間7*/
	@Column(name = "TRANS_TIME_7")
	public int transTime7;
	/*振替時間8*/
	@Column(name = "TRANS_TIME_8")
	public int transTime8;
	/*振替時間9*/
	@Column(name = "TRANS_TIME_9")
	public int transTime9;
	/*振替時間10*/
	@Column(name = "TRANS_TIME_10")
	public int transTime10;
	/*計算休日出勤時間1*/
	@Column(name = "CALC_HOLI_WORK_TIME_1")
	public int calcHoliWorkTime1;
	/*計算休日出勤時間2*/
	@Column(name = "CALC_HOLI_WORK_TIME_2")
	public int calcHoliWorkTime2;
	/*計算休日出勤時間3*/
	@Column(name = "CALC_HOLI_WORK_TIME_3")
	public int calcHoliWorkTime3;
	/*計算休日出勤時間4*/
	@Column(name = "CALC_HOLI_WORK_TIME_4")
	public int calcHoliWorkTime4;
	/*計算休日出勤時間5*/
	@Column(name = "CALC_HOLI_WORK_TIME_5")
	public int calcHoliWorkTime5;
	/*計算休日出勤時間6*/
	@Column(name = "CALC_HOLI_WORK_TIME_6")
	public int calcHoliWorkTime6;
	/*計算休日出勤時間7*/
	@Column(name = "CALC_HOLI_WORK_TIME_7")
	public int calcHoliWorkTime7;
	/*計算休日出勤時間8*/
	@Column(name = "CALC_HOLI_WORK_TIME_8")
	public int calcHoliWorkTime8;
	/*計算休日出勤時間9*/
	@Column(name = "CALC_HOLI_WORK_TIME_9")
	public int calcHoliWorkTime9;
	/*計算休日出勤時間10*/
	@Column(name = "CALC_HOLI_WORK_TIME_10")
	public int calcHoliWorkTime10;
	/*計算振替時間1*/
	@Column(name = "CALC_TRANS_TIME_1")
	public int calcTransTime1;
	/*計算振替時間2*/
	@Column(name = "CALC_TRANS_TIME_2")
	public int calcTransTime2;
	/*計算振替時間3*/
	@Column(name = "CALC_TRANS_TIME_3")
	public int calcTransTime3;
	/*計算振替時間4*/
	@Column(name = "CALC_TRANS_TIME_4")
	public int calcTransTime4;
	/*計算振替時間5*/
	@Column(name = "CALC_TRANS_TIME_5")
	public int calcTransTime5;
	/*計算振替時間6*/
	@Column(name = "CALC_TRANS_TIME_6")
	public int calcTransTime6;
	/*計算振替時間7*/
	@Column(name = "CALC_TRANS_TIME_7")
	public int calcTransTime7;
	/*計算振替時間8*/
	@Column(name = "CALC_TRANS_TIME_8")
	public int calcTransTime8;
	/*計算振替時間9*/
	@Column(name = "CALC_TRANS_TIME_9")
	public int calcTransTime9;
	/*計算振替時間10*/
	@Column(name = "CALC_TRANS_TIME_10")
	public int calcTransTime10;
	/*事前申請時間1*/
	@Column(name = "PRE_APP_TIME_1")
	public int preAppTime1;
	/*事前申請時間2*/
	@Column(name = "PRE_APP_TIME_2")
	public int preAppTime2;
	/*事前申請時間3*/
	@Column(name = "PRE_APP_TIME_3")
	public int preAppTime3;
	/*事前申請時間4*/
	@Column(name = "PRE_APP_TIME_4")
	public int preAppTime4;
	/*事前申請時間5*/
	@Column(name = "PRE_APP_TIME_5")
	public int preAppTime5;
	/*事前申請時間6*/
	@Column(name = "PRE_APP_TIME_6")
	public int preAppTime6;
	/*事前申請時間7*/
	@Column(name = "PRE_APP_TIME_7")
	public int preAppTime7;
	/*事前申請時間8*/
	@Column(name = "PRE_APP_TIME_8")
	public int preAppTime8;
	/*事前申請時間9*/
	@Column(name = "PRE_APP_TIME_9")
	public int preAppTime9;
	/*事前申請時間10*/
	@Column(name = "PRE_APP_TIME_10")
	public int preAppTime10;
	/*法定内休日出勤深夜*/
	@Column(name = "LEG_HOLI_WORK_MIDN")
	public int legHoliWorkMidn;
	/*法定外休日出勤深夜*/
	@Column(name = "ILLEG_HOLI_WORK_MIDN")
	public int illegHoliWorkMidn;
	/*祝日日出勤深夜*/
	@Column(name = "PB_HOLI_WORK_MIDN")
	public int pbHoliWorkMidn;
	/*計算法定内休日出勤深夜*/
	@Column(name = "CALC_LEG_HOLI_WORK_MIDN")
	public int calcLegHoliWorkMidn;
	/*計算法定外休日出勤深夜*/
	@Column(name = "CALC_ILLEG_HOLI_WORK_MIDN")
	public int calcIllegHoliWorkMidn;
	/*計算祝日日出勤深夜*/
	@Column(name = "CALC_PB_HOLI_WORK_MIDN")
	public int calcPbHoliWorkMidn;
	/*休日出勤拘束時間*/
	@Column(name = "HOLI_WORK_BIND_TIME")
	public int holiWorkBindTime;
	
	@OneToOne(mappedBy="krcdtDayHolidyWork")
	public KrcdtDayAttendanceTime krcdtDayAttendanceTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayHolidyWorkPK;
	}
	
	
	public static KrcdtDayHolidyWork create(String employeeId, GeneralDate date, HolidayWorkTimeOfDaily domain) {
		val entity = new KrcdtDayHolidyWork();
		/*主キー*/
		entity.krcdtDayHolidyWorkPK = new KrcdtDayHolidyWorkPK(employeeId,date);
		/*休日出勤時間*/
		entity.holiWorkTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 1).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		entity.holiWorkTime2 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 2).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		entity.holiWorkTime3 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 3).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		entity.holiWorkTime4 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 4).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		entity.holiWorkTime5 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 5).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		entity.holiWorkTime6 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 6).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		entity.holiWorkTime7 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 7).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		entity.holiWorkTime8 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 8).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		entity.holiWorkTime9 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 9).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		entity.holiWorkTime10 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 10).findFirst().get().getHolidayWorkTime().get().getTime().valueAsMinutes();
		/*振替時間*/
		entity.transTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 1).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		entity.transTime2 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 2).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		entity.transTime3 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 3).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		entity.transTime4 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 4).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		entity.transTime5 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 5).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		entity.transTime6 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 6).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		entity.transTime7 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 7).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		entity.transTime8 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 8).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		entity.transTime9 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 9).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		entity.transTime10 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 10).findFirst().get().getTransferTime().get().getTime().valueAsMinutes();
		/*計算休日出勤時間*/
		entity.calcHoliWorkTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 1).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcHoliWorkTime2 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 2).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcHoliWorkTime3 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 3).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcHoliWorkTime4 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 4).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcHoliWorkTime5 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 5).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcHoliWorkTime6 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 6).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcHoliWorkTime7 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 7).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcHoliWorkTime8 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 8).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcHoliWorkTime9 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 9).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcHoliWorkTime10 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 10).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		/*計算振替時間*/
		entity.calcTransTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 1).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcTransTime2 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 2).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcTransTime3 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 3).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcTransTime4 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 4).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcTransTime5 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 5).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcTransTime6 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 6).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcTransTime7 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 7).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcTransTime8 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 8).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcTransTime9 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 9).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		entity.calcTransTime10 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 10).findFirst().get().getTransferTime().get().getCalcTime().valueAsMinutes();
		/*事前申請時間*/
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 1).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 2).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 3).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 4).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 5).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 6).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 7).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 8).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 9).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();
		entity.preAppTime1 = domain.getHolidayWorkFrameTime().stream().filter(tc -> tc.getHolidayFrameNo().v() == 10).findFirst().get().getBeforeApplicationTime().get().valueAsMinutes();		
		/*法定内休日出勤深夜*/
		entity.legHoliWorkMidn = domain.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().stream().filter(tc -> tc.getStatutoryAtr() == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork).findFirst().get().getTime().getTime().valueAsMinutes();
		/*法定外休日出勤深夜*/
		entity.illegHoliWorkMidn = domain.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().stream().filter(tc -> tc.getStatutoryAtr() == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork).findFirst().get().getTime().getTime().valueAsMinutes();
		/*祝日日出勤深夜*/
		entity.pbHoliWorkMidn = domain.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().stream().filter(tc -> tc.getStatutoryAtr() == StaturoryAtrOfHolidayWork.PublicHolidayWork).findFirst().get().getTime().getTime().valueAsMinutes();
		/*計算法定内休日出勤深夜*/
		entity.calcLegHoliWorkMidn = domain.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().stream().filter(tc -> tc.getStatutoryAtr() == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork).findFirst().get().getTime().getCalcTime().valueAsMinutes();
		/*計算法定外休日出勤深夜*/
		entity.calcIllegHoliWorkMidn = domain.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().stream().filter(tc -> tc.getStatutoryAtr() == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork).findFirst().get().getTime().getCalcTime().valueAsMinutes();
		/*計算祝日日出勤深夜*/
		entity.calcPbHoliWorkMidn = domain.getHolidayMidNightWork().get().getHolidayWorkMidNightTime().stream().filter(tc -> tc.getStatutoryAtr() == StaturoryAtrOfHolidayWork.PublicHolidayWork).findFirst().get().getTime().getCalcTime().valueAsMinutes();
		/*休日出勤拘束時間*/
		entity.holiWorkBindTime = domain.getHolidayTimeSpentAtWork().valueAsMinutes();
		
		return entity;
	}
	
	
	public HolidayWorkTimeOfDaily toDomain() {
		
		List<HolidayWorkFrameTimeSheet> holidayWorkFrameTimeSheetList = new ArrayList<>();//KrcdtDayHolidyWorkTsのtoDomainを呼べる？
		
		List<HolidayWorkFrameTime> holiWorkFrameTimeList = new ArrayList<>();
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(1)),
														   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime1),new AttendanceTime(this.calcHoliWorkTime1))),
														   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime1),new AttendanceTime(this.calcTransTime1))),
														   Finally.of(new AttendanceTime(this.preAppTime1))));
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(2)),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime2),new AttendanceTime(this.calcHoliWorkTime2))),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime2),new AttendanceTime(this.calcTransTime2))),
				   										   Finally.of(new AttendanceTime(this.preAppTime2))));
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(3)),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime3),new AttendanceTime(this.calcHoliWorkTime3))),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime3),new AttendanceTime(this.calcTransTime3))),
				   										   Finally.of(new AttendanceTime(this.preAppTime3))));
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(4)),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime4),new AttendanceTime(this.calcHoliWorkTime4))),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime4),new AttendanceTime(this.calcTransTime4))),
				   										   Finally.of(new AttendanceTime(this.preAppTime4))));
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(5)),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime5),new AttendanceTime(this.calcHoliWorkTime5))),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime5),new AttendanceTime(this.calcTransTime5))),
				   										   Finally.of(new AttendanceTime(this.preAppTime5))));
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(3)),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime6),new AttendanceTime(this.calcHoliWorkTime6))),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime6),new AttendanceTime(this.calcTransTime6))),
				   										   Finally.of(new AttendanceTime(this.preAppTime6))));
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(7)),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime7),new AttendanceTime(this.calcHoliWorkTime7))),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime7),new AttendanceTime(this.calcTransTime7))),
				   										   Finally.of(new AttendanceTime(this.preAppTime7))));
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(8)),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime8),new AttendanceTime(this.calcHoliWorkTime8))),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime8),new AttendanceTime(this.calcTransTime8))),
				   										   Finally.of(new AttendanceTime(this.preAppTime8))));
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(9)),
														   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime9),new AttendanceTime(this.calcHoliWorkTime9))),
														   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime9),new AttendanceTime(this.calcTransTime9))),
														   Finally.of(new AttendanceTime(this.preAppTime9))));
		holiWorkFrameTimeList.add(new HolidayWorkFrameTime(new HolidayWorkFrameNo(Integer.valueOf(10)),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.holiWorkTime10),new AttendanceTime(this.calcHoliWorkTime10))),
				   										   Finally.of(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.calcTransTime10),new AttendanceTime(this.calcTransTime10))),
				   										   Finally.of(new AttendanceTime(this.preAppTime10))));
		
		List<HolidayWorkMidNightTime> holidayWorkMidNightTimeList = new ArrayList<>();
		holidayWorkMidNightTimeList.add(new HolidayWorkMidNightTime(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.legHoliWorkMidn),new AttendanceTime(this.calcLegHoliWorkMidn)),
																	StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork));
		holidayWorkMidNightTimeList.add(new HolidayWorkMidNightTime(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.illegHoliWorkMidn),new AttendanceTime(this.calcIllegHoliWorkMidn)),
																	StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork));
		holidayWorkMidNightTimeList.add(new HolidayWorkMidNightTime(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.pbHoliWorkMidn),new AttendanceTime(this.calcPbHoliWorkMidn)),
																	StaturoryAtrOfHolidayWork.PublicHolidayWork));
		
		return new HolidayWorkTimeOfDaily(holidayWorkFrameTimeSheetList,holiWorkFrameTimeList,Finally.of(new HolidayMidnightWork(holidayWorkMidNightTimeList)), new AttendanceTime(this.holiWorkBindTime));
	}
	
	
	
}