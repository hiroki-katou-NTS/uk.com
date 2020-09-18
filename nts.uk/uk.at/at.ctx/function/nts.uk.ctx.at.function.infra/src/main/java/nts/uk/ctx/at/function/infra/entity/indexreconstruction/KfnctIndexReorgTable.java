package nts.uk.ctx.at.function.infra.entity.indexreconstruction;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KfnctIndexReorgTable.
 */
@Entity
@Table(name="KFNCT_INDEX_REORG_TABLE")
@NoArgsConstructor
@AllArgsConstructor
public class KfnctIndexReorgTable extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk. */
	@EmbeddedId
	public KfnctIndexReogrTablePk pk;
	
	/** The exclus ver. */
	@Version
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    /** The Contract Code. */
    @Column(name = "CONTRACT_CD")
    public String contractCode;
    
    /** The company id.
	 * 	会社ID
	 */
	@Column(name = "CID")
	public String companyId;
	
	/** The table jp name. */
	@Column(name = "TABLE_JP_NAME")
	public String tableJpName;
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
