package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriodDto;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.承認すべき36協定があるかチェックする
 * @author tutt
 *
 */
@Stateless
public class Check36AgreementApproved {
	
	@Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;

	//承認すべき36協定があるかチェックする
	public boolean check36AgreementApproved(String companyId, String employeeId, GetYearProcessAndPeriodDto period) {
		
		List<SpecialProvisionsOfAgreement> specialProvisionsOfAgreements = specialProvisionsOfAgreementRepo.getByEmployeeId(employeeId, period.getClosureStartDate(), period.getClosureEndDate(), companyId);
	
		return !specialProvisionsOfAgreements.isEmpty();
	}
}
