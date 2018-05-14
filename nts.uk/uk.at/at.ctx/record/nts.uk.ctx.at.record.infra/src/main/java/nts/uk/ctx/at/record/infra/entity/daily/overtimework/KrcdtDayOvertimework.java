package nts.uk.ctx.at.record.infra.entity.daily.overtimework;

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
import nts.uk.ctx.at.record.dom.daily.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculationMinusExist;
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
	/*残業乖離時間1*/
	@Column(name = "DIVERGENCE_TIME_1")
	public int divergenceTime1;
	/*残業乖離時間2*/
	@Column(name = "DIVERGENCE_TIME_2")
	public int divergenceTime2;
	/*残業乖離時間3*/
	@Column(name = "DIVERGENCE_TIME_3")
	public int divergenceTime3;
	/*残業乖離時間4*/
	@Column(name = "DIVERGENCE_TIME_4")
	public int divergenceTime4;
	/*残業乖離時間5*/
	@Column(name = "DIVERGENCE_TIME_5")
	public int divergenceTime5;
	/*残業乖離時間6*/
	@Column(name = "DIVERGENCE_TIME_6")
	public int divergenceTime6;
	/*残業乖離時間7*/
	@Column(name = "DIVERGENCE_TIME_7")
	public int divergenceTime7;
	/*残業乖離時間8*/
	@Column(name = "DIVERGENCE_TIME_8")
	public int divergenceTime8;
	/*残業乖離時間9*/
	@Column(name = "DIVERGENCE_TIME_9")
	public int divergenceTime9;
	/*残業乖離時間10*/
	@Column(name = "DIVERGENCE_TIME_10")
	public int divergenceTime10;
	/*振替乖離時間１*/
	@Column(name = "DIVERGENCE_TRANS_TIME_1")
	public int divergenceTransTime1;
	/*振替乖離時間2*/
	@Column(name = "DIVERGENCE_TRANS_TIME_2")
	public int divergenceTransTime2;
	/*振替乖離時間3*/
	@Column(name = "DIVERGENCE_TRANS_TIME_3")
	public int divergenceTransTime3;
	/*振替乖離時間4*/
	@Column(name = "DIVERGENCE_TRANS_TIME_4")
	public int divergenceTransTime4;
	/*振替乖離時間5*/
	@Column(name = "DIVERGENCE_TRANS_TIME_5")
	public int divergenceTransTime5;
	/*振替乖離時間6*/
	@Column(name = "DIVERGENCE_TRANS_TIME_6")
	public int divergenceTransTime6;
	/*振替乖離時間7*/
	@Column(name = "DIVERGENCE_TRANS_TIME_7")
	public int divergenceTransTime7;
	/*振替乖離時間8*/
	@Column(name = "DIVERGENCE_TRANS_TIME_8")
	public int divergenceTransTime8;
	/*振替乖離時間9*/
	@Column(name = "DIVERGENCE_TRANS_TIME_9")
	public int divergenceTransTime9;
	/*振替乖離時間１0*/
	@Column(name = "DIVERGENCE_TRANS_TIME_10")
	public int divergenceTransTime10;
	/*フレックス乖離時間*/
	@Column(name = "DIVERGENCE_FLEX_TIME")
	public int divergenceFlexTime;
	/*法定外残業深夜乖離時間*/
	@Column(name = "DIV_ILEGL_MIDN_OVER_TIME")
	public int divIleglMidnOverTime;

	
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
		entity.setData(overTimeOfDaily);
		return entity;
	}
	
	public void setData(OverTimeOfDaily overTimeOfDaily){
		this.overTime1  = 0;
		this.overTime2  = 0;
		this.overTime3  = 0;
		this.overTime4  = 0;
		this.overTime5  = 0;
		this.overTime6  = 0;
		this.overTime7  = 0;
		this.overTime8  = 0;
		this.overTime9  = 0;
		this.overTime10 = 0;
		//振替時間
		this.transTime1 = 0;
		this.transTime2 = 0;
		this.transTime3 = 0;
		this.transTime4 = 0;
		this.transTime5 = 0;
		this.transTime6 = 0;
		this.transTime7 = 0;
		this.transTime8 = 0;
		this.transTime9 = 0;
		this.transTime10= 0;
		//計算残業時間
		this.calcOverTime1 = 0;
		this.calcOverTime2 = 0;
		this.calcOverTime3 = 0;
		this.calcOverTime4 = 0;
		this.calcOverTime5 = 0;
		this.calcOverTime6 = 0;
		this.calcOverTime7 = 0;
		this.calcOverTime8 = 0;
		this.calcOverTime9 = 0;
		this.calcOverTime10= 0;
		//計算振替時間
		this.calcTransTime1 = 0;
		this.calcTransTime2 = 0;
		this.calcTransTime3 = 0;
		this.calcTransTime4 = 0;
		this.calcTransTime5 = 0;
		this.calcTransTime6 = 0;
		this.calcTransTime7 = 0;
		this.calcTransTime8 = 0;
		this.calcTransTime9 = 0;
		this.calcTransTime10= 0;
		//事前残業申請
		this.preOverTimeAppTime1 = 0;
		this.preOverTimeAppTime2 = 0;
		this.preOverTimeAppTime3 = 0;
		this.preOverTimeAppTime4 = 0;
		this.preOverTimeAppTime5 = 0;
		this.preOverTimeAppTime6 = 0;
		this.preOverTimeAppTime7 = 0;
		this.preOverTimeAppTime8 = 0;
		this.preOverTimeAppTime9 = 0;
		this.preOverTimeAppTime10 = 0;
		//残業乖離時間
		this.divergenceTime1  = 0;
		this.divergenceTime2  = 0;
		this.divergenceTime3  = 0;
		this.divergenceTime4  = 0;
		this.divergenceTime5  = 0;
		this.divergenceTime6  = 0;
		this.divergenceTime7  = 0;
		this.divergenceTime8  = 0;
		this.divergenceTime9  = 0;
		this.divergenceTime10 = 0;
		//振替乖離時間
		this.divergenceTransTime1 = 0;
		this.divergenceTransTime2 = 0;
		this.divergenceTransTime3 = 0;
		this.divergenceTransTime4 = 0;
		this.divergenceTransTime5 = 0;
		this.divergenceTransTime6 = 0;
		this.divergenceTransTime7 = 0;
		this.divergenceTransTime8 = 0;
		this.divergenceTransTime9 = 0;
		this.divergenceTransTime10= 0;
		
		this.ileglMidntOverTime = 0;
		//計算法定外
		this.calcIleglMidNOverTime = 0;
		//法定外残業深夜乖離時間
		this.divIleglMidnOverTime = 0;
		
		//拘束時間
		this.overTimeBindTime = 0;
		//変形法定内残業
		this.deformLeglOverTime = 0;
		//フレックス時間
		this.flexTime = 0;
		//計算フレックス時間
		this.calcFlexTime = 0;
		//事前フレックス時間
		this.preAppFlexTime = 0;
		//フレックス乖離時間
		this.divergenceFlexTime = 0;
		
		if(overTimeOfDaily != null) {
			//残業枠時間
			if(overTimeOfDaily.getOverTimeWorkFrameTime() != null
				&& !overTimeOfDaily.getOverTimeWorkFrameTime().isEmpty()) {
				OverTimeFrameTime frame1 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 1);
				OverTimeFrameTime frame2 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 2);
				OverTimeFrameTime frame3 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 3);
				OverTimeFrameTime frame4 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 4);
				OverTimeFrameTime frame5 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 5);
				OverTimeFrameTime frame6 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 6);
				OverTimeFrameTime frame7 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 7);
				OverTimeFrameTime frame8 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 8);
				OverTimeFrameTime frame9 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 9);
				OverTimeFrameTime frame10 = getOverTimeFrame(overTimeOfDaily.getOverTimeWorkFrameTime(), 10);
				
				//残業時間
				this.overTime1  = frame1.getOverTimeWork() == null || frame1.getOverTimeWork().getTime() == null ? 0 : frame1.getOverTimeWork().getTime().valueAsMinutes();
				this.overTime2  = frame2.getOverTimeWork() == null || frame2.getOverTimeWork().getTime() == null ? 0 : frame2.getOverTimeWork().getTime().valueAsMinutes();
				this.overTime3  = frame3.getOverTimeWork() == null || frame3.getOverTimeWork().getTime() == null ? 0 : frame3.getOverTimeWork().getTime().valueAsMinutes();
				this.overTime4  = frame4.getOverTimeWork() == null || frame4.getOverTimeWork().getTime() == null ? 0 : frame4.getOverTimeWork().getTime().valueAsMinutes();
				this.overTime5  = frame5.getOverTimeWork() == null || frame5.getOverTimeWork().getTime() == null ? 0 : frame5.getOverTimeWork().getTime().valueAsMinutes();
				this.overTime6  = frame6.getOverTimeWork() == null || frame6.getOverTimeWork().getTime() == null ? 0 : frame6.getOverTimeWork().getTime().valueAsMinutes();
				this.overTime7  = frame7.getOverTimeWork() == null || frame7.getOverTimeWork().getTime() == null ? 0 : frame7.getOverTimeWork().getTime().valueAsMinutes();
				this.overTime8  = frame8.getOverTimeWork() == null || frame8.getOverTimeWork().getTime() == null ? 0 : frame8.getOverTimeWork().getTime().valueAsMinutes();
				this.overTime9  = frame9.getOverTimeWork() == null || frame9.getOverTimeWork().getTime() == null ? 0 : frame9.getOverTimeWork().getTime().valueAsMinutes();
				this.overTime10 = frame10.getOverTimeWork() == null || frame10.getOverTimeWork().getTime() == null ? 0 : frame10.getOverTimeWork().getTime().valueAsMinutes();
				//振替時間
				this.transTime1 = frame1.getTransferTime() == null || frame1.getTransferTime().getTime() == null ? 0 : frame1.getTransferTime().getTime().valueAsMinutes();
				this.transTime2 = frame2.getTransferTime() == null || frame2.getTransferTime().getTime() == null ? 0 : frame2.getTransferTime().getTime().valueAsMinutes();
				this.transTime3 = frame3.getTransferTime() == null || frame3.getTransferTime().getTime() == null ? 0 : frame3.getTransferTime().getTime().valueAsMinutes();
				this.transTime4 = frame4.getTransferTime() == null || frame4.getTransferTime().getTime() == null ? 0 : frame4.getTransferTime().getTime().valueAsMinutes();
				this.transTime5 = frame5.getTransferTime() == null || frame5.getTransferTime().getTime() == null ? 0 : frame5.getTransferTime().getTime().valueAsMinutes();
				this.transTime6 = frame6.getTransferTime() == null || frame6.getTransferTime().getTime() == null ? 0 : frame6.getTransferTime().getTime().valueAsMinutes();
				this.transTime7 = frame7.getTransferTime() == null || frame7.getTransferTime().getTime() == null ? 0 : frame7.getTransferTime().getTime().valueAsMinutes();
				this.transTime8 = frame8.getTransferTime() == null || frame8.getTransferTime().getTime() == null ? 0 : frame8.getTransferTime().getTime().valueAsMinutes();
				this.transTime9 = frame9.getTransferTime() == null || frame9.getTransferTime().getTime() == null ? 0 : frame9.getTransferTime().getTime().valueAsMinutes();
				this.transTime10= frame10.getTransferTime() == null || frame10.getTransferTime().getTime() == null ? 0 : frame10.getTransferTime().getTime().valueAsMinutes();
				//計算残業時間
				this.calcOverTime1 = frame1.getOverTimeWork() == null || frame1.getOverTimeWork().getCalcTime() == null ? 0 : frame1.getOverTimeWork().getCalcTime().valueAsMinutes();
				this.calcOverTime2 = frame2.getOverTimeWork() == null || frame2.getOverTimeWork().getCalcTime() == null ? 0 : frame2.getOverTimeWork().getCalcTime().valueAsMinutes();
				this.calcOverTime3 = frame3.getOverTimeWork() == null || frame3.getOverTimeWork().getCalcTime() == null ? 0 : frame3.getOverTimeWork().getCalcTime().valueAsMinutes();
				this.calcOverTime4 = frame4.getOverTimeWork() == null || frame4.getOverTimeWork().getCalcTime() == null ? 0 : frame4.getOverTimeWork().getCalcTime().valueAsMinutes();
				this.calcOverTime5 = frame5.getOverTimeWork() == null || frame5.getOverTimeWork().getCalcTime() == null ? 0 : frame5.getOverTimeWork().getCalcTime().valueAsMinutes();
				this.calcOverTime6 = frame6.getOverTimeWork() == null || frame6.getOverTimeWork().getCalcTime() == null ? 0 : frame6.getOverTimeWork().getCalcTime().valueAsMinutes();
				this.calcOverTime7 = frame7.getOverTimeWork() == null || frame7.getOverTimeWork().getCalcTime() == null ? 0 : frame7.getOverTimeWork().getCalcTime().valueAsMinutes();
				this.calcOverTime8 = frame8.getOverTimeWork() == null || frame8.getOverTimeWork().getCalcTime() == null ? 0 : frame8.getOverTimeWork().getCalcTime().valueAsMinutes();
				this.calcOverTime9 = frame9.getOverTimeWork() == null || frame9.getOverTimeWork().getCalcTime() == null ? 0 : frame9.getOverTimeWork().getCalcTime().valueAsMinutes();
				this.calcOverTime10= frame10.getOverTimeWork() == null || frame10.getOverTimeWork().getCalcTime() == null ? 0 : frame10.getOverTimeWork().getCalcTime().valueAsMinutes();
				//計算振替時間
				this.calcTransTime1 = frame1.getTransferTime() == null || frame1.getTransferTime().getCalcTime() == null ? 0 : frame1.getTransferTime().getCalcTime().valueAsMinutes();
				this.calcTransTime2 = frame2.getTransferTime() == null || frame2.getTransferTime().getCalcTime() == null ? 0 : frame2.getTransferTime().getCalcTime().valueAsMinutes();
				this.calcTransTime3 = frame3.getTransferTime() == null || frame3.getTransferTime().getCalcTime() == null ? 0 : frame3.getTransferTime().getCalcTime().valueAsMinutes();
				this.calcTransTime4 = frame4.getTransferTime() == null || frame4.getTransferTime().getCalcTime() == null ? 0 : frame4.getTransferTime().getCalcTime().valueAsMinutes();
				this.calcTransTime5 = frame5.getTransferTime() == null || frame5.getTransferTime().getCalcTime() == null ? 0 : frame5.getTransferTime().getCalcTime().valueAsMinutes();
				this.calcTransTime6 = frame6.getTransferTime() == null || frame6.getTransferTime().getCalcTime() == null ? 0 : frame6.getTransferTime().getCalcTime().valueAsMinutes();
				this.calcTransTime7 = frame7.getTransferTime() == null || frame7.getTransferTime().getCalcTime() == null ? 0 : frame7.getTransferTime().getCalcTime().valueAsMinutes();
				this.calcTransTime8 = frame8.getTransferTime() == null || frame8.getTransferTime().getCalcTime() == null ? 0 : frame8.getTransferTime().getCalcTime().valueAsMinutes();
				this.calcTransTime9 = frame9.getTransferTime() == null || frame9.getTransferTime().getCalcTime() == null ? 0 : frame9.getTransferTime().getCalcTime().valueAsMinutes();
				this.calcTransTime10= frame10.getTransferTime() == null || frame10.getTransferTime().getCalcTime() == null ? 0 : frame10.getTransferTime().getCalcTime().valueAsMinutes();
				//事前残業申請
				this.preOverTimeAppTime1 = frame1.getBeforeApplicationTime() == null ? 0 : frame1.getBeforeApplicationTime().valueAsMinutes();
				this.preOverTimeAppTime2 = frame2.getBeforeApplicationTime() == null ? 0 : frame2.getBeforeApplicationTime().valueAsMinutes();
				this.preOverTimeAppTime3 = frame3.getBeforeApplicationTime() == null ? 0 : frame3.getBeforeApplicationTime().valueAsMinutes();
				this.preOverTimeAppTime4 = frame4.getBeforeApplicationTime() == null ? 0 : frame4.getBeforeApplicationTime().valueAsMinutes();
				this.preOverTimeAppTime5 = frame5.getBeforeApplicationTime() == null ? 0 : frame5.getBeforeApplicationTime().valueAsMinutes();
				this.preOverTimeAppTime6 = frame6.getBeforeApplicationTime() == null ? 0 : frame6.getBeforeApplicationTime().valueAsMinutes();
				this.preOverTimeAppTime7 = frame7.getBeforeApplicationTime() == null ? 0 : frame7.getBeforeApplicationTime().valueAsMinutes();
				this.preOverTimeAppTime8 = frame8.getBeforeApplicationTime() == null ? 0 : frame8.getBeforeApplicationTime().valueAsMinutes();
				this.preOverTimeAppTime9 = frame9.getBeforeApplicationTime() == null ? 0 : frame9.getBeforeApplicationTime().valueAsMinutes();
				this.preOverTimeAppTime10 = frame10.getBeforeApplicationTime() == null ? 0 : frame10.getBeforeApplicationTime().valueAsMinutes();
				//残業乖離時間
				this.divergenceTime1  = frame1.getOverTimeWork() == null || frame1.getOverTimeWork().getTime() == null ? 0 : frame1.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				this.divergenceTime2  = frame2.getOverTimeWork() == null || frame2.getOverTimeWork().getTime() == null ? 0 : frame2.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				this.divergenceTime3  = frame3.getOverTimeWork() == null || frame3.getOverTimeWork().getTime() == null ? 0 : frame3.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				this.divergenceTime4  = frame4.getOverTimeWork() == null || frame4.getOverTimeWork().getTime() == null ? 0 : frame4.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				this.divergenceTime5  = frame5.getOverTimeWork() == null || frame5.getOverTimeWork().getTime() == null ? 0 : frame5.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				this.divergenceTime6  = frame6.getOverTimeWork() == null || frame6.getOverTimeWork().getTime() == null ? 0 : frame6.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				this.divergenceTime7  = frame7.getOverTimeWork() == null || frame7.getOverTimeWork().getTime() == null ? 0 : frame7.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				this.divergenceTime8  = frame8.getOverTimeWork() == null || frame8.getOverTimeWork().getTime() == null ? 0 : frame8.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				this.divergenceTime9  = frame9.getOverTimeWork() == null || frame9.getOverTimeWork().getTime() == null ? 0 : frame9.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				this.divergenceTime10 = frame10.getOverTimeWork() == null || frame10.getOverTimeWork().getTime() == null ? 0 : frame10.getOverTimeWork().getDivergenceTime().valueAsMinutes();
				//振替乖離時間
				this.divergenceTransTime1 = frame1.getTransferTime() == null || frame1.getTransferTime().getTime() == null ? 0 : frame1.getTransferTime().getDivergenceTime().valueAsMinutes();
				this.divergenceTransTime2 = frame2.getTransferTime() == null || frame2.getTransferTime().getTime() == null ? 0 : frame2.getTransferTime().getDivergenceTime().valueAsMinutes();
				this.divergenceTransTime3 = frame3.getTransferTime() == null || frame3.getTransferTime().getTime() == null ? 0 : frame3.getTransferTime().getDivergenceTime().valueAsMinutes();
				this.divergenceTransTime4 = frame4.getTransferTime() == null || frame4.getTransferTime().getTime() == null ? 0 : frame4.getTransferTime().getDivergenceTime().valueAsMinutes();
				this.divergenceTransTime5 = frame5.getTransferTime() == null || frame5.getTransferTime().getTime() == null ? 0 : frame5.getTransferTime().getDivergenceTime().valueAsMinutes();
				this.divergenceTransTime6 = frame6.getTransferTime() == null || frame6.getTransferTime().getTime() == null ? 0 : frame6.getTransferTime().getDivergenceTime().valueAsMinutes();
				this.divergenceTransTime7 = frame7.getTransferTime() == null || frame7.getTransferTime().getTime() == null ? 0 : frame7.getTransferTime().getDivergenceTime().valueAsMinutes();
				this.divergenceTransTime8 = frame8.getTransferTime() == null || frame8.getTransferTime().getTime() == null ? 0 : frame8.getTransferTime().getDivergenceTime().valueAsMinutes();
				this.divergenceTransTime9 = frame9.getTransferTime() == null || frame9.getTransferTime().getTime() == null ? 0 : frame9.getTransferTime().getDivergenceTime().valueAsMinutes();
				this.divergenceTransTime10= frame10.getTransferTime() == null || frame10.getTransferTime().getTime() == null ? 0 : frame10.getTransferTime().getDivergenceTime().valueAsMinutes();
			}
			//深夜時間
			if(overTimeOfDaily.getExcessOverTimeWorkMidNightTime() != null
			   && overTimeOfDaily.getExcessOverTimeWorkMidNightTime().isPresent()) {
				
				Finally<ExcessOverTimeWorkMidNightTime> excessOver = overTimeOfDaily.getExcessOverTimeWorkMidNightTime();
				//法定外
				this.ileglMidntOverTime = excessOver.get().getTime().getTime() == null ? 0 : excessOver.get().getTime().getTime().valueAsMinutes();
				//計算法定外
				this.calcIleglMidNOverTime = excessOver.get().getTime().getCalcTime() == null ? 0 : excessOver.get().getTime().getCalcTime().valueAsMinutes();
				//法定外残業深夜乖離時間
				this.divIleglMidnOverTime = excessOver.get().getTime().getDivergenceTime() == null ? 0 : excessOver.get().getTime().getDivergenceTime().valueAsMinutes();
			}
				
			//拘束時間
			this.overTimeBindTime = overTimeOfDaily.getOverTimeWorkSpentAtWork() == null ? 0 : overTimeOfDaily.getOverTimeWorkSpentAtWork().valueAsMinutes();
			//変形法定内残業
			this.deformLeglOverTime = overTimeOfDaily.getIrregularWithinPrescribedOverTimeWork() == null ? 0 : overTimeOfDaily.getIrregularWithinPrescribedOverTimeWork().valueAsMinutes();
			
			if(overTimeOfDaily.getFlexTime() != null) {
				//フレックス時間
				this.flexTime = overTimeOfDaily.getFlexTime().getFlexTime().getTime().valueAsMinutes();
				//計算フレックス時間
				this.calcFlexTime = overTimeOfDaily.getFlexTime().getFlexTime().getCalcTime().valueAsMinutes();
				//事前フレックス時間
				this.preAppFlexTime = overTimeOfDaily.getFlexTime().getBeforeApplicationTime() == null ? 0 :overTimeOfDaily.getFlexTime().getBeforeApplicationTime().valueAsMinutes();
				//フレックス乖離時間
				this.divergenceFlexTime = overTimeOfDaily.getFlexTime().getFlexTime().getDivergenceTime().valueAsMinutes();
			}
		}
	}

	private OverTimeFrameTime getOverTimeFrame(List<OverTimeFrameTime> overTimeFrame, int frameNo) {
		val getFrame = overTimeFrame.stream().filter(tc -> tc.getOverWorkFrameNo().v() == frameNo).findFirst();
		if(getFrame.isPresent()) {
			return getFrame.get();
		}
		else {
			return new OverTimeFrameTime(new OverTimeFrameNo(frameNo),
										 TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
										 TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
										 new AttendanceTime(0),
										 new AttendanceTime(0));
		}
			
	}

	public OverTimeOfDaily toDomain() {
		List<OverTimeFrameTime> list = new ArrayList<>();
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(1),
									   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime1), new AttendanceTime(this.calcOverTime1)),
									   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime1), new AttendanceTime(this.calcTransTime1)),
									   new AttendanceTime(this.preOverTimeAppTime1),
									   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(2),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime2), new AttendanceTime(this.calcOverTime2)),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime2), new AttendanceTime(this.calcTransTime2)),
				   new AttendanceTime(this.preOverTimeAppTime2),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(3),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime3), new AttendanceTime(this.calcOverTime3)),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime3), new AttendanceTime(this.calcTransTime3)),
				   new AttendanceTime(this.preOverTimeAppTime3),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(4),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime4), new AttendanceTime(this.calcOverTime4)),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime4), new AttendanceTime(this.calcTransTime4)),
				   new AttendanceTime(this.preOverTimeAppTime4),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(5),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime5), new AttendanceTime(this.calcOverTime5)),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime5), new AttendanceTime(this.calcTransTime5)),
				   new AttendanceTime(this.preOverTimeAppTime5),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(6),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime6), new AttendanceTime(this.calcOverTime6)),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime6), new AttendanceTime(this.calcTransTime6)),
				   new AttendanceTime(this.preOverTimeAppTime6),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(7),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime7), new AttendanceTime(this.calcOverTime7)),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime7), new AttendanceTime(this.calcTransTime7)),
				   new AttendanceTime(this.preOverTimeAppTime7),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(8),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime8), new AttendanceTime(this.calcOverTime8)),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime8), new AttendanceTime(this.calcTransTime8)),
				   new AttendanceTime(this.preOverTimeAppTime8),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(9),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime9), new AttendanceTime(this.calcOverTime9)),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime9), new AttendanceTime(this.calcTransTime9)),
				   new AttendanceTime(this.preOverTimeAppTime9),
				   new AttendanceTime(0)));
		
		list.add(new OverTimeFrameTime(new OverTimeFrameNo(10),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.overTime10), new AttendanceTime(this.calcOverTime10)),
				   TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.transTime10), new AttendanceTime(this.calcTransTime10)),
				   new AttendanceTime(this.preOverTimeAppTime10),
				   new AttendanceTime(0)));
		
		return new OverTimeOfDaily(new ArrayList<>(), 
								   list,
								   Finally.of(new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(this.ileglMidntOverTime),new AttendanceTime(this.calcIleglMidNOverTime)))),
								   new AttendanceTime(this.deformLeglOverTime),
								   new FlexTime(TimeDivergenceWithCalculationMinusExist.createTimeWithCalculation(new AttendanceTimeOfExistMinus(this.flexTime), new AttendanceTimeOfExistMinus(this.calcFlexTime)),new AttendanceTime(this.preAppFlexTime)),
								   new AttendanceTime(this.overTimeBindTime)
								   ); 
		
	}
}
