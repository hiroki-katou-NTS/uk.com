package nts.uk.ctx.at.schedule.infra.repository.shift.management.shifttable;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompanyRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityPeriodUnit;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KscmtShiftTableRuleForCompany;

@Stateless
public class JpaShiftTableRuleForCompanyRepo extends JpaRepository implements ShiftTableRuleForCompanyRepo {

	private static final String SELECT_BY_KEY = "SELECT c FROM KscmtShiftTableRuleForCompany c WHERE c.companyId = :companyId ";
	
	@Override
	public void insert(String companyId, ShiftTableRuleForCompany domain) {
		this.commandProxy().insert(KscmtShiftTableRuleForCompany.toEntity(companyId, domain));
	}

	@Override
	public void update(String companyId, ShiftTableRuleForCompany domain) {
		this.queryProxy().query(SELECT_BY_KEY, KscmtShiftTableRuleForCompany.class)
				.setParameter("companyId", companyId)
				.getSingle().ifPresent(oldEntity -> {
			KscmtShiftTableRuleForCompany newEntity = KscmtShiftTableRuleForCompany.toEntity(companyId, domain);
			oldEntity.usePublicAtr = newEntity.usePublicAtr;
			oldEntity.useWorkAvailabilityAtr = newEntity.useWorkAvailabilityAtr;
			if (oldEntity.kscmtShiftTableRuleForCompanyAvai != null && newEntity.kscmtShiftTableRuleForCompanyAvai != null) {
				oldEntity.kscmtShiftTableRuleForCompanyAvai.holidayAtr = newEntity.kscmtShiftTableRuleForCompanyAvai.holidayAtr;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.shiftAtr = newEntity.kscmtShiftTableRuleForCompanyAvai.shiftAtr;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.timeSheetAtr = newEntity.kscmtShiftTableRuleForCompanyAvai.timeSheetAtr;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.fromNoticeDays = newEntity.kscmtShiftTableRuleForCompanyAvai.fromNoticeDays;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.periodUnit = newEntity.kscmtShiftTableRuleForCompanyAvai.periodUnit;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.dateCloseDay = newEntity.kscmtShiftTableRuleForCompanyAvai.dateCloseDay;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay = newEntity.kscmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.dateDeadlineDay = newEntity.kscmtShiftTableRuleForCompanyAvai.dateDeadlineDay;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay = newEntity.kscmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.dateHDUpperlimit = newEntity.kscmtShiftTableRuleForCompanyAvai.dateHDUpperlimit;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.weekSetStart = newEntity.kscmtShiftTableRuleForCompanyAvai.weekSetStart;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr = newEntity.kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr;
				oldEntity.kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek = newEntity.kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek;
			} else if (newEntity.kscmtShiftTableRuleForCompanyAvai != null) {
				oldEntity.kscmtShiftTableRuleForCompanyAvai = newEntity.kscmtShiftTableRuleForCompanyAvai;
			}
			this.commandProxy().update(oldEntity);
		});
	}

	@Override
	public void delete(String companyId) {
		Optional<ShiftTableRuleForCompany>  data = this.get(companyId);
		if(data.isPresent()) {
			this.commandProxy().remove(KscmtShiftTableRuleForCompany.class, companyId);
		}
	}

	@Override
	public Optional<ShiftTableRuleForCompany> get(String companyId) {
		Optional<ShiftTableRuleForCompany> shiftTableRuleForCompany = this.queryProxy().query(SELECT_BY_KEY, KscmtShiftTableRuleForCompany.class)
				.setParameter("companyId", companyId)
				.getSingle(c -> c.toDomain());
		return shiftTableRuleForCompany;
	}

}
