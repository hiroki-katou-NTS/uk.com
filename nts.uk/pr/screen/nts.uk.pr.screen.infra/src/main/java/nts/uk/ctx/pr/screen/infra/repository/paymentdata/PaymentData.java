package nts.uk.ctx.pr.screen.infra.repository.paymentdata;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.screen.app.query.paymentdata.repository.PaymentDataQueryRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.DetailItemDto;

@RequestScoped
public class PaymentData extends JpaRepository implements PaymentDataQueryRepository {
	
	private String SELECT_ALL = " SELECT d" +
								" FROM QSTDT_PAYMENT_DETAIL d" +
								" WHERE d.CCD = :CCD AND d.PID = :PID" +
										" AND d.PAY_BONUS_ATR = :PAY_BONUS_ATR" +
										" AND d.PROCESSING_YM = :PROCESSING_YM";
	
	private String SELECT_ITEM_BY_CATEGORY = " SELECT d" +
										" FROM QSTDT_PAYMENT_DETAIL d" +
										" WHERE d.CCD = :CCD AND d.PID = :PID" +
												" AND d.PAY_BONUS_ATR = :PAY_BONUS_ATR" +
												" AND d.PROCESSING_YM = :PROCESSING_YM" +
												" AND d.CTG_ATR = :CTG_ATR";

	private String SELECT_DEDUCTION_ITEMS = " SELECT d" +
											" FROM QSTDT_PAYMENT_DETAIL d" +
											" WHERE d.CCD = :CCD AND d.PID = :PID" +
													" AND d.PAY_BONUS_ATR = :PAY_BONUS_ATR" +
													" AND d.PROCESSING_YM = :PROCESSING_YM" +
													" AND d.CTG_ATR = :CTG_ATR" +
													" AND d.DEDUCT_ATR = :DEDUCT_ATR";
	
	private String SELECT_ITEM = " SELECT d" +
											" FROM QSTDT_PAYMENT_DETAIL d" +
											" WHERE d.CCD = :CCD AND d.PID = :PID" +
													" AND d.PAY_BONUS_ATR = :PAY_BONUS_ATR" +
													" AND d.PROCESSING_YM = :PROCESSING_YM" +
													" AND d.CTG_ATR = :CTG_ATR" +
													" AND d.DEDUCT_ATR = :DEDUCT_ATR";
	
	@Override
	public List<DetailItemDto> findAll(String companyCode, String personId, int payBonusAtr, int processingYM) {
		return this.queryProxy()
						.query(SELECT_ALL, DetailItemDto.class)
						.getList();
	}

	@Override
	public List<DetailItemDto> findItemByCategory(String companyCode, String personId, int payBonusAtr,int processingYm, int categoryAtr) {
		return this.queryProxy()
				.query(SELECT_ITEM_BY_CATEGORY, DetailItemDto.class)
				.getList();
	}

	@Override
	public List<DetailItemDto> findDeductionItem(String companyCode, String personId, int payBonusAtr,
			int processingYm, int categoryAtr, int deductionAtr) {
		
		return this.queryProxy()
				.query(SELECT_DEDUCTION_ITEMS, DetailItemDto.class)
				.getList();
	}

	@Override
	public Optional<DetailItemDto> findItem(String companyCode, String personId, int payBonusAtr, int processingYm,
			int categoryAtr, int itemCode) {
		
		return this.queryProxy()
				.query(SELECT_ITEM, DetailItemDto.class)
				.getSingle();
	}
	
	
	
}
