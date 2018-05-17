package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;


@Stateless
public class PayoutManagementDataService {
	
	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;
	
	
	public boolean checkInfoPayMana(PayoutManagementData domain){
		Optional<PayoutManagementData> payout = payoutManagementDataRepository.findByID(domain.getPayoutId());
		if (payout.isPresent()){
			return true;
		}
		return false;
	}
	public GeneralDate getClosingDate(){
		return null;
	}
}
