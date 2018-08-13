package nts.uk.ctx.at.record.infra.entity.byperiod.verticaltotal.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：集計外出
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpAggrGooutPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeID;
	/** 任意集計枠コード */
	@Column(name = "FRAME_CODE")
	public String frameCode;
	/** 外出理由 */
	@Column(name = "GOOUT_REASON")
	public int goOutReason;
}
