package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KfnmtExecutionIndexCategory.
 * Entity 実行設定のインデックス再構成カテゴリ
 */
@Entity
@Table(name="KFNMT_EXEC_INDEX_CAT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KfnmtExecutionIndexCategory extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk. */
	@EmbeddedId
	public KfnmtExecutionIndexCategoryPk pk;
    
    /** The Contract Code. */
    @Column(name = "CONTRACT_CD")
    public String contractCode;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumns({
    	@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
    	@JoinColumn(name = "EXEC_ITEM_CD", referencedColumnName = "EXEC_ITEM_CD", insertable = false, updatable = false)
    })
    public KfnmtProcessExecutionSetting execSetting;
    
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
