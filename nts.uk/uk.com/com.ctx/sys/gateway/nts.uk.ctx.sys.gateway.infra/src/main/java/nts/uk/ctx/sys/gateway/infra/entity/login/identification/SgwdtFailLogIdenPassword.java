package nts.uk.ctx.sys.gateway.infra.entity.login.identification;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.PasswordAuthIdentificateFailureLog;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 	パスワード認証による社員の識別失敗記録
 * 
 * @author hiroki_katou
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SGWDT_FAIL_LOG_IDEN_PASSWORD")
public class SgwdtFailLogIdenPassword extends UkJpaEntity {
	
	@EmbeddedId
	public SgwdtFailLogIdenPasswordPK pk;

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static final JpaEntityMapper<SgwdtFailLogIdenPassword> MAPPER = new JpaEntityMapper<>(SgwdtFailLogIdenPassword.class);
	
	public PasswordAuthIdentificateFailureLog toDomain() {
		return new PasswordAuthIdentificateFailureLog(
				pk.getFailureDateTime(), 
				pk.getTriedCompanyId(), 
				pk.getTriedEmployeeCode());
	}
}
