package nts.uk.ctx.at.aggregation.infra.entity.scheduletable.outputsetting;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author quytb
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KagmtRptSchedulePk {
	
	@Column(name = "CID")
	public String companyId;

	@Column(name = "CODE")
	public String code;
}
