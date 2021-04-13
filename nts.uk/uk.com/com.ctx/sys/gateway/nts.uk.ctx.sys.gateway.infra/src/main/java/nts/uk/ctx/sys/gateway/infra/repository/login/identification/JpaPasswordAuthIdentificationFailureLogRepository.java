package nts.uk.ctx.sys.gateway.infra.repository.login.identification;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.PasswordAuthIdentificateFailureLog;
import nts.uk.ctx.sys.gateway.dom.login.password.identification.PasswordAuthIdentificateFailureLogRepository;
import nts.uk.ctx.sys.gateway.infra.entity.login.identification.SgwdtFailLogIdenPassword;
import nts.uk.ctx.sys.gateway.infra.entity.login.identification.SgwdtFailLogIdenPasswordPK;

@Stateless
public class JpaPasswordAuthIdentificationFailureLogRepository extends JpaRepository implements PasswordAuthIdentificateFailureLogRepository {
	
	private final String BASIC_SELECT 
					= "select * from SGWDT_FAIL_LOG_IDEN_PASSWORD ";
	
	private SgwdtFailLogIdenPassword fromDomain(PasswordAuthIdentificateFailureLog domain) {
		return new SgwdtFailLogIdenPassword(
				new SgwdtFailLogIdenPasswordPK(
				domain.getFailureDateTime(), 
				domain.getTriedCompanyCode(), 
				domain.getTriedEmployeeCode()));
	}

	@Override
	public void insert(PasswordAuthIdentificateFailureLog domain) {
		this.commandProxy().insert(fromDomain(domain));
	}

	@Override
	public List<PasswordAuthIdentificateFailureLog> find(String companyId) {
		String query = BASIC_SELECT 
				+ "where CID = @companyId ";
		return new NtsStatement(query, this.jdbcProxy())
				.paramString("companyId", companyId)
				.getList(rec -> SgwdtFailLogIdenPassword.MAPPER.toEntity(rec).toDomain());
	}

}
