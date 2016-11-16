package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateMasterRepository;

@RequestScoped
public class JpaPaymentDateMasterRepository implements PaymentDateMasterRepository {

	@Override
	public Optional<PaymentDateMaster> find(String companyCode, int payBonusAtr, int processingYm, int sparePayAtr) {
		// TODO Auto-generated method stub
		return null;
	}

}
