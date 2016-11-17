package nts.uk.ctx.pr.screen.infra.repository.paymentdata;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.repository.PaymentDataQueryRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.DetailItemDto;

@RequestScoped
public class JpaPaymentDataQueryRepository extends JpaRepository implements PaymentDataQueryRepository {
	
	private String SELECT_ALL = " SELECT d, i.ITEM_NAME" +
								" FROM QSTDT_PAYMENT_DETAIL d JOIN QCAMT_ITEM i ON d.CCD = i.CCD" +
																	" AND d.CTG_ATR = i.CTG_ATR AND" +
																	" AND d.ITEM_CD = i.ITEM_CD" +
								" WHERE d.CCD = :CCD AND d.PID = :PID" +
										" AND d.PAY_BONUS_ATR = :PAY_BONUS_ATR" +
										" AND d.PROCESSING_YM = :PROCESSING_YM";
	
	
	private String SELECT_ITEM_BY_CATEGORY = " SELECT d, i.ITEM_NAME" +
											" FROM QSTDT_PAYMENT_DETAIL d JOIN QCAMT_ITEM i ON d.CCD = i.CCD" +
																							" AND d.CTG_ATR = i.CTG_ATR AND" +
																							" AND d.ITEM_CD = i.ITEM_CD" +
											" WHERE d.CCD = :CCD AND d.PID = :PID" +
													" AND d.PAY_BONUS_ATR = :PAY_BONUS_ATR" +
													" AND d.PROCESSING_YM = :PROCESSING_YM" +
													" AND d.CTG_ATR = :CTG_ATR";

	
	private String SELECT_DEDUCTION_ITEMS = " SELECT d, i.ITEM_NAME" +
											" FROM QSTDT_PAYMENT_DETAIL d JOIN QCAMT_ITEM i ON d.CCD = i.CCD" +
																							" AND d.CTG_ATR = i.CTG_ATR AND" +
																							" AND d.ITEM_CD = i.ITEM_CD" +
											" WHERE d.CCD = :CCD AND d.PID = :PID" +
													" AND d.PAY_BONUS_ATR = :PAY_BONUS_ATR" +
													" AND d.PROCESSING_YM = :PROCESSING_YM" +
													" AND d.CTG_ATR = :CTG_ATR" +
													" AND d.DEDUCT_ATR = :DEDUCT_ATR";
	
	
	private String SELECT_ITEM = " SELECT d, i.ITEM_NAME" +
								" FROM QSTDT_PAYMENT_DETAIL d JOIN QCAMT_ITEM i ON d.CCD = i.CCD" +
																				" AND d.CTG_ATR = i.CTG_ATR AND" +
																				" AND d.ITEM_CD = i.ITEM_CD" +
								" WHERE d.CCD = :CCD AND d.PID = :PID" +
										" AND d.PAY_BONUS_ATR = :PAY_BONUS_ATR" +
										" AND d.PROCESSING_YM = :PROCESSING_YM" +
										" AND d.CTG_ATR = :CTG_ATR" +
										" AND d.DEDUCT_ATR = :DEDUCT_ATR" +
										" AND d.ITEM_CD = :ITEM_CD";
	
	@Override
	public List<DetailItemDto> findAll(String companyCode, String personId, int payBonusAtr, int processingYm) {
		return this.queryProxy()
						.query(SELECT_ALL, DetailItemDto.class)
							.setParameter("CCD", companyCode)
							.setParameter("PID", personId)
							.setParameter("PAY_BONUS_ATR", payBonusAtr)
							.setParameter("PROCESSING_YM", processingYm)
						.getList();
	}

	@Override
	public List<DetailItemDto> findItemByCategory(String companyCode, String personId, int payBonusAtr,int processingYm, int categoryAtr) {
		return this.queryProxy()
				.query(SELECT_ITEM_BY_CATEGORY, DetailItemDto.class)
					.setParameter("CCD", companyCode)
					.setParameter("PID", personId)
					.setParameter("PAY_BONUS_ATR", payBonusAtr)
					.setParameter("PROCESSING_YM", processingYm)
					.setParameter("CTG_ATR", categoryAtr)
				.getList();
	}

	@Override
	public List<DetailItemDto> findDeductionItem(String companyCode, String personId, int payBonusAtr,
			int processingYm, int categoryAtr, int deductionAtr) {
		
		return this.queryProxy()
				.query(SELECT_DEDUCTION_ITEMS, DetailItemDto.class)
					.setParameter("CCD", companyCode)
					.setParameter("PID", personId)
					.setParameter("PAY_BONUS_ATR", payBonusAtr)
					.setParameter("PROCESSING_YM", processingYm)
					.setParameter("CTG_ATR", categoryAtr)
					.setParameter("DEDUCT_ATR", deductionAtr)
				.getList();
	}

	@Override
	public Optional<DetailItemDto> findItem(String companyCode, String personId, int payBonusAtr, int processingYm,
			int categoryAtr, int itemCode) {
		
		return this.queryProxy()
				.query(SELECT_ITEM, DetailItemDto.class)
					.setParameter("CCD", companyCode)
					.setParameter("PID", personId)
					.setParameter("PAY_BONUS_ATR", payBonusAtr)
					.setParameter("PROCESSING_YM", processingYm)
					.setParameter("CTG_ATR", categoryAtr)
					.setParameter("ITEM_CD", itemCode)
				.getSingle();
	}
	
	
	
}
