package nts.uk.ctx.sys.log.infra.repository.logbasicinfo;

import java.util.List;

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
	public List<LogBasicInformation> getAllLogBasicInfo(String companyId, String loginEmployeeId) {
		String query = "SELECT a FROM SrcdtLogBasicInfo a WHERE a.companyId = :companyId AND a.employeeId = :loginEmpId";
		return this.queryProxy().query(query, SrcdtLogBasicInfo.class).setParameter("companyId", companyId)
				.setParameter("loginEmpId", loginEmployeeId).getList(item -> item.toDomain());
	}

}
