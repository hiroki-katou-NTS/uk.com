package nts.uk.ctx.sys.gateway.infra.entity.login.authnticate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SGWDT_FAIL_LOG_PASSWORD_AUTH")
public class SgwdtFailLogPasswordAuth extends UkJpaEntity{
	
	@Column(name = "FAILURE_DATE_TIME")
	private GeneralDateTime failureTimestamps;

	@Column(name = "TRIED_USER_ID")
	private String triedUserId;
	
	@Column(name = "TRIED_PASSWORD")
	private String triedPassword;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static final JpaEntityMapper<SgwdtFailLogPasswordAuth> MAPPER = new JpaEntityMapper<>(SgwdtFailLogPasswordAuth.class);

}
