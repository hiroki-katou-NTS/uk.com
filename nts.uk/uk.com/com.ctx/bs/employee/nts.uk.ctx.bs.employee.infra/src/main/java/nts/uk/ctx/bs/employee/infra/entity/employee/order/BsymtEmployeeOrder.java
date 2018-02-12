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

import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The Class BsymtEmployeeOrder.
 */
@Entity
@Table(name="BSYMT_EMPLOYEE_ORDER")
public class BsymtEmployeeOrder extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private BsymtEmployeeOrderPK id;

	/** The name. */
	@Column(name="NAME")
	private String name;

	/**
	 * Instantiates a new bsymt employee order.
	 *
	 * @param id the id
	 */
	public BsymtEmployeeOrder(BsymtEmployeeOrderPK id) {
		this.id = id;
	}

	/**
	 * Instantiates a new bsymt employee order.
	 */
	public BsymtEmployeeOrder() {
	}
	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}