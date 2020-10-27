package nts.uk.ctx.at.record.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtDayTsAtdPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "SID")
	public String employeeId;

	@Column(name = "YMD")
	public GeneralDate ymd;

}
