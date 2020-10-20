package nts.uk.ctx.sys.gateway.infra.entity.singlesignon.saml;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.onelogin.saml2.util.Constants;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.gul.security.saml.SamlSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SGWMT_SAML_SETTING")
public class SgwmtSamlSetting extends UkJpaEntity {

	@Id
	@Column(name="TENANT_CD")
	private String tenantCode;
	
	@Column(name="IDP_IDENTIFIER")
	private String IdpIdentifier;
	
	@Column(name="CERTIFICATE")
	private String Certificate;
	
	@Column(name="CLIENT_ID")
	private String ClientId;
	
	public static final JpaEntityMapper<SgwmtSamlSetting> MAPPER = new JpaEntityMapper<>(SgwmtSamlSetting.class);

	public SamlSetting toDomain() {
		val samlSetting = new SamlSetting();
		samlSetting.SetIdpEntityId(IdpIdentifier);
		samlSetting.SetIdpCertFingerprint(Certificate);
		samlSetting.SetSpEntityId(ClientId);
		return samlSetting;
	}

	@Override
	protected Object getKey() {
		return tenantCode;
	}
}
