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
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KagmtRptScheduleTallyByWkpPk {

	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "CODE")
	public String code;
	
	@Column(name = "CATEGORY_NO")
	public Integer categoryNo;
}
