package nts.uk.ctx.at.shared.infra.entity.worktime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
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
@Table(name="KWTMT_WORK_TIME")
public class KwtmtWorkTime extends UkJpaEntity{
	
	@EmbeddedId
	public KwtmpWorkTimePK kwtmpWorkTimePK;
	
	@Column(name="SORT_BY")
	public int sortBy;

	@Column(name="NAME")
	public String name;

	@Column(name="AB_NAME")
	public String abName;

	@Column(name="SYMBOL")
	public String symbol;

	@Column(name="REMARKS")
	public String remarks;

	@Column(name="DISPLAY_ATR")
	public int displayAtr;

	@Column(name="METHOD_ATR")
	public int methodAtr;

	/*@OneToOne(targetEntity=KwtmtWorkTime.class)
	@JoinTable(name="KWTST_WORK_TIME_SET")
	@JoinColumns(value = {
		@JoinColumn(name="CID",referencedColumnName="CID"),
		@JoinColumn(name="WORK_TIME_CD",referencedColumnName="WORK_TIME_CD")
	})
	public KwtstWorkTimeSet kwtstWorkTimeSet;*/
	
	@Override
	protected Object getKey() {
		return kwtmpWorkTimePK;
	}
}
