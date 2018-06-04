package nts.uk.ctx.at.record.infra.entity.remainingnumber.subhdmana;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcmtLeaveDayOffManaPK implements Serializable{
	private static final long serialVersionUID = 1L;

	// 休出ID
	@Column(name="LEAVE_ID")
	public String leaveID;
	
	// 代休ID	
	@Column(name="COM_DAYOFF_ID")
	public String comDayOffID;
}
