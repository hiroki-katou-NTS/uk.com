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
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTimeSheet;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;
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
	
	@OneToOne(mappedBy="krcdtDayOvertimeworkTs")
	public KrcdtDayAttendanceTime krcdtDayAttendanceTime;
	
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
	public static KrcdtDayOvertimeworkTs create(String employeeId, GeneralDate generalDate, List<OverTimeFrameTimeSheet> overTimeSheet) {
		val entity = new  KrcdtDayOvertimeworkTs();
		entity.krcdtDayOvertimeworkTsPK = new KrcdtDayOvertimeworkTsPK(employeeId,generalDate);
		
		return entity;
	}
	
	public void setData(List<OverTimeFrameTimeSheet> overTimeSheet){
		if(overTimeSheet.size() > 0) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 0).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 0).endValue();
		}
		if(overTimeSheet.size() > 1) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 1).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 1).endValue();
		}
		if(overTimeSheet.size() > 2) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 2).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 2).endValue();
		}
		if(overTimeSheet.size() > 3) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 3).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 3).endValue();
		}
		if(overTimeSheet.size() > 4) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 4).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 4).endValue();
		}
		if(overTimeSheet.size() > 5) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 5).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 5).endValue();
		}
		if(overTimeSheet.size() > 6) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 6).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 6).endValue();
		}
		if(overTimeSheet.size() > 7) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 7).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 7).endValue();
		}
		if(overTimeSheet.size() > 8) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 8).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 8).endValue();
		}
		if(overTimeSheet.size() > 9) {
			this.overTime1StrClc = getTimeSpan(overTimeSheet, 9).startValue();
			this.overTime1EndClc = getTimeSpan(overTimeSheet, 9).endValue();
		}
	}

	private TimeSpanForCalc getTimeSpan(List<OverTimeFrameTimeSheet> overTimeSheet, int sheetNo) {
		return overTimeSheet.get(sheetNo).getTimeSpan();
	}
	
	
	public OverTimeOfDaily toDomain() {
		List<OverTimeFrameTimeSheet> timeSheet = new ArrayList<>();
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime1StrClc),new TimeWithDayAttr(this.overTime1EndClc)),new OverTimeFrameNo(1)));
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime2StrClc),new TimeWithDayAttr(this.overTime2EndClc)),new OverTimeFrameNo(2)));
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime3StrClc),new TimeWithDayAttr(this.overTime3EndClc)),new OverTimeFrameNo(3)));
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime4StrClc),new TimeWithDayAttr(this.overTime4EndClc)),new OverTimeFrameNo(4)));
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime5StrClc),new TimeWithDayAttr(this.overTime5EndClc)),new OverTimeFrameNo(5)));
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime6StrClc),new TimeWithDayAttr(this.overTime6EndClc)),new OverTimeFrameNo(6)));
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime7StrClc),new TimeWithDayAttr(this.overTime7EndClc)),new OverTimeFrameNo(7)));
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime8StrClc),new TimeWithDayAttr(this.overTime8EndClc)),new OverTimeFrameNo(8)));
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime9StrClc),new TimeWithDayAttr(this.overTime9EndClc)),new OverTimeFrameNo(9)));
		timeSheet.add(new OverTimeFrameTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(this.overTime10StrClc),new TimeWithDayAttr(this.overTime10EndClc)),new OverTimeFrameNo(10)));
		
		return new OverTimeOfDaily(timeSheet,Collections.emptyList(),Finally.empty());
	}
}
