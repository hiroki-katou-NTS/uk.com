package nts.uk.ctx.pr.report.infra.repository.insurance;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSetting;
import nts.uk.ctx.pr.report.dom.insurance.ChecklistPrintSettingRepository;

@Stateless
public class JpaChecklistPrintSettingRepository implements ChecklistPrintSettingRepository {

	@Override
	public void save(ChecklistPrintSetting printSetting) {
		//Do nothing
	}

	@Override
	public Optional<ChecklistPrintSetting> findByCompanyCode(CompanyCode companyCode) {
		return null;
	}

}
