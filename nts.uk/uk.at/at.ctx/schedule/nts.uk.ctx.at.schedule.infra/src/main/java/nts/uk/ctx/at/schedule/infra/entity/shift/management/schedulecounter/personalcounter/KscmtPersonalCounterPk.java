package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.personalcounter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPersonalCounterPk {

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	@Column(name = "PERSONAL_CATEGORY")
	public int useCategories;

}
