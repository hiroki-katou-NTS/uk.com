package nts.uk.ctx.at.request.infra.entity.valication.history;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KRQMT_VACATION_HISTORY database table.
 * 
 */
@Entity
@Table(name="KRQMT_VACATION_HISTORY")
@Getter
@Setter
public class KrqmtVacationHistory extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrqmtVacationHistoryPK krqmtVacationHistoryPK;

	@Column(name="END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endDate;

	@Column(name="MAX_DAY")
	private Integer maxDay;

	@Column(name="START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate startDate;

	public KrqmtVacationHistory() {
	}

	@Override
	protected Object getKey() {
		return this.krqmtVacationHistoryPK;
	}
}