package nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEMT_PER_INIT_SET")
public class PpemtPersonInitValueSetting extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtPersonInitValueSettingPk initValueSettingPk;

	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "PER_INIT_SET_CD")
	public String settingCode;

	@Basic(optional = false)
	@Column(name = "PER_INIT_SET_NAME")
	public String settingName;

	@Override
	protected Object getKey() {
		return initValueSettingPk;
	}

}
