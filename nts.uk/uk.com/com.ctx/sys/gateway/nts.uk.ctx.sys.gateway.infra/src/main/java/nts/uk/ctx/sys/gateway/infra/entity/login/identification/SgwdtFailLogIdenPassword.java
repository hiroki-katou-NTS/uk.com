package nts.uk.ctx.sys.gateway.infra.entity.login.identification;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.sys.gateway.dom.login.identification.PasswordAuthIdentificationFailureLog;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SGWDT_FAIL_LOG_IDEN_PASSWORD")
public class SgwdtFailLogIdenPassword extends ContractUkJpaEntity {

	@Id
	@Column(name="FAILURE_DATE_TIME")
	private LocalDateTime failureDateTime;
	
	@Column(name="INPUT_COMPANY_CODE")
	private String inputCompanyId;
	
	@Column(name="INPUT_EMPLOYEE_CODE")
	private String inputEmployeeCode;
	
	@Column(name="IP_ADDRESS")
	private String ipAddress;
	
	public static final JpaEntityMapper<SgwdtFailLogIdenPassword> MAPPER = new JpaEntityMapper<>(SgwdtFailLogIdenPassword.class);

	@Override
	protected Object getKey() {
		return this.failureDateTime;
	}
	
	public PasswordAuthIdentificationFailureLog toDomain() {
		return new PasswordAuthIdentificationFailureLog(
				failureDateTime, 
				inputCompanyId, 
				inputEmployeeCode, 
				ipAddress);
	}
}
