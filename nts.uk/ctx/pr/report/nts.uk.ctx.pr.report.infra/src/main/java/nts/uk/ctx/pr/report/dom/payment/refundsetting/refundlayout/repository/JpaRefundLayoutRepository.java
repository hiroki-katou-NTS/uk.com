package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.repository;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.RefundLayout;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.RefundLayoutRepository;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.ShowRefundLayout;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.entity.QrfdtRefundLayoutSet;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.entity.QrfdtRefundLayoutSetPK;

@Stateless
public class JpaRefundLayoutRepository extends JpaRepository implements RefundLayoutRepository {

	@Override
	public Optional<RefundLayout> getRefundLayout(String companyCode, int printType) {
		val refundLayoutSetPK = new QrfdtRefundLayoutSetPK();
		refundLayoutSetPK.companyCode = companyCode;
		refundLayoutSetPK.printType = printType;
		return this.queryProxy().find(refundLayoutSetPK, QrfdtRefundLayoutSet.class).map(s -> convertToDomain(s));
	}

	@Override
	public void insertRefundLayout(RefundLayout refundLayout) {
		this.commandProxy().insert(convertToEntity(refundLayout));
	}

	@Override
	public void updateRefundLayout(RefundLayout refundLayout) {
		this.commandProxy().update(convertToEntity(refundLayout));
	}

	private static RefundLayout convertToDomain(QrfdtRefundLayoutSet entity) {
		ShowRefundLayout showRefundLayout = ShowRefundLayout.createFromJavaType(entity.showCompName,
				entity.showCompAddInSurface, entity.showCompNameInSurface, entity.showDependencePerNum,
				entity.showInsuranceLevel, entity.showMnyItemName, entity.showPerAddInSurface,
				entity.showPerNameInSurface, entity.showRemainAnnualLeave, entity.showTotalTaxMny,
				entity.showZeroInAttend, entity.showPerTaxCatalog, entity.showDepartment, entity.showZeroInMny,
				entity.showProductsPayMny, entity.showAttendItemName);

		return RefundLayout.createFromJavaType(entity.refundLayoutSetPK.companyCode, entity.refundLayoutSetPK.printType,
				entity.usingZeroSettingCtg, entity.printYearMonth, showRefundLayout, entity.paymentCellNameCtg,
				entity.isShaded, entity.bordWidth);
	}

	private static QrfdtRefundLayoutSet convertToEntity(RefundLayout domain) {
		val refundLayoutSetPK = new QrfdtRefundLayoutSetPK();
		refundLayoutSetPK.companyCode = domain.getCompanyCode().v();
		refundLayoutSetPK.printType = domain.getPrintType().value;

		return new QrfdtRefundLayoutSet(refundLayoutSetPK, domain.getUsingZeroSettingCtg().value,
				domain.getShowRefundLayout().getShowZeroInMny().value,
				domain.getShowRefundLayout().getShowMnyItemName().value,
				domain.getShowRefundLayout().getShowZeroInAttend().value,
				domain.getShowRefundLayout().getShowAttendItemName().value, domain.getPrintYearMonth().value,
				domain.getShowRefundLayout().getShowTotalTaxMny().value,
				domain.getShowRefundLayout().getShowRemainAnnualLeave().value,
				domain.getShowRefundLayout().getShowProductsPayMny().value, domain.getPaymentCellNameCtg().value,
				domain.getShowRefundLayout().getShowCompName().value,
				domain.getShowRefundLayout().getShowDepartment().value, domain.getIsShaded().value,
				domain.getBordWidth().value, domain.getShowRefundLayout().getShowDependencePerNum().value,
				domain.getShowRefundLayout().getShowPerTaxCatalog().value,
				domain.getShowRefundLayout().getShowInsuranceLevel().value,
				domain.getShowRefundLayout().getShowPerAddInSurface().value,
				domain.getShowRefundLayout().getShowPerNameInSurface().value,
				domain.getShowRefundLayout().getShowCompAddInSurface().value,
				domain.getShowRefundLayout().getShowCompNameInSurface().value);
	}

}
