package entity.person.info.setting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;

public class BsystEmployeeCopySettingItemPk implements Serializable  {

	@Basic(optional = false)
	@Column(name = "PER_INFO_ITEM_DEFINITION_ID")
	public String perInfoItemDefId;

}
