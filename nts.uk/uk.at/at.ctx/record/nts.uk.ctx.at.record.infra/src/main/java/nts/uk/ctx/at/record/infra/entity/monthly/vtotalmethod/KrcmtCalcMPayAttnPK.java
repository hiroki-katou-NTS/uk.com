package nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：給与出勤日数 
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcmtCalcMPayAttnPK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** 勤務種類コード */
	@Column(name = "WORK_TYPE_CODE")
	public String workTypeCode;
}
