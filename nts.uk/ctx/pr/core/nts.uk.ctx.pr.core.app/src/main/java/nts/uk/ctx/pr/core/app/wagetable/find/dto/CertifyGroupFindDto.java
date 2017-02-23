package nts.uk.ctx.pr.core.app.wagetable.find.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.certification.MultipleTargetSetting;

@Data
public class CertifyGroupFindDto implements CertifyGroupSetMemento {
	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The multi apply set. */
	private Integer multiApplySet;

	/** The certifies. */
	private List<CertificationFindDto> certifies;

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCode(String code) {
		this.code = code;

	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public void setMultiApplySet(MultipleTargetSetting multiApplySet) {
		this.multiApplySet = multiApplySet.value;

	}

	@Override
	public void setCertifies(Set<Certification> certifies) {
		List<CertificationFindDto> lstCertificationDto = new ArrayList<>();
		for (Certification certification : certifies) {
			CertificationFindDto certificationDto = new CertificationFindDto();
			certification.saveToMemento(certificationDto);
			lstCertificationDto.add(certificationDto);
		}
		this.certifies = lstCertificationDto;

	}
}
