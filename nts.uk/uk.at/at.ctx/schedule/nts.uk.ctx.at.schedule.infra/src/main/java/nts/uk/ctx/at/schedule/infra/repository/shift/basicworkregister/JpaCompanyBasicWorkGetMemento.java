package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyId;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtCompanyWorkSet;

public class JpaCompanyBasicWorkGetMemento implements CompanyBasicWorkGetMemento {

	private KcbmtCompanyWorkSet typeValue;
	
	public JpaCompanyBasicWorkGetMemento(KcbmtCompanyWorkSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKcbmtCompanyWorkSetPK().getCid());
	}

	@Override
	public List<BasicWorkSetting> getBasicWorkSetting() {
		return null;
	}

}
