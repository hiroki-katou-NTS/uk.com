package nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author thanhnx
 * 本人確認処理の利用設定
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_IDENTITY_PROCE_SET")
public class KrcmtIdentityProceSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtIdentityProceSetPK krcmtIdentityProceSetPK;
	
	@Column(name = "USE_CONFIRM_SELF_ATR")
	public int useConfirmByYourself;
	
	@Column(name = "USE_INDENT_MONTH_ATR")
	public int useIdentityOfMonth;
	
	@Column(name = "SELF_CONFIRM_ERROR")
	public Integer yourSelfConfirmError;
	
	@Override
	protected Object getKey() {
		return this.krcmtIdentityProceSetPK;
	}
}
