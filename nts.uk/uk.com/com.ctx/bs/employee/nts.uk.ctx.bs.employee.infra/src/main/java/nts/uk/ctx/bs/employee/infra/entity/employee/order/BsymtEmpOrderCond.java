/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.employee.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class BsymtEmpOrderCond.
 */
@Entity
@Setter
@Getter
@Table(name = "BSYMT_EMP_ORDER_COND")
public class BsymtEmpOrderCond extends JpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private BsymtEmpOrderCondPK id;

	/** The condition order. */
	@Column(name = "CONDITION_ORDER")
	private Integer conditionOrder;

	/**
	 * Instantiates a new bsymt emp order cond.
	 *
	 * @param id the id
	 */
	public BsymtEmpOrderCond(BsymtEmpOrderCondPK id) {
		this.id = id;
	}
	/**
	 * Instantiates a new bsymt emp order cond.
	 */
	public BsymtEmpOrderCond() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}