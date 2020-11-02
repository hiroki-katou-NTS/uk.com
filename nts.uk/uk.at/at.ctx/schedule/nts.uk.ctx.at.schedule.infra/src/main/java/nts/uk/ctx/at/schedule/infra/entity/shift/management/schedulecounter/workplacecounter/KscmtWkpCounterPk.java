package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.workplacecounter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_WKP_COUNTER") //TODO invalid name
public class KscmtWkpCounterPk {

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** ページ */
	@Column(name = "WKP_CATEGORY")
	public int useCategories;

}
