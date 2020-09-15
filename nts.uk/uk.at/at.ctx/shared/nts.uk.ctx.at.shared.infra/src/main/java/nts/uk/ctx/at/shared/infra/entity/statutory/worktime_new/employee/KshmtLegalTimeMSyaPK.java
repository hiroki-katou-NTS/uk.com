package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class KshmtLegalTimeMSyaPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String cid;

	/** 社員ID */
	@Column(name = "SID")
	public String sid;

	/** 労働勤務区分 */
	@Column(name = "LABOR_TYPE")
	public int type;

	/** 年月 */
	@Column(name = "YM")
	public int ym;
}
