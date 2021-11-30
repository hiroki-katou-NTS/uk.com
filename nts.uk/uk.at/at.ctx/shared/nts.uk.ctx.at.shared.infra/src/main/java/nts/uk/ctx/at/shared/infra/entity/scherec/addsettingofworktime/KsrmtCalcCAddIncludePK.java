package nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：労働時間の加算設定(含める要素を指定)
 * @author shuichi_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KsrmtCalcCAddIncludePK implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String cid;
	/** 労働制 */
	@Column(name = "LABOR_SYSTEM_ATR")
	public int laborSystemAtr;
	/** 割増区分 */
	@Column(name = "PREMIUM_ATR")
	public int premiumAtr;
}
