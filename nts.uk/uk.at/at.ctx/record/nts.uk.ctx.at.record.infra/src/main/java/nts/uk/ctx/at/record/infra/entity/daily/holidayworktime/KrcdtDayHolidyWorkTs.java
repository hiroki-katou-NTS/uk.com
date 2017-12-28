package nts.uk.ctx.at.record.infra.entity.daily.holidayworktime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
		/*休日出勤1開始時刻*/
		entity.holiWork1StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 1).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤1終了時刻*/
		entity.holiWork1EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 1).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤2開始時刻*/
		entity.holiWork2StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 2).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤2終了時刻*/
		entity.holiWork2EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 2).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤3開始時刻*/
		entity.holiWork3StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 3).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤3終了時刻*/
		entity.holiWork3EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 3).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤4開始時刻*/
		entity.holiWork4StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 4).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤4終了時刻*/
		entity.holiWork4EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 4).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤5開始時刻*/
		entity.holiWork5StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 5).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤5終了時刻*/
		entity.holiWork5EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 5).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤6開始時刻*/
		entity.holiWork6StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 6).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤6終了時刻*/
		entity.holiWork6EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 6).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤7開始時刻*/
		entity.holiWork7StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 7).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤7終了時刻*/
		entity.holiWork7EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 7).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤8開始時刻*/
		entity.holiWork8StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 8).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤8終了時刻*/
		entity.holiWork8EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 8).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤9開始時刻*/
		entity.holiWork9StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 9).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤9終了時刻*/
		entity.holiWork9EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 9).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤10開始時刻*/
		entity.holiWork10StrClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 10).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getStart()).min(Comparator.naturalOrder()).get().valueAsMinutes();
		/*休日出勤10終了時刻*/
		entity.holiWork10EndClc = domain.getHolidayWorkFrameTimeSheet().stream().filter(tc -> tc.getHolidayWorkTimeSheetNo().v() == 10).collect(Collectors.toList())
								.stream().map(s -> s.getTimeSheet().getEnd()).max(Comparator.naturalOrder()).get().valueAsMinutes();
		return entity;
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