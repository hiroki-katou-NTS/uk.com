package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 特別休暇
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP")
public class KshmtHdsp extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshmtHdspPK pk;

	/* 特別休暇名称 */
	@Column(name = "SPHD_NAME")
	public String specialHolidayName;
	
	/*自動付与区分*/
	@Column(name = "SPHD_AUTO_GRANT")
	public int autoGrant;
	
	/* メモ */
	@Column(name = "MEMO")
	public String memo;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
