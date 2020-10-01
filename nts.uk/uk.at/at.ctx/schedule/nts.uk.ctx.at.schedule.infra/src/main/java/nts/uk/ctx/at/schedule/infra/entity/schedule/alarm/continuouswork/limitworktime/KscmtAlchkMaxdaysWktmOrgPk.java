package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.limitworktime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkMaxdaysWktmOrgPk {
	
	@Column(name = "CID")
	public String companyId;

	@Column(name = "TARGET_UNIT")
	public int targetUnit;
	
	@Column(name = "TARGET_ID")
	public String targetId;
	
	@Column(name = "CD")
	public String code;
	
}
