/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.refundsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.PrintType;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPadding;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPaddingRepository;

/**
 * The Class JpaContactItemsSettingRepository.
 */
@Stateless
public class JpaRefundPaddingRepository extends JpaRepository implements RefundPaddingRepository {

	@Override
	public void add(RefundPadding office) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(RefundPadding office) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<RefundPadding> findById(String companyCode, PrintType printType) {
		// TODO Auto-generated method stub
		return null;
	}

}
