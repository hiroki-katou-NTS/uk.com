package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationSetMemento;

@Data
public class CertificationDto {
	/** The code. */
	private String code;

	/** The name. */
	private String name;

	public Certification toDomain(String companyCode) {
		CertificationDto dto = this;
		return new Certification(new CertificationGetMemento() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return dto.name;
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return new CompanyCode(companyCode);
			}

			@Override
			public String getCode() {
				// TODO Auto-generated method stub
				return dto.code;
			}
		});
	}

}
