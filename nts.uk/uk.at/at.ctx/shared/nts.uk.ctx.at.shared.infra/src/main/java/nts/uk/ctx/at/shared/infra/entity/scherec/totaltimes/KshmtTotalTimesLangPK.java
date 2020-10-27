package nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author phongtq
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshmtTotalTimesLangPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	public String companyId;

	/** The total times no. */
	@Column(name = "TOTAL_TIMES_NO")
	public Integer totalTimesNo;
	
	@Column(name = "LANG_ID")
	public String languageId;
	
}
