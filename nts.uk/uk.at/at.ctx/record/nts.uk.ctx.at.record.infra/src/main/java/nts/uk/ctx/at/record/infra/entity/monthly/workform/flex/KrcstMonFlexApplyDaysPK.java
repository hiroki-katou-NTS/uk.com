package nts.uk.ctx.at.record.infra.entity.monthly.workform.flex;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：フレックス勤務の時短日割適用日数
 * @author shuichu_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcstMonFlexApplyDaysPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
}
