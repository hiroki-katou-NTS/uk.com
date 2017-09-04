package nts.uk.ctx.at.shared.infra.entity.worktype.language;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkTypeLanguagePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyId;
	/*勤務種類コード*/
	@Column(name = "WORKTYPE_CD")
	public String workTypeCode;
	/*言語ID*/
	@Column(name = "LANG_ID")
	public String langId;

}
