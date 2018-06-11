package nts.uk.ctx.at.record.infra.entity.daily.holidayworktime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
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

	public static KrcdtDayHolidyWorkTs create(String employeeId, GeneralDate date, List<HolidayWorkFrameTimeSheet> domain) {
		val entity = new KrcdtDayHolidyWorkTs();
		/*主キー*/
		entity.krcdtDayHolidyWorkTsPK = new KrcdtDayHolidyWorkTsPK(employeeId,date);
		
		entity.setData(domain);
		return entity;
	}

	public void setData(List<HolidayWorkFrameTimeSheet> domain) {
		Optional<HolidayWorkFrameTimeSheet> sheet = Optional.empty();
		if(domain == null) return;
		sheet = getTimeSheet(domain, 1);
		sheet.ifPresent(tc -> {
			/*休日出勤1開始時刻*/
			this.holiWork1StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤1終了時刻*/
			this.holiWork1EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
		sheet = getTimeSheet(domain, 2);
		sheet.ifPresent(tc -> {
			/*休日出勤2開始時刻*/
			this.holiWork2StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤2終了時刻*/
			this.holiWork2EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
		sheet = getTimeSheet(domain, 3);
		sheet.ifPresent(tc -> {
			/*休日出勤3開始時刻*/
			this.holiWork3StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤3終了時刻*/
			this.holiWork3EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
		sheet = getTimeSheet(domain, 4);
		sheet.ifPresent(tc -> {
			/*休日出勤4開始時刻*/
			this.holiWork4StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤4終了時刻*/
			this.holiWork4EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
		sheet = getTimeSheet(domain, 5);
		sheet.ifPresent(tc -> {
			/*休日出勤5開始時刻*/
			this.holiWork5StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤5終了時刻*/
			this.holiWork5EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
		sheet = getTimeSheet(domain, 6);
		sheet.ifPresent(tc -> {
			/*休日出勤6開始時刻*/
			this.holiWork6StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤6終了時刻*/
			this.holiWork6EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
		sheet = getTimeSheet(domain, 7);
		sheet.ifPresent(tc -> {
			/*休日出勤7開始時刻*/
			this.holiWork7StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤7終了時刻*/
			this.holiWork7EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
		sheet = getTimeSheet(domain, 8);
		sheet.ifPresent(tc -> {
			/*休日出勤8開始時刻*/
			this.holiWork8StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤8終了時刻*/
			this.holiWork8EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
		sheet = getTimeSheet(domain, 9);
		sheet.ifPresent(tc -> {
			/*休日出勤9開始時刻*/
			this.holiWork9StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤9終了時刻*/
			this.holiWork9EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
		sheet = getTimeSheet(domain, 10);
		sheet.ifPresent(tc -> {
			/*休日出勤10開始時刻*/
			this.holiWork10StrClc = tc.getTimeSheet().getStart().valueAsMinutes();
			/*休日出勤10終了時刻*/
			this.holiWork10EndClc = tc.getTimeSheet().getEnd().valueAsMinutes();
		});
	}

	private Optional<HolidayWorkFrameTimeSheet> getTimeSheet(List<HolidayWorkFrameTimeSheet> domain, int sheetNo) {
		return domain.stream()
					 .filter(tc -> tc.getHolidayWorkTimeSheetNo().v().intValue() == sheetNo)
					 .findFirst();
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