package nts.uk.ctx.sys.gateway.infra.entity.login.identification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.PasswordAuthIdentificateFailureLog;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SGWDT_FAIL_LOG_IDEN_PASSWORD")
public class SgwdtFailLogIdenPassword extends ContractUkJpaEntity {

	@Id
	@Column(name="FAILURE_DATE_TIME")
	private GeneralDateTime failureDateTime;
	
	@Column(name="INPUT_COMPANY_CODE")
	private String inputCompanyId;
	
	@Column(name="INPUT_EMPLOYEE_CODE")
	private String inputEmployeeCode;
	
	public static final JpaEntityMapper<SgwdtFailLogIdenPassword> MAPPER = new JpaEntityMapper<>(SgwdtFailLogIdenPassword.class);

	@Override
	protected Object getKey() {
		return this.failureDateTime;
	}
	
	public PasswordAuthIdentificateFailureLog toDomain() {
		return new PasswordAuthIdentificateFailureLog(
				failureDateTime, 
				inputCompanyId, 
				inputEmployeeCode 
				);
	}
}
