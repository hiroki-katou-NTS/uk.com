package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.password;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.LoginPasswordOfUser;
import nts.uk.ctx.sys.gateway.dom.login.password.userpassword.PasswordChangeLogDetail;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * パスワード変更履歴
 * 
 * @author hiroki_katou
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="SGWDT_PASSWORD_CHANGE_LOG")
public class SgwdtPasswordChangeLog extends ContractUkJpaEntity{
	
	@EmbeddedId
	public SgwdtPasswordChangeLogPK pk;
	
	@Column(name = "HASHED_PASSWORD")
	private String hashedPassword;

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static final JpaEntityMapper<SgwdtPasswordChangeLog> MAPPER = new JpaEntityMapper<>(SgwdtPasswordChangeLog.class);
	
	public static LoginPasswordOfUser toDomain(String userId, List<SgwdtPasswordChangeLog> entityLst) {
		
		if(!entityLst.stream().map(e -> e.pk.getUserId()).allMatch(u -> u.equals(userId))) {
	        throw new RuntimeException("複数ユーザへの処理には対応していません。");
		}
		
		List<PasswordChangeLogDetail> details = entityLst.stream()
				.map(x -> {
					return new PasswordChangeLogDetail(x.pk.getChangeDateTime(), x.hashedPassword);
				}).collect(Collectors.toList());
		
		return new LoginPasswordOfUser(userId, details);
	}
}
