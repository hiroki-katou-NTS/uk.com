package entity.person.info.setting;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BSYST_EMP_COPY_SET")
public class BsystEmployeeCopySetting {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BsystEmployeeCopySettingPk BsystEmployeeCopySettingPk;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;
}
