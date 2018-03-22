package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.calculation.holiday.FlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtion;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDepLabor;
import nts.uk.ctx.at.shared.dom.calculation.holiday.RegularWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.TimeHolidayAddingMethod;
import nts.uk.ctx.at.shared.dom.calculation.holiday.TimeHolidayAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkClassOfTimeHolidaySet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstAddSetManWKHour;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstAddSetManWKHourPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHolidayAdditionSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHolidayAdditionSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHourPayAaddSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHourPayAaddSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWorkDepLaborSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWorkDepLaborSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWorkFlexSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWorkFlexSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWorkRegularSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWorkRegularSetPK;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaHolidayAddtionRepository extends JpaRepository implements HolidayAddtionRepository {

	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstHolidayAdditionSet e");
		builderString.append(" WHERE e.kshstHolidayAddtimeSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Holiday Addtime
	 * @param holidayAddtimeSet
	 * @return
	 */
	private HolidayAddtion convertToDomain(KshstHolidayAdditionSet holidayAddtimeSet){
		
		RegularWork regularWork = convertToDomainRegularWork(holidayAddtimeSet.regularWorkSet);
		FlexWork flexWork = convertToDomainFlexWork(holidayAddtimeSet.flexWorkSet);
		WorkDepLabor irregularWork = convertToDomainIrregularWork(holidayAddtimeSet.irregularWorkSet);
		HourlyPaymentAdditionSet hourlyPaymentAdditionSet = convertToDomainHourlyPaymentAddSet(holidayAddtimeSet.hourPayAaddSet);
		
		List<TimeHolidayAdditionSet> lstTimeHDAddSet = new ArrayList<>();
		TimeHolidayAdditionSet timeHolidayAdditionSet = TimeHolidayAdditionSet.builder()
															.addingMethod(TimeHolidayAddingMethod.valueOf(holidayAddtimeSet.addingMethod1))
															.workClass(WorkClassOfTimeHolidaySet.valueOf(holidayAddtimeSet.workClass1))
															.build();
		lstTimeHDAddSet.add(timeHolidayAdditionSet);
		
		timeHolidayAdditionSet = TimeHolidayAdditionSet.builder()
				.addingMethod(TimeHolidayAddingMethod.valueOf(holidayAddtimeSet.addingMethod2))
				.workClass(WorkClassOfTimeHolidaySet.valueOf(holidayAddtimeSet.workClass2))
				.build();
		lstTimeHDAddSet.add(timeHolidayAdditionSet);
		
//		AddSetManageWorkHour addSetManageWorkHour = AddSetManageWorkHour.builder()
//														.companyId("00001")
//														.additionSettingOfOvertime(NotUseAtr.valueOf(1))
//														.build();
		
		AddSetManageWorkHour addSetManageWorkHour = convertToDomainAddSetManageWorkHour(holidayAddtimeSet.addSetManWKHour);
		
		HolidayAddtion addtime = HolidayAddtion.createFromJavaType(holidayAddtimeSet.kshstHolidayAddtimeSetPK.companyId, 
				holidayAddtimeSet.referComHolidayTime,
				holidayAddtimeSet.oneDay, 
				holidayAddtimeSet.morning, 
				holidayAddtimeSet.afternoon, 
				holidayAddtimeSet.referActualWorkHours, 
				holidayAddtimeSet.notReferringAch, 
				holidayAddtimeSet.annualHoliday, 
				holidayAddtimeSet.specialHoliday, 
				holidayAddtimeSet.yearlyReserved, 
				regularWork, 
				flexWork, 
				irregularWork,
				addSetManageWorkHour,
				hourlyPaymentAdditionSet,
				lstTimeHDAddSet);
		return addtime;
	}

	/**
	 * Convert to Database Regular Work
	 * @param regularWork
	 * @return
	 */
	private KshstWorkRegularSet convertToDbTypeRegularWork(RegularWork regularWork) {
			KshstWorkRegularSetPK kshstRegularWorkSetPK = new KshstWorkRegularSetPK(regularWork.getCompanyId());
			KshstWorkRegularSet kshstRegularWorkSet = this.queryProxy().find(kshstRegularWorkSetPK,KshstWorkRegularSet.class).get();
				kshstRegularWorkSet.calcActualOperation1 = regularWork.getCalcActualOperation1().value;
				kshstRegularWorkSet.exemptTaxTime1 = regularWork.getExemptTaxTime1();
				kshstRegularWorkSet.incChildNursingCare1 = regularWork.getIncChildNursingCare1();
				kshstRegularWorkSet.additionTime1  = regularWork.getAdditionTime1();
				kshstRegularWorkSet.notDeductLateleave1 = regularWork.getNotDeductLateleave1();
				kshstRegularWorkSet.deformatExcValue1 = regularWork.getDeformatExcValue1().value;
				kshstRegularWorkSet.exemptTaxTime2 = regularWork.getExemptTaxTime2();
				kshstRegularWorkSet.calcActualOperation2 = regularWork.getCalcActualOperation2().value;
				kshstRegularWorkSet.incChildNursingCare2 = regularWork.getIncChildNursingCare2();
				kshstRegularWorkSet.notDeductLateleave2 = regularWork.getNotDeductLateleave2();
				kshstRegularWorkSet.additionTime2 = regularWork.getAdditionTime2();
				kshstRegularWorkSet.kshstRegularWorkSetPK = kshstRegularWorkSetPK;
		return kshstRegularWorkSet;
	}

	/**
	 * Convert to Domain Irregular Work
	 * @param irregularWorkSet
	 * @return
	 */
	private WorkDepLabor convertToDomainIrregularWork(KshstWorkDepLaborSet irregularWorkSet) {
		WorkDepLabor irregularWork = WorkDepLabor.createFromJavaType(irregularWorkSet.kshstWorkDepLaborSetPK.companyId, 
				irregularWorkSet.calcActualOperation1, 
				irregularWorkSet.exemptTaxTime1, 
				irregularWorkSet.incChildNursingCare1, 
				irregularWorkSet.additionTime1, 
				irregularWorkSet.notDeductLateleave1,
				irregularWorkSet.deformatExcValue,
				irregularWorkSet.exemptTaxTime2, 
				irregularWorkSet.minusAbsenceTime2, 
				irregularWorkSet.calcActualOperation2, 
				irregularWorkSet.incChildNursingCare2, 
				irregularWorkSet.notDeductLateleave2,
				irregularWorkSet.additionTime2,
				irregularWorkSet.enableSetPerWorkHour1,
				irregularWorkSet.enableSetPerWorkHour2
				);
		return irregularWork;
	}

	/**
	 * Convert to Domain Flex Work
	 * @param flexWorkSet
	 * @return
	 */
	private FlexWork convertToDomainFlexWork(KshstWorkFlexSet flexWorkSet) {
		FlexWork flexWork = FlexWork.createFromJavaType(flexWorkSet.kshstFlexWorkSetPK.companyId, 
				flexWorkSet.calcActualOperation1, 
				flexWorkSet.exemptTaxTime1, 
				flexWorkSet.incChildNursingCare1, 
				flexWorkSet.predeterminedOvertime1, 
				flexWorkSet.additionTime1, 
				flexWorkSet.notDeductLateleave1, 
				flexWorkSet.exemptTaxTime2, 
				flexWorkSet.minusAbsenceTime2, 
				flexWorkSet.calcActualOperation2, 
				flexWorkSet.incChildNursingCare2, 
				flexWorkSet.notDeductLateleave2, 
				flexWorkSet.predeterminDeficiency2, 
				flexWorkSet.additionTime2,
				flexWorkSet.enableSetPerWorkHour1,
				flexWorkSet.enableSetPerWorkHour2,
				flexWorkSet.additionWithinMonthlyStatutory);
		return flexWork;
	}

	/**
	 * Convert to Domain Regular Work
	 * @param regularWorkSet
	 * @return
	 */
	private RegularWork convertToDomainRegularWork(KshstWorkRegularSet regularWorkSet) {
		RegularWork regularWork = RegularWork.createFromJavaType(regularWorkSet.kshstRegularWorkSetPK.companyId, 
				regularWorkSet.calcActualOperation1, 
				regularWorkSet.exemptTaxTime1, 
				regularWorkSet.incChildNursingCare1, 
				regularWorkSet.additionTime1, 
				regularWorkSet.notDeductLateleave1, 
				regularWorkSet.deformatExcValue1, 
				regularWorkSet.exemptTaxTime2, 
				regularWorkSet.calcActualOperation2, 
				regularWorkSet.incChildNursingCare2, 
				regularWorkSet.notDeductLateleave2, 
				regularWorkSet.additionTime2,
				regularWorkSet.enableSetPerWorkHour1,
				regularWorkSet.enableSetPerWorkHour2);
		return regularWork;
	}
	
	/**
	 * Convert to domain hourly payment add set.
	 *
	 * @param hourPayAaddSet the hour pay aadd set
	 * @return the hourly payment addition set
	 */
	private HourlyPaymentAdditionSet convertToDomainHourlyPaymentAddSet(KshstHourPayAaddSet hourPayAaddSet) {
		if (hourPayAaddSet != null) {
			HourlyPaymentAdditionSet hourlyPaymentAdditionSet = HourlyPaymentAdditionSet.createFromJavaType(hourPayAaddSet.kshstHourPayAaddSetPK.companyId, 
					hourPayAaddSet.calcPremiumVacation, 
					hourPayAaddSet.addition1, 
					hourPayAaddSet.deformatExcValue, 
					hourPayAaddSet.incChildNursingCare, 
					hourPayAaddSet.deduct, 
					hourPayAaddSet.calculateIncludeIntervalExemptionTime1, 
					hourPayAaddSet.calcWorkHourVacation, 
					hourPayAaddSet.addition2, 
					hourPayAaddSet.calculateIncludCareTime, 
					hourPayAaddSet.notDeductLateLeaveEarly, 
					hourPayAaddSet.calculateIncludeIntervalExemptionTime2, 
					hourPayAaddSet.enableSetPerWorkHour1, 
					hourPayAaddSet.enableSetPerWorkHour2);
			return hourlyPaymentAdditionSet;
		}
		return null;
		
	}
	
	private AddSetManageWorkHour convertToDomainAddSetManageWorkHour(KshstAddSetManWKHour kshstAddSetManWKHour) {
		if (kshstAddSetManWKHour != null) {
			AddSetManageWorkHour addSetManageWorkHour = AddSetManageWorkHour.builder()
					.companyId(kshstAddSetManWKHour.kshstAddSetManWKHourPK.companyId)
					.additionSettingOfOvertime(NotUseAtr.valueOf(kshstAddSetManWKHour.addSetOT))
					.build();
			return addSetManageWorkHour;
		}
		return null;
	}

	/**
	 * Convert to Database Holiday Addtime
	 * @param holidayAddtime
	 * @return
	 */
	private KshstHolidayAdditionSet convertToDbType(HolidayAddtion holidayAddtime){
			KshstHolidayAdditionSetPK kshstHolidayAddtimeSetPK = new KshstHolidayAdditionSetPK(holidayAddtime.getCompanyId());
			KshstHolidayAdditionSet kshstHolidayAddtimeSet =  this.queryProxy().find(kshstHolidayAddtimeSetPK,KshstHolidayAdditionSet.class).get();
				kshstHolidayAddtimeSet.referComHolidayTime = holidayAddtime.getReferComHolidayTime();
				kshstHolidayAddtimeSet.oneDay = holidayAddtime.getOneDay();
				kshstHolidayAddtimeSet.morning = holidayAddtime.getMorning();
				kshstHolidayAddtimeSet.afternoon = holidayAddtime.getAfternoon();
				kshstHolidayAddtimeSet.referActualWorkHours = holidayAddtime.getReferActualWorkHours();
				kshstHolidayAddtimeSet.notReferringAch = holidayAddtime.getNotReferringAch().value;
				kshstHolidayAddtimeSet.annualHoliday = holidayAddtime.getAnnualHoliday();
				kshstHolidayAddtimeSet.specialHoliday = holidayAddtime.getSpecialHoliday();
				kshstHolidayAddtimeSet.yearlyReserved = holidayAddtime.getYearlyReserved();
				kshstHolidayAddtimeSet.regularWorkSet = convertToDbTypeRegularWork(holidayAddtime.getRegularWork());
				kshstHolidayAddtimeSet.flexWorkSet = convertToDbTypeFlexWork(holidayAddtime.getFlexWork());
				kshstHolidayAddtimeSet.irregularWorkSet = convertToDbTypeIrregularWork(holidayAddtime.getIrregularWork());
				kshstHolidayAddtimeSet.kshstHolidayAddtimeSetPK = kshstHolidayAddtimeSetPK;
				kshstHolidayAddtimeSet.addingMethod1 = holidayAddtime.getTimeHolidayAddition().get(0).getAddingMethod().value;
				kshstHolidayAddtimeSet.workClass1 = holidayAddtime.getTimeHolidayAddition().get(0).getWorkClass().value;
				kshstHolidayAddtimeSet.addingMethod2 = holidayAddtime.getTimeHolidayAddition().get(1).getAddingMethod().value;
				kshstHolidayAddtimeSet.workClass2 = holidayAddtime.getTimeHolidayAddition().get(1).getWorkClass().value;
				kshstHolidayAddtimeSet.addSetManWKHour = convertToDbTypeAddSetManWKHour(holidayAddtime.getAdditionSettingOfOvertime());
		return kshstHolidayAddtimeSet;
	} 

	/**
	 * Convert to Database Irregular Work
	 * @param irregularWork
	 * @return
	 */
	private KshstWorkDepLaborSet convertToDbTypeIrregularWork(WorkDepLabor irregularWork) {
			KshstWorkDepLaborSetPK kshstWorkDepLaborSetPK = new KshstWorkDepLaborSetPK(irregularWork.getCompanyId());
			KshstWorkDepLaborSet kshstWorkDepLaborSet = this.queryProxy().find(kshstWorkDepLaborSetPK,KshstWorkDepLaborSet.class).get();
				kshstWorkDepLaborSet.calcActualOperation1 = irregularWork.getCalcActualOperation1().value;
				kshstWorkDepLaborSet.exemptTaxTime1 = irregularWork.getExemptTaxTime1();
				kshstWorkDepLaborSet.incChildNursingCare1 = irregularWork.getIncChildNursingCare1();
				kshstWorkDepLaborSet.additionTime1 = irregularWork.getAdditionTime1();
				kshstWorkDepLaborSet.notDeductLateleave1 = irregularWork.getNotDeductLateleave1();
				kshstWorkDepLaborSet.deformatExcValue = irregularWork.getDeformatExcValue().value;
				kshstWorkDepLaborSet.exemptTaxTime2 = irregularWork.getExemptTaxTime2();
				kshstWorkDepLaborSet.minusAbsenceTime2 = irregularWork.getMinusAbsenceTime2();
				kshstWorkDepLaborSet.calcActualOperation2 = irregularWork.getCalcActualOperation2().value;
				kshstWorkDepLaborSet.incChildNursingCare2 = irregularWork.getIncChildNursingCare2();
				kshstWorkDepLaborSet.notDeductLateleave2 = irregularWork.getNotDeductLateleave2();
				kshstWorkDepLaborSet.additionTime2 = irregularWork.getAdditionTime2();
				kshstWorkDepLaborSet.kshstWorkDepLaborSetPK = kshstWorkDepLaborSetPK;
		return kshstWorkDepLaborSet;
	}
	
	private KshstHourPayAaddSet convertToDbTypeHourPayAaddSet(HourlyPaymentAdditionSet hourlyPaymentAdditionSet) {
		KshstHourPayAaddSetPK kshstHourPayAaddSetPK = new KshstHourPayAaddSetPK(hourlyPaymentAdditionSet.getCompanyId());
		KshstHourPayAaddSet kshstHourPayAaddSet = this.queryProxy().find(kshstHourPayAaddSetPK,KshstHourPayAaddSet.class).get();
			kshstHourPayAaddSet.calcPremiumVacation = hourlyPaymentAdditionSet.getCalcPremiumVacation().value;
			kshstHourPayAaddSet.addition1 = hourlyPaymentAdditionSet.getAddition1().value;
			kshstHourPayAaddSet.deformatExcValue = hourlyPaymentAdditionSet.getDeformatExcValue().value;
			kshstHourPayAaddSet.incChildNursingCare = hourlyPaymentAdditionSet.getIncChildNursingCare().value;
			kshstHourPayAaddSet.deduct = hourlyPaymentAdditionSet.isDeduct() == true ? 1 : 0;
			kshstHourPayAaddSet.calculateIncludeIntervalExemptionTime1 = hourlyPaymentAdditionSet.getCalculateIncludeIntervalExemptionTime1().value;
			kshstHourPayAaddSet.calcWorkHourVacation = hourlyPaymentAdditionSet.getCalcWorkHourVacation().value;
			kshstHourPayAaddSet.addition2 = hourlyPaymentAdditionSet.getAddition2().value;
			kshstHourPayAaddSet.calculateIncludCareTime = hourlyPaymentAdditionSet.getCalculateIncludCareTime().value;
			kshstHourPayAaddSet.notDeductLateLeaveEarly = hourlyPaymentAdditionSet.getNotDeductLateLeaveEarly().value;
			kshstHourPayAaddSet.calculateIncludeIntervalExemptionTime2 = hourlyPaymentAdditionSet.getCalculateIncludeIntervalExemptionTime2().value;
			kshstHourPayAaddSet.enableSetPerWorkHour1 = hourlyPaymentAdditionSet.isEnableSetPerWorkHour1() == true ? 1 : 0;
			kshstHourPayAaddSet.enableSetPerWorkHour2 = hourlyPaymentAdditionSet.isEnableSetPerWorkHour2() == true ? 1 : 0;
			kshstHourPayAaddSet.kshstHourPayAaddSetPK = kshstHourPayAaddSetPK;
		return kshstHourPayAaddSet;
}

	/**
	 * Convert to Database Flex Work
	 * @param flexWork
	 * @return
	 */
	private KshstWorkFlexSet convertToDbTypeFlexWork(FlexWork flexWork) {
			
			KshstWorkFlexSetPK kshstFlexWorkSetPK = new KshstWorkFlexSetPK(flexWork.getCompanyId());
			KshstWorkFlexSet kshstFlexWorkSet =  this.queryProxy().find(kshstFlexWorkSetPK,KshstWorkFlexSet.class).get();
				kshstFlexWorkSet.calcActualOperation1 = flexWork.getCalcActualOperation1().value;
				kshstFlexWorkSet.exemptTaxTime1 = flexWork.getExemptTaxTime1();
				kshstFlexWorkSet.incChildNursingCare1 = flexWork.getIncChildNursingCare1();
				kshstFlexWorkSet.predeterminedOvertime1 = flexWork.getPredeterminedOvertime1().value;
				kshstFlexWorkSet.additionTime1  = flexWork.getAdditionTime1();
				kshstFlexWorkSet.notDeductLateleave1 = flexWork.getNotDeductLateleave1();
				kshstFlexWorkSet.exemptTaxTime2 = flexWork.getExemptTaxTime2();
				kshstFlexWorkSet.minusAbsenceTime2 = flexWork.getMinusAbsenceTime2();
				kshstFlexWorkSet.calcActualOperation2 = flexWork.getCalcActualOperation2().value;
				kshstFlexWorkSet.incChildNursingCare2 = flexWork.getIncChildNursingCare2();
				kshstFlexWorkSet.notDeductLateleave2 = flexWork.getNotDeductLateleave2();
				kshstFlexWorkSet.predeterminDeficiency2 = flexWork.getPredeterminDeficiency2().value;
				kshstFlexWorkSet.additionTime2 = flexWork.getAdditionTime2();
				kshstFlexWorkSet.kshstFlexWorkSetPK = kshstFlexWorkSetPK;
		return kshstFlexWorkSet;
	}
	
	private KshstAddSetManWKHour convertToDbTypeAddSetManWKHour(AddSetManageWorkHour addSetManageWorkHour) {
		
		KshstAddSetManWKHourPK kshstAddSetManWKHourPK = new KshstAddSetManWKHourPK(addSetManageWorkHour.getCompanyId());
		KshstAddSetManWKHour kshstAddSetManWKHour = this.queryProxy().find(kshstAddSetManWKHourPK, KshstAddSetManWKHour.class).get();
		kshstAddSetManWKHour.addSetOT = addSetManageWorkHour.getAdditionSettingOfOvertime().value;
		
		return kshstAddSetManWKHour;
	}

	/**
	 * Find by Company Id
	 */
	@Override
	public List<HolidayAddtion> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstHolidayAdditionSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Holiday Addtime
	 */
	@Override
	public void add(HolidayAddtion holidayAddtime) {
		this.commandProxy().insert(convertToDbType(holidayAddtime));
	}
	
	/**
	 * Update Holiday Addtime
	 */
	@Override
	public void update(HolidayAddtion holidayAddtime) {
			KshstHolidayAdditionSetPK primaryKey = new KshstHolidayAdditionSetPK(holidayAddtime.getCompanyId());
			KshstHolidayAdditionSet entity = this.queryProxy().find(primaryKey, KshstHolidayAdditionSet.class).get();
				entity.referComHolidayTime = holidayAddtime.getReferComHolidayTime();
				entity.oneDay = holidayAddtime.getOneDay();
				entity.morning = holidayAddtime.getMorning();
				entity.afternoon = holidayAddtime.getAfternoon();
				entity.referActualWorkHours = holidayAddtime.getReferActualWorkHours();
				entity.notReferringAch = holidayAddtime.getNotReferringAch().value;
				entity.annualHoliday = holidayAddtime.getAnnualHoliday();
				entity.specialHoliday = holidayAddtime.getSpecialHoliday();
				entity.yearlyReserved = holidayAddtime.getYearlyReserved();
				
				entity.regularWorkSet = convertToDbTypeRegularWork(holidayAddtime.getRegularWork());
				entity.flexWorkSet = convertToDbTypeFlexWork(holidayAddtime.getFlexWork());
				entity.irregularWorkSet = convertToDbTypeIrregularWork(holidayAddtime.getIrregularWork());
				entity.hourPayAaddSet = convertToDbTypeHourPayAaddSet(holidayAddtime.getHourPaymentAddition());
				
				entity.kshstHolidayAddtimeSetPK = primaryKey;
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<HolidayAddtion> findByCId(String companyId) {
		return this.queryProxy().find(new KshstHolidayAdditionSetPK(companyId),KshstHolidayAdditionSet.class)
				.map(c->convertToDomain(c));
	}
}
