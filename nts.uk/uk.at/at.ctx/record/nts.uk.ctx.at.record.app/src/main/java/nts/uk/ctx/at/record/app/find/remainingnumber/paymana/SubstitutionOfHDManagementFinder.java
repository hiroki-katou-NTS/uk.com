package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TrungBV
 *
 */

@Stateless
public class SubstitutionOfHDManagementFinder {
	@Inject
	private SubstitutionOfHDManaDataRepository repo;

	public List<SubstitutionOfHDManagementDto> getBysiDRemCod(String empId) {
		String cid = AppContexts.user().companyId();

		return repo.getBysiDRemCod(cid, empId).stream().map(item -> SubstitutionOfHDManagementDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
