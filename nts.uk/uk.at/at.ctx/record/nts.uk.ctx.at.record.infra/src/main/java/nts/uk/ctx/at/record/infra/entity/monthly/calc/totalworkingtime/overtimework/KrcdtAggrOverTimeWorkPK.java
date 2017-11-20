package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * プライマリキー：集計残業時間
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAggrOverTimeWorkPK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeID;

	/** 開始年月日 */
	@Column(name = "START_YMD")
	public GeneralDate startYmd;

	/** 終了年月日 */
	@Column(name = "END_YMD")
	public GeneralDate endYmd;

	/** 残業枠NO */
	@Column(name = "OVR_TM_WORK_FRAME_NO")
	public int overTimeWorkFrameNo;
}
