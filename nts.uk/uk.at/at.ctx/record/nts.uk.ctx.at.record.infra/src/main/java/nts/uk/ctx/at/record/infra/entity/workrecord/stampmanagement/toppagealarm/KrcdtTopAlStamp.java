package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.toppagealarm;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TOP_AL_STAMP")
public class KrcdtTopAlStamp extends ContractUkJpaEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTopAlStampPk pk;
	
	/**
	 * エラーの有無
	 */
	@Basic(optional = false)
	@Column(name = "EXISTENCE_ERROR")
	public int existence_error;
	
	/**
	 * 中止フラグ
	 */
	@Basic(optional = false)
	@Column(name = "IS_CANCELLED")
	public int is_cancelled;
	
	@Override
	protected Object getKey() {
		return pk;
	}

}
