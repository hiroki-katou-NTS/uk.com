package nts.uk.ctx.sys.gateway.infra.entity.tenantlogin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.LoginClient;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationFailureLog;
import nts.uk.shr.com.net.Ipv4Address;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SGWDT_FAIL_LOG_TENANT_AUTH")
public class SgwdtFailLogTenantAuth extends UkJpaEntity {

	@Id
	@Column(name="FAILURE_DATE_TIME")
	private GeneralDateTime failureDateTime;
	
	@Column(name="IP_ADDRESS")
	private String ipv4Address;
	
	@Column(name="USER_AGENT")
	private String userAgent;
	
	@Column(name="TRIED_TENANT_CODE")
	private String triedTenantCode;
	
	@Column(name="TRIED_PASSWORD")
	private String triedPassword;
	
	public static final JpaEntityMapper<SgwdtFailLogTenantAuth> MAPPER = new JpaEntityMapper<>(SgwdtFailLogTenantAuth.class);

	@Override
	protected Object getKey() {
		return this.failureDateTime;
	}
	
	public TenantAuthenticationFailureLog toDomain() {
		return new TenantAuthenticationFailureLog(
				failureDateTime, 
				new LoginClient(Ipv4Address.parse(ipv4Address), userAgent),
				triedTenantCode, 
				triedPassword);
	}
}
