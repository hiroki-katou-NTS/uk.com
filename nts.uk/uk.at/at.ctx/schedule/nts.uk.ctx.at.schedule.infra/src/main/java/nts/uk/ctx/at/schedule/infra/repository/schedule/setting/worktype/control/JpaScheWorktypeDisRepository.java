package nts.uk.ctx.at.schedule.infra.repository.schedule.setting.worktype.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.WorkTypeDisplaySetting;
import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDis;
import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDisRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.worktypecontrol.KscstWorkTypeDispSet;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.worktypecontrol.KscstWorkTypeDispSetPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.worktypecontrol.KscstWorkTypeDisplay;
import nts.uk.ctx.at.schedule.infra.entity.schedule.setting.worktypecontrol.KscstWorkTypeDisplayPK;



@Stateless
public class JpaScheWorktypeDisRepository extends JpaRepository implements WorktypeDisRepository{
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KscstWorkTypeDisplay e");
		builderString.append(" WHERE e.kscstWorkTypeDisplayPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}
	private WorktypeDis convertToDomain(KscstWorkTypeDisplay typeDisplay) {
		List<WorkTypeDisplaySetting> workTypeList = new ArrayList<>();
		for (KscstWorkTypeDispSet obj : typeDisplay.typeDispSets) {
			workTypeList.add(toDomain(obj));
		}
		
		WorktypeDis dis = WorktypeDis.createFromJavaType(typeDisplay.kscstWorkTypeDisplayPk.companyId, typeDisplay.useAtr, workTypeList);
		return dis;
	}
	
	private KscstWorkTypeDisplay convertToDbType(WorktypeDis dis) {
		KscstWorkTypeDisplay display = new KscstWorkTypeDisplay();
		KscstWorkTypeDisplayPK displayPk = new KscstWorkTypeDisplayPK(dis.getCompanyId());
		display.useAtr = dis.getUseAtr().value;
		// Add list KDL 002
		List<KscstWorkTypeDispSet> workTypes = dis.getWorkTypeList().stream().map(x -> {
			KscstWorkTypeDispSetPK key = new KscstWorkTypeDispSetPK(dis.getCompanyId(), x.getWorkTypeCode());
			return new KscstWorkTypeDispSet(key);
		}).collect(Collectors.toList());
		display.typeDispSets = workTypes;
		display.kscstWorkTypeDisplayPk = displayPk;
		return display;
	}
	
	/**
	 * Find by Company Id
	 */
	@Override
	public List<WorktypeDis> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KscstWorkTypeDisplay.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Worktype Display
	 */
	@Override
	public void add(WorktypeDis worktypeDis) {
		this.commandProxy().insert(convertToDbType(worktypeDis));
	}

	/**
	 * Update Worktype Display
	 */
	@Override
	public void update(WorktypeDis worktypeDis) {
		KscstWorkTypeDisplayPK primaryKey = new KscstWorkTypeDisplayPK(worktypeDis.getCompanyId());
		KscstWorkTypeDisplay entity = this.queryProxy().find(primaryKey, KscstWorkTypeDisplay.class).get();
		entity.useAtr = worktypeDis.getUseAtr().value;
		List<KscstWorkTypeDispSet> workTypes = worktypeDis.getWorkTypeList().stream().map(x -> {
			KscstWorkTypeDispSetPK key = new KscstWorkTypeDispSetPK(worktypeDis.getCompanyId(), x.getWorkTypeCode());
			return new KscstWorkTypeDispSet(key);
		}).collect(Collectors.toList());

		entity.typeDispSets = workTypes;
		entity.kscstWorkTypeDisplayPk = primaryKey;
		this.commandProxy().update(entity);
	}
	
	@Override
	public Optional<WorktypeDis> findByCId(String companyId) {
		return this.queryProxy().find(new KscstWorkTypeDisplayPK(companyId), KscstWorkTypeDisplay.class)
				.map(c -> convertToDomain(c));
	}
	
	private static WorkTypeDisplaySetting toDomain(KscstWorkTypeDispSet entity) {
		WorkTypeDisplaySetting domain = WorkTypeDisplaySetting.createFromJavaType(entity.kscstWorkTypeDispSetPk.companyId,
				entity.kscstWorkTypeDispSetPk.workTypeCode);
		
		return domain;
	}
}
