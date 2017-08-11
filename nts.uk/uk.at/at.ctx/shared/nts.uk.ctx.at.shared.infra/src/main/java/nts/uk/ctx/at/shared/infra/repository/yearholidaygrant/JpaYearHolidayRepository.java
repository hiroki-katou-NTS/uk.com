package nts.uk.ctx.at.shared.infra.repository.yearholidaygrant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.ConditionValue;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantingCondition;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHoliday;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayName;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstGrantingCondition;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstYearHoliday;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstYearHolidayPK;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author TanLV
 *
 */

@Stateless
public class JpaYearHolidayRepository extends JpaRepository implements YearHolidayRepository {
	
	private final String findAllByCompanyID = "SELECT a FROM KshstYearHoliday a "
			+ "WHERE a.kshstYearHolidayPK.companyId = :companyId";
	
	@Override
	public List<YearHoliday> findAll(String companyId) {
		return this.queryProxy().query(findAllByCompanyID, KshstYearHoliday.class)
				.setParameter("companyId", companyId)
				.getList(x -> convertToDomainYearHoliday(x));
	}	

	@Override
	public Optional<YearHoliday> findByCode(String companyId, String yearHolidayCode) {
		return this.queryProxy().find(new KshstYearHolidayPK(companyId, yearHolidayCode), KshstYearHoliday.class)
				.map(x -> convertToDomainYearHoliday(x));
	}

	@Override
	public void add(YearHoliday yearHoliday) {
		this.commandProxy().insert(toEntity(yearHoliday));
	}

	@Override
	public void update(YearHoliday yearHoliday) {
		KshstYearHolidayPK key = new KshstYearHolidayPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v());
		Optional<KshstYearHoliday> entity = this.queryProxy().find(key, KshstYearHoliday.class);
		KshstYearHoliday kshstYearHoliday = entity.get();
		kshstYearHoliday.yearHolidayName = yearHoliday.getYearHolidayName().v();
		kshstYearHoliday.calculationMethod = yearHoliday.getCalculationMethod();
		kshstYearHoliday.simultaneousGrandMonthDays = yearHoliday.getSimultaneousGrandMonthDays();
		kshstYearHoliday.standardCalculation = yearHoliday.getStandardCalculation();
		kshstYearHoliday.useSimultaneousGrant = yearHoliday.getUseSimultaneousGrant();
		kshstYearHoliday.yearHolidayNote = yearHoliday.getYearHolidayNote().v();
		
		List<KshstGrantingCondition> grantCoditionList = new ArrayList<KshstGrantingCondition>();
		// todo
		kshstYearHoliday.grantingConditions = grantCoditionList;
		
		this.commandProxy().update(entity);
	}

	@Override
	public void remove(String companyId, String yearHolidayCode) {
		this.commandProxy().remove(KshstYearHoliday.class, new KshstYearHolidayPK(companyId, yearHolidayCode));
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	private YearHoliday convertToDomainYearHoliday(KshstYearHoliday x) {
		List<GrantingCondition> grantConditions = x.grantingConditions.stream().map(t -> {
			return new 	GrantingCondition(t.kshstGrantingConditionPK.companyId, 
					new YearHolidayCode(t.kshstGrantingConditionPK.yearHolidayCode), 
					t.kshstGrantingConditionPK.conditionNo, 
					new ConditionValue(t.conditionValue), 
					t.useConditionClassification);
		}).collect(Collectors.toList());
		
		return new YearHoliday(x.kshstYearHolidayPK.companyId, 
				new YearHolidayCode(x.kshstYearHolidayPK.yearHolidayCode), 
				new YearHolidayName(x.yearHolidayName), 
				x.calculationMethod, 
				x.standardCalculation, 
				x.useSimultaneousGrant,	
				x.simultaneousGrandMonthDays, 
				new Memo(x.yearHolidayNote), grantConditions);
	}

	/**
	 * 
	 * @param yearHoliday
	 * @return
	 */
	private KshstYearHoliday toEntity(YearHoliday yearHoliday) {
		List<KshstGrantingCondition> grantCoditionList = new ArrayList<KshstGrantingCondition>();
		
		return new KshstYearHoliday(
				new KshstYearHolidayPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v()),
				yearHoliday.getYearHolidayName().v(),
				yearHoliday.getCalculationMethod(),
				yearHoliday.getStandardCalculation(),
				yearHoliday.getUseSimultaneousGrant(),
				yearHoliday.getSimultaneousGrandMonthDays(),
				yearHoliday.getYearHolidayNote().v(), grantCoditionList);
	}
	
}
