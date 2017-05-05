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
import nts.uk.ctx.pr.report.dom.payment.contact.ReportComment;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCp;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QcmtCommentMonthCpPK;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCpInitialCmtPK;
import nts.uk.ctx.pr.report.infra.entity.payment.contact.QctmtCpInitialCmt;

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
		
		Optional<QctmtCpInitialCmt> commentInitCp = this.findCommentInitCp(code);
		
		if(commentInitCp.isPresent()){
			jpa.setCommentInitialCp(commentInitCp.get());
		}
		return new ContactItemsSetting(jpa);
	}

	/**
	 * Find comment month cp.
	 *
	 * @param code
	 *            the code
	 * @return the optional
	 */
	private Optional<QcmtCommentMonthCp> findCommentMonthCp(ContactItemsCode code) {
		return this.queryProxy().find(
			new QcmtCommentMonthCpPK(code.getCompanyCode(), code.getProcessingNo().v(),
				PAY_BONUS_ATR, code.getProcessingYm().v(), SPARE_PAY_ATR),
			QcmtCommentMonthCp.class);
	}

	/**
	 * Find comment init cp.
	 *
	 * @param code the code
	 * @return the optional
	 */
	private Optional<QctmtCpInitialCmt> findCommentInitCp(ContactItemsCode code) {
		return this.queryProxy().find(
			new QctmtCpInitialCmtPK(code.getCompanyCode(), PAY_BONUS_ATR, SPARE_PAY_ATR),
			QctmtCpInitialCmt.class);
	}

	@Override
	public void save(ContactItemsSetting contactItemsSetting) {
		this.saveInitialCpComment(contactItemsSetting.getContactItemsCode(),
			contactItemsSetting.getInitialCpComment());
	}

	/**
	 * Save initial cp comment.
	 *
	 * @param code
	 *            the code
	 * @param initialCpComment
	 *            the initial cp comment
	 */
	private void saveInitialCpComment(ContactItemsCode code, ReportComment initialCpComment) {
		QcmtCommentMonthCp entity = new QcmtCommentMonthCp();
		QcmtCommentMonthCpPK pk = new QcmtCommentMonthCpPK();
		pk.setCcd(code.getCompanyCode());
		pk.setPayBonusAtr(PAY_BONUS_ATR);
		pk.setProcessingNo(code.getProcessingNo().v());
		pk.setProcessingYm(code.getProcessingYm().v());
		pk.setSparePayAtr(SPARE_PAY_ATR);
		entity.setQcmtCommentMonthCpPK(pk);
		entity.setComment(initialCpComment.v());
		this.commandProxy().update(entity);
	}

}
