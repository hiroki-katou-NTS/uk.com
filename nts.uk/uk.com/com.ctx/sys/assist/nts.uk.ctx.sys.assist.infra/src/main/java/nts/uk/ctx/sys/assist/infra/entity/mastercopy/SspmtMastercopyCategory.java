package nts.uk.ctx.sys.assist.infra.entity.mastercopy;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The Class SspmtMastercopyCategory.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="SSPMT_MASTERCOPY_CATEGORY")
public class SspmtMastercopyCategory extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The master copy id. */
	@Id
	@Column(name="MASTER_COPY_ID")
	private String masterCopyId;

	/** The category order. */
	@Column(name="CATEGORY_ORDER")
	private BigDecimal categoryOrder;

	/** The master copy category. */
	@Column(name="MASTER_COPY_CATEGORY")
	private String masterCopyCategory;

	/** The system type. */
	@Column(name="SYSTEM_TYPE")
	private BigDecimal systemType;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.masterCopyId;
	}

}