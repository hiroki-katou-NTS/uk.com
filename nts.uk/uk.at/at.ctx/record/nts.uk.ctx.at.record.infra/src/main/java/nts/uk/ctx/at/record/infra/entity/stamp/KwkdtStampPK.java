package nts.uk.ctx.at.record.infra.entity.stamp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KwkdtStampPK implements Serializable {
	private static final long serialVersionUID = 1L;
	/* カード番号 */
	@Column(name = "CARD_NUMBER")
	public String cardNumber;
	/* 勤怠時刻 */
	@Column(name = "ATTENDANCE_CLOCK")
	public int attendanceTime;

	/* 打刻区分 */
	@Column(name = "STAMP_ATR")
	public int stampAtr;

	/* 年月日 */
	@Column(name = "STAMP_DATE")
	public GeneralDateTime stampDate;

}
