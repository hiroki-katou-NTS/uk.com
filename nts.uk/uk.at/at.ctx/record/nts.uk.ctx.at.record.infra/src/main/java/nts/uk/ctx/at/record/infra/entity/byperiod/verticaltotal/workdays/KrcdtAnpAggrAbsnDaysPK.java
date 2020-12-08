package nts.uk.ctx.at.record.infra.entity.byperiod.verticaltotal.workdays;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：集計欠勤日数
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpAggrAbsnDaysPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeId;
	/** 任意集計枠コード */
	@Column(name = "FRAME_CODE")
	public String frameCode;
	/** 欠勤枠NO */
	@Column(name = "ABSENCE_FRAME_NO")
	public int absenceFrameNo;
}
