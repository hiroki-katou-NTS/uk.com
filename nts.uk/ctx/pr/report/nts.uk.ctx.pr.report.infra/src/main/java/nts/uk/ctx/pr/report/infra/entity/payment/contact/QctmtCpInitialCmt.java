/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.contact;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class QismtLaborInsuOffice.
 */
@Getter
@Setter
@Entity
@Table(name = "QCTMT_CP_INITIAL_CMT")
public class QctmtCpInitialCmt extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt labor insu office PK. */
	@EmbeddedId
	protected QctmtCpInitialCmtPK qctmtCpInitialCmtPK;

	/** The li office name. */
	@Basic(optional = false)
	@Column(name = "COMMENT")
	private String comment;

	/**
	 * Instantiates a new qcmt comment month cp.
	 */
	public QctmtCpInitialCmt() {
		super();
	}

	/**
	 * Instantiates a new qismt labor insu office.
	 *
	 * @param qismtLaborInsuOfficePK
	 *            the qismt labor insu office PK
	 */
	public QctmtCpInitialCmt(QctmtCpInitialCmtPK qctmtCpInitialCmtPK) {
		this.qctmtCpInitialCmtPK = qctmtCpInitialCmtPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.qctmtCpInitialCmtPK;
	}

}
