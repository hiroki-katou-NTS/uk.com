package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class KshmtLegalTimeMEmpPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String cid;

	/** 雇用コード */
	@Column(name = "EMP_CD")
	public String empCD;

	/** 労働勤務区分 */
	@Column(name = "LABOR_TYPE")
	public int type;

	/** 年月 */
	@Column(name = "YM")
	public int ym;
}
