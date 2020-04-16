package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
/**
 * 
 * @author phongtq
 *
 */
public class BsympAffWorkPlaceGroupPk implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CID")
	public String CID;

	/** 職場グループID */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WKPGRP_ID")
	public String WKPGRPID;
	
	/** 職場ID */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WKP_ID")
	public String WKPID;

}
