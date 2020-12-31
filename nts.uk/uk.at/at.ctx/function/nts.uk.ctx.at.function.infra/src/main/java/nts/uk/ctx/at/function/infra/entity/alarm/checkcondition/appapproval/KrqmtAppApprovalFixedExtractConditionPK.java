package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.appapproval;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class KrqmtAppApprovalFixedExtractConditionPK {

	@Column(name = "ERRALARM_APLIAPRO_ID")
	private String erAlId;
	
	@Column(name = "NO")
	private int no;
}
