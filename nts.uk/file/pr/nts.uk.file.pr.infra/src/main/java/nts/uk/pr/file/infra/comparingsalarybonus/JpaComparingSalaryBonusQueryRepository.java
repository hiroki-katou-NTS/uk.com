package nts.uk.pr.file.infra.comparingsalarybonus;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.pr.app.export.comparingsalarybonus.ComparingSalaryBonusQueryRepository;

@Stateless
public class JpaComparingSalaryBonusQueryRepository extends JpaRepository implements ComparingSalaryBonusQueryRepository { 
	private String SELECT_PAYMENT = " SELECT c, d"
			+ " FROM QstdtPaymentDetail d JOIN QstdtPaymentHeader c ON c.qstdtPaymentHeaderPK.companyCode = d.qstdtPaymentDetailPK.companyCode"
			+ " AND c.qstdtPaymentHeaderPK.personId =  d.qstdtPaymentDetailPK.personId"
			+ " AND c.qstdtPaymentHeaderPK.processingNo =  d.qstdtPaymentDetailPK.processingNo"
			+ " AND c.qstdtPaymentHeaderPK.payBonusAtr =  d.qstdtPaymentDetailPK.payBonusAttribute "
			+ " AND c.qstdtPaymentHeaderPK.processingYM =  d.qstdtPaymentDetailPK.processingYM"
			+ " AND c.qstdtPaymentHeaderPK.sparePayAtr =  d.qstdtPaymentDetailPK.sparePayAttribute"
			+ " WHERE c.qstdtPaymentHeaderPK.companyCode = :CCD" + " AND c.qstdtPaymentHeaderPK.personId = :PID"
			+ " AND c.qstdtPaymentHeaderPK.payBonusAtr = :PAY_BONUS_ATR"
			+ " AND c.qstdtPaymentHeaderPK.processingYM = :PROCESSING_YM1"
			+ " OR c.qstdtPaymentHeaderPK.processingYM = :PROCESSING_YM2";

	private String SELECT_PAYMENT_DETAIL = " SELECT c" + " FROM QstdtPaymentDetail d "
			+ " WHERE c.qstdtPaymentHeaderPK.companyCode = :CCD" + " AND c.qstdtPaymentHeaderPK.personId = :PID"
			+ " AND c.qstdtPaymentHeaderPK.payBonusAtr = :PAY_BONUS_ATR"
			+ " AND c.qstdtPaymentHeaderPK.processingYM = :PROCESSING_YM1"
			+ " OR c.qstdtPaymentHeaderPK.processingYM = :PROCESSING_YM2";

	public List<?> getAllPayment(String companyCode, String personId, int paymentBonus, String yearMonth1,
			String yearMonth2) {

		return this.queryProxy().query(SELECT_PAYMENT, Object[].class).setParameter("CCD", companyCode)
				.setParameter("PID", personId).setParameter("PAY_BONUS_ATR", paymentBonus)
				.setParameter("PROCESSING_YM1", yearMonth1).setParameter("PROCESSING_YM2", yearMonth2).getList();
	}

	public List<?> getPaymentDetail(String companyCode, String personId, int paymentBonus, String yearMonth1,
			String yearMonth2) {
		return this.queryProxy().query(SELECT_PAYMENT_DETAIL, Object[].class)
				.setParameter("CCD", companyCode)
				.setParameter("PID", personId).setParameter("PAY_BONUS_ATR", paymentBonus)
				.setParameter("PROCESSING_YM1", yearMonth1)
				.setParameter("PROCESSING_YM2", yearMonth2).getList();
	}


	public List<?> getPayComDetail(String companyCode, String formCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
