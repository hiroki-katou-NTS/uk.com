/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class KscdtBasicSchedulePK.
 */
@Getter
@Setter
@Embeddable
public class KscdtBasicSchedulePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "SID")
	public String sId;

	@Column(name = "YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate date;

	/**
	 * Instantiates a new kscdt basic schedule PK.
	 *
	 * @param sId the s id
	 * @param date the date
	 */
	public KscdtBasicSchedulePK(String sId, GeneralDate date) {
		this.sId = sId;
		this.date = date;
	}

	/**
	 * Instantiates a new kscdp basic schedule PK.
	 */
	public KscdtBasicSchedulePK() {
		super();
	}

}
