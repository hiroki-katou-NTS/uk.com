package nts.uk.ctx.at.record.infra.entity.byperiod.verticaltotal.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：月別実績の医療時間
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpMedicalTimePK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeId;
	/** 任意集計枠コード */
	@Column(name = "FRAME_CODE")
	public String frameCode;
	/** 日勤夜勤区分 */
	@Column(name = "DAY_NIGHT_ATR")
	public int dayNightAtr;
}
