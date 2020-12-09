package nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEMT_ENTRY_INIT_CTG")
public class PpemtPersonInitValueSettingCtg extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtPersonInitValueSettingCtgPk settingCtgPk;

	@Override
	protected Object getKey() {
		return settingCtgPk;
	}

}
