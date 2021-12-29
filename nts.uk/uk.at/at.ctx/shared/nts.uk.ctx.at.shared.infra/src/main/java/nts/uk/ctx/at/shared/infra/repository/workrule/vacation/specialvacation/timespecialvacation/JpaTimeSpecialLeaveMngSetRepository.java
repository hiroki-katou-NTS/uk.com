package nts.uk.ctx.at.shared.infra.repository.workrule.vacation.specialvacation.timespecialvacation;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.ctx.at.shared.infra.entity.workrule.vacation.specialvacation.timespecialvacation.KshmtHdspTimeMgt;
import nts.uk.ctx.at.shared.infra.repository.vacation.setting.specialleave.JpaTimeSpecialLeaveManagementSettingSetMemento;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaTimeSpecialLeaveMngSetRepository extends JpaRepository implements TimeSpecialLeaveMngSetRepository {
	
    @Override
    public Optional<TimeSpecialLeaveManagementSetting> findByCompany(String companyId) {
        return this.queryProxy().find(companyId, KshmtHdspTimeMgt.class).map(KshmtHdspTimeMgt::toDomain);
    }
    
    @Override
	public void add(TimeSpecialLeaveManagementSetting setting) {
		this.commandProxy().insert(this.toEntity(setting));
	}

	@Override
	public void update(TimeSpecialLeaveManagementSetting setting) {
		this.commandProxy().update(this.toEntity(setting));
	}
	
    private KshmtHdspTimeMgt toEntity(TimeSpecialLeaveManagementSetting setting) {
    	KshmtHdspTimeMgt entity = new KshmtHdspTimeMgt();
    	setting.saveToMemento(new JpaTimeSpecialLeaveManagementSettingSetMemento(entity));
    	return entity;
    }
}
