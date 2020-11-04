package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.wkpcounterlaborcostandtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtWkpLaborCostAndTimePk {

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	@Column(name = "TYPE")
	public int type;
}
