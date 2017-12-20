package nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpemtPersonInitValueSettingItemPk implements Serializable {

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "PER_INFO_ITEM_DEF_ID")
	public String perInfoItemDefId;
	
	@Basic(optional = false)
	@Column(name = "PER_INFO_CTG_ID")
	public String perInfoCtgId;

	@Basic(optional = false)
	@Column(name = "PER_INIT_SET_ID")
	public String settingId;
}
