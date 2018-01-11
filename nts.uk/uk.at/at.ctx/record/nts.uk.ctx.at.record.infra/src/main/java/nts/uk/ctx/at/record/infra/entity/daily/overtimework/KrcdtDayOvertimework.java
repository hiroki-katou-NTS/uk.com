package nts.uk.ctx.at.record.infra.entity.daily.overtimework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculationMinusExist;
import nts.uk.ctx.at.record.dom.daily.overtimework.FlexTime;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTime;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_OVERTIMEWORK")
public class KrcdtDayOvertimework extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayOvertimeworkPK krcdtDayOvertimeworkPK;
	/*残業時間1*/
	@Column(name = "OVER_TIME_1")
	public int overTime1;
	/*残業時間2*/
	@Column(name = "OVER_TIME_2")
	public int overTime2;
	/*残業時間3*/
	@Column(name = "OVER_TIME_3")
	public int overTime3;
	/*残業時間4*/
	@Column(name = "OVER_TIME_4")
	public int overTime4;
	/*残業時間5*/
	@Column(name = "OVER_TIME_5")
	public int overTime5;
	/*残業時間6*/
	@Column(name = "OVER_TIME_6")
	public int overTime6;
	/*残業時間7*/
	@Column(name = "OVER_TIME_7")
	public int overTime7;
	/*残業時間8*/
	@Column(name = "OVER_TIME_8")
	public int overTime8;
	/*残業時間9*/
	@Column(name = "OVER_TIME_9")
	public int overTime9;
	/*残業時間10*/
	@Column(name = "OVER_TIME_10")
	public int overTime10;
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
	/*計算残業時間1*/
	@Column(name = "CALC_OVER_TIME_1")
	public int calcOverTime1;
	/*計算残業時間2*/
	@Column(name = "CALC_OVER_TIME_2")
	public int calcOverTime2;
	/*計算残業時間3*/
	@Column(name = "CALC_OVER_TIME_3")
	public int calcOverTime3;
	/*計算残業時間4*/
	@Column(name = "CALC_OVER_TIME_4")
	public int calcOverTime4;
	/*計算残業時間5*/
	@Column(name = "CALC_OVER_TIME_5")
	public int calcOverTime5;
	/*計算残業時間6*/
	@Column(name = "CALC_OVER_TIME_6")
	public int calcOverTime6;
	/*計算残業時間7*/
	@Column(name = "CALC_OVER_TIME_7")
	public int calcOverTime7;
	/*計算残業時間8*/
	@Column(name = "CALC_OVER_TIME_8")
	public int calcOverTime8;
	/*計算残業時間9*/
	@Column(name = "CALC_OVER_TIME_9")
	public int calcOverTime9;
	/*計算残業時間10*/
	@Column(name = "CALC_OVER_TIME_10")
	public int calcOverTime10;
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
	/*事前残業申請時間1*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_1")
	public int preOverTimeAppTime1;
	/*事前残業申請時間2*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_2")
	public int preOverTimeAppTime2;
	/*事前残業申請時間3*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_3")
	public int preOverTimeAppTime3;
	/*事前残業申請時間4*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_4")
	public int preOverTimeAppTime4;
	/*事前残業申請時間5*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_5")
	public int preOverTimeAppTime5;
	/*事前残業申請時間6*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_6")
	public int preOverTimeAppTime6;
	/*事前残業申請時間7*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_7")
	public int preOverTimeAppTime7;
	/*事前残業申請時間8*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_8")
	public int preOverTimeAppTime8;
	/*事前残業申請時間9*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_9")
	public int preOverTimeAppTime9;
	/*事前残業申請時間10*/
	@Column(name = "PRE_OVER_TIME_APP_TIME_10")
	public int preOverTimeAppTime10;
	/*法定外残業深夜時間*/
	@Column(name = "ILEGL_MIDN_OVER_TIME")
	public int ileglMidntOverTime;
	/*計算法定外残業深夜時間*/
	@Column(name = "CALC_ILEGL_MIDN_OVER_TIME")
	public int calcIleglMidNOverTime;
	/*残業拘束時間*/
	@Column(name = "OVER_TIME_BIND_TIME")
	public int overTimeBindTime;
	/*変形法定内残業*/
	@Column(name = "DEFORM_LEGL_OVER_TIME")
	public int deformLeglOverTime;
	/*フレックス時間*/
	@Column(name = "FLEX_TIME")
	public int flexTime;
	/*計算フレックス時間*/
	@Column(name = "CALC_FLEX_TIME")
	public int calcFlexTime;
	/*事前申請フレックス時間*/
	@Column(name = "PRE_APP_FLEX_TIME")
	public int preAppFlexTime;
	
	@OneToOne(mappedBy="krcdtDayOvertimework")
	public KrcdtDayAttendanceTime krcdtDayAttendanceTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayOvertimeworkPK;
	}
	
	/**
	 * create entity
	 * @param employeeId
	 * @param generalDate
	 * @param overTimeOfDaily
	 * @return
	 */
	public static KrcdtDayOvertimework create(String employeeId, GeneralDate generalDate, OverTimeOfDaily overTimeOfDaily) {
		val entity = new KrcdtDayOvertimework();
		entity.krcdtDayOvertimeworkPK = new KrcdtDayOvertimeworkPK(employeeId, generalDate);
		//残業時間
		entity.overTime1  = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 1).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		entity.overTime2  = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 2).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		entity.overTime3  = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 3).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		entity.overTime4  = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 4).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		entity.overTime5  = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 5).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		entity.overTime6  = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 6).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		entity.overTime7  = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 7).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		entity.overTime8  = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 8).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		entity.overTime9  = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 9).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		entity.overTime10 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 10).findFirst().get().getOverTimeWork().getTime().valueAsMinutes();
		//振替時間
		entity.transTime1 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 1).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		entity.transTime2 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 2).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		entity.transTime3 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 3).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		entity.transTime4 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 4).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		entity.transTime5 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 5).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		entity.transTime6 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 6).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		entity.transTime7 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 7).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		entity.transTime8 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 8).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		entity.transTime9 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 9).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		entity.transTime10= overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 10).findFirst().get().getTransferTime().getTime().valueAsMinutes();
		//計算残業時間
		entity.calcOverTime1 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 1).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		entity.calcOverTime2 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 2).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		entity.calcOverTime3 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 3).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		entity.calcOverTime4 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 4).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		entity.calcOverTime5 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 5).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		entity.calcOverTime6 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 6).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		entity.calcOverTime7 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 7).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		entity.calcOverTime8 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 8).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		entity.calcOverTime9 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 9).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		entity.calcOverTime10= overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 10).findFirst().get().getOverTimeWork().getCalcTime().valueAsMinutes();
		//計算振替時間
		entity.calcTransTime1 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 1).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		entity.calcTransTime2 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 2).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		entity.calcTransTime3 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 3).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		entity.calcTransTime4 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 4).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		entity.calcTransTime5 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 5).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		entity.calcTransTime6 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 6).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		entity.calcTransTime7 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 7).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		entity.calcTransTime8 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 8).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		entity.calcTransTime9 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 9).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		entity.calcTransTime10= overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 10).findFirst().get().getTransferTime().getCalcTime().valueAsMinutes();
		//事前残業申請
		entity.preOverTimeAppTime1 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 1).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		entity.preOverTimeAppTime2 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 2).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		entity.preOverTimeAppTime3 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 3).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		entity.preOverTimeAppTime4 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 4).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		entity.preOverTimeAppTime5 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 5).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		entity.preOverTimeAppTime6 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 6).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		entity.preOverTimeAppTime7 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 7).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		entity.preOverTimeAppTime8 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 8).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		entity.preOverTimeAppTime9 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 9).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		entity.preOverTimeAppTime10 = overTimeOfDaily.getOverTimeWorkFrameTime().stream().filter(tc -> tc.getOverWorkFrameNo().v() == 10).findFirst().get().getBeforeApplicationTime().valueAsMinutes();
		//法定外
		entity.ileglMidntOverTime = overTimeOfDaily.getExcessOverTimeWorkMidNightTime().get().getTime().getTime().valueAsMinutes();
		//計算法定外
		entity.calcIleglMidNOverTime = overTimeOfDaily.getExcessOverTimeWorkMidNightTime().get().getTime().getCalcTime().valueAsMinutes();
		//拘束時間
		entity.overTimeBindTime = overTimeOfDaily.getOverTimeWorkSpentAtWork().valueAsMinutes();
		//変形法定内残業
		entity.deformLeglOverTime = overTimeOfDaily.getIrregularWithinPrescribedOverTimeWork().valueAsMinutes();
		//フレックス時間
		entity.flexTime = overTimeOfDaily.getFlexTime().getFlexTime().getTime().valueAsMinutes();
		//計算フレックス時間
		entity.calcFlexTime = overTimeOfDaily.getFlexTime().getFlexTime().getCalcTime().valueAsMinutes();
		//事前フレックス時間
		entity.preAppFlexTime = overTimeOfDaily.getFlexTime().getBeforeApplicationTime().valueAsMinutes();
		
		return entity;
	}

	public OverTimeOfDaily toDomain() {
		List<OverTimeFrameTime> list = new ArrayList<>();
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(1),
									   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime1), new AttendanceTime(this.calcOverTime1)),
									   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime1), new AttendanceTime(this.calcTransTime1)),
									   new AttendanceTime(this.preOverTimeAppTime1),
									   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(2),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime2), new AttendanceTime(this.calcOverTime2)),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime2), new AttendanceTime(this.calcTransTime2)),
				   new AttendanceTime(this.preOverTimeAppTime2),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(3),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime3), new AttendanceTime(this.calcOverTime3)),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime3), new AttendanceTime(this.calcTransTime3)),
				   new AttendanceTime(this.preOverTimeAppTime3),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(4),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime4), new AttendanceTime(this.calcOverTime4)),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime4), new AttendanceTime(this.calcTransTime4)),
				   new AttendanceTime(this.preOverTimeAppTime4),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(5),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime5), new AttendanceTime(this.calcOverTime5)),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime5), new AttendanceTime(this.calcTransTime5)),
				   new AttendanceTime(this.preOverTimeAppTime5),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(6),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime6), new AttendanceTime(this.calcOverTime6)),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime6), new AttendanceTime(this.calcTransTime6)),
				   new AttendanceTime(this.preOverTimeAppTime6),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(7),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime7), new AttendanceTime(this.calcOverTime7)),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime7), new AttendanceTime(this.calcTransTime7)),
				   new AttendanceTime(this.preOverTimeAppTime7),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(8),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime8), new AttendanceTime(this.calcOverTime8)),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime8), new AttendanceTime(this.calcTransTime8)),
				   new AttendanceTime(this.preOverTimeAppTime8),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(9),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime9), new AttendanceTime(this.calcOverTime9)),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime9), new AttendanceTime(this.calcTransTime9)),
				   new AttendanceTime(this.preOverTimeAppTime9),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(10),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime10), new AttendanceTime(this.calcOverTime10)),
				   TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime10), new AttendanceTime(this.calcTransTime10)),
				   new AttendanceTime(this.preOverTimeAppTime10),
				   new AttendanceTime(0)));
		
		return new OverTimeOfDaily(new ArrayList<>(), 
								   list,
								   Finally.of(new ExcessOverTimeWorkMidNightTime(TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(this.ileglMidntOverTime),new AttendanceTime(this.calcIleglMidNOverTime)))),
								   new AttendanceTime(this.ileglMidntOverTime),
								   new FlexTime(TimeWithCalculationMinusExist.createTimeWithCalculation(new AttendanceTimeOfExistMinus(this.flexTime), new AttendanceTimeOfExistMinus(this.calcFlexTime)),new AttendanceTime(this.preAppFlexTime)),
								   new AttendanceTime(this.overTimeBindTime)
								   ); 
		
	}
}
