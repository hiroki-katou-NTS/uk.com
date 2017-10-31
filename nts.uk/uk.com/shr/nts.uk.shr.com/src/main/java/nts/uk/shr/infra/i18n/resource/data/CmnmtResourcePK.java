package nts.uk.shr.infra.i18n.resource.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class CmnmtResourcePK {
	@Column(name = "CID")
	private String companyID;
	@Column(name = "CODE")
	private String code;
	@Column(name = "LANGUAGE_CODE")
	private String languageCode;
}
