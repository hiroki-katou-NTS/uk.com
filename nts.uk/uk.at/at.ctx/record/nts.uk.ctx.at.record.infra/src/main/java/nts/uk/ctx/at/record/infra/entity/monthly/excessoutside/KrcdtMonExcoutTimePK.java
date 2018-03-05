package nts.uk.ctx.at.record.infra.entity.monthly.excessoutside;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：時間外超過
 * @author shuichu_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonExcoutTimePK implements Serializable {

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

	/** 内訳NO */
	@Column(name = "BREAKDOWN_NO")
	public int breakdownNo;

	/** 超過NO */
	@Column(name = "EXCESS_NO")
	public int excessNo;
}
