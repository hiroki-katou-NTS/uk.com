package entity.person.info.setting.copysetting;

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
@Table(name = "BSYST_EMP_COPY_SET")
public class BsystEmployeeCopySetting  extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BsystEmployeeCopySettingPk BsystEmployeeCopySettingPk;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return BsystEmployeeCopySettingPk;
	}
}
