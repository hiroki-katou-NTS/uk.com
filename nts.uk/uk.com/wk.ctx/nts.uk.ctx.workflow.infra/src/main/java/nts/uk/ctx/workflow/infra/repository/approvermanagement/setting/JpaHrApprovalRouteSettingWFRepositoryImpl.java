package nts.uk.ctx.workflow.infra.repository.approvermanagement.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.StringUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWF;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWFRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.setting.JcmmtRootArpWF;


@Stateless
public class JpaHrApprovalRouteSettingWFRepositoryImpl extends JpaRepository implements HrApprovalRouteSettingWFRepository {

	@Override
	public Optional<HrApprovalRouteSettingWF> getDomainByCid(String cid) {
		
		if(StringUtil.isNullOrEmpty(cid, true))
			return Optional.empty();
		
		Optional<JcmmtRootArpWF> entity = this.queryProxy().find(cid, JcmmtRootArpWF.class);
		if (!entity.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(toDomain(entity.get()));
		
	}

	private HrApprovalRouteSettingWF toDomain(JcmmtRootArpWF entity) {
		return HrApprovalRouteSettingWF.createFromJavaType(entity.comMode == 1 ? true : false, entity.cid, entity.devMode == 1 ? true : false, entity.empMode == 1 ? true : false);
	}

	@Override
	public void insert(HrApprovalRouteSettingWF hrApprovalRouteSetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(HrApprovalRouteSettingWF hrApprovalRouteSetting) {
		// TODO Auto-generated method stub
		
	}

}
