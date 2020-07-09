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
@Table(name = "KRCDT_TOP_AL_DETAIL_STAMP")
public class KrcdtTopAlDetailStamp extends ContractUkJpaEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTopAlDetailStampPk pk;

	/**
	 * エラーメッセージ
	 */
	@Basic(optional = false)
	@Column(name = "ERROR_MESSAGE")
	public String error_message;

	@Override
	protected Object getKey() {

		return pk;
	}

}
