package nts.uk.ctx.at.function.infra.entity.alarmworkplace.singlemonth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmlstrole.KfnmtALstWkpPmsPk;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace.KfnmtALstWkpPtn;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class KfnmtAssignNumofMonPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyID;

	@Column(name = "ALARM_PATTERN_CD")
	public String patternCD;

	@Column(name = "CATEGORY")
	public int category;

}
