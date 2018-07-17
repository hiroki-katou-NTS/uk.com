package nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 和暦記号
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_JAP_CLD_SYMBOL_DFS")
public class OiomtJapCldSymbolDfs extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtJapCldSymbolDfsPk japCldSymbolDfsPk;
	
	/**
	 * 元号名
	 */
	@Basic(optional = false)
	@Column(name = "ERA_NAME")
	public int eraName;
	
	@Override
	protected Object getKey() {
		return japCldSymbolDfsPk;
	}
}
