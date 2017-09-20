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
@Table(name = "BSYST_EMP_COPY_SET_ITEM")
public class BsystEmployeeCopySettingItem {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BsystEmployeeCopySettingItemPk BsystEmployeeCopySettingItemPk;
	
	@Basic(optional = false)
	@Column(name = "COPY_CTG_ID")
	public String copyCategoryId;
}
