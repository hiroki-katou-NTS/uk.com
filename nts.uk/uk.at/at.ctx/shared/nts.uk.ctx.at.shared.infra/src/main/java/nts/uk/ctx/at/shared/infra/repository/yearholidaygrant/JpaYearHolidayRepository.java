package nts.uk.ctx.at.shared.infra.repository.yearholidaygrant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.ConditionValue;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantCondition;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstGrantCondition;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstGrantHdTblSet;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstGrantHdTblSetPK;

/**
 * 
 * @author TanLV
 *
 */

@Stateless
public class JpaYearHolidayRepository extends JpaRepository implements YearHolidayRepository {
	
	private final String findAllByCompanyID = "SELECT a FROM KshstYearHoliday a "
			+ "WHERE a.kshstGrantHdTblSetPK.companyId = :companyId";
	
	@Override
	public List<GrantHdTblSet> findAll(String companyId) {
		return this.queryProxy().query(findAllByCompanyID, KshstGrantHdTblSet.class)
				.setParameter("companyId", companyId)
				.getList(x -> convertToDomainYearHoliday(x));
	}	

	@Override
	public Optional<GrantHdTblSet> findByCode(String companyId, String yearHolidayCode) {
		return this.queryProxy().find(new KshstGrantHdTblSetPK(companyId, yearHolidayCode), KshstGrantHdTblSet.class)
				.map(x -> convertToDomainYearHoliday(x));
	}

	@Override
	public void add(GrantHdTblSet yearHoliday) {
		this.commandProxy().insert(toEntity(yearHoliday));
	}

	@Override
	public void update(GrantHdTblSet yearHoliday) {
		KshstGrantHdTblSetPK key = new KshstGrantHdTblSetPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v());
		Optional<KshstGrantHdTblSet> entity = this.queryProxy().find(key, KshstGrantHdTblSet.class);
		KshstGrantHdTblSet kshstYearHoliday = entity.get();
		kshstYearHoliday.yearHolidayName = yearHoliday.getYearHolidayName().v();
		kshstYearHoliday.calculationMethod = yearHoliday.getCalculationMethod().value;
		kshstYearHoliday.simultaneousGrandMonthDays = yearHoliday.getSimultaneousGrandMonthDays();
		kshstYearHoliday.standardCalculation = yearHoliday.getStandardCalculation().value;
		kshstYearHoliday.useSimultaneousGrant = yearHoliday.getUseSimultaneousGrant().value;
		kshstYearHoliday.yearHolidayNote = yearHoliday.getYearHolidayNote().v();
		
		List<KshstGrantCondition> grantCoditionList = new ArrayList<KshstGrantCondition>();
		// todo
		kshstYearHoliday.grantConditions = grantCoditionList;
		
		this.commandProxy().update(entity);
	}

	@Override
	public void remove(String companyId, String yearHolidayCode) {
		this.commandProxy().remove(KshstGrantHdTblSet.class, new KshstGrantHdTblSetPK(companyId, yearHolidayCode));
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	private GrantHdTblSet convertToDomainYearHoliday(KshstGrantHdTblSet x) {
		List<GrantCondition> grantConditions = x.grantConditions.stream().map(t -> {
			return new 	GrantCondition(t.kshstGrantConditionPK.companyId, 
					new YearHolidayCode(t.kshstGrantConditionPK.yearHolidayCode), 
					t.kshstGrantConditionPK.conditionNo, 
					new ConditionValue(t.conditionValue), 
					t.useConditionAtr);
		}).collect(Collectors.toList());
		
		return GrantHdTblSet.createFromJavaType(x.kshstGrantHdTblSetPK.companyId, 
				x.kshstGrantHdTblSetPK.yearHolidayCode, 
				x.yearHolidayName, 
				x.calculationMethod, 
				x.standardCalculation, 
				x.useSimultaneousGrant,	
				x.simultaneousGrandMonthDays, 
				x.yearHolidayNote, grantConditions);
	}

	/**
	 * 
	 * @param yearHoliday
	 * @return
	 */
	private KshstGrantHdTblSet toEntity(GrantHdTblSet yearHoliday) {
		List<KshstGrantCondition> grantCoditionList = new ArrayList<KshstGrantCondition>();
		
		return new KshstGrantHdTblSet(
				new KshstGrantHdTblSetPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v()),
				yearHoliday.getYearHolidayName().v(),
				yearHoliday.getCalculationMethod().value,
				yearHoliday.getStandardCalculation().value,
				yearHoliday.getUseSimultaneousGrant().value,
				yearHoliday.getSimultaneousGrandMonthDays(),
				yearHoliday.getYearHolidayNote().v(), grantCoditionList);
	}
	
}
