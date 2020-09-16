package nts.uk.ctx.at.function.infra.entity.processexecution;

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
 * The Class KfnctExecutionExternalAccept.
 * Entity 実行設定の外部受入項目
 */
@Entity
@Table(name="KFNCT_EXEC_EXT_ACCEPT")
@NoArgsConstructor
@AllArgsConstructor
public class KfnctExecutionExternalAccept extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pk. */
	@EmbeddedId
	public KfndtProcExecIndexPk pk;
	
	/** The exclus ver. */
	@Version
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    /** The Contract Code. */
    @Column(name = "CONTRACT_CD")
    public String contractCode;
    
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
