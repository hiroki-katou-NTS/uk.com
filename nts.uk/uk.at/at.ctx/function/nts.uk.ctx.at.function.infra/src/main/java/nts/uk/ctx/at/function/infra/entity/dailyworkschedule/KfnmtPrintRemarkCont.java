/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import java.math.BigDecimal;


/**
 * The Class KfnmtPrintRemarkCont.
 * @author HoangDD
 */
@Entity
@Table(name="KFNMT_PRINT_REMARK_CONT")
@Getter
@Setter
@NoArgsConstructor
public class KfnmtPrintRemarkCont extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KfnmtPrintRemarkContPK id;

	/** The use cls. */
	@Column(name="USE_CLS")
	private BigDecimal useCls;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}