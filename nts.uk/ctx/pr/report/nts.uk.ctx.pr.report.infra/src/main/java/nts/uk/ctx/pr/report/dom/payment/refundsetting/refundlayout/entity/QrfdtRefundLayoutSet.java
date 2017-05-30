package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "QRFDT_REFUND_LAYOUT_SET")
public class QrfdtRefundLayoutSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QrfdtRefundLayoutSetPK refundLayoutSetPK;

	@Basic(optional = false)
	@Column(name = "USING_ZERO_SETTING_CTG")
	public int usingZeroSettingCtg;

	@Basic(optional = false)
	@Column(name = "SHOW_ZERO_IN_MNY")
	public int showZeroInMny;

	@Basic(optional = false)
	@Column(name = "SHOW_MNY_ITEM_NAME")
	public int showMnyItemName;

	@Basic(optional = false)
	@Column(name = "SHOW_ZERO_IN_ATTEND")
	public int showZeroInAttend;

	@Basic(optional = false)
	@Column(name = "SHOW_ATTEND_ITEM_NAME")
	public int showAttendItemName;

	@Basic(optional = false)
	@Column(name = "PRINT_YEARMONTH")
	public int printYearMonth;

	@Basic(optional = false)
	@Column(name = "SHOW_TOTAL_TAX_MNY")
	public int showTotalTaxMny;

	@Basic(optional = false)
	@Column(name = "SHOW_REMAIN_ANNUAL_LEAVE")
	public int showRemainAnnualLeave;

	@Basic(optional = false)
	@Column(name = "SHOW_PRODUCTS_PAY_MNY")
	public int showProductsPayMny;

	@Basic(optional = false)
	@Column(name = "PAYMENT_CELL_NAME_CTG")
	public int paymentCellNameCtg;

	@Basic(optional = false)
	@Column(name = "SHOW_COMPANY_NAME")
	public int showCompName;

	@Basic(optional = false)
	@Column(name = "SHOW_DEPRT")
	public int showDepartment;

	@Basic(optional = false)
	@Column(name = "IS_SHADED")
	public int isShaded;

	@Basic(optional = false)
	@Column(name = "BORDER_WIDTH")
	public int bordWidth;

	@Basic(optional = false)
	@Column(name = "SHOW_DEPENDENCE_PER_NUM")
	public int showDependencePerNum;

	@Basic(optional = false)
	@Column(name = "SHOW_PER_TAX_CTG")
	public int showPerTaxCatalog;

	@Basic(optional = false)
	@Column(name = "SHOW_INSURANCE_LEVEL")
	public int showInsuranceLevel;

	@Basic(optional = false)
	@Column(name = "SHOW_PER_ADD_IN_SURFACE")
	public int showPerAddInSurface;

	@Basic(optional = false)
	@Column(name = "SHOW_PER_NAME_IN_SURFACE")
	public int showPerNameInSurface;

	@Basic(optional = false)
	@Column(name = "SHOW_COMP_ADD_IN_SURFACE")
	public int showCompAddInSurface;

	@Basic(optional = false)
	@Column(name = "SHOW_COMP_NAME_IN_SURFACE")
	public int showCompNameInSurface;

	@Override
	protected Object getKey() {
		return refundLayoutSetPK;
	}

	public QrfdtRefundLayoutSet(QrfdtRefundLayoutSetPK refundLayoutSetPK, int usingZeroSettingCtg, int showZeroInMny,
			int showMnyItemName, int showZeroInAttend, int showAttendItemName, int printYearMonth, int showTotalTaxMny,
			int showRemainAnnualLeave, int showProductsPayMny, int paymentCellNameCtg, int showCompName,
			int showDepartment, int isShaded, int bordWidth, int showDependencePerNum, int showPerTaxCatalog,
			int showInsuranceLevel, int showPerAddInSurface, int showPerNameInSurface, int showCompAddInSurface,
			int showCompNameInSurface) {
		super();
		this.refundLayoutSetPK = refundLayoutSetPK;
		this.usingZeroSettingCtg = usingZeroSettingCtg;
		this.showZeroInMny = showZeroInMny;
		this.showMnyItemName = showMnyItemName;
		this.showZeroInAttend = showZeroInAttend;
		this.showAttendItemName = showAttendItemName;
		this.printYearMonth = printYearMonth;
		this.showTotalTaxMny = showTotalTaxMny;
		this.showRemainAnnualLeave = showRemainAnnualLeave;
		this.showProductsPayMny = showProductsPayMny;
		this.paymentCellNameCtg = paymentCellNameCtg;
		this.showCompName = showCompName;
		this.showDepartment = showDepartment;
		this.isShaded = isShaded;
		this.bordWidth = bordWidth;
		this.showDependencePerNum = showDependencePerNum;
		this.showPerTaxCatalog = showPerTaxCatalog;
		this.showInsuranceLevel = showInsuranceLevel;
		this.showPerAddInSurface = showPerAddInSurface;
		this.showPerNameInSurface = showPerNameInSurface;
		this.showCompAddInSurface = showCompAddInSurface;
		this.showCompNameInSurface = showCompNameInSurface;
	}

}
