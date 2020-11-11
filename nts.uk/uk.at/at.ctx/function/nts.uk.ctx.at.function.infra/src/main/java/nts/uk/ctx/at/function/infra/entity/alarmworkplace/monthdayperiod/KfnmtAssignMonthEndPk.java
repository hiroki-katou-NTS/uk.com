package nts.uk.ctx.at.function.infra.entity.alarmworkplace.monthdayperiod;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtAssignMonthEndPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyID;

	@Column(name = "PATTERN_CD")
	public String patternCD;

	@Column(name = "CATEGORY")
	public int category;

}
