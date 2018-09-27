package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 36年間超過月: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqdtYear36OverMonthPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 申請ID
	 */
	@Basic(optional = false)
	@Column(name = "APP_ID")
	public String appId;

	/**
	 * 36年間超過月
	 */
	@Basic(optional = false)
	@Column(name = "OVER_MONTH")
	public int overMonth;
}
