package nts.uk.ctx.workflow.infra.repository.approvermanagement.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.BooleanUtils;

import lombok.val;
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
		return HrApprovalRouteSettingWF.createFromJavaType(
				BooleanUtils.toBoolean(entity.comMode), 
				entity.cid, 
				BooleanUtils.toBoolean(entity.empMode), 
				BooleanUtils.toBoolean(entity.devMode));
	}

	@Override
	public void insert(HrApprovalRouteSettingWF hrApprovalRouteSetting) {
		JcmmtRootArpWF jcmmtRootArpWF = toEntity(hrApprovalRouteSetting);
		this.commandProxy().insert(jcmmtRootArpWF);
	}

	@Override
	public void update(HrApprovalRouteSettingWF hrApprovalRouteSetting) {
		JcmmtRootArpWF entity = toEntity(hrApprovalRouteSetting);
		JcmmtRootArpWF entityUpdate = this.queryProxy().find(entity.cid, JcmmtRootArpWF.class).get();
		entityUpdate.comMode = entity.comMode;
		entityUpdate.devMode = entity.devMode;
		entityUpdate.empMode = entity.empMode;
	}
	public JcmmtRootArpWF toEntity(HrApprovalRouteSettingWF hrApprovalRouteSettingWF) {
		val entity = new JcmmtRootArpWF();
		entity.cid = hrApprovalRouteSettingWF.cid;
		entity.comMode = BooleanUtils.toInteger(hrApprovalRouteSettingWF.comMode);
		entity.devMode = BooleanUtils.toInteger(hrApprovalRouteSettingWF.devMode);
		entity.empMode = BooleanUtils.toInteger(hrApprovalRouteSettingWF.empMode);
		return entity;
	}

}
