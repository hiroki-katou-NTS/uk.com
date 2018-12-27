package nts.uk.ctx.at.record.infra.entity.weekly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：期間別の時間外超過項目
 * @author shuichu_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtWekExcoutTimePK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeId;
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
	/** 週NO */
	@Column(name = "WEEK_NO")
	public int weekNo;
	/** 内訳NO */
	@Column(name = "BREAKDOWN_NO")
	public int breakdownNo;
}
