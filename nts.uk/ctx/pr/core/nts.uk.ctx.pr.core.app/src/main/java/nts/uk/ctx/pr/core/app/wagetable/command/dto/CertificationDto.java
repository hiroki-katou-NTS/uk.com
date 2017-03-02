/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationGetMemento;

/**
 * Instantiates a new certification dto.
 */
@Data
public class CertificationDto {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the certification
	 */
	public Certification toDomain(String companyCode) {
		CertificationDto dto = this;
		return new Certification(new CertificationGetMemento() {

			@Override
			public String getName() {
				return dto.name;
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(companyCode);
			}

			@Override
			public String getCode() {
				return dto.code;
			}
		});
	}

}
