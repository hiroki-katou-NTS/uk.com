package nts.uk.ctx.at.request.infra.entity.setting.requestofearch;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_WP_APP_CONFIG")
public class KrqstWpAppConfig extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KrqstWpAppConfigPK krqstWpAppConfigPK;
	
	/**
	 * 申請時の承認者の選択
	 */
	@Column(name = "SELECT_OF_APPROVERS_FLG")
	public int selectOfApproversFlg;
	
	@Override
	protected Object getKey() {
		return krqstWpAppConfigPK;
	}

}
