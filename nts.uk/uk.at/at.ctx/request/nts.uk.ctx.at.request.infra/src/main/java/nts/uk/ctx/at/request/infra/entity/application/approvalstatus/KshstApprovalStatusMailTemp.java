package nts.uk.ctx.at.request.infra.entity.application.approvalstatus;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Content;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Subject;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_APPROVAL_MAIL_TEMP")
public class KshstApprovalStatusMailTemp extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KshstApprovalStatusMailTempPk approvalStatusMailTempPk;

	/**
	 * URL承認埋込
	 */
	@Basic(optional = false)
	@Column(name = "URL_APPROVAL_EMBED")
	public int urlApprovalEmbed;

	/**
	 * URL日別埋込
	 */
	@Basic(optional = false)
	@Column(name = "URL_DAY_EMBED")
	public int urlDayEmbed;

	/**
	 * URL月別埋込
	 */
	@Basic(optional = false)
	@Column(name = "URL_MONTH_EMBED")
	public int urlMonthEmbed;

	/**
	 * メール件名
	 */
	@Basic(optional = false)
	@Column(name = "SUBJECT")
	public String subject;

	/**
	 * メール本文
	 */
	@Basic(optional = false)
	@Column(name = "TEXT")
	public String text;

	@Override
	protected Object getKey() {
		return approvalStatusMailTempPk;
	}

	public ApprovalStatusMailTemp toDomain() {
		return new ApprovalStatusMailTemp(this.approvalStatusMailTempPk.cid,
				EnumAdaptor.valueOf(this.approvalStatusMailTempPk.type, ApprovalStatusMailType.class),
				EnumAdaptor.valueOf(this.urlApprovalEmbed, NotUseAtr.class),
				EnumAdaptor.valueOf(this.urlDayEmbed, NotUseAtr.class),
				EnumAdaptor.valueOf(this.urlMonthEmbed, NotUseAtr.class), new Subject(this.subject),
				new Content(this.text));
	}

	public static KshstApprovalStatusMailTemp toEntity(ApprovalStatusMailTemp domain) {
		return new KshstApprovalStatusMailTemp(
				new KshstApprovalStatusMailTempPk(domain.getCid(), domain.getMailType().value),
				domain.getUrlApprovalEmbed().value, domain.getUrlDayEmbed().value, domain.getUrlMonthEmbed().value,
				domain.getMailSubject().v(), domain.getMailContent().v());
	}

}
