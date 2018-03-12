package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：集計乖離時間
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAggrDivgTimePK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeID;

	/** 年月 */
	@Column(name = "YM")
	public int yearMonth;
	
	/** 締めID */
	@Column(name = "CLOSURE_ID")
	public int closureId;
	
	/** 締め日 */
	@Column(name = "CLOSURE_DAY")
	public int closureDay;
	
	/** 末日とする */
	@Column(name = "IS_LAST_DAY")
	public int isLastDay;

	/** 乖離時間NO */
	@Column(name = "DVRGEN_TIME_NO")
	public int divergenceTimeNo;
}
