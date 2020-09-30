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
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgTable;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * Entity インデックス再構成テーブル
 * The Class KfnctIndexReorgTable.
 */
@Data
@Entity
@Table(name="KFNCT_INDEX_REORG_TABLE")
@EqualsAndHashCode(callSuper = true)
public class KfnctIndexReorgTable extends UkJpaEntity implements IndexReorgTable.MementoGetter, IndexReorgTable.MementoSetter, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk. */
	@EmbeddedId
	public KfnctIndexReorgTablePk pk;
	
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

	@Override
	public void setCategoryNo(BigDecimal indexNo) {
		if (this.pk == null) {
			this.pk = new KfnctIndexReorgTablePk();
		}
		this.pk.setCategoryNo(indexNo);
	}

	@Override
	public void setTablePhysName(String tablePhysicalName) {
		if (this.pk == null) {
			this.pk = new KfnctIndexReorgTablePk();
		}
		this.pk.setTablePhysName(tablePhysicalName);
	}

	@Override
	public String getTablePhysName() {
		if (this.pk != null) {
			return this.pk.tablePhysName;
		}
		return null;
	}

	@Override
	public BigDecimal getCategoryNo() {
		if (this.pk != null) {
			return this.pk.categoryNo;
		}
		return null;
	}
}
