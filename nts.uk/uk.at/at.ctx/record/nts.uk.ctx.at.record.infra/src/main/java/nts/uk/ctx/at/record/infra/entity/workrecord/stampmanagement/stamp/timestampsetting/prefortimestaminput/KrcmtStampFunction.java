package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author chungnt
 *
 */

@Entity
@NoArgsConstructor
@Table(name = "KRCMT_STAMP_FUNCTION")
public class KrcmtStampFunction extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cid;

	/**
	 * 打刻後の実績表示
	 */
	@Basic(optional = false)
	@Column(name = "RECORD_DISPLAY_ART")
	public int recordDisplayArt;
	/**

	 * GoogleMap利用するか
	 */
	@Basic(optional = false)
	@Column(name = "GOOGLE_MAP_USE_ART")
	public int googleMapUseArt;

	/**
	 * マップ表示アドレス
	 */
	@Basic(optional = true)
	@Column(name = "MAP_ADDRESS")
	public String mapAddress;

	@Override
	protected Object getKey() {
		return this.cid;
	}

	public void update(CommonSettingsStampInput domain) {
		this.googleMapUseArt = domain.isGooglemap() ? 1 : 0;
		this.mapAddress = domain.getMapAddres();
	}
}
