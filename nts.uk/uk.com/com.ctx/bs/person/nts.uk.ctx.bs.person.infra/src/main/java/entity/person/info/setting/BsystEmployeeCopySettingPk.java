package entity.person.info.setting;

import javax.persistence.Basic;
import javax.persistence.Column;

public class BsystEmployeeCopySettingPk {

	@Basic(optional = false)
	@Column(name = "COPY_CTG_ID")
	public String copyCategoryId;

}
