/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.log.infra.entity.loginrecord;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The persistent class for the SRCDT_LOGIN_RECORD database table.
 * 
 */
@Entity
@Table(name="SRCDT_LOGIN_RECORD")
@Getter
@Setter
public class SrcdtLoginRecord extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The operation id. */
	@Id
	@Column(name="OPERATION_ID")
	private String operationId;

	/** The lock status. */
	@Column(name="LOCK_STATUS")
	private Integer lockStatus;

	/** The login method. */
	@Column(name="LOGIN_METHOD")
	private Integer loginMethod;

	/** The login status. */
	@Column(name="LOGIN_STATUS")
	private Integer loginStatus;

	/** The remarks. */
	@Column(name="REMARKS")
	private String remarks;

	/** The url. */
	@Column(name="URL")
	private String url;

	/**
	 * Instantiates a new srcdt login record.
	 */
	public SrcdtLoginRecord() {
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.operationId;
	}

}