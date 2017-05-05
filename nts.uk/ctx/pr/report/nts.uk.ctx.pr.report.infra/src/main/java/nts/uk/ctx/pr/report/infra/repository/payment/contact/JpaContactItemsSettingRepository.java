/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.contact;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSetting;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCp;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCpPK;

/**
 * The Class this.
 */
@Stateless
public class JpaContactItemsSettingRepository extends JpaRepository
	implements ContactItemsSettingRepository {

	/** The pay bonus atr. */
	public static int PAY_BONUS_ATR = 0;

	/** The spare pay atr. */
	public static int SPARE_PAY_ATR = 0;

	@Override
	public ContactItemsSetting findByCode(ContactItemsCode code, List<String> empCds) {
		Optional<QcmtCommentMonthCp> commentMonthCp = this.findCommentMonthCp(code);
		JpaContactItemsSettingGetMemento jpa = new JpaContactItemsSettingGetMemento();
		if (commentMonthCp.isPresent()) {
			jpa.setCommentMonthCp(commentMonthCp.get());
		}
		return new ContactItemsSetting(jpa);
	}

	public Optional<QcmtCommentMonthCp> findCommentMonthCp(ContactItemsCode code) {
		return this.queryProxy().find(
			new QcmtCommentMonthCpPK(code.getCompanyCode(), code.getProcessingNo().v(),
				PAY_BONUS_ATR, code.getProcessingYm().v(), SPARE_PAY_ATR),
			QcmtCommentMonthCp.class);
	}

}
