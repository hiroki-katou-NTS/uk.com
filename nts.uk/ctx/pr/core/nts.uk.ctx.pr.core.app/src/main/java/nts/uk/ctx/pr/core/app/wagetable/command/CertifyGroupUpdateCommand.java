/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.CertificationDto;
import nts.uk.ctx.pr.core.app.wagetable.CertifyGroupDto;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;

/**
 * The Class CertifyGroupAddCommand.
 */
@Setter
@Getter
public class CertifyGroupUpdateCommand implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The certify group dto. */
	private CertifyGroupDto certifyGroupDto;

	/** The comany code. */
	private String companyCode;

	/**
	 * To domain.
	 *
	 * @return the certify group
	 */
	public CertifyGroup toDomain() {
		CertifyGroupUpdateCommand command = this;
		return new CertifyGroup(new CertifyGroupGetMemento() {

			@Override
			public String getName() {
				return command.certifyGroupDto.getName();
			}

			@Override
			public MultipleTargetSetting getMultiApplySet() {
				return MultipleTargetSetting.valueOf(command.certifyGroupDto.getMultiApplySet());
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(command.companyCode);
			}

			@Override
			public String getCode() {
				return command.certifyGroupDto.getCode();
			}

			@Override
			public Set<Certification> getCertifies() {
				Set<Certification> setCertification = new HashSet<>();
				for (CertificationDto certificationDto : command.certifyGroupDto.getCertifies()) {
					Certification certification = new Certification(new CertificationGetMemento() {

						@Override
						public String getName() {
							return certificationDto.getName();
						}

						@Override
						public CompanyCode getCompanyCode() {
							return new CompanyCode(command.companyCode);
						}

						@Override
						public String getCode() {
							return certificationDto.getCode();
						}
					});
					setCertification.add(certification);
				}
				return setCertification;
			}
		});
	}

}
