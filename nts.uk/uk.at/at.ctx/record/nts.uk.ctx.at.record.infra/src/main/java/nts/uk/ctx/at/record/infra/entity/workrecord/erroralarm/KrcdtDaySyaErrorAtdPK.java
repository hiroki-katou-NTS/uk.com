package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtDaySyaErrorAtdPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "ID")
	public String iD;
	
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attendanceItemId;

}
