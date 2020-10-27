package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtHdspEventPK implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/* 会社ID */
	@Column(name = "CID")
	public String companyId;

	/* 特別休暇枠NO */
	@Column(name = "S_HOLIDAY_EVENT_NO")
	public int specialHolidayEventNo;

}
