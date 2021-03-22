package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.snapshot;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
@Getter
public class KscdtSnapshotPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 社員ID **/
	@Column(name = "SID")
	public String sid;
	
	/** 年月日 **/
	@Column(name = "YMD")
	public GeneralDate ymd;

}