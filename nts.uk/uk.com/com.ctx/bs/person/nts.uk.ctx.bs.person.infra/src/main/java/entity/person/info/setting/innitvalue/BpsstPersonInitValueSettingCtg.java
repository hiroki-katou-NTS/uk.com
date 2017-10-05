package entity.person.info.setting.innitvalue;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BPSST_PER_INIT_SET_CTG")
public class BpsstPersonInitValueSettingCtg extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BpsstPersonInitValueSettingCtgPk settingCtgPk;

	@Basic(optional = false)
	@Column(name = "PER_INIT_SET_ID")
	public String settingId;

	@Override
	protected Object getKey() {
		return settingCtgPk;
	}

}
