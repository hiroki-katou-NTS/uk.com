package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import java.util.List;

import lombok.Data;

@Data
public class PaymentDataResult {

	private PaymentDataHeaderDto paymenHeader;

	private List<LayoutMasterCategoryDto> categories;

}
