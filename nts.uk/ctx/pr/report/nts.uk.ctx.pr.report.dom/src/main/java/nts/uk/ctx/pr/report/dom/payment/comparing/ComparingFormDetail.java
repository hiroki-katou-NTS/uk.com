package nts.uk.ctx.pr.report.dom.payment.comparing;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComparingFormDetail extends AggregateRoot {

	private String companyCode;

	private FormCode formCode;

	private CategoryAtr categoryAtr;

	private ItemCode itemCode;

	private DispOrder dispOrder;

	public static ComparingFormDetail createFromJavaType(String companyCode, String formCode, int categoryAtr,
			String itemCode, BigDecimal dispOrder) {
		return new ComparingFormDetail(companyCode, new FormCode(formCode),
				EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class), new ItemCode(itemCode), new DispOrder(dispOrder));
	}

}
