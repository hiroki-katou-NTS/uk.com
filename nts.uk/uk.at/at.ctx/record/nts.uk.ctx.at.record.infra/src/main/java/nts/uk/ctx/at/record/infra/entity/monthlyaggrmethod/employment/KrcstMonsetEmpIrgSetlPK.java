package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：雇用の変形労働の精算期間
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcstMonsetEmpIrgSetlPK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** 雇用コード */
	@Column(name = "EMP_CD")
	public String employmentCd;

	/** 開始月 */
	@Column(name = "START_MONTH")
	public int startMonth;
}
