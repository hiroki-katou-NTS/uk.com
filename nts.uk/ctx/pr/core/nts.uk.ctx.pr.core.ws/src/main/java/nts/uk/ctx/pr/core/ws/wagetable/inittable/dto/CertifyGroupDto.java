/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.inittable.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupName;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.MultipleTargetSetting;

/**
 * The Class CodeItem.
 */
@Data
public class CertifyGroupDto implements CertifyGroupSetMemento {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The memo. */
	private Integer multiApplySet;

	/** The certify items. */
	private List<CertifyItemDto> certifyItems;

	@Override
	public void setCompanyCode(String companyCode) {
		// Do nothing.
	}

	@Override
	public void setCode(CertifyGroupCode code) {
		this.code = code.v();
	}

	@Override
	public void setName(CertifyGroupName name) {
		this.name = name.v();
	}

	@Override
	public void setMultiApplySet(MultipleTargetSetting multiApplySet) {
		this.multiApplySet = multiApplySet.value;
	}

	@Override
	public void setCertifies(Set<Certification> certifies) {
		this.certifyItems = certifies.stream().map(item -> {
			CertifyItemDto certifyItemDto = new CertifyItemDto();
			item.saveToMemento(certifyItemDto);
			return certifyItemDto;
		}).collect(Collectors.toList());
	}

}
