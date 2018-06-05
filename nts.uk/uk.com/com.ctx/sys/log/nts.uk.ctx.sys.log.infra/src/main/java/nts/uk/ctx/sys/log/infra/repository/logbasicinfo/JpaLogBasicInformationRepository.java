package nts.uk.ctx.sys.log.infra.repository.logbasicinfo;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.log.dom.logbasicinfo.LogBasicInfoRepository;
import nts.uk.ctx.sys.log.infra.entity.logbasicinfo.SrcdtLogBasicInfo;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaLogBasicInformationRepository extends JpaRepository implements LogBasicInfoRepository {

	@Override
	public Optional<LogBasicInformation> getLogBasicInfo(String companyId, String operationId) {
		String query = "SELECT a FROM SrcdtLogBasicInfo a WHERE a.companyId = :companyId AND a.operationId = :operationId";
		return this.queryProxy().query(query, SrcdtLogBasicInfo.class).setParameter("companyId", companyId)
				.setParameter("operationId", operationId).getSingle(item -> item.toDomain());
	}

}
