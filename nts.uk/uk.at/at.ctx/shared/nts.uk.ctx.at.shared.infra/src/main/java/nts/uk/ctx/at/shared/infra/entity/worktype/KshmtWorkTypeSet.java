package nts.uk.ctx.at.shared.infra.entity.worktype;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KSHMT_WORKTYPE_SET")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkTypeSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshmtWorkTypeSetPK kshmtWorkTypeSetPK;
	
	@Column(name = "DIGEST_PUBLIC_HD")
	public int digestPublicHd;
	
	@Column(name = "HD_ATR")
	public int hodidayAtr;
	
	@Column(name = "COUNT_HD")
	public int countHoliday;
	
	@Column(name = "CLOSE_ATR")
	public Integer closeAtr;
	
	@Column(name = "SUM_ABSENSE_NO")
	public int sumAbsenseNo;
	
	@Column(name = "SUM_SPHD_NO")
	public int sumSpHolidayNo;
	
	@Column(name = "TIME_LEAVE_WORK")
	public int timeLeaveWork;
	
	@Column(name = "ATTENDANCE_TIME")
	public int attendanceTime;
	
	@Column(name = "GEN_SUB_HD")
	public int genSubHoliday;
	
	@Column(name = "DAY_NIGHT_TIME_ASK")
	public int dayNightTimeAsk;
	
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="WORKTYPE_CD", referencedColumnName="CD", insertable = false, updatable = false)
    })
	public KshmtWorkType workType;

	@Override
	protected Object getKey() {		
		return kshmtWorkTypeSetPK;
	}

	public KshmtWorkTypeSet(KshmtWorkTypeSetPK kshmtWorkTypeSetPK, int digestPublicHd, int hodidayAtr, int countHoliday,
			Integer closeAtr, int sumAbsenseNo, int sumSpHolidayNo, int timeLeaveWork, int attendanceTime,
			int genSubHoliday, int dayNightTimeAsk) {
		super();
		this.kshmtWorkTypeSetPK = kshmtWorkTypeSetPK;
		this.digestPublicHd = digestPublicHd;
		this.hodidayAtr = hodidayAtr;
		this.countHoliday = countHoliday;
		this.closeAtr = closeAtr;
		this.sumAbsenseNo = sumAbsenseNo;
		this.sumSpHolidayNo = sumSpHolidayNo;
		this.timeLeaveWork = timeLeaveWork;
		this.attendanceTime = attendanceTime;
		this.genSubHoliday = genSubHoliday;
		this.dayNightTimeAsk = dayNightTimeAsk;
	}

}
