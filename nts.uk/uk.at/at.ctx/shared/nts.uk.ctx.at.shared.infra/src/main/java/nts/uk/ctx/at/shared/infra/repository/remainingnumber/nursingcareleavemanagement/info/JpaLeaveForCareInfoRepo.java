package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfoRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcmtCareHDInfo;

@Stateless
public class JpaLeaveForCareInfoRepo extends JpaRepository implements LeaveForCareInfoRepository {

	@Override
	public Optional<LeaveForCareInfo> getCareByEmpId(String empId) {
		Optional<KrcmtCareHDInfo> entityOpt = this.queryProxy().find(empId, KrcmtCareHDInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtCareHDInfo entity = entityOpt.get();
			return Optional.of(LeaveForCareInfo.createCareLeaveInfo(entity.getSId(), entity.getUseAtr(),
					entity.getUpperLimSetAtr(), entity.getMaxDayThisFiscalYear(), entity.getMaxDayNextFiscalYear()));
		}
		return Optional.empty();
	}

	@Override
	public void add(LeaveForCareInfo obj, String cId) {
		KrcmtCareHDInfo entity = new KrcmtCareHDInfo(obj.getSId(), cId, obj.isUseClassification() ? 1 : 0,
				obj.getUpperlimitSetting().value,
				obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null,
				obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
		this.commandProxy().insert(entity);

	}

	@Override
	public void update(LeaveForCareInfo obj, String cId) {
		Optional<KrcmtCareHDInfo> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtCareHDInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtCareHDInfo entity = entityOpt.get();
			entity.setCId(cId);
			entity.setUseAtr(obj.isUseClassification() ? 1 : 0);
			entity.setUpperLimSetAtr(obj.getUpperlimitSetting().value);
			entity.setMaxDayNextFiscalYear(
					obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
			entity.setMaxDayThisFiscalYear(
					obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null);
			this.commandProxy().update(entity);
		}
	}

}
