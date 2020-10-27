/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.personinfoctgdata;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author danpv
 *
 */
@Entity
@Table(name = "PPEMT_PER_INFO_ITEM_DATA")
@AllArgsConstructor
@NoArgsConstructor
public class PpemtPerInfoItemData extends ContractUkJpaEntity {

	@EmbeddedId
	public PpemtPerInfoItemDataPK primaryKey;

	@Column(name = "SAVE_DATA_ATR")
	public int saveDataAtr;

	@Column(name = "STRING_VAL")
	public String stringVal;

	@Column(name = "INT_VAL")
	public BigDecimal intVal;

	@Column(name = "DATE_VAL")
	public GeneralDate dateVal;

	@Override
	protected Object getKey() {
		return this.primaryKey;
	}

}
