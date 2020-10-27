package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtExecCaseentPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "OPERATION_CASE_ID")
	public String caseSpecExeContentID;
	
}
