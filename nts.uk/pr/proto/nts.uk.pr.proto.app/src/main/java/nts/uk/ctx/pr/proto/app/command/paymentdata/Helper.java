package nts.uk.ctx.pr.proto.app.command.paymentdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.SumScopeAtr;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LineDispAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.position.PrintPosCatalogLines;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.position.PrintPositionCategory;

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
			.collect(Collectors.toList());
	}
	
	static List<PrintPositionCategory> createPositionCategory(Map<CategoryAtr, List<DetailItem>> payDetail,
			List<LayoutMasterLine> lineList) {
		List<PrintPositionCategory> result = new ArrayList<>();
		
		payDetail.keySet().stream().forEach(category -> {
			List<PrintPositionCategory> lines = lineList.stream()
					.filter(x -> x.getCategoryAtr() == category && LineDispAtr.ENABLE == x.getLineDispayAttribute())
					.map(line -> new PrintPositionCategory(category, new PrintPosCatalogLines(line.getLinePosition().v())))
					.collect(Collectors.toList());
			if (!lines.isEmpty()) {
				result.addAll(lines);
			}
		});
		
		return result;
	}

	/** get payment detail to calculate total payment **/
	static List<DetailItem> detailItemByTotalPayment(Map<CategoryAtr, List<DetailItem>> payDetail, List<LayoutMasterDetail> layoutMasterDetailList) {
		return payDetail.get(CategoryAtr.PAYMENT).stream()
				.filter(c -> layoutMasterDetailList.stream().anyMatch(x -> x.getItemCode().equals(c.getItemCode()) && SumScopeAtr.INCLUDED == x.getSumScopeAtr()))
				.collect(Collectors.toList());
	}
	
	/** get payment detail to calculate deduction total payment **/
	static List<DetailItem> layoutMasterByDeductionTotalPayment(Map<CategoryAtr, List<DetailItem>> payDetail, List<LayoutMasterDetail> layoutMasterDetailList) {
		return payDetail.get(CategoryAtr.DEDUCTION).stream()
				.filter(c -> layoutMasterDetailList.stream().anyMatch(x -> x.getItemCode().equals(c.getItemCode()) && SumScopeAtr.INCLUDED == x.getSumScopeAtr()))
				.collect(Collectors.toList());
	}
}
