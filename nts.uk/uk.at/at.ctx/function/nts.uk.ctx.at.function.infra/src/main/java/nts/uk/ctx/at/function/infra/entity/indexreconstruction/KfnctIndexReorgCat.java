package nts.uk.ctx.at.function.infra.entity.indexreconstruction;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgCat;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KfnctIndexReorgCat.
 * Entity インデックス再構成カテゴリ
 */
@Data
@Entity
@Table(name="KFNCT_INDEX_REORG_CAT")
@EqualsAndHashCode(callSuper = true)
public class KfnctIndexReorgCat extends UkJpaEntity implements IndexReorgCat.MementoGetter, IndexReorgCat.MementoSetter, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk. */
	@EmbeddedId
	public KfnctIndexReorgCatPk pk;
	
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
	
	/** The category name. */
	@Column(name = "CATEGORY_NAME")
	public String categoryName;
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		return this.pk;
	}

	@Override
	public void setCategoryNo(BigDecimal indexNo) {
		if (this.pk == null) {
			this.pk = new KfnctIndexReorgCatPk();
		}
		this.pk.setCategoryNo(indexNo);
	}

	@Override
	public BigDecimal getCategoryNo() {
		if (this.pk != null) {
			return this.pk.categoryNo;
		}
		return null;
	}

}
