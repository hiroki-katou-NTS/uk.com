package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShowRefundLayout {

	private ShowCompanyName showCompName;

	private ShowCompAddInSurface showCompAddInSurface;

	private ShowCompNameInSurface showCompNameInSurface;

	private ShowDependencePerNum showDependencePerNum;

	private ShowInsuranceLevel showInsuranceLevel;

	private ShowMnyItemName showMnyItemName;

	private ShowPerAddInSurface showPerAddInSurface;

	private ShowPerNameInSurface showPerNameInSurface;

	private ShowRemainAnnualLeave showRemainAnnualLeave;

	private ShowTotalTaxMny showTotalTaxMny;

	private ShowZeroInAttend showZeroInAttend;

	private ShowPerTaxCatalog showPerTaxCatalog;

	private ShowDepartment showDepartment;

	private ShowZeroInMny showZeroInMny;

	private ShowProductsPayMny showProductsPayMny;

	public static ShowRefundLayout createFromJavaType(int showCompName, int showCompAddInSurface,
			int showCompNameInSurface, int showDependencePerNum, int showInsuranceLevel, int showMnyItemName,
			int showPerAddInSurface, int showPerNameInSurface, int showRemainAnnualLeave, int showTotalTaxMny,
			int showZeroInAttend, int showPerTaxCatalog, int showDepartment, int showZeroInMny,
			int showProductsPayMny) {
		return new ShowRefundLayout(EnumAdaptor.valueOf(showCompName, ShowCompanyName.class),
				EnumAdaptor.valueOf(showCompAddInSurface, ShowCompAddInSurface.class),
				EnumAdaptor.valueOf(showCompNameInSurface, ShowCompNameInSurface.class),
				EnumAdaptor.valueOf(showDependencePerNum, ShowDependencePerNum.class),
				EnumAdaptor.valueOf(showInsuranceLevel, ShowInsuranceLevel.class),
				EnumAdaptor.valueOf(showMnyItemName, ShowMnyItemName.class),
				EnumAdaptor.valueOf(showPerAddInSurface, ShowPerAddInSurface.class),
				EnumAdaptor.valueOf(showPerNameInSurface, ShowPerNameInSurface.class),
				EnumAdaptor.valueOf(showRemainAnnualLeave, ShowRemainAnnualLeave.class),
				EnumAdaptor.valueOf(showTotalTaxMny, ShowTotalTaxMny.class),
				EnumAdaptor.valueOf(showZeroInAttend, ShowZeroInAttend.class),
				EnumAdaptor.valueOf(showPerTaxCatalog, ShowPerTaxCatalog.class),
				EnumAdaptor.valueOf(showDepartment, ShowDepartment.class),
				EnumAdaptor.valueOf(showZeroInMny, ShowZeroInMny.class),
				EnumAdaptor.valueOf(showProductsPayMny, ShowProductsPayMny.class));
	}
}
