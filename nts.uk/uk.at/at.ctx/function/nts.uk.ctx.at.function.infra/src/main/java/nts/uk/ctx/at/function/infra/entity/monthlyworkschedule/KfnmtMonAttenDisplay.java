package nts.uk.ctx.at.function.infra.entity.monthlyworkschedule;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import java.math.BigDecimal;

// TODO: Auto-generated Javadoc
/**
 * The persistent class for the KFNMT_MON_ATTEN_DISPLAY database table.
 * 
 */
@Entity

/**
 * Instantiates a new kfnmt mon atten display.
 */
@NoArgsConstructor

/**
 * Gets the atd display.
 *
 * @return the atd display
 */
@Getter

/**
 * Sets the atd display.
 *
 * @param atdDisplay the new atd display
 */
@Setter
@Table(name = "KFNMT_MON_ATTEN_DISPLAY")
public class KfnmtMonAttenDisplay extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KfnmtMonAttenDisplayPK id;

	/** The atd display. */
	@Column(name = "ATD_DISPLAY")
	private BigDecimal atdDisplay;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.id;
	}

}