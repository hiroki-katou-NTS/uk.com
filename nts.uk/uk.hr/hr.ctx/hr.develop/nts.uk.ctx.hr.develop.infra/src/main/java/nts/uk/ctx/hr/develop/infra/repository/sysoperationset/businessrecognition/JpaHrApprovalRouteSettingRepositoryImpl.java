package nts.uk.ctx.hr.develop.infra.repository.sysoperationset.businessrecognition;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.HrApprovalRouteSetting;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.HrApprovalRouteSettingRepository;
import nts.uk.ctx.hr.develop.infra.entity.sysoperationset.businessrecognition.JcmmtRootArp;

@Stateless
public class JpaHrApprovalRouteSettingRepositoryImpl extends JpaRepository implements HrApprovalRouteSettingRepository {

	@Override
	public HrApprovalRouteSetting getDomainByCid(String cid) {
		
		if(StringUtil.isNullOrEmpty(cid, true))
			return null;
		
		Optional<JcmmtRootArp> entity = this.queryProxy().find(cid, JcmmtRootArp.class);
		if (!entity.isPresent()) {
			return null;
		}
		
		return toDomain(entity.get());
		
	}

	private HrApprovalRouteSetting toDomain(JcmmtRootArp entity) {
		return HrApprovalRouteSetting.createFromJavaType(entity.cid, entity.comMode == 1 ? true : false, entity.devMode == 1 ? true : false, entity.empMode == 1 ? true : false);
	}

}
