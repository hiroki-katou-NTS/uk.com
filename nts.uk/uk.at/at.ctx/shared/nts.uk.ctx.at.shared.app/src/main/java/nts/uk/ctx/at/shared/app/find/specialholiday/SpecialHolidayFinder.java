package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SphdLimit;
import nts.uk.ctx.at.shared.dom.specialholiday.SubCondition;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateCom;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantPeriodic;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegularRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantSingle;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SpecialHolidayFinder {

	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	
	@Inject
	private GrantRegularRepository grantRegularRepository;

	/**
	 * Find all Special Holiday by CompanyId
	 * 
	 * @return
	 */
	public List<SpecialHolidayDto> findAllSpecialHoliday() {
		String companyId = AppContexts.user().companyId();
		return specialHolidayRepository.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}
	
	/**
	 * Find Grant Date Com by special holiday code
	 * 
	 * @return
	 */
	public GrantDateComDto getComByCode(String specialHolidayCode) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		Optional<GrantDateCom> data = this.grantRegularRepository.getComByCode(companyId, specialHolidayCode);
		
		if(data.isPresent()){
			return GrantDateComDto.fromDomain(data.get());
		}
		
		return null;
	}
	
	/**
	 * Find all Grant Date Set by code
	 * 
	 * @return
	 */
	public List<GrantDateSetDto> getAllSetByCode(String specialHolidayCode) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		return this.grantRegularRepository.getSetByCode(companyId, specialHolidayCode).stream().map(c -> GrantDateSetDto.fromDomain(c))
				.collect(Collectors.toList());
	}

	/**
	 * Convert to Database Type SpecialHolidayDto
	 * 
	 * @param specialHoliday
	 * @return
	 */
	private SpecialHolidayDto convertToDbType(SpecialHoliday specialHoliday) {
		SpecialHolidayDto specialHolidayDto = new SpecialHolidayDto();
		specialHolidayDto.setSpecialHolidayCode(specialHoliday.getSpecialHolidayCode().v());
		specialHolidayDto.setSpecialHolidayName(specialHoliday.getSpecialHolidayName().v());
		specialHolidayDto.setGrantMethod(specialHoliday.getGrantMethod().value);
		specialHolidayDto.setMemo(specialHoliday.getMemo().v());
		specialHolidayDto.setWorkTypeList(specialHoliday.getWorkTypeList());
		specialHolidayDto.setGrantRegular(convertToDbTypeRegular(specialHoliday.getGrantRegular()));
		specialHolidayDto.setGrantPeriodic(convertToDbTypePeriodic(specialHoliday.getGrantPeriodic()));
		specialHolidayDto.setSphdLimit(convertToDbTypeSphdLimit(specialHoliday.getSphdLimit()));
		specialHolidayDto.setSubCondition(convertToDbTypeSubCondition(specialHoliday.getSubCondition()));
		specialHolidayDto.setGrantSingle(convertToDbTypeGrantSingle(specialHoliday.getGrantSingle()));
		
		return specialHolidayDto;
	}
	
	/**
	 * Convert to Database Type GrantRegularDto
	 * @param grantRegular
	 * @return
	 */
	private GrantRegularDto convertToDbTypeRegular(GrantRegular grantRegular) {
		if (grantRegular == null) {
			return null;
		}
		
		GrantRegularDto grantRegularDto = new GrantRegularDto();
		grantRegularDto.setSpecialHolidayCode(grantRegular.getSpecialHolidayCode().v());
		grantRegularDto.setGrantStartDate(grantRegular.getGrantStartDate());
		grantRegularDto.setMonths(grantRegular.getMonths().v());
		grantRegularDto.setYears(grantRegular.getYears().v());
		
		return grantRegularDto;
	}
	
	/**
	 * Convert to Database Type GrantPeriodicDto
	 * @param grantPeriodic
	 * @return
	 */
	private GrantPeriodicDto convertToDbTypePeriodic(GrantPeriodic grantPeriodic){
		if (grantPeriodic == null) {
			return null;
		}
		GrantPeriodicDto grantPeriodicDto = new GrantPeriodicDto();
		grantPeriodicDto.setSpecialHolidayCode(grantPeriodic.getSpecialHolidayCode().v());
		grantPeriodicDto.setGrantDay(grantPeriodic.getGrantDay().v());
		grantPeriodicDto.setSplitAcquisition(grantPeriodic.getSplitAcquisition().value);
		grantPeriodicDto.setGrantPeriodicMethod(grantPeriodic.getGrantPeriodicMethod().value);
		
		return grantPeriodicDto;
	}
	
	/**
	 * Convert to Database Type SphdLimitDto
	 * @param sphdLimit
	 * @return
	 */
	private SphdLimitDto convertToDbTypeSphdLimit(SphdLimit sphdLimit){
		if (sphdLimit == null) {
			return null;
		}
		
		SphdLimitDto sphdLimitDto = new SphdLimitDto();
		sphdLimitDto.setSpecialHolidayCode(sphdLimit.getSpecialHolidayCode().v());
		sphdLimitDto.setSpecialVacationMonths(sphdLimit.getSpecialVacationMonths().v());
		sphdLimitDto.setSpecialVacationYears(sphdLimit.getSpecialVacationYears().v());
		sphdLimitDto.setGrantCarryForward(sphdLimit.getGrantCarryForward().value);
		sphdLimitDto.setLimitCarryoverDays(sphdLimit.getLimitCarryoverDays().v());
		sphdLimitDto.setSpecialVacationMethod(sphdLimit.getSpecialVacationMethod().value);
		return sphdLimitDto;
	}
	
	/**
	 * Convert to Database Type SubConditionDto
	 * @param subCondition
	 * @return
	 */
	private SubConditionDto convertToDbTypeSubCondition(SubCondition subCondition){
		if (subCondition == null) {
			return null;
		}
		
		SubConditionDto subConditionDto = new SubConditionDto();
		subConditionDto.setSpecialHolidayCode(subCondition.getSpecialHolidayCode().v());
		subConditionDto.setUseGender(subCondition.getUseGender().value);
		subConditionDto.setUseEmployee(subCondition.getUseEmployee().value);
		subConditionDto.setUseCls(subCondition.getUseCls().value);
		subConditionDto.setUseAge(subCondition.getUseAge().value);
		subConditionDto.setGenderAtr(subCondition.getGenderAtr().value);
		subConditionDto.setLimitAgeFrom(subCondition.getLimitAgeFrom().v());
		subConditionDto.setLimitAgeTo(subCondition.getLimitAgeTo().v());
		subConditionDto.setAgeCriteriaAtr(subCondition.getAgeCriteriaAtr().value);
		subConditionDto.setAgeBaseYearAtr(subCondition.getAgeBaseYearAtr().value);
		subConditionDto.setAgeBaseDates(subCondition.getAgeBaseDates().v());
		return subConditionDto;
	}
	
	/**
	 * Convert to Database Type GrantSingleDto
	 * @param grantSingle
	 * @return
	 */
	private GrantSingleDto convertToDbTypeGrantSingle(GrantSingle grantSingle){
		if (grantSingle == null) {
			return null;
		}
		GrantSingleDto grantSingleDto = new GrantSingleDto();
		grantSingleDto.setSpecialHolidayCode(grantSingle.getSpecialHolidayCode().v());
		grantSingleDto.setGrantDaySingleType(grantSingle.getGrantDaySingleType().value);
		grantSingleDto.setFixNumberDays(grantSingle.getFixNumberDays().v());
		grantSingleDto.setMakeInvitation(grantSingle.getMakeInvitation().value);
		grantSingleDto.setHolidayExclusionAtr(grantSingle.getHolidayExclusionAtr().value);
		return grantSingleDto;
	}

}
