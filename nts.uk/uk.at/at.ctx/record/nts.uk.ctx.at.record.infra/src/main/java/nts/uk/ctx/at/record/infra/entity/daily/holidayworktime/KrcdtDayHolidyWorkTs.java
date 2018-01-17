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
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_DAY_HOLIDYWORK_TS")
public class KrcdtDayHolidyWorkTs extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrcdtDayHolidyWorkTsPK krcdtDayHolidyWorkTsPK;
	/*休日出勤1開始時刻*/
	@Column(name = "HOLI_WORK_1_STR_CLC")
	public int holiWork1StrClc;
	/*休日出勤1終了時刻*/
	@Column(name = "HOLI_WORK_1_END_CLC")
	public int holiWork1EndClc;
	/*休日出勤2開始時刻*/
	@Column(name = "HOLI_WORK_2_STR_CLC")
	public int holiWork2StrClc;
	/*休日出勤2終了時刻*/
	@Column(name = "HOLI_WORK_2_END_CLC")
	public int holiWork2EndClc;
	/*休日出勤3開始時刻*/
	@Column(name = "HOLI_WORK_3_STR_CLC")
	public int holiWork3StrClc;
	/*休日出勤3終了時刻*/
	@Column(name = "HOLI_WORK_3_END_CLC")
	public int holiWork3EndClc;
	/*休日出勤4開始時刻*/
	@Column(name = "HOLI_WORK_4_STR_CLC")
	public int holiWork4StrClc;
	/*休日出勤4終了時刻*/
	@Column(name = "HOLI_WORK_4_END_CLC")
	public int holiWork4EndClc;
	/*休日出勤5開始時刻*/
	@Column(name = "HOLI_WORK_5_STR_CLC")
	public int holiWork5StrClc;
	/*休日出勤5終了時刻*/
	@Column(name = "HOLI_WORK_5_END_CLC")
	public int holiWork5EndClc;
	/*休日出勤6開始時刻*/
	@Column(name = "HOLI_WORK_6_STR_CLC")
	public int holiWork6StrClc;
	/*休日出勤6終了時刻*/
	@Column(name = "HOLI_WORK_6_END_CLC")
	public int holiWork6EndClc;
	/*休日出勤7開始時刻*/
	@Column(name = "HOLI_WORK_7_STR_CLC")
	public int holiWork7StrClc;
	/*休日出勤7終了時刻*/
	@Column(name = "HOLI_WORK_7_END_CLC")
	public int holiWork7EndClc;
	/*休日出勤8開始時刻*/
	@Column(name = "HOLI_WORK_8_STR_CLC")
	public int holiWork8StrClc;
	/*休日出勤8終了時刻*/
	@Column(name = "HOLI_WORK_8_END_CLC")
	public int holiWork8EndClc;
	/*休日出勤9開始時刻*/
	@Column(name = "HOLI_WORK_9_STR_CLC")
	public int holiWork9StrClc;
	/*休日出勤9終了時刻*/
	@Column(name = "HOLI_WORK_9_END_CLC")
	public int holiWork9EndClc;
	/*休日出勤10開始時刻*/
	@Column(name = "HOLI_WORK_10_STR_CLC")
	public int holiWork10StrClc;
	/*休日出勤10終了時刻*/
	@Column(name = "HOLI_WORK_10_END_CLC")
	public int holiWork10EndClc;
	
	@OneToOne(mappedBy="krcdtDayHolidyWorkTs")
	public KrcdtDayAttendanceTime krcdtDayAttendanceTime;
	
	@Override
	protected Object getKey() {
		return this.krcdtDayHolidyWorkTsPK;
	}

	public static KrcdtDayHolidyWorkTs create(String employeeId, GeneralDate date, HolidayWorkTimeOfDaily domain) {
		val entity = new KrcdtDayHolidyWorkTs();
		/*主キー*/
		entity.krcdtDayHolidyWorkTsPK = new KrcdtDayHolidyWorkTsPK(employeeId,date);
		
		entity.setData(domain);
		return entity;
	}

	public void setData(HolidayWorkTimeOfDaily domain) {
		TimeSpanForCalc sheet1 = getTimeSheet(domain, 1);
		TimeSpanForCalc sheet2 = getTimeSheet(domain, 2);
		TimeSpanForCalc sheet3 = getTimeSheet(domain, 3);
		TimeSpanForCalc sheet4 = getTimeSheet(domain, 4);
		TimeSpanForCalc sheet5 = getTimeSheet(domain, 5);
		TimeSpanForCalc sheet6 = getTimeSheet(domain, 6);
		TimeSpanForCalc sheet7 = getTimeSheet(domain, 7);
		TimeSpanForCalc sheet8 = getTimeSheet(domain, 8);
		TimeSpanForCalc sheet9 = getTimeSheet(domain, 9);
		TimeSpanForCalc sheet10 = getTimeSheet(domain, 10);
		/*休日出勤1開始時刻*/
		this.holiWork1StrClc = sheet1.getStart().valueAsMinutes();
		/*休日出勤1終了時刻*/
		this.holiWork1EndClc = sheet1.getEnd().valueAsMinutes();
		/*休日出勤2開始時刻*/
		this.holiWork2StrClc = sheet2.getStart().valueAsMinutes();
		/*休日出勤2終了時刻*/
		this.holiWork2EndClc = sheet2.getEnd().valueAsMinutes();
		/*休日出勤3開始時刻*/
		this.holiWork3StrClc = sheet3.getStart().valueAsMinutes();
		/*休日出勤3終了時刻*/
		this.holiWork3EndClc = sheet3.getEnd().valueAsMinutes();
		/*休日出勤4開始時刻*/
		this.holiWork4StrClc = sheet4.getStart().valueAsMinutes();
		/*休日出勤4終了時刻*/
		this.holiWork4EndClc = sheet4.getEnd().valueAsMinutes();
		/*休日出勤5開始時刻*/
		this.holiWork5StrClc = sheet5.getStart().valueAsMinutes();
		/*休日出勤5終了時刻*/
		this.holiWork5EndClc = sheet5.getEnd().valueAsMinutes();
		/*休日出勤6開始時刻*/
		this.holiWork6StrClc = sheet6.getStart().valueAsMinutes();
		/*休日出勤6終了時刻*/
		this.holiWork6EndClc = sheet6.getEnd().valueAsMinutes();
		/*休日出勤7開始時刻*/
		this.holiWork7StrClc = sheet7.getStart().valueAsMinutes();
		/*休日出勤7終了時刻*/
		this.holiWork7EndClc = sheet7.getEnd().valueAsMinutes();
		/*休日出勤8開始時刻*/
		this.holiWork8StrClc = sheet8.getStart().valueAsMinutes();
		/*休日出勤8終了時刻*/
		this.holiWork8EndClc = sheet8.getEnd().valueAsMinutes();
		/*休日出勤9開始時刻*/
		this.holiWork9StrClc = sheet9.getStart().valueAsMinutes();
		/*休日出勤9終了時刻*/
		this.holiWork9EndClc = sheet9.getEnd().valueAsMinutes();
		/*休日出勤10開始時刻*/
		this.holiWork10StrClc = sheet10.getStart().valueAsMinutes();
		/*休日出勤10終了時刻*/
		this.holiWork10EndClc = sheet10.getEnd().valueAsMinutes();
	}

	private TimeSpanForCalc getTimeSheet(HolidayWorkTimeOfDaily domain, int sheetNo) {
		return domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == sheetNo).findFirst().get().getTimeSheet();
	}
	
	
	public List<HolidayWorkFrameTimeSheet> toDomain(){
		List<HolidayWorkFrameTimeSheet> list = new ArrayList<>();
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(1)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(2)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(3)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(4)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(5)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(6)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(7)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(8)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(9)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		list.add(new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(Integer.valueOf(10)),new TimeSpanForCalc(new TimeWithDayAttr(this.holiWork1StrClc),new TimeWithDayAttr(this.holiWork1EndClc))));
		return list;
	}
}