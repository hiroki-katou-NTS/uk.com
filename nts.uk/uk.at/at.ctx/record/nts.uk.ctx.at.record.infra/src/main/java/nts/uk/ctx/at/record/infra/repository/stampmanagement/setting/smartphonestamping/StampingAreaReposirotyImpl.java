package nts.uk.ctx.at.record.infra.repository.stampmanagement.setting.smartphonestamping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaReposiroty;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcmStampEreaLimitSyaPK;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcmtStampEreaLimitSya;

@Stateless
public class StampingAreaReposirotyImpl extends JpaRepository implements StampingAreaReposiroty {
	private static final String SELECT_SINGLE = "SELECT c FROM KrcmtStampEreaLimitSya c WHERE c.PK.sId = :employId";

	@Override
	public void insertStampingArea(String emplId, StampingAreaRestriction areaRestriction ) {
		this.commandProxy().insert(KrcmtStampEreaLimitSya.toEntity(emplId,areaRestriction));
	}

	@Override
	public Optional<StampingAreaRestriction> findByEmployeeId(String employId) {
		Optional<StampingAreaRestriction> result = this.queryProxy().query(SELECT_SINGLE, KrcmtStampEreaLimitSya.class).setParameter("employId", employId)
				.getSingle(c -> c.toDomain());
		return result;
	}

	@Override
	public Boolean updateStampingArea(String emplId,StampingAreaRestriction areaRestriction) {
		Optional<KrcmtStampEreaLimitSya> oldData = this.queryProxy().query(SELECT_SINGLE, KrcmtStampEreaLimitSya.class).setParameter("employId", emplId)
				.getSingle();
		if (oldData.isPresent()) {
			KrcmtStampEreaLimitSya newData = KrcmtStampEreaLimitSya.toEntity(emplId, areaRestriction);
			oldData.get().setAreaLimitAtr(newData.getAreaLimitAtr());
			oldData.get().setLocationInforUse(newData.getLocationInforUse());
			this.commandProxy().update(oldData.get());
			return true;
		}
		return false;
	}

	@Override
	public List<String> getStatusStampingEmpl(List<String> listEmplId) {
		List<String> resultList = new ArrayList<>();
		for (String emppl : listEmplId) {
			if (this.findByEmployeeId(emppl).isPresent()) {
				resultList.add(emppl);
			}
		}
		return resultList;
	}

	@Override
	public void deleteStampSetting(String employeeId) {
		Optional<StampingAreaRestriction> data = this.findByEmployeeId(employeeId);
		if (data.isPresent()) {
			this.commandProxy().remove(KrcmtStampEreaLimitSya.class, new KrcmStampEreaLimitSyaPK(employeeId));
		}
		
	}
}
