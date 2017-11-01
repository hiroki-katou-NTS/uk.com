package nts.uk.ctx.at.record.infra.repository.log;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.enums.EnumConstant;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.ctx.at.record.infra.entity.log.KrcdtEmpExeTarget;
import nts.uk.ctx.at.record.infra.entity.log.KrcdtEmpExecutionLog;
import nts.uk.ctx.at.record.infra.entity.log.KrcdtExecutionLog;
/**
 * 
 * @author hieult
 *
 */
@Stateless
public class JpaExecutionLogRepository extends JpaRepository implements ExecutionLogRepository {

	private final String SELECT_BY_COMPANY = "SELECT c FROM KrcdtExecutionLog AS c WHERE c.KrcdtExecutionLogPK.companyID = :companyID ";
	
	
	@Override
	public List<EnumConstant> getEnumContent(String companyID) {
		List<ExecutionLog> data = this.queryProxy().query(SELECT_BY_COMPANY, KrcdtEmpExecutionLog.class)
				.setParameter("companyID", companyID).getList(c -> toDomain(c));
		return data;
		
	}


	private ExecutionLog toDomain(KrcdtExecutionLog entity) {
		
		return  null;
	}




}
