package nts.uk.ctx.at.function.infra.entity.attendancerecord;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The persistent class for the KFNST_ATTND_REC database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNST_ATTND_REC")
public class KfnstAttndRec extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KfnstAttndRecPK id;

	/** The attribute. */
	@Column(name = "ATTRIBUTE")
	private BigDecimal attribute;

	/** The item name. */
	@Column(name = "ITEM_NAME")
	private String itemName;

	/** The use atr. */
	@Column(name = "USE_ATR")
	private BigDecimal useAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}