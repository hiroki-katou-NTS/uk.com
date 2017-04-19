package nts.uk.shr.infra.i18n.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class CompanyResourcePK {
	@Column(name = "CONTRACT_CODE")
	private String contractCode;
	@Column(name = "COMPANY_CODE")
	private String companyCode;
	@Column(name = "CODE")
	private String code;
}
