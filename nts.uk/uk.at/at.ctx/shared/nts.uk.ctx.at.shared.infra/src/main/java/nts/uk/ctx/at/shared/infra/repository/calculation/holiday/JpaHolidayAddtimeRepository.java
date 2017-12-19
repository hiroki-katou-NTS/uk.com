package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.FlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtime;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtimeRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.IrregularWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.RegularWork;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstFlexWorkSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHolidayAddtimeSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHolidayAddtimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstIrregularWorkSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstIrregularWorkSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstRegularWorkSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstRegularWorkSetPK;

@Stateless
public class JpaHolidayAddtimeRepository extends JpaRepository implements HolidayAddtimeRepository {

	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstHolidayAddtimeSet e");
		builderString.append(" WHERE e.kshstHolidayAddtimeSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Holiday Addtime
	 * @param holidayAddtimeSet
	 * @return
	 */
	private HolidayAddtime convertToDomain(KshstHolidayAddtimeSet holidayAddtimeSet){
		
//		RegularWork regularWork = convertToDomainRegularWork(holidayAddtimeSet.regularWorkSet);
//		FlexWork flexWork = convertToDomainFlexWork(holidayAddtimeSet.flexWorkSet);
//		IrregularWork irregularWork = convertToDomainIrregularWork(holidayAddtimeSet.irregularWorkSet);

		HolidayAddtime addtime = HolidayAddtime.createFromJavaType(holidayAddtimeSet.kshstHolidayAddtimeSetPK.companyId, 
				holidayAddtimeSet.referComHolidayTime,
				holidayAddtimeSet.oneDay, 
				holidayAddtimeSet.morning, 
				holidayAddtimeSet.afternoon, 
				holidayAddtimeSet.referActualWorkHours, 
				holidayAddtimeSet.notReferringAch, 
				holidayAddtimeSet.annualHoliday, 
				holidayAddtimeSet.specialHoliday, 
				holidayAddtimeSet.yearlyReserved, 
				null, 
				null, 
				null);
		return addtime;
	}
	
	/**
	 * Convert to Database Regular Work
	 * @param regularWork
	 * @return
	 */
	private KshstRegularWorkSet convertToDbTypeRegularWork(RegularWork regularWork) {
			KshstRegularWorkSet kshstRegularWorkSet = new KshstRegularWorkSet();
			KshstRegularWorkSetPK kshstRegularWorkSetPK = new KshstRegularWorkSetPK(regularWork.getCompanyId());
				kshstRegularWorkSet.calcActualOperationPre = regularWork.getCalcActualOperationPre();
				kshstRegularWorkSet.calcIntervalTimePre = regularWork.getCalcIntervalTimePre();
				kshstRegularWorkSet.calcIncludCarePre = regularWork.getCalcIncludCarePre();
				kshstRegularWorkSet.additionTimePre  = regularWork.getAdditionTimePre();
				kshstRegularWorkSet.notDeductLateleavePre = regularWork.getNotDeductLateleavePre();
				kshstRegularWorkSet.deformatExcValuePre = regularWork.getDeformatExcValuePre();
				kshstRegularWorkSet.minusAbsenceTimeWork = regularWork.getMinusAbsenceTimeWork();
				kshstRegularWorkSet.calcActualOperaWork = regularWork.getCalcActualOperaWork();
				kshstRegularWorkSet.calcIncludCareWork = regularWork.getCalcIncludCareWork();
				kshstRegularWorkSet.notDeductLateleaveWork = regularWork.getNotDeductLateleaveWork();
				kshstRegularWorkSet.additionTimeWork = regularWork.getAdditionTimeWork();
				kshstRegularWorkSet.kshstRegularWorkSetPK = kshstRegularWorkSetPK;
		return kshstRegularWorkSet;
	}

	/**
	 * Convert to Domain Irregular Work
	 * @param irregularWorkSet
	 * @return
	 */
	private IrregularWork convertToDomainIrregularWork(KshstIrregularWorkSet irregularWorkSet) {
		IrregularWork irregularWork = IrregularWork.createFromJavaType(irregularWorkSet.kshstIrregularWorkSetPK.companyId, 
				irregularWorkSet.calcActualOperationPre, 
				irregularWorkSet.calcIntervalTimePre, 
				irregularWorkSet.calcIncludCarePre, 
				irregularWorkSet.additionTimePre, 
				irregularWorkSet.notDeductLateleavePre, 
				irregularWorkSet.deformatExcValuePre, 
				irregularWorkSet.calcIntervalTimeWork, 
				irregularWorkSet.minusAbsenceTimeWork, 
				irregularWorkSet.calcActualOperaWork, 
				irregularWorkSet.calcIncludCareWork, 
				irregularWorkSet.notDeductLateleaveWork, 
				irregularWorkSet.additionTimeWork);
		return irregularWork;
	}

	/**
	 * Convert to Domain Flex Work
	 * @param flexWorkSet
	 * @return
	 */
	private FlexWork convertToDomainFlexWork(KshstFlexWorkSet flexWorkSet) {
		FlexWork flexWork = FlexWork.createFromJavaType(flexWorkSet.kshstFlexWorkSetPK.companyId, 
				flexWorkSet.holidayCalcMethodSet, 
				flexWorkSet.addWithMonthStatutory, 
				flexWorkSet.calcActualOperationPre, 
				flexWorkSet.calcIntervalTimePre, 
				flexWorkSet.calcIncludCarePre, 
				flexWorkSet.predExcessTimeflexPre, 
				flexWorkSet.additionTimePre, 
				flexWorkSet.notDeductLateleavePre, 
				flexWorkSet.deformatExcValuePre, 
				flexWorkSet.calsIntervalTimeWork, 
				flexWorkSet.minusAbsenceTimeWork, 
				flexWorkSet.calcActualOperaWork, 
				flexWorkSet.calcIncludCareWork, 
				flexWorkSet.notDeductLateleaveWork,  
				flexWorkSet.additionTimeWork);
		return flexWork;
	}

	/**
	 * Convert to Domain Regular Work
	 * @param regularWorkSet
	 * @return
	 */
	private RegularWork convertToDomainRegularWork(KshstRegularWorkSet regularWorkSet) {
		RegularWork regularWork = RegularWork.createFromJavaType(regularWorkSet.kshstRegularWorkSetPK.companyId, 
				regularWorkSet.calcActualOperationPre, 
				regularWorkSet.calcIntervalTimePre, 
				regularWorkSet.calcIncludCarePre, 
				regularWorkSet.additionTimePre, 
				regularWorkSet.notDeductLateleavePre, 
				regularWorkSet.deformatExcValuePre, 
				regularWorkSet.calsIntervalTimeWork, 
				regularWorkSet.minusAbsenceTimeWork, 
				regularWorkSet.calcActualOperaWork, 
				regularWorkSet.calcIncludCareWork, 
				regularWorkSet.notDeductLateleaveWork, 
				regularWorkSet.additionTimeWork);
		return regularWork;
	}

	/**
	 * Convert to Database Holiday Addtime
	 * @param holidayAddtime
	 * @return
	 */
	private KshstHolidayAddtimeSet convertToDbType(HolidayAddtime holidayAddtime){
			KshstHolidayAddtimeSet kshstHolidayAddtimeSet = new KshstHolidayAddtimeSet();
			KshstHolidayAddtimeSetPK kshstHolidayAddtimeSetPK = new KshstHolidayAddtimeSetPK(holidayAddtime.getCompanyId());
				kshstHolidayAddtimeSet.referComHolidayTime = holidayAddtime.getReferComHolidayTime();
				kshstHolidayAddtimeSet.oneDay = holidayAddtime.getOneDay();
				kshstHolidayAddtimeSet.morning = holidayAddtime.getMorning();
				kshstHolidayAddtimeSet.afternoon = holidayAddtime.getAfternoon();
				kshstHolidayAddtimeSet.referActualWorkHours = holidayAddtime.getReferActualWorkHours();
				kshstHolidayAddtimeSet.notReferringAch = holidayAddtime.getNotReferringAch().value;
				kshstHolidayAddtimeSet.annualHoliday = holidayAddtime.getAnnualHoliday();
				kshstHolidayAddtimeSet.specialHoliday = holidayAddtime.getSpecialHoliday();
				kshstHolidayAddtimeSet.yearlyReserved = holidayAddtime.getYearlyReserved();
//				kshstHolidayAddtimeSet.regularWorkSet = convertToDbTypeRegularWork(holidayAddtime.getRegularWork());
//				kshstHolidayAddtimeSet.flexWorkSet = convertToDbTypeFlexWork(holidayAddtime.getFlexWork());
//				kshstHolidayAddtimeSet.irregularWorkSet = convertToDbTypeIrregularWork(holidayAddtime.getIrregularWork());
				kshstHolidayAddtimeSet.kshstHolidayAddtimeSetPK = kshstHolidayAddtimeSetPK;
		return kshstHolidayAddtimeSet;
	} 

	/**
	 * Convert to Database Irregular Work
	 * @param irregularWork
	 * @return
	 */
	private KshstIrregularWorkSet convertToDbTypeIrregularWork(IrregularWork irregularWork) {
			KshstIrregularWorkSet kshstIrregularWorkSet = new KshstIrregularWorkSet();
			KshstIrregularWorkSetPK kshstIrregularWorkSetPK = new KshstIrregularWorkSetPK(irregularWork.getCompanyId());
				kshstIrregularWorkSet.calcActualOperationPre = irregularWork.getCalcActualOperationPre();
				kshstIrregularWorkSet.calcIntervalTimePre = irregularWork.getCalcIntervalTimePre();
				kshstIrregularWorkSet.calcIncludCarePre = irregularWork.getCalcIncludCarePre();
				kshstIrregularWorkSet.additionTimePre  = irregularWork.getAdditionTimePre();
				kshstIrregularWorkSet.notDeductLateleavePre = irregularWork.getNotDeductLateleavePre();
				kshstIrregularWorkSet.deformatExcValuePre = irregularWork.getDeformatExcValuePre();
				kshstIrregularWorkSet.calcIntervalTimeWork = irregularWork.getCalsIntervalTimeWork();
				kshstIrregularWorkSet.minusAbsenceTimeWork = irregularWork.getMinusAbsenceTimeWork();
				kshstIrregularWorkSet.calcActualOperaWork = irregularWork.getCalcActualOperaWork();
				kshstIrregularWorkSet.calcIncludCareWork = irregularWork.getCalcIncludCareWork();
				kshstIrregularWorkSet.notDeductLateleaveWork = irregularWork.getNotDeductLateleaveWork();
				kshstIrregularWorkSet.additionTimeWork = irregularWork.getAdditionTimeWork();
				kshstIrregularWorkSet.kshstIrregularWorkSetPK = kshstIrregularWorkSetPK;
		return kshstIrregularWorkSet;
	}

	/**
	 * Convert to Database Flex Work
	 * @param flexWork
	 * @return
	 */
	private KshstFlexWorkSet convertToDbTypeFlexWork(FlexWork flexWork) {
			KshstFlexWorkSet kshstFlexWorkSet = new KshstFlexWorkSet();
			KshstFlexWorkSetPK kshstFlexWorkSetPK = new KshstFlexWorkSetPK(flexWork.getCompanyId());
				kshstFlexWorkSet.holidayCalcMethodSet = flexWork.getHolidayCalcMethodSet();
				kshstFlexWorkSet.addWithMonthStatutory = flexWork.getAddWithMonthStatutory();
				kshstFlexWorkSet.calcActualOperationPre = flexWork.getCalcActualOperationPre();
				kshstFlexWorkSet.calcIntervalTimePre = flexWork.getCalcIntervalTimePre();
				kshstFlexWorkSet.calcIncludCarePre = flexWork.getCalcIncludCarePre();
				kshstFlexWorkSet.additionTimePre  = flexWork.getAdditionTimePre();
				kshstFlexWorkSet.notDeductLateleavePre = flexWork.getNotDeductLateleavePre();
				kshstFlexWorkSet.deformatExcValuePre = flexWork.getDeformatExcValuePre();
				kshstFlexWorkSet.minusAbsenceTimeWork = flexWork.getMinusAbsenceTimeWork();
				kshstFlexWorkSet.calcActualOperaWork = flexWork.getCalcActualOperaWork();
				kshstFlexWorkSet.calcIncludCareWork = flexWork.getCalcIncludCareWork();
				kshstFlexWorkSet.notDeductLateleaveWork = flexWork.getNotDeductLateleaveWork();
				kshstFlexWorkSet.additionTimeWork = flexWork.getAdditionTimeWork();
				kshstFlexWorkSet.kshstFlexWorkSetPK = kshstFlexWorkSetPK;
		return kshstFlexWorkSet;
	}

	/**
	 * Find by Company Id
	 */
	@Override
	public List<HolidayAddtime> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstHolidayAddtimeSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Holiday Addtime
	 */
	@Override
	public void add(HolidayAddtime holidayAddtime) {
		this.commandProxy().insert(convertToDbType(holidayAddtime));
	}
	
	/**
	 * Update Holiday Addtime
	 */
	@Override
	public void update(HolidayAddtime holidayAddtime) {
			KshstHolidayAddtimeSetPK primaryKey = new KshstHolidayAddtimeSetPK(holidayAddtime.getCompanyId());
			KshstHolidayAddtimeSet entity = this.queryProxy().find(primaryKey, KshstHolidayAddtimeSet.class).get();
				entity.referComHolidayTime = holidayAddtime.getReferComHolidayTime();
				entity.oneDay = holidayAddtime.getOneDay();
				entity.morning = holidayAddtime.getMorning();
				entity.afternoon = holidayAddtime.getAfternoon();
				entity.referActualWorkHours = holidayAddtime.getReferActualWorkHours();
				entity.notReferringAch = holidayAddtime.getNotReferringAch().value;
				entity.annualHoliday = holidayAddtime.getAnnualHoliday();
				entity.specialHoliday = holidayAddtime.getSpecialHoliday();
				entity.yearlyReserved = holidayAddtime.getYearlyReserved();
//				
//				entity.regularWorkSet = convertToDbTypeRegularWork(holidayAddtime.getRegularWork());
//				entity.flexWorkSet = convertToDbTypeFlexWork(holidayAddtime.getFlexWork());
//				entity.irregularWorkSet = convertToDbTypeIrregularWork(holidayAddtime.getIrregularWork());
				
				entity.kshstHolidayAddtimeSetPK = primaryKey;
		this.commandProxy().update(entity);
	}
}
