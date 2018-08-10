package nts.uk.ctx.sys.assist.infra.entity.mastercopy;

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
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The persistent class for the SSPMT_MASTERCOPY_DATA database table.
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SSPMT_MASTERCOPY_DATA")
public class SspmtMastercopyData extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private SspmtMastercopyDataPK id;

	/** The copy atr. */
	@Column(name="COPY_ATR")
	private BigDecimal copyAtr;

	/** The key 1. */
	@Column(name="KEY1")
	private String key1;

	/** The key 2. */
	@Column(name="KEY2")
	private String key2;

	/** The key 3. */
	@Column(name="KEY3")
	private String key3;

	/** The key 4. */
	@Column(name="KEY4")
	private String key4;

	/** The key 5. */
	@Column(name="KEY5")
	private String key5;

	/** The table name. */
	@Column(name="TABLE_NAME")
	private String tableName;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}