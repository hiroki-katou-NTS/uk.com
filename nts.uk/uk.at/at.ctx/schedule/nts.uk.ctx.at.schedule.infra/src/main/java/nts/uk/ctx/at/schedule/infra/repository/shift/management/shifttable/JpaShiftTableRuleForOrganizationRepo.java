package nts.uk.ctx.at.schedule.infra.repository.shift.management.shifttable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganizationRepo;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KscmtShiftTableRuleForOrg;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KscmtShiftTableRuleForOrgPK;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Stateless
public class JpaShiftTableRuleForOrganizationRepo extends JpaRepository implements ShiftTableRuleForOrganizationRepo {

	private static final String SELECT_BY_KEY = "SELECT c FROM KscmtShiftTableRuleForOrg c WHERE c.pk.companyId = :companyId "
			+ " AND c.pk.targetUnit = :targetUnit" + " AND c.pk.targetID = :targetID  ";

	private static final String SELECT_ALL = "SELECT c FROM KscmtShiftTableRuleForOrg c WHERE c.pk.companyId = :companyId ";

	@Override
	public void insert(String companyId, ShiftTableRuleForOrganization domain) {
		this.commandProxy().insert(KscmtShiftTableRuleForOrg.toEntity(companyId, domain));
	}

	@Override
	public void update(String companyId, ShiftTableRuleForOrganization domain) {
		this.queryProxy().query(SELECT_BY_KEY, KscmtShiftTableRuleForOrg.class)
				.setParameter("companyId", companyId)
				.setParameter("targetUnit", domain.getTargetOrg().getUnit().value)
				.setParameter("targetID", domain.getTargetOrg().getTargetId())
				.getSingle().ifPresent(oldEntity -> {
			KscmtShiftTableRuleForOrg newEntity = KscmtShiftTableRuleForOrg.toEntity(companyId, domain);
			oldEntity.usePublicAtr = newEntity.usePublicAtr;
			oldEntity.useWorkAvailabilityAtr = newEntity.useWorkAvailabilityAtr;
			if (oldEntity.kscmtShiftTableRuleForOrgAvai != null && newEntity.kscmtShiftTableRuleForOrgAvai != null) {
				oldEntity.kscmtShiftTableRuleForOrgAvai.holidayAtr = newEntity.kscmtShiftTableRuleForOrgAvai.holidayAtr;
				oldEntity.kscmtShiftTableRuleForOrgAvai.shiftAtr = newEntity.kscmtShiftTableRuleForOrgAvai.shiftAtr;
				oldEntity.kscmtShiftTableRuleForOrgAvai.timeSheetAtr = newEntity.kscmtShiftTableRuleForOrgAvai.timeSheetAtr;
				oldEntity.kscmtShiftTableRuleForOrgAvai.fromNoticeDays = newEntity.kscmtShiftTableRuleForOrgAvai.fromNoticeDays;
				oldEntity.kscmtShiftTableRuleForOrgAvai.periodUnit = newEntity.kscmtShiftTableRuleForOrgAvai.periodUnit;
                oldEntity.kscmtShiftTableRuleForOrgAvai.dateCloseDay = newEntity.kscmtShiftTableRuleForOrgAvai.dateCloseDay;
				oldEntity.kscmtShiftTableRuleForOrgAvai.dateCloseIsLastDay = newEntity.kscmtShiftTableRuleForOrgAvai.dateCloseIsLastDay;
				oldEntity.kscmtShiftTableRuleForOrgAvai.dateDeadlineDay = newEntity.kscmtShiftTableRuleForOrgAvai.dateDeadlineDay;
				oldEntity.kscmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay = newEntity.kscmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay;
				oldEntity.kscmtShiftTableRuleForOrgAvai.dateHDUpperlimit = newEntity.kscmtShiftTableRuleForOrgAvai.dateHDUpperlimit;
				oldEntity.kscmtShiftTableRuleForOrgAvai.weekSetStart = newEntity.kscmtShiftTableRuleForOrgAvai.weekSetStart;
				oldEntity.kscmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr = newEntity.kscmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr;
				oldEntity.kscmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek = newEntity.kscmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek;
			} else if (newEntity.kscmtShiftTableRuleForOrgAvai != null) {
                oldEntity.kscmtShiftTableRuleForOrgAvai = newEntity.kscmtShiftTableRuleForOrgAvai;
            }
			this.commandProxy().update(oldEntity);
		});
	}

	@Override
	public void delete(String companyId, TargetOrgIdenInfor targetOrg) {
		Optional<ShiftTableRuleForOrganization> data = this.get(companyId, targetOrg);
		if (data.isPresent()) {
			this.commandProxy().remove(KscmtShiftTableRuleForOrg.class,
					new KscmtShiftTableRuleForOrgPK(companyId, targetOrg.getUnit().value, targetOrg.getTargetId()));
		}
	}

	@Override
	public Optional<ShiftTableRuleForOrganization> get(String companyId, TargetOrgIdenInfor targetOrg) {
		Optional<ShiftTableRuleForOrganization> shiftTableRuleForOrganization = this.queryProxy()
				.query(SELECT_BY_KEY, KscmtShiftTableRuleForOrg.class).setParameter("companyId", companyId)
				.setParameter("targetUnit", targetOrg.getUnit().value).setParameter("targetID", targetOrg.getTargetId())
				.getSingle(c -> c.toDomain());
		return shiftTableRuleForOrganization;
	}

	@Override
	public List<ShiftTableRuleForOrganization> getList(String companyId, List<TargetOrgIdenInfor> targetOrgList) {
		List<ShiftTableRuleForOrganization> data = new ArrayList<>();
		for (TargetOrgIdenInfor targetOrgIdenInfor : targetOrgList) {
			Optional<ShiftTableRuleForOrganization> shiftTableRuleForOrganization = this.get(companyId,
					targetOrgIdenInfor);
			if (shiftTableRuleForOrganization.isPresent()) {
				data.add(shiftTableRuleForOrganization.get());
			}
		}
		return data;
	}

	@Override
	public List<ShiftTableRuleForOrganization> getAll(String companyId) {
		List<ShiftTableRuleForOrganization> shiftTableRuleForOrganization = this.queryProxy()
				.query(SELECT_ALL, KscmtShiftTableRuleForOrg.class).setParameter("companyId", companyId)
				.getList(c -> c.toDomain());
		return shiftTableRuleForOrganization;
	}

}
