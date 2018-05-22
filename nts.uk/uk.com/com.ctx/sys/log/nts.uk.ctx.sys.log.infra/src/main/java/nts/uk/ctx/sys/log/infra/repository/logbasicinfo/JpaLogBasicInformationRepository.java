package nts.uk.ctx.sys.log.infra.repository.logbasicinfo;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.log.dom.logbasicinfo.LogBasicInfoRepository;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaLogBasicInformationRepository extends JpaRepository implements LogBasicInfoRepository {

	@Override
	public List<LogBasicInformation> getAllLogBasicInfo(String companyId, List<String> listEmployeeId) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

}
