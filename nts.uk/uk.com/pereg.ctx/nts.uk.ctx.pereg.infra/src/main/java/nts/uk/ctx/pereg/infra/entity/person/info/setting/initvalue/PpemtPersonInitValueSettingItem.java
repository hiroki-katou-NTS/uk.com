package nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEMT_PER_INIT_SET_ITEM")
public class PpemtPersonInitValueSettingItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtPersonInitValueSettingItemPk settingItemPk;

	@Basic(optional = false)
	@Column(name = "REF_METHOD_ATR")
	public int refMethodAtr;

	@Basic(optional = true)
	@Column(name = "SAVE_DATA_TYPE")
	public Integer saveDataType;

	@Basic(optional = true)
	@Column(name = "STRING_VAL")
	public String stringValue;

	@Basic(optional = true)
	@Column(name = "INT_VAL")
	public BigDecimal intValue;

	@Basic(optional = true)
	@Column(name = "DATE_VAL")
	public GeneralDate dateValue;

	@Override
	protected Object getKey() {
		return settingItemPk;
	}

	public PpemtPersonInitValueSettingItem updateFromDomain(PerInfoInitValueSetItem domain) {
		this.refMethodAtr = domain.getRefMethodType().value;
		if (this.refMethodAtr == 2) {
			this.saveDataType = domain.getSaveDataType().value;
			if (domain.getSaveDataType().value == 1) {
				this.stringValue = domain.getStringValue().v();
				this.intValue = null;
				this.dateValue = null;

			} else if (domain.getSaveDataType().value == 2) {

				this.intValue = domain.getIntValue().v();
				this.stringValue = null;
				this.dateValue = null;

			} else if (domain.getSaveDataType().value == 3) {

				if (domain.getDateValue() != null) {
					this.dateValue = domain.getDateValue();
					this.stringValue = null;
					this.intValue = null;
				}

			}

		}else {
			this.stringValue = null;
			this.intValue = null;
			this.dateValue = null;
		}

		return this;
	}

}
