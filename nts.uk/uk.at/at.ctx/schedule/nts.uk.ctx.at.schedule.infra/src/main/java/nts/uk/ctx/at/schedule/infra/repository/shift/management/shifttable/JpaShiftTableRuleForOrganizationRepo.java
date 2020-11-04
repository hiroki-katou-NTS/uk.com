package nts.uk.ctx.at.schedule.infra.repository.shift.management.shifttable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganizationRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityPeriodUnit;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KrcmtShiftTableRuleForOrg;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KscdtManagementOfShiftTablePk;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Stateless
public class JpaShiftTableRuleForOrganizationRepo extends JpaRepository implements ShiftTableRuleForOrganizationRepo  {
	
	private static final String SELECT_BY_KEY = "SELECT c FROM KrcmtShiftTableRuleForOrg c WHERE c.pk.companyId = :companyId "
			+ " AND c.pk.targetUnit = :targetUnit"
			+ " AND c.pk.targetID = :targetID  ";

	@Override
	public void insert(String companyId, ShiftTableRuleForOrganization domain) {
		this.commandProxy().insert(KrcmtShiftTableRuleForOrg.toEntity(companyId, domain));
		
	}

	@Override
	public void update(String companyId, ShiftTableRuleForOrganization domain) {
		Optional<KrcmtShiftTableRuleForOrg> oldEntity = this.queryProxy().query(SELECT_BY_KEY, KrcmtShiftTableRuleForOrg.class)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", domain.getTargetOrg().getUnit().value)
				.setParameter("targetID", domain.getTargetOrg().getTargetId())
				.getSingle();
		
		if(oldEntity.isPresent()) {
			KrcmtShiftTableRuleForOrg newEntity = KrcmtShiftTableRuleForOrg.toEntity(companyId, domain);
			oldEntity.get().usePublicAtr = newEntity.usePublicAtr;
			oldEntity.get().useWorkAvailabilityAtr = newEntity.useWorkAvailabilityAtr;
			oldEntity.get().krcmtShiftTableRuleForOrgAvai.holidayAtr = newEntity.krcmtShiftTableRuleForOrgAvai.holidayAtr;
			oldEntity.get().krcmtShiftTableRuleForOrgAvai.shiftAtr = newEntity.krcmtShiftTableRuleForOrgAvai.shiftAtr;
			oldEntity.get().krcmtShiftTableRuleForOrgAvai.timeSheetAtr = newEntity.krcmtShiftTableRuleForOrgAvai.timeSheetAtr;
			oldEntity.get().krcmtShiftTableRuleForOrgAvai.fromNoticeDays = newEntity.krcmtShiftTableRuleForOrgAvai.fromNoticeDays;
			oldEntity.get().krcmtShiftTableRuleForOrgAvai.periodUnit = newEntity.krcmtShiftTableRuleForOrgAvai.periodUnit;
			
			if(newEntity.krcmtShiftTableRuleForOrgAvai.periodUnit == WorkAvailabilityPeriodUnit.MONTHLY.value) {
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateCloseDay = newEntity.krcmtShiftTableRuleForOrgAvai.dateCloseDay;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateCloseIsLastDay = newEntity.krcmtShiftTableRuleForOrgAvai.dateCloseIsLastDay;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateDeadlineDay = newEntity.krcmtShiftTableRuleForOrgAvai.dateDeadlineDay;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay = newEntity.krcmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateHDUpperlimit = newEntity.krcmtShiftTableRuleForOrgAvai.dateHDUpperlimit;
				
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.weekSetStart = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek = null;
			}else if(newEntity.krcmtShiftTableRuleForOrgAvai.periodUnit == WorkAvailabilityPeriodUnit.WEEKLY.value) {
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateCloseDay = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateCloseIsLastDay = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateDeadlineDay = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateHDUpperlimit = null;
				
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.weekSetStart = newEntity.krcmtShiftTableRuleForOrgAvai.weekSetStart;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr = newEntity.krcmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek = newEntity.krcmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek;
			}else {
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateCloseDay = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateCloseIsLastDay = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateDeadlineDay = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.dateHDUpperlimit = null;
				
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.weekSetStart = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr = null;
				oldEntity.get().krcmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek = null;
			}
			
			this.commandProxy().update(oldEntity.get());
		}
	}

	@Override
	public void delete(String companyId, TargetOrgIdenInfor targetOrg) {
		Optional<ShiftTableRuleForOrganization>  data = this.get(companyId, targetOrg);
		if(data.isPresent()) {
			this.commandProxy().remove(KrcmtShiftTableRuleForOrg.class, new KscdtManagementOfShiftTablePk(companyId, targetOrg.getUnit().value, targetOrg.getTargetId()));
		}
	}

	@Override
	public Optional<ShiftTableRuleForOrganization> get(String companyId, TargetOrgIdenInfor targetOrg) {
		Optional<ShiftTableRuleForOrganization> shiftTableRuleForOrganization = this.queryProxy().query(SELECT_BY_KEY, KrcmtShiftTableRuleForOrg.class)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", targetOrg.getUnit().value)
				.setParameter("targetID", targetOrg.getTargetId())
				.getSingle(c -> c.toDomain());
		return shiftTableRuleForOrganization;
	}

	@Override
	public List<ShiftTableRuleForOrganization> getList(String companyId, List<TargetOrgIdenInfor> targetOrgList) {
		List<ShiftTableRuleForOrganization>  data = new ArrayList<>();
		for(TargetOrgIdenInfor targetOrgIdenInfor : targetOrgList) {
			Optional<ShiftTableRuleForOrganization> shiftTableRuleForOrganization = this.get(companyId, targetOrgIdenInfor);
			if(shiftTableRuleForOrganization.isPresent()) {
				data.add(shiftTableRuleForOrganization.get());
			}
		}
		return data;
	}

}
