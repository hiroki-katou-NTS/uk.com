package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.List;

import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;

public interface PaymentDateMasterRepository {
	/**
	 * 
	 * @param companyCode
	 * @param payBonusAtr
	 * @param processingYm
	 * @param sparePayAtr
	 * @return paymentDateMaster
	 */
	List<PaymentDateMaster> find(String companyCode, int payBonusAtr, int processingYm, int sparePayAtr);
}
