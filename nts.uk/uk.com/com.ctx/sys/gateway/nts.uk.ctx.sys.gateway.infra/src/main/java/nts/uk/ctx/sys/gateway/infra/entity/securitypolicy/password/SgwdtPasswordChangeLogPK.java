package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.password;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

/**
 * パスワード変更履歴PK
 * 
 * @author hiroki_katou
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class SgwdtPasswordChangeLogPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** ユーザID */
	@Column(name = "USER_ID")
	private String userId;
	
	/** 変更日時 */
	@Column(name = "CHANGE_DATE_TIME")
	private GeneralDateTime changeDateTime;
}
