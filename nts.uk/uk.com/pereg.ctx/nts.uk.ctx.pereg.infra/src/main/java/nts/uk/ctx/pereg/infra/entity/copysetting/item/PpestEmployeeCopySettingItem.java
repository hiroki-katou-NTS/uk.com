package nts.uk.ctx.pereg.infra.entity.copysetting.item;

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
@Table(name = "PPEST_EMP_COPY_SET_ITEM")
public class PpestEmployeeCopySettingItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpestEmployeeCopySettingItemPk ppestEmployeeCopySettingItemPk;

	@Basic(optional = false)
	@Column(name = "PER_INFO_CTG_ID")
	public String categoryId;

	@Override
	protected Object getKey() {
		return ppestEmployeeCopySettingItemPk;
	}
}
