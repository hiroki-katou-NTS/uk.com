package nts.uk.ctx.workflow.infra.repository.approvermanagement.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.StringUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.setting.JcmmtRootArp;


@Stateless
public class JpaHrApprovalRouteSettingRepositoryImpl extends JpaRepository implements HrApprovalRouteSettingRepository {

	@Override
	public Optional<HrApprovalRouteSetting> getDomainByCid(String cid) {
		
		if(StringUtil.isNullOrEmpty(cid, true))
			return Optional.empty();
		
		Optional<JcmmtRootArp> entity = this.queryProxy().find(cid, JcmmtRootArp.class);
		if (!entity.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(toDomain(entity.get()));
		
	}

	private HrApprovalRouteSetting toDomain(JcmmtRootArp entity) {
		return HrApprovalRouteSetting.createFromJavaType(entity.comMode == 1 ? true : false, entity.cid, entity.devMode == 1 ? true : false, entity.empMode == 1 ? true : false);
	}

}
