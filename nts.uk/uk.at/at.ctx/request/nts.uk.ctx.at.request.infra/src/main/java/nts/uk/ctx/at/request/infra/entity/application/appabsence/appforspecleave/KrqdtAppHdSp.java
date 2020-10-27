package nts.uk.ctx.at.request.infra.entity.application.appabsence.appforspecleave;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
//import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRQDT_APP_HD_SP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppHdSp extends ContractUkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected KrqdtAppHdSpPK krqdtAppHdSpPK;
	/**
     * 排他バージョン
     */
	@Column(name="EXCLUS_VER")
	public Long version;
    
	/**
	 * 喪主フラグ
	 */
	@Column(name ="MOURNER_FLG")
	private boolean mournerFlg;
	/**
	 * 続柄コード
	 */
	@Column(name ="RELATIONSHIP_CD")
	private String relationshipCD;
	/**
	 * 続柄理由
	 */
	@Column(name ="RELATIONSHIP_REASON")
	private String relationshipReason;
	

	@Override
	protected Object getKey() {
		return krqdtAppHdSpPK;
	}

}
