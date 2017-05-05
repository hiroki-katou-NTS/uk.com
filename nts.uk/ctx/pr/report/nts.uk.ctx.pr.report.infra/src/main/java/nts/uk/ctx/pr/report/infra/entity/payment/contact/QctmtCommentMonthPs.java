/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.contact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class QctmtCommentMonthPs.
 */
@Getter
@Setter
@Entity
@Table(name = "QCTMT_COMMENT_MONTH_PS")
public class QctmtCommentMonthPs extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qctmt comment month ps PK. */
	@EmbeddedId
	protected QctmtCommentMonthPsPK qctmtCommentMonthPsPK;

	/** The comment. */
	@Column(name = "COMMENT")
	private String comment;

	/**
	 * Instantiates a new qctmt comment month ps.
	 */
	public QctmtCommentMonthPs() {
		super();
	}

	public QctmtCommentMonthPs(QctmtCommentMonthPsPK qctmtCommentMonthPsPK, String comment) {
		super();
		this.qctmtCommentMonthPsPK = qctmtCommentMonthPsPK;
		this.comment = comment;
	}

	@Override
	protected Object getKey() {
		return this.qctmtCommentMonthPsPK;
	}
}
