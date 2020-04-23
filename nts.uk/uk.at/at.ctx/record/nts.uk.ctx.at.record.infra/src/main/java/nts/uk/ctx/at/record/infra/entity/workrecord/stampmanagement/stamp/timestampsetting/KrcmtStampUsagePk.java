package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * 
 * @author sonnlb
 * 
 *         打刻機能の利用設定PK
 *
 */
public class KrcmtStampUsagePk implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
}
