package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.CategoryAtr;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ItemCode;

@AllArgsConstructor
@Getter
public class PaycompConfirm extends AggregateRoot {

	private String companyCode;

	private PersonID personID;

	private ProcessingYMEarlier processingYMEarlier;

	private ProcessingYMLater processingYMLater;

	private CategoryAtr categoryAtr;

	private ItemCode itemCode;

	private ConfirmedStatus confirmedStatus;

	private ValueDifference valueDifference;

	private ReasonDifference reasonDifference;

	public static PaycompConfirm createFromJavaType(String companyCode, String personID, String itemCode,
			int processingYMEarlier, int processingYMLater, int categoryAtr, BigDecimal valueDifference,
			String reasonDifference, int confirmedStatus) {
		return new PaycompConfirm(companyCode, new PersonID(personID),
				new ProcessingYMEarlier(processingYMEarlier), new ProcessingYMLater(processingYMLater),
				EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class), new ItemCode(itemCode),
				EnumAdaptor.valueOf(confirmedStatus, ConfirmedStatus.class), new ValueDifference(valueDifference),
				new ReasonDifference(reasonDifference));
	}
}
