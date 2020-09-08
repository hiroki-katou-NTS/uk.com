package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class KshmtLegalTimeMWkpPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String cid;

	/** 職場ID */
	@Column(name = "WKP_ID")
	public String wkpId;

	/** 労働勤務区分 */
	@Column(name = "LABOR_TYPE")
	public int type;

	/** 年月 */
	@Column(name = "YM")
	public int ym;
}
