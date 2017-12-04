package nts.uk.ctx.at.record.infra.entity.daily.overtimework;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_OVERTIMEWORK_TS")
public class KrcdtDayOvertimeworkTs extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayOvertimeworkTsPK krcdtDayOvertimeworkTsPK;
	
	/*残業1開始時刻*/
	@Column(name = "OVER_TIME_1_STR_CLC")
	public int overTime1StrClc;
	/*残業1終了時刻*/
	@Column(name = "OVER_TIME_1_END_CLC")
	public int overTime1EndClc;
	/*残業2開始時刻*/
	@Column(name = "OVER_TIME_2_STR_CLC")
	public int overTime2StrClc;
	/*残業2終了時刻*/
	@Column(name = "OVER_TIME_2_END_CLC")
	public int overTime2EndClc;
	/*残業3開始時刻*/
	@Column(name = "OVER_TIME_3_STR_CLC")
	public int overTime3StrClc;
	/*残業3終了時刻*/
	@Column(name = "OVER_TIME_3_END_CLC")
	public int overTime3EndClc;
	/*残業4開始時刻*/
	@Column(name = "OVER_TIME_4_STR_CLC")
	public int overTime4StrClc;
	/*残業4終了時刻*/
	@Column(name = "OVER_TIME_4_END_CLC")
	public int overTime4EndClc;
	/*残業5開始時刻*/
	@Column(name = "OVER_TIME_5_STR_CLC")
	public int overTime5StrClc;
	/*残業5終了時刻*/
	@Column(name = "OVER_TIME_5_END_CLC")
	public int overTime5EndClc;
	/*残業6開始時刻*/
	@Column(name = "OVER_TIME_6_STR_CLC")
	public int overTime6StrClc;
	/*残業6終了時刻*/
	@Column(name = "OVER_TIME_6_END_CLC")
	public int overTime6EndClc;
	/*残業7開始時刻*/
	@Column(name = "OVER_TIME_7_STR_CLC")
	public int overTime7StrClc;
	/*残業7終了時刻*/
	@Column(name = "OVER_TIME_7_END_CLC")
	public int overTime7EndClc;
	/*残業8開始時刻*/
	@Column(name = "OVER_TIME_8_STR_CLC")
	public int overTime8StrClc;
	/*残業8終了時刻*/
	@Column(name = "OVER_TIME_8_END_CLC")
	public int overTime8EndClc;
	/*残業9開始時刻*/
	@Column(name = "OVER_TIME_9_STR_CLC")
	public int overTime9StrClc;
	/*残業9終了時刻*/
	@Column(name = "OVER_TIME_9_END_CLC")
	public int overTime9EndClc;
	/*残業10開始時刻*/
	@Column(name = "OVER_TIME_10_STR_CLC")
	public int overTime10StrClc;
	/*残業10終了時刻*/
	@Column(name = "OVER_TIME_10_END_CLC")
	public int overTime10EndClc;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayOvertimeworkTsPK;
	}
	
	/**
	 * create entity
	 * @param employeeId
	 * @param generalDate
	 * @return
	 */
	public static KrcdtDayOvertimeworkTs create(String employeeId, GeneralDate generalDate, OverTimeOfDaily overTimeOfDaily) {
		val entity = new  KrcdtDayOvertimeworkTs();
		entity.krcdtDayOvertimeworkTsPK = new KrcdtDayOvertimeworkTsPK(employeeId,generalDate);
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 0) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(0).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(0).getCalcrange().endValue();
		}
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 1) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(1).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(1).getCalcrange().endValue();
		}
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 2) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(2).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(2).getCalcrange().endValue();
		}
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 3) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(3).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(3).getCalcrange().endValue();
		}
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 4) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(4).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(4).getCalcrange().endValue();
		}
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 5) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(5).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(5).getCalcrange().endValue();
		}
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 6) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(6).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(6).getCalcrange().endValue();
		}
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 7) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(7).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(7).getCalcrange().endValue();
		}
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 8) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(8).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(8).getCalcrange().endValue();
		}
		if(overTimeOfDaily.getOverTimeWorkFrameTimeSheet().size() > 9) {
			entity.overTime1StrClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(9).getCalcrange().startValue();
			entity.overTime1EndClc = overTimeOfDaily.getOverTimeWorkFrameTimeSheet().get(9).getCalcrange().endValue();
		}
		return entity;
	}
}
