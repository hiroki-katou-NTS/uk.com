package nts.uk.ctx.pr.screen.infra.repository.paymentdata;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.PaymentDataQueryRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.DetailItemDto;

@RequestScoped
public class JpaPaymentDataQueryRepository extends JpaRepository implements PaymentDataQueryRepository {
	
	private String SELECT_ALL = " SELECT d, i.ITEM_NAME" +
								" FROM QstdtPaymentDetail d JOIN QcamtItem i ON d.QstdtPaymentDetailPK.companyCode = i.QcamtItemPK.ccd" +
																	" AND d.QstdtPaymentDetailPK.categoryATR = i.QcamtItemPK.ctgAtr" +
																	" AND d.QstdtPaymentDetailPK.itemCode = i.QcamtItemPK.itemCd" +
								" WHERE d.QstdtPaymentDetailPK.companyCode = :CCD" +
										" AND d.QstdtPaymentDetailPK.PID = :PID" +
										" AND d.QstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR" +
										" AND d.QstdtPaymentDetailPK.processingYM = :PROCESSING_YM";
	
	
	private String SELECT_ITEM_BY_CATEGORY = " SELECT d, i.itemName" +
											 " FROM QstdtPaymentDetail d JOIN QcamtItem i ON d.QstdtPaymentDetailPK.companyCode = i.QcamtItemPK.ccd" +
																			" AND d.QstdtPaymentDetailPK.categoryATR = i.QcamtItemPK.ctgAtr" +
																			" AND d.QstdtPaymentDetailPK.itemCode = i.QcamtItemPK.itemCd" +
											 " WHERE d.QstdtPaymentDetailPK.companyCode = :CCD"+
													" AND d.QstdtPaymentDetailPK.personId = :PID" +
													" AND d.QstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR" +
													" AND d.QstdtPaymentDetailPK.processingYM = :PROCESSING_YM" +
													" AND d.QstdtPaymentDetailPK.categoryATR = :CTG_ATR";

	
	private String SELECT_DEDUCTION_ITEMS = " SELECT d, i.itemName" +
											" FROM QstdtPaymentDetail d JOIN QcamtItem i ON d.QstdtPaymentDetailPK.companyCode = i.QcamtItemPK.ccd" +
																			" AND d.QstdtPaymentDetailPK.categoryATR = i.QcamtItemPK.ctgAtr" +
																			" AND d.QstdtPaymentDetailPK.itemCode = i.QcamtItemPK.itemCd" +
											" WHERE d.QstdtPaymentDetailPK.companyCode = :CCD"+
													" AND d.QstdtPaymentDetailPK.personId = :PID" +
													" AND d.QstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR" +
													" AND d.QstdtPaymentDetailPK.processingYM = :PROCESSING_YM" +
													" AND d.QstdtPaymentDetailPK.categoryATR = :CTG_ATR" +
													" AND d.deductAttribute = :DEDUCT_ATR";
	
	
	private String SELECT_ITEM = " SELECT d, i.itemName" +
								 " FROM QstdtPaymentDetail d JOIN QcamtItem i ON d.QstdtPaymentDetailPK.companyCode = i.QcamtItemPK.ccd" +
																 " AND d.QstdtPaymentDetailPK.categoryATR = i.QcamtItemPK.ctgAtr" +
																 " AND d.QstdtPaymentDetailPK.itemCode = i.QcamtItemPK.itemCd" +
								 " WHERE d.QstdtPaymentDetailPK.companyCode = :CCD"+
										" AND d.QstdtPaymentDetailPK.personId = :PID" +
										" AND d.QstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR" +
										" AND d.QstdtPaymentDetailPK.processingYM = :PROCESSING_YM" +
										" AND d.QstdtPaymentDetailPK.categoryATR = :CTG_ATR" +
										" AND d.QstdtPaymentDetailPK.itemCode = :ITEM_CD";
	
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
