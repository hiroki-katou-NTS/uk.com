package nts.uk.ctx.pr.proto.app.command.paymentdata;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.DeductionAtr;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;

class Helper {


	static List<DetailItem> createDetailsOfPayment(Map<CategoryAtr, List<DetailItem>> payDetail,
			List<LayoutMasterDetail> layoutDetailMasterList) {
		
		return payDetail.get(CategoryAtr.PAYMENT).stream()
			.filter(c -> layoutDetailMasterList.stream().anyMatch(x -> x.getItemCode().equals(c.getItemCode())))
			.collect(Collectors.toList());
	}

	static List<DetailItem> createDetailsOfDeduction(Map<CategoryAtr, List<DetailItem>> payDetail,
			List<LayoutMasterDetail> layoutDetailMasterList) {
		
		return payDetail.get(CategoryAtr.DEDUCTION).stream()
			.filter(c -> layoutDetailMasterList.stream().anyMatch(x -> x.getItemCode().equals(c.getItemCode())))
			.map(x -> toDetailDeductionItem(x))
			.collect(Collectors.toList());
	}

	/**
	 * Convert to detail deduction
	 * @param detailItem
	 * @return
	 */
	private static DetailItem toDetailDeductionItem(DetailItem detailItem) {
		return new DetailItem(detailItem.getItemCode(), detailItem.getValue(), detailItem.getCorrectFlag(),
				detailItem.getSocialInsuranceAtr(), detailItem.getLaborInsuranceAtr(), detailItem.getCategoryAtr(), DeductionAtr.ANY_DEDUCTION);
	}
}
