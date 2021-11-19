package nts.uk.ctx.at.aggregation.infra.entity.form9;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KagmtForm9OutputLayoutPk {

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/**
	 * コード
	 */
	@Column(name = "CODE")
	public String code;

}
