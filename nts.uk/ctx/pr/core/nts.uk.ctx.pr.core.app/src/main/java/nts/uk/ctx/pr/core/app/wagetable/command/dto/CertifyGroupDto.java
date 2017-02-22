/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;

/**
 * The Class CertifyGroupDto.
 */
@Data
public class CertifyGroupDto {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The multi apply set. */
	private Integer multiApplySet;

	/** The certifies. */
	private List<CertificationDto> certifies;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the certify group
	 */
	public CertifyGroup toDomain(String companyCode) {
		CertifyGroupDto dto = this;
		CertifyGroup certifyGroup = new CertifyGroup(new CertifyGroupGetMemento() {

			@Override
			public String getName() {
				return dto.name;
			}

			@Override
			public MultipleTargetSetting getMultiApplySet() {
				return MultipleTargetSetting.valueOf(dto.multiApplySet);
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(companyCode);
			}

			@Override
			public String getCode() {
				return dto.code;
			}

			@Override
			public Set<Certification> getCertifies() {
				Set<Certification> setCertification = new HashSet<>();
				for (CertificationDto certificationDto : dto.certifies) {
					setCertification.add(certificationDto.toDomain(companyCode));
				}
				return setCertification;
			}
		});
		return certifyGroup;
	}
}
