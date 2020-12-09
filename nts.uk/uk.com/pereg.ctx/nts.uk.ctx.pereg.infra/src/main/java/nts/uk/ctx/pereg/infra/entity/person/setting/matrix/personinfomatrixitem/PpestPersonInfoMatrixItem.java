/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.setting.matrix.personinfomatrixitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hieult
 *
 */
@Entity
@Table(name = "PPEST_PERSON_INFO_MATRIX")
@NoArgsConstructor
@AllArgsConstructor
public class PpestPersonInfoMatrixItem extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpestPersonInfoMatrixItemPK ppestPersonInfoMatrixItemPK;

	@Column(name = "COLUMN_WIDTH")
	public int columnWidth;

	@Column(name = "REGULATION_ATR")
	public int regulationATR;

	@Override
	protected Object getKey() {
		return ppestPersonInfoMatrixItemPK;
	}

	public PersonInfoMatrixItem toDomain() {
		return PersonInfoMatrixItem.createFromJavatype(this.ppestPersonInfoMatrixItemPK.pInfoCategoryID,
				this.ppestPersonInfoMatrixItemPK.pInfoDefiID, columnWidth, regulationATR);
	}

	public static PpestPersonInfoMatrixItem toEntity(PersonInfoMatrixItem setting) {
		return new PpestPersonInfoMatrixItem(
				new PpestPersonInfoMatrixItemPK(setting.getPInfoCategoryID(), setting.getPInfoItemDefiID()),
				setting.getColumnWidth(), setting.getRegulationATR().value);
	}
}
