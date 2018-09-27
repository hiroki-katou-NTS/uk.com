package nts.uk.ctx.at.record.infra.entity.byperiod;

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
public class KrcdtAnpExcoutTimePK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeID;
	/** 任意集計枠コード */
	@Column(name = "FRAME_CODE")
	public String frameCode;
	/** 内訳NO */
	@Column(name = "BREAKDOWN_NO")
	public int breakdownNo;
}
