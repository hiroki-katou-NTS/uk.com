package nts.uk.ctx.at.record.infra.entity.daily.temporarytime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * プライマリキー：日別実績の臨時枠時間
 * @author shuichi_ishida
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtDayTempFrmTimePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String sid;
	/** 年月日 */
	@Column(name = "YMD")
	public GeneralDate ymd;
	/** 勤務NO */
	@Column(name = "WORK_NO")
	public int workNo;
}
