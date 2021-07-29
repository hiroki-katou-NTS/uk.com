/**
 * 
 */
package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * @author laitv
 * 労働条件ー個人勤務日区分別所定時間
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "KSHMT_WORKCOND_WORK_TS")
public class KshmtWorkcondWorkTs  extends ContractCompanyUkJpaEntity implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshmtWorkcondWorkTsPK pk;
	
	@Column(name = "START_TIME_1")
	private Integer startTime1;
	
	@Column(name = "END_TIME_1")
	private Integer endTime1;
	
	@Column(name = "START_TIME_2")
	private Integer startTime2;
	
	@Column(name = "END_TIME_2")
	private Integer endTime2;
	
	@JoinColumns({
		@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false)})
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private KshmtWorkcondWorkInfo kshmtWorkcondWorkInfo;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public KshmtWorkcondWorkTs(KshmtWorkcondWorkTsPK pk,
			Integer startTime1, Integer endTime1, Integer startTime2, Integer endTime2) {
		super();
		this.pk = pk;
		this.startTime1 = startTime1;
		this.endTime1 = endTime1;
		this.startTime2 = startTime2;
		this.endTime2 = endTime2;
	}
	
	public SingleDaySchedule toDomain() {
		List<TimeZone> timeZones = new ArrayList<>();
		timeZones.add(new TimeZone(NotUseAtr.USE, 1, this.startTime1, this.endTime1));
		timeZones.add(new TimeZone(NotUseAtr.USE, 2, this.startTime2, this.endTime2));
		String workTimeCode = null;
		switch(this.pk.getPerWorkDayAtr()) {
		case 0:
			workTimeCode = this.kshmtWorkcondWorkInfo.getWeekdaysWorktime();
			break;
		case 1:
			workTimeCode = this.kshmtWorkcondWorkInfo.getHolidayWorkWorktime();
			break;
		case 2:
			workTimeCode = this.kshmtWorkcondWorkInfo.getMondayWorkTime();
			break;
		case 3:
			workTimeCode = this.kshmtWorkcondWorkInfo.getTuesdayWorkTime();
			break;
		case 4:
			workTimeCode = this.kshmtWorkcondWorkInfo.getWednesdayWorkTime();
			break;
		case 5:
			workTimeCode = this.kshmtWorkcondWorkInfo.getThursdayWorkTime();
			break;
		case 6:
			workTimeCode = this.kshmtWorkcondWorkInfo.getFridayWorkTime();
			break;
		case 7:
			workTimeCode = this.kshmtWorkcondWorkInfo.getSaturdayWorkTime();
			break;
		case 8:
			workTimeCode = this.kshmtWorkcondWorkInfo.getSundayWorkTime();
			break;
		default:
			break;
		}
		return new SingleDaySchedule(timeZones, Optional.ofNullable(workTimeCode));
	}
	
	public static KshmtWorkcondWorkTs toEntity(SingleDaySchedule domain,String hisId,String sid,int perWorkDayOffAtr) {
		List<TimeZone> timeZones = domain.getWorkingHours();
		TimeZone timeZone1 = timeZones.stream().filter(i -> i.getCnt() == 1).findFirst().get();
		TimeZone timeZone2 = timeZones.stream().filter(i -> i.getCnt() == 2).findFirst().get();
		return new KshmtWorkcondWorkTs(
				new KshmtWorkcondWorkTsPK(sid, hisId, perWorkDayOffAtr),
				timeZone1.getStart().minute(),
				timeZone1.getEnd().minute(),
				timeZone2.getStart().minute(),
				timeZone2.getEnd().minute());
	}
}
