package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.timenumber;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtTimeNumberPk {

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	@Column(name = "TYPE")
	public int type;

	@Column(name = "NO")
	public Integer no;
}
