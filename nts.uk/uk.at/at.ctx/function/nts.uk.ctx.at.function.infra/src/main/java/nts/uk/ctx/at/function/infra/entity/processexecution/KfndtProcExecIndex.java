package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndexResult;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KfndtProcExecIndex.
 * Entity インデックス再構成結果履歴
 */
@Data
@Entity
@Table(name="KFNDT_PROC_EXEC_INDEX")
@EqualsAndHashCode(callSuper = true)
public class KfndtProcExecIndex extends UkJpaEntity implements ProcExecIndexResult.MementoGetter, ProcExecIndexResult.MementoSetter, Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk. */
	@EmbeddedId
	public KfndtProcExecIndexPk pk;
	
	/** The exclus ver. 	
	 * 	排他バージョン									
	 */
	@Version
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    /** The Contract Code. 
	 * 	契約コード	
	 */
    @Column(name = "CONTRACT_CD")
    public String contractCode;

	/** The company id.
	 * 	会社ID
	 */
	@Column(name = "CID")
	public String companyId;
	
	/** The frs bef. 
	 * 	処理前の断片化率									
	 */
	@Column(name = "FRS_BEF")
	public BigDecimal fragmentationRate;
	
	/** The frs aft. 
	 * 	処理後の断片化率									
	*/
	@Column(name = "FRS_AFT")
	public BigDecimal fragmentationRateAfterProcessing;

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
	public void setIndexName(String indexNo) {
		if (this.pk == null) {
			this.pk = new KfndtProcExecIndexPk();
		}
		this.pk.setIndexName(indexNo);
	}

	@Override
	public void setTablePhysicalName(String tablePhysicalName) {
		if (this.pk == null) {
			this.pk = new KfndtProcExecIndexPk();
		}
		this.pk.setTableName(tablePhysicalName);
	}

	@Override
	public String getIndexName() {
		if (this.pk != null) {
			return this.pk.indexName;
		}
		return null;
	}

	@Override
	public String getTablePhysicalName() {
		if (this.pk != null) {
			return this.pk.tableName;
		}
		return null;
	}

}
