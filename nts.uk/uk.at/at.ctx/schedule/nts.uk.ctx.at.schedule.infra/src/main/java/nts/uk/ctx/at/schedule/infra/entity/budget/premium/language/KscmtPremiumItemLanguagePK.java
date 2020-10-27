package nts.uk.ctx.at.schedule.infra.entity.budget.premium.language;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author tutk
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KscmtPremiumItemLanguagePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="CID")
	public String companyID;
	
	@Column(name="PREMIUM_NO")
	public Integer displayNumber;
	
	@Column(name="LANG_ID")
	public String langID;
}
