package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TrungBV
 *
 */

@Stateless
public class PayoutManagementDataFinder {
	@Inject
	private PayoutManagementDataRepository repo;
	
	public List<PayoutManagementDto> getBysiDRemCod(String empId, int state) {
		String cid = AppContexts.user().companyId();

		return repo.getSidWithCod(cid, empId, state).stream().map(item -> PayoutManagementDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
