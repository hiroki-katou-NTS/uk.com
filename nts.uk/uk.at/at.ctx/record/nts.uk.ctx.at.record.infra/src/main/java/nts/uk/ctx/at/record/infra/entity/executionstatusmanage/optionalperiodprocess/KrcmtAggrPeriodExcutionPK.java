package nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtAggrPeriodExcutionPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyId;
	//実行社員ID
	@Column(name = "EXECUTION_EMP_ID")
	public String executionEmpId;
	
	//ExcuteID
	@Column(name = "AGGR_ID")
	public String aggrId;

	
}
