/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class KmnmtAffiliJobTitleHistPK.
 */
@Getter
@Setter
@Embeddable
public class KmnmtAffiliJobTitleHistPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The empId. */
	@Column(name = "SID")
	private String empId;

	/** The pos id. */
	@Column(name = "JOB_ID")
	private String jobId;
	
	 /** The str D. */
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;

	/**
	 * Instantiates a new kmnmt affili job title hist PK.
	 */
	public KmnmtAffiliJobTitleHistPK() {
		super();
	}

}
