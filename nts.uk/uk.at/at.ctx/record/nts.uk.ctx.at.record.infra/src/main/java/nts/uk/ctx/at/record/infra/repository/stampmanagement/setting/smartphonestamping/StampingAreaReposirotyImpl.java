package nts.uk.ctx.at.record.infra.repository.stampmanagement.setting.smartphonestamping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSetting;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcmStampEreaLimitSyaPK;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcmtStampEreaLimitSya;

@Stateless
public class StampingAreaReposirotyImpl extends JpaRepository implements StampingAreaRepository {
	private static final String SELECT_SINGLE = "SELECT c FROM KrcmtStampEreaLimitSya c WHERE c.PK.sId = :employId";
	/*
	 * 選択社員の打刻エリア制限設定を取得する
	 * */
	@Override
	public void insertStampingArea(EmployeeStampingAreaRestrictionSetting restrictionSetting) {
		this.commandProxy().insert(KrcmtStampEreaLimitSya.toEntity(restrictionSetting));
	}
	
	@Override
	public Optional<EmployeeStampingAreaRestrictionSetting> findByEmployeeId(String employId) {
		Optional<EmployeeStampingAreaRestrictionSetting> result = this.queryProxy()
				.query(SELECT_SINGLE, KrcmtStampEreaLimitSya.class).setParameter("employId", employId)
				.getSingle(c -> c.toDomain());
		return result;
	}
	
	/*
	 * 選択社員の打刻エリア制限設定を取得する
	 * */
	@Override
	public Boolean updateStampingArea(EmployeeStampingAreaRestrictionSetting areaRestriction) {
		Optional<KrcmtStampEreaLimitSya> oldData = this.queryProxy().query(SELECT_SINGLE, KrcmtStampEreaLimitSya.class)
				.setParameter("employId", areaRestriction.getEmployeeId()).getSingle();
		if (oldData.isPresent()) {
			KrcmtStampEreaLimitSya newData = KrcmtStampEreaLimitSya.toEntity(areaRestriction);
			oldData.get().setAreaLimitAtr(newData.getAreaLimitAtr());
			oldData.get().setLocationInforUse(newData.getLocationInforUse());
			this.commandProxy().update(oldData.get());
			return true;
		}
		return false;
	}
	
	/*
	 *  社員別の打刻エリア制限設定状態を取得する
	 * */
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
	
	/*
	 * 選択社員の打刻エリア制限設定を削除する
	 * */
	@Override
	public void deleteStampSetting(String employeeId) {
		Optional<EmployeeStampingAreaRestrictionSetting> data = this.findByEmployeeId(employeeId);
		if (data.isPresent()) {
			this.commandProxy().remove(KrcmtStampEreaLimitSya.class, new KrcmStampEreaLimitSyaPK(employeeId));
		}

	}

	@Override
	public void saveStampingArea(EmployeeStampingAreaRestrictionSetting restrictionSetting) {
		Optional<EmployeeStampingAreaRestrictionSetting> data = this
				.findByEmployeeId(restrictionSetting.getEmployeeId());

		if (data.isPresent()) {
			this.updateStampingArea(restrictionSetting);
		} else {
			this.insertStampingArea(restrictionSetting);
		}
	}
}
