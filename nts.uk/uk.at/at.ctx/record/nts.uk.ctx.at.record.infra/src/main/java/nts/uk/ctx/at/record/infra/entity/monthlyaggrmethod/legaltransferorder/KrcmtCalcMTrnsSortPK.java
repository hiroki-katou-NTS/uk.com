package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.legaltransferorder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：月別実績の法定内振替順設定
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcmtCalcMTrnsSortPK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
}
