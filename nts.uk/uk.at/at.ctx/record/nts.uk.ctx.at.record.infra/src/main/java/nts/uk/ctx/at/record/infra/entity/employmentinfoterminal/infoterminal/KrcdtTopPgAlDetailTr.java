package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TOPPG_AL_DETAIL_TR")
public class KrcdtTopPgAlDetailTr extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTopPgAlDetailTrPK pk;

	/**
	 * 対象社員ID
	 */
	@Column(name = "TARGET_SID")
	public String targetSid;

	/**
	 * カード番号
	 */
	@Column(name = "CARD_NUMBER")
	public String cardNumber;

	/**
	 * エラーメッセージ
	 */
	@Column(name = "MESSAGE")
	public String message;

	@Override
	protected Object getKey() {
		return pk;
	}

}
