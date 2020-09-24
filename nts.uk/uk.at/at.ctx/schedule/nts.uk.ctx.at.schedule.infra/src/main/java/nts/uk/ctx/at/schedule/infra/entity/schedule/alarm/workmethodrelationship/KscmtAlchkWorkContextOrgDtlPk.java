package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.workmethodrelationship;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkWorkContextOrgDtlPk {
	
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "TARGET_UNIT")
	public int targetUnit;
	
	@Column(name = "TARGET_ID")
	public String targetId;

	@Column(name = "PREVIOUS_WORK_ATR")
	public String prevWorkMethod;
	
	@Column(name = "PREVIOUS_WKTM_CD")
	public String prevWorkTimeCode;
	
	@Column(name = "TGT_WKTM_CD")
	public String currentWorkTimeCode;
	
}
