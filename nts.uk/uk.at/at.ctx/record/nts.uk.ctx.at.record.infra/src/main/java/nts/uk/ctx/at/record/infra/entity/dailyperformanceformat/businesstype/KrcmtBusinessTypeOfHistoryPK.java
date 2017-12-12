package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.businesstype;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBusinessTypeOfHistoryPK {

	/** 履歴ID */
	@Column(name = "HIST_ID")
	public String historyId;
}
