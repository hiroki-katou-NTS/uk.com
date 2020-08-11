package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformationRepository;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
public class DeletePersonInfoHR {

	@Inject
	private PersonalInformationRepository repo;
	
	public void deletePersonalInfo(String histId) {
		this.repo.delete(histId);
	}
}
