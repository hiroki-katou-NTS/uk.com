package nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author TanLV
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscdtScheMasterInfoPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SID")
	public String sId;
	
	@Column(name = "YMD")
	public GeneralDate generalDate;
}