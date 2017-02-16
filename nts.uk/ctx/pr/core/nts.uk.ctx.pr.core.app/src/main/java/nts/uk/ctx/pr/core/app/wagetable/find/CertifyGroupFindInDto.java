package nts.uk.ctx.pr.core.app.wagetable.find;

import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;

@Data
public class CertifyGroupFindInDto implements CertifyGroupSetMemento {
	private String code;
	private String name;

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

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
	public void setWageTableCode(WageTableCode wageTableCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMultiApplySet(MultipleTargetSetting multiApplySet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCertifies(Set<Certification> certifies) {
		// TODO Auto-generated method stub

	}

}
