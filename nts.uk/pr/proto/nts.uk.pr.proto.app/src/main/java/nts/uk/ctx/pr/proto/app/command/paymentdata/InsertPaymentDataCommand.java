package nts.uk.ctx.pr.proto.app.command.paymentdata;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.DetailItemCommandBase;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.PaymentDataCommandBase;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;

/**
 * Command: insert payment data
 * 
 * @author vunv
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InsertPaymentDataCommand extends PaymentDataCommandBase{
	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	@Override
	public Payment toDomain(String companyCode) {
		Payment domain = super.toDomain(companyCode);
		domain.setDetailPaymentItems(toDomainDetails(this.getDetailPaymentItems()));
		domain.setDetailDeductionItems(this.setDudectionDetailItems(this.getDetailDeductionItems()));
		domain.setDetailArticleItems(toDomainDetails(this.getDetailDeductionItems()));
		domain.setDetailPersonalTimeItems(toDomainDetails(this.getDetailPersonalTimeItems()));
		
		return domain;
	}
	
	/**
	 * convert data from command to value object detail item
	 * @param items
	 * @return
	 */
	public static List<DetailItem> toDomainDetails(List<DetailItemCommandBase> items) {
		return items.stream().map(c -> c.toDomain()).collect(Collectors.toList());
	}
}
