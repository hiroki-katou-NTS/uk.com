package nts.uk.ctx.at.record.infra.entity.dailyattendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshstDailyAttdItemAuthPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Column(name = "AUTHORITY_ID")
	public BigDecimal authorityId;
	@Column(name = "ATTENDANCE_ITEM_ID")
	public BigDecimal attendanceItemId;
}
