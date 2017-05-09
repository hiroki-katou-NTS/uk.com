/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.refundsetting.refundpadding;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.PrintType;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPadding;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPaddingRepository;
import nts.uk.ctx.pr.report.infra.entity.payment.refundsetting.refundpadding.QrfdtRefundPaddingSet;
import nts.uk.ctx.pr.report.infra.entity.payment.refundsetting.refundpadding.QrfdtRefundPaddingSetPK;

/**
 * The Class JpaContactItemsSettingRepository.
 */
@Stateless
public class JpaRefundPaddingRepository extends JpaRepository implements RefundPaddingRepository {

	@Override
	public void save(RefundPadding refundPadding) {

		QrfdtRefundPaddingSet entity = new QrfdtRefundPaddingSet();
		refundPadding.saveToMemento(new JpaRefundPaddingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<RefundPadding> findByPrintType(String companyCode, PrintType printType) {
		QrfdtRefundPaddingSetPK pk = new QrfdtRefundPaddingSetPK();
		pk.setCcd(companyCode);
		pk.setPrintType(printType.value);
		// find entity
		Optional<QrfdtRefundPaddingSet> optionalEntity = this.queryProxy().find(pk,
			QrfdtRefundPaddingSet.class);
		if (optionalEntity.isPresent()) {
			return Optional.ofNullable(
				new RefundPadding(new JpaRefundPaddingGetMemento(optionalEntity.get())));
		}
		return Optional.empty();
	}

}
