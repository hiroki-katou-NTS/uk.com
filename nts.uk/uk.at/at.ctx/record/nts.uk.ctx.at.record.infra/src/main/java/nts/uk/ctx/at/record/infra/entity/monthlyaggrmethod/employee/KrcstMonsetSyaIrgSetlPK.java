package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：社員の変形労働の精算期間
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcstMonsetSyaIrgSetlPK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeId;

	/** 開始月 */
	@Column(name = "START_MONTH")
	public int startMonth;
}
