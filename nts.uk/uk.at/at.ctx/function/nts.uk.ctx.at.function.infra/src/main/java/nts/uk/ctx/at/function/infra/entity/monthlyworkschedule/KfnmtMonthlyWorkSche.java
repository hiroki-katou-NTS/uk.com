package nts.uk.ctx.at.function.infra.entity.monthlyworkschedule;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import java.math.BigDecimal;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The persistent class for the KFNMT_MONTHLY_WORK_SCHE database table.
 * 
 */

/**
 * Gets the lst kfnmt mon atten display.
 *
 * @return the lst kfnmt mon atten display
 */
@Getter

/**
 * Sets the lst kfnmt mon atten display.
 *
 * @param lstKfnmtMonAttenDisplay the new lst kfnmt mon atten display
 */
@Setter
@Entity
@Table(name = "KFNMT_MONTHLY_WORK_SCHE")

/**
 * Instantiates a new kfnmt monthly work sche.
 */
@NoArgsConstructor
public class KfnmtMonthlyWorkSche extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KfnmtMonthlyWorkSchePK id;

	/** The is print. */
	@Column(name = "IS_PRINT")
	private BigDecimal isPrint;

	/** The item name. */
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	/** The remark input no. */
	@Column(name="REMARK_INPUT_NO")
	private BigDecimal remarkInputNo; 

	/** The lst kfnmt mon atten display. */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID"),
			@JoinColumn(name = "ITEM_CD", referencedColumnName = "ITEM_CD") })
	private List<KfnmtMonAttenDisplay> lstKfnmtMonAttenDisplay;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.id;
	}

}