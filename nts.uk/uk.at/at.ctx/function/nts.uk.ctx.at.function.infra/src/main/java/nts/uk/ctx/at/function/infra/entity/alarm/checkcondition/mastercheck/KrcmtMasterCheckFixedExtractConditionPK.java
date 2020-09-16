package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.mastercheck;

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
public class KrcmtMasterCheckFixedExtractConditionPK {

	@Column(name = "ALARM_CHK_ID")
	private String erAlId;
	
	@Column(name = "NO")
	private int no;
}
