package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.wkptimezonepeoplenumber;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtTallyByWkpEveryTimeZonePk {

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	@Column(name = "START_CLOCK")
	public int startClock;

}
