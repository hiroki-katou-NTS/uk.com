package nts.uk.ctx.pereg.infra.entity.copysetting.item;

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
@Table(name = "PPEST_EMP_COPY_SET_ITEM")
public class PpestEmployeeCopySettingItem extends ContractUkJpaEntity implements Serializable {

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpestEmployeeCopySettingItemPk ppestEmployeeCopySettingItemPk;

	@Basic(optional = false)
	@Column(name = "PER_INFO_CTG_ID")
	public String categoryId;
	
	public PpestEmployeeCopySettingItem(String categoryId, String itemId) {
		this.ppestEmployeeCopySettingItemPk = new PpestEmployeeCopySettingItemPk(itemId);
		this.categoryId = categoryId;
	}

	@Override
	protected Object getKey() {
		return ppestEmployeeCopySettingItemPk;
	}
	
	public String getCategoryId() {
		return categoryId;
	}
	
}
