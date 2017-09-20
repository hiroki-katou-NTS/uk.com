package entity.person.info.setting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;

public class BsystEmployeeCopySettingItemPk implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "PER_INFO_ITEM_DEFINITION_ID")
	public String perInfoItemDefId;

}
