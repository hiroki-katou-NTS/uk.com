package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.spLea.basicInfo.KrcmtSpecialLeaveInfo;

@Stateless
public class JpaSpecialLeaveBasicInfoRepo extends JpaRepository implements SpecialLeaveBasicInfoRepository{

	private String FIND_QUERY = "SELECT s FROM KrcmtSpecialLeaveInfo s WHERE s.employeeId = :employeeId ";
	
	
	@Override
	public List<SpecialLeaveBasicInfo> listSPLeav(String sid) {
		List<SpecialLeaveBasicInfo> result = this.queryProxy().query(FIND_QUERY,KrcmtSpecialLeaveInfo.class)
		.setParameter("employeeId", sid).getList(item ->{
			return toDomain(item);
		});
		return result;
	}

	@Override
	public void add(SpecialLeaveBasicInfo domain) {
		this.commandProxy().insert(toEntity(domain));
		
	}

	@Override
	public void update(SpecialLeaveBasicInfo domain) {
		this.commandProxy().update(toEntity(domain));
		
	}

	@Override
	public void delete(String infoId) {
		this.commandProxy().remove(KrcmtSpecialLeaveInfo.class, infoId);
	}
	
	/**
	 * Convert to entity
	 * @param domain
	 * @return
	 */
	private KrcmtSpecialLeaveInfo toEntity(SpecialLeaveBasicInfo domain){
		KrcmtSpecialLeaveInfo entity = new KrcmtSpecialLeaveInfo();
		entity.employeeId = domain.getEmployeeId();
		entity.infoId = domain.getInfoId();
		entity.appSetting = domain.getApplicationSet().value;
		entity.grantDate = domain.getGrantSetting().getGrantDate();
		if (domain.getGrantSetting().getGrantDays().isPresent()){
			entity.grantNumber = domain.getGrantSetting().getGrantDays().get().v();
		}
		if (domain.getGrantSetting().getGrantTable().isPresent()){
			entity.grantTable = domain.getGrantSetting().getGrantTable().get().v();
		}
		
		return entity;
	}
	
	/**
	 * Convert to domain
	 * @param entity
	 * @return
	 */
	private SpecialLeaveBasicInfo toDomain(KrcmtSpecialLeaveInfo entity) {
		return new SpecialLeaveBasicInfo(entity.infoId, entity.employeeId, entity.spLeaveCD, entity.useCls,
				entity.appSetting, entity.grantDate, entity.grantNumber, entity.grantTable);
	}

}
