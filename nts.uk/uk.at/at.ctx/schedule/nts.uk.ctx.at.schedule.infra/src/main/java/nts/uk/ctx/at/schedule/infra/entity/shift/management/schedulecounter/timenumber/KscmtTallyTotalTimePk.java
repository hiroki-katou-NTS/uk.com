package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.timenumber;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtTallyTotalTimePk {

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	@Column(name = "COUNT_TYPE")
	public int countType;

	@Column(name = "TOTAL_TIMES_NO")
	public Integer timeNo;
}
