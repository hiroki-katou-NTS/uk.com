package nts.uk.ctx.at.record.infra.entity.workrecord.actualsituation.createapproval.dailyperformance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KrcmtAppDataInfoDailyPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SID")
	public String employeeId;
	
	@Column(name = "EXECUTION_ID")
	public String executionId;
}
