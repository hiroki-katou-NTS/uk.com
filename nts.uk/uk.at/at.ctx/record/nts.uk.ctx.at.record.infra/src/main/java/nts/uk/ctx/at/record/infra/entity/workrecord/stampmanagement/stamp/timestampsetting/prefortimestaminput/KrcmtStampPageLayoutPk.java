package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author phongtq
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtStampPageLayoutPk implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/**
	 * 運用方法 (0:共有打刻 1:個人利用 2:ICカード 3:スマホ打刻)
	 * 0: Shared stamp, 1: Personal use, 2: IC card, 3: smartphone engraving
	 */
	@Column(name = "STAMP_MEANS")
	public int stampMeans;

	/** ページNO */
	@Column(name = "PAGE_NO")
	public int pageNo;
}
