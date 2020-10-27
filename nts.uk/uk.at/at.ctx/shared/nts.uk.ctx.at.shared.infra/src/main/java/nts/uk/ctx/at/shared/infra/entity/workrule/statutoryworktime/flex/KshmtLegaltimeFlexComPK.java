package nts.uk.ctx.at.shared.infra.entity.workrule.statutoryworktime.flex;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：フレックス勤務所定労働時間取得
 * @author shuichu_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KshmtLegaltimeFlexComPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
}
