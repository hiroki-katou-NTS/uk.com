/**
 * 
 */
package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * @author laitv
 * 労働条件ー個人勤務日区分別
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "KSHMT_WORKCOND_WORKINFO")
public class KshmtWorkcondWorkInfo  extends ContractCompanyUkJpaEntity implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshmtWorkcondWorkInfoPK pk;
	
	@Column(name = "WORKING_DAY_WORKTYPE")
	private String workingDayWorktype;
	
	@Column(name = "HOLIDAY_WORK_WORKTYPE")
	private String holidayWorkWorktype;
	
	@Column(name = "HOLIDAY_WORKTYPE")
	private String holidayWorktype;
	
	@Column(name = "LEGAL_HOLIDAY_WORK_WORKTYPE")
	private String legalHolidayWorkWorktype;
	
	@Column(name = "ILLEGAL_HOLIDAY_WORK_WORKTYPE")
	private String iLegalHolidayWorkWorktype;
	
	@Column(name = "PUBLIC_HOLIDAY_WORK_WORKTYPE")
	private String publicHolidayWorkWorktype;
	
	@Column(name = "WEEKDAYS_WORKTIME")
	private String weekdaysWorktime;
	
	@Column(name = "HOLIDAY_WORK_WORKTIME")
	private String holidayWorkWorktime;
	
	@Column(name = "MONDAY_WORKTIME")
	private String mondayWorkTime;
	
	@Column(name = "TUESDAY_WORKTIME")
	private String tuesdayWorkTime;
	
	@Column(name = "WEDNESDAY_WORKTIME")
	private String wednesdayWorkTime;
	
	@Column(name = "THURSDAY_WORKTIME")
	private String thursdayWorkTime;
	
	@Column(name = "FRIDAY_WORKTIME")
	private String fridayWorkTime;
	
	@Column(name = "SATURDAY_WORKTIME")
	private String saturdayWorkTime;
	
	@Column(name = "SUNDAY_WORKTIME")
	private String sundayWorkTime;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public KshmtWorkcondWorkInfo(
	 KshmtWorkcondWorkInfoPK pk,
	 String workingDayWorktype,
	 String holidayWorkWorktype,
	 String holidayWorktype,
	 String legalHolidayWorkWorktype,
	 String iLegalHolidayWorkWorktype,
	 String publicHolidayWorkWorktype,
	 String weekdaysWorktime,
	 String holidayWorkWorktime,
	 String mondayWorkTime,
	 String tuesdayWorkTime,
	 String wednesdayWorkTime,
	 String thursdayWorkTime,
	 String fridayWorkTime,
	 String saturdayWorkTime,
	 String sundayWorkTime) {
		super();
		this.pk = pk;
		this.workingDayWorktype = workingDayWorktype;
		this.holidayWorkWorktype = holidayWorkWorktype;
		this.holidayWorktype = holidayWorktype;
		this.legalHolidayWorkWorktype = legalHolidayWorkWorktype;
		this.iLegalHolidayWorkWorktype = iLegalHolidayWorkWorktype;
		this.publicHolidayWorkWorktype = publicHolidayWorkWorktype;
		this.weekdaysWorktime = weekdaysWorktime;
		this.holidayWorkWorktime = holidayWorkWorktime;
		this.mondayWorkTime = mondayWorkTime;
		this.tuesdayWorkTime = tuesdayWorkTime;
		this.wednesdayWorkTime = wednesdayWorkTime;
		this.thursdayWorkTime = thursdayWorkTime;
		this.fridayWorkTime = fridayWorkTime;
		this.saturdayWorkTime = saturdayWorkTime;
		this.sundayWorkTime = sundayWorkTime;
	}
	
}
