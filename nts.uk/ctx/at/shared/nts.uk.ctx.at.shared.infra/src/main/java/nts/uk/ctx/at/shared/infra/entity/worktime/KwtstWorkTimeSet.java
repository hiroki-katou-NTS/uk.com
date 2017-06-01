package nts.uk.ctx.at.shared.infra.entity.worktime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KWTST_WORK_TIME_SET")
public class KwtstWorkTimeSet extends UkJpaEntity{
	@EmbeddedId
	public KwtspWorkTimeSetPK kwtspWorkTimeSetPK;
	
	@Column(name="RANGE_TIME_DAY")
	public int rangeTimeDay;
	
	@Column(name="ADDITION_SET_CD")
	public String additionSetCD;
	
	@Column(name="NIGHT_SHIFT_ATR")
	public int nightShiftAtr;
	
	@Column(name="START_DATE_TIME")
	public int startDateTime;
	
	@Column(name="START_DATE_ATR")
	public int startDateAtr;

	@OneToMany
	@JoinTable(name="KWTDT_WORK_TIME_DAY")
	@JoinColumns(value = {
		@JoinColumn(name="CID",referencedColumnName="CID"),
		@JoinColumn(name="WORK_TIME_SET_CD",referencedColumnName="WORK_TIME_SET_CD")
	})
	public KwtdtWorkTimeDay kwtdtWorkTimeDay;
	
	@Override
	protected Object getKey() {
		return kwtspWorkTimeSetPK;
	}
}
