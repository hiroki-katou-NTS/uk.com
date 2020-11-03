package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.workplacecounter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtWkpCounterPk {

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** ページ */
	@Column(name = "WKP_CATEGORY")
	public int useCategories;

}
