package nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting;

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
 * The persistent class for the KFNST_ATTND_REC_OUT_SET database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="KFNST_ATTND_REC_OUT_SET")
public class KfnstAttndRecOutSet extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KfnstAttndRecOutSetPK id;

	/** The name. */
	@Column(name="NAME")
	private String name;

	/** The seal use atr. */
	@Column(name="SEAL_USE_ATR")
	private BigDecimal sealUseAtr;
	
	/** The name use atr. */
	@Column(name="NAME_USE_ATR")
	private BigDecimal nameUseAtr;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}