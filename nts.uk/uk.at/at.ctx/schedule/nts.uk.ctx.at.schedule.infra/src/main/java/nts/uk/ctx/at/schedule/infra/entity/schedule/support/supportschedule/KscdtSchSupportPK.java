/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.support.supportschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Embeddable
public class KscdtSchSupportPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "SID")
	public String sid;

	@Column(name = "YMD")
	public GeneralDate date;
	
	@Column(name = "SERIAL_NO")
	public int serialNo;

	public KscdtSchSupportPK(String sid, GeneralDate date, int serialNo) {
		this.sid = sid;
		this.date = date;
		this.serialNo = serialNo;
	}

	public KscdtSchSupportPK() {
		super();
	}

}
