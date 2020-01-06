/**
 * 
 */
package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class RegistrationPersonReportFinder {
	
	@Inject
	private RegistrationPersonReportRepository repo;
	
	public List<RegistrationPersonReport> getListReportSaveDraft(String sid) {
		List<RegistrationPersonReport> result = repo.getListReportSaveDraft(sid);
		return result;
	};
	
	RegistrationPersonReport getDomain(String cid, Integer reportLayoutID){
		Optional<RegistrationPersonReport> result = repo.getDomain(cid, reportLayoutID);
		return result.isPresent() ? result.get() : null;
	};
	
	
}
