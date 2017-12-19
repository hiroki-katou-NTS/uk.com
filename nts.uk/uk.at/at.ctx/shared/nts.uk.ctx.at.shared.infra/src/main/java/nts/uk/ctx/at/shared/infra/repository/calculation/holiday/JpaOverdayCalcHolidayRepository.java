package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;
/**
 * @author phongtq
 * JPA Overday Calc Holiday
 */
import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalc;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalcHoliday;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalcHolidayRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayHolidayAtten;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.WeekdayHoliday;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstOverDayCalcSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstOverDayCalcSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstOverDayHdSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstOverDayHdSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstOverdayHdAttSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstOverdayHdAttSetPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWeekdayHd;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWeekdayHdPK;

public class JpaOverdayCalcHolidayRepository extends JpaRepository implements OverdayCalcHolidayRepository {
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstOverDayCalcSet e");
		builderString.append(" WHERE e.kshstOverDay CalcSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Overday Calc Set 
	 * @param kshstOverDayCalcSet
	 * @return
	 */
	private OverdayCalc convertToDomain(KshstOverDayCalcSet kshstOverDayCalcSet) {
		WeekdayHoliday weekdayHoliday = convertToDomainWeekday(kshstOverDayCalcSet.weekdayHd);
		OverdayHolidayAtten overdayHolidayAtten = convertToDomainHolidayAtten(kshstOverDayCalcSet.overdayHdAttSet);
		OverdayCalcHoliday overdayCalcHoliday = convertToDomainCalcHoliday(kshstOverDayCalcSet.overDayHdSet);
		
		OverdayCalc calcHoliday = OverdayCalc.createFromJavaType(kshstOverDayCalcSet.kshstOverDayCalcSetPK.companyId,
				kshstOverDayCalcSet.calcOverDayEnd, kshstOverDayCalcSet.statutoryHd, kshstOverDayCalcSet.excessHd,
				kshstOverDayCalcSet.excessSpecialHoliday, kshstOverDayCalcSet.weekDayStatutoryHd,
				kshstOverDayCalcSet.excessStatutoryHd, kshstOverDayCalcSet.excessStatutorSphd,
				kshstOverDayCalcSet.weekDayLegalHd, kshstOverDayCalcSet.excessLegalHd,
				kshstOverDayCalcSet.excessLegalSphd, kshstOverDayCalcSet.weekDayPublicHd,
				kshstOverDayCalcSet.excessPublicHd, kshstOverDayCalcSet.excessPublicSphd,
				weekdayHoliday,
				overdayHolidayAtten,
				overdayCalcHoliday);
		
		return calcHoliday;
	}

	/**
	 * Convert to Database Overday Calc Set 
	 * @param overdayCalc
	 * @return
	 */
	private KshstOverDayCalcSet convertToDbType(OverdayCalc overdayCalc) {
		KshstOverDayCalcSet calcSet = new KshstOverDayCalcSet();
		KshstOverDayCalcSetPK calcSetPK = new KshstOverDayCalcSetPK(overdayCalc.getCompanyId());
				calcSet.calcOverDayEnd = overdayCalc.getCalcOverDayEnd();
				calcSet.statutoryHd = overdayCalc.getStatutoryHd();
				calcSet.excessHd = overdayCalc.getExcessHd();
				calcSet.excessSpecialHoliday = overdayCalc.getExcessSpecialHoliday();
				calcSet.weekDayStatutoryHd = overdayCalc.getWeekDayStatutoryHd();
				calcSet.excessStatutoryHd = overdayCalc.getExcessStatutoryHd();
				calcSet.excessStatutorSphd = overdayCalc.getExcessStatutorSphd();
				calcSet.weekDayLegalHd = overdayCalc.getWeekDayLegalHd();
				calcSet.excessLegalHd = overdayCalc.getExcessLegalHd();
				calcSet.excessLegalSphd = overdayCalc.getExcessLegalSphd();
				calcSet.weekDayPublicHd = overdayCalc.getWeekDayPublicHd();
				calcSet.excessPublicHd = overdayCalc.getExcessPublicHd();
				calcSet.excessPublicSphd = overdayCalc.getExcessPublicSphd();
				calcSet.weekdayHd = convertToDbTypeWeekday(overdayCalc.getWeekdayHoliday());
				calcSet.overdayHdAttSet = convertToDbTypeHolidayAtten(overdayCalc.getOverdayHolidayAtten());
				calcSet.overDayHdSet = convertToDbTypeCalcHoliday(overdayCalc.getOverdayCalcHoliday());
				calcSet.kshstOverDayCalcSetPK = calcSetPK;
				
		return calcSet;
	}

	private WeekdayHoliday convertToDomainWeekday(KshstWeekdayHd kshstWeekdayHd) {
		WeekdayHoliday weekdayHoliday = WeekdayHoliday.createFromJavaType(kshstWeekdayHd.kshstWeekdayHdPK.companyId,
				kshstWeekdayHd.kshstWeekdayHdPK.overworkFrameNo, kshstWeekdayHd.weekdayNo,
				kshstWeekdayHd.excessHolidayNo, kshstWeekdayHd.excessSphdNo);
		
		return weekdayHoliday;
	}

	private KshstWeekdayHd convertToDbTypeWeekday(WeekdayHoliday holiday) {
		KshstWeekdayHd weekdayHd = new KshstWeekdayHd();
		KshstWeekdayHdPK weekdayHdPK = new KshstWeekdayHdPK(holiday.getCompanyId(), holiday.getOverworkFrameNo());
				weekdayHd.weekdayNo = holiday.getWeekdayNo();
				weekdayHd.excessHolidayNo = holiday.getExcessHolidayNo();
				weekdayHd.excessSphdNo = holiday.getExcessSphdNo();
				weekdayHd.kshstWeekdayHdPK = weekdayHdPK;
				
		return weekdayHd;
	}

	private OverdayHolidayAtten convertToDomainHolidayAtten(KshstOverdayHdAttSet kshstOverdayHdAttSet) {
		OverdayHolidayAtten overdayHolidayAtten = OverdayHolidayAtten.createFromJavaType(
				kshstOverdayHdAttSet.kshstOverdayHdAttSetPK.companyId,
				kshstOverdayHdAttSet.kshstOverdayHdAttSetPK.holidayWorkFrameNo, kshstOverdayHdAttSet.overWorkNo);
		
		return overdayHolidayAtten;
	}

	private KshstOverdayHdAttSet convertToDbTypeHolidayAtten(OverdayHolidayAtten atten) {
		KshstOverdayHdAttSet attSet = new KshstOverdayHdAttSet();
		KshstOverdayHdAttSetPK attSetPK = new KshstOverdayHdAttSetPK(atten.getCompanyId(),
				atten.getHolidayWorkFrameNo());
				attSet.overWorkNo = atten.getOverWorkNo();
				attSet.kshstOverdayHdAttSetPK = attSetPK;

		return attSet;
	}
	
	private OverdayCalcHoliday convertToDomainCalcHoliday(KshstOverDayHdSet dayHdSet){
		OverdayCalcHoliday calcHoliday = OverdayCalcHoliday.createFromJavaType(dayHdSet.kshstOverDayHdSetPK.companyId, 
				dayHdSet.kshstOverDayHdSetPK.holidayWorkFrameNo, 
				dayHdSet.calcOverDayEnd, 
				dayHdSet.statutoryHd, 
				dayHdSet.excessHd);
		
		return calcHoliday;
	}
	
	private KshstOverDayHdSet convertToDbTypeCalcHoliday(OverdayCalcHoliday overdayCalcHoliday){
		KshstOverDayHdSet dayHdSet = new KshstOverDayHdSet();
		KshstOverDayHdSetPK dayHdSetPK = new KshstOverDayHdSetPK(overdayCalcHoliday.getCompanyId(), overdayCalcHoliday.getHolidayWorkFrameNo());
		dayHdSet.calcOverDayEnd = overdayCalcHoliday.getCalcOverDayEnd();
		dayHdSet.statutoryHd = overdayCalcHoliday.getStatutoryHd();
		dayHdSet.excessHd = overdayCalcHoliday.getExcessHd();
		dayHdSet.kshstOverDayHdSetPK = dayHdSetPK;
		
		return dayHdSet;
	}
	
	/**
	 * Find by Overday Calc
	 */
	@Override
	public List<OverdayCalc> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstOverDayCalcSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Overday Calc
	 */
	@Override
	public void add(OverdayCalc overdayCalc) {
		this.commandProxy().insert(convertToDbType(overdayCalc));
	}

	/**
	 * Update Overday Calc
	 */
	@Override
	public void update(OverdayCalc overdayCalc) {
		KshstOverDayCalcSetPK primaryKey = new KshstOverDayCalcSetPK(overdayCalc.getCompanyId());
		KshstOverDayCalcSet entity = this.queryProxy().find(primaryKey, KshstOverDayCalcSet.class).get();
				entity.calcOverDayEnd = overdayCalc.getCalcOverDayEnd();
				entity.statutoryHd = overdayCalc.getStatutoryHd();
				entity.excessHd = overdayCalc.getExcessHd();
				entity.excessSpecialHoliday = overdayCalc.getExcessSpecialHoliday();
				entity.weekDayStatutoryHd = overdayCalc.getWeekDayStatutoryHd();
				entity.excessStatutoryHd = overdayCalc.getExcessStatutoryHd();
				entity.excessStatutorSphd = overdayCalc.getExcessStatutorSphd();
				entity.weekDayLegalHd = overdayCalc.getWeekDayLegalHd();
				entity.excessLegalHd = overdayCalc.getExcessLegalHd();
				entity.excessLegalSphd = overdayCalc.getExcessLegalSphd();
				entity.weekDayPublicHd = overdayCalc.getWeekDayPublicHd();
				entity.excessPublicHd = overdayCalc.getExcessPublicHd();
				entity.excessPublicSphd = overdayCalc.getExcessPublicSphd();
				entity.weekdayHd = convertToDbTypeWeekday(overdayCalc.getWeekdayHoliday());
				entity.overdayHdAttSet = convertToDbTypeHolidayAtten(overdayCalc.getOverdayHolidayAtten());
				entity.overDayHdSet = convertToDbTypeCalcHoliday(overdayCalc.getOverdayCalcHoliday());
				
				entity.kshstOverDayCalcSetPK = primaryKey;
		this.commandProxy().update(entity);
	}
	
}
