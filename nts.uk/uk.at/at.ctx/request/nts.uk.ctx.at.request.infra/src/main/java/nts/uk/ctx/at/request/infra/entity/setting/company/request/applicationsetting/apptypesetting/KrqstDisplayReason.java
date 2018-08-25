package nts.uk.ctx.at.request.infra.entity.setting.company.request.applicationsetting.apptypesetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQST_DISPLAY_REASON")
public class KrqstDisplayReason extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KrqstDisplayReasonPK krqstDisplayReasonPK;
	
	/** 定型理由の表示 */
	@Column(name = "DISPLAY_FIXED_REASON")
	public int displayFixedReason;
	
	/** 申請理由の表示 */
	@Column(name = "DISPLAY_APP_REASON")
	public int displayAppReason;

	@Override
	protected Object getKey() {
		return krqstDisplayReasonPK;
	}
	
}
