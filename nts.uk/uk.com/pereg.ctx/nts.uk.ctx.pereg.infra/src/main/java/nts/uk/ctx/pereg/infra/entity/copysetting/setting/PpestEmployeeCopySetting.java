package nts.uk.ctx.pereg.infra.entity.copysetting.setting;

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
@Table(name = "PPEST_EMP_COPY_SET")
public class PpestEmployeeCopySetting extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpestEmployeeCopySettingPk ppestEmployeeCopySettingPk;

	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Override
	protected Object getKey() {
		return ppestEmployeeCopySettingPk;
	}
}
