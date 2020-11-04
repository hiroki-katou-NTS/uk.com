package nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.wkptimezonepeoplenumber;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtWkpTimeZoneNumberPk {

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

}
