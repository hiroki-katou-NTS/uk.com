package nts.uk.ctx.pr.core.app.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;

@Builder
public class CertifyGroupDto implements CertifyGroupSetMemento {

	/** The wage table code. */
	private String wageTableCode;

	/** The history id. */
	private String historyId;

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The multi apply set. */
	private Integer multiApplySet;

	/** The certifies. */
	private List<CertificationDto> certifies;

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWageTableCode(WageTableCode wageTableCode) {
		// TODO Auto-generated method stub
		this.wageTableCode = wageTableCode.v();

	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub
		this.historyId = historyId;

	}

	@Override
	public void setCode(String code) {
		// TODO Auto-generated method stub
		this.code = code;

	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;

	}

	@Override
	public void setMultiApplySet(MultipleTargetSetting multiApplySet) {
		// TODO Auto-generated method stub
		this.multiApplySet = multiApplySet.value;

	}

	@Override
	public void setCertifies(Set<Certification> certifies) {
		// TODO Auto-generated method stub
		this.certifies = new ArrayList<>();
		for (Certification certification : certifies) {
			CertificationDto certificationDto=new CertificationDto();
			certification.saveToMemento(certificationDto);
			this.certifies.add(certificationDto);
		}
	}

}
