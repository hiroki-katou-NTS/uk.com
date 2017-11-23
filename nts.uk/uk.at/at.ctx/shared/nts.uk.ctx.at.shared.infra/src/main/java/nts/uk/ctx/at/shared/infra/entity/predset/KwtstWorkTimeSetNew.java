package nts.uk.ctx.at.shared.infra.entity.predset;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name="KWTST_WORK_TIME_SET")
public class KwtstWorkTimeSetNew extends UkJpaEntity{
	@EmbeddedId
	public KwtspWorkTimeSetPKNew kwtspWorkTimeSetPK;
	
	@Column(name="RANGE_TIME_DAY")
	public int rangeTimeDay;
	
	@Column(name="ADDITION_SET_ID")
	public String additionSetID;
	
	@Column(name="NIGHT_SHIFT_ATR")
	public int nightShiftAtr;
	
	@OneToMany(targetEntity=KwtdtWorkTimeDayNew.class, fetch=FetchType.LAZY)
	@JoinTable(name="KWTDT_WORK_TIME_DAY")
	public List<KwtdtWorkTimeDayNew> kwtdtWorkTimeDay;
	
	@Column(name="START_DATE_CLOCK")
	public int startDateClock;
	
	@Column(name="PREDETERMINE_ATR")
	public int predetermineAtr;

	@Column(name = "MORNING_END_TIME")
	public int morningEndTime;

	@Column(name = "AFTERNOON_START_TIME")
	public int afternoonStartTime;

	@Override
	protected Object getKey() {
		return kwtspWorkTimeSetPK;
	}
}
