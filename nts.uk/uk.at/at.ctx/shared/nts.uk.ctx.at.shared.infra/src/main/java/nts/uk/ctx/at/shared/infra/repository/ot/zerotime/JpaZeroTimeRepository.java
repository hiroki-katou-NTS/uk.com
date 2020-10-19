package nts.uk.ctx.at.shared.infra.repository.ot.zerotime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.HdFromHd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.HdFromWeekday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.WeekdayHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.ZeroTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.ZeroTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshstHdFromHd;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshstHdFromHdPK;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshstHdFromWeekday;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshstHdFromWeekdayPK;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshstWeekdayFromHd;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshstWeekdayFromHdPK;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshstZeroTimeSet;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshstZeroTimeSetPK;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaZeroTimeRepository extends JpaRepository implements ZeroTimeRepository {
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstZeroTimeSet e");
		builderString.append(" WHERE e.kshstOverDayCalcSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Overday Calc Set
	 * 
	 * @param kshstOverDayCalcSet
	 * @return
	 */
	private ZeroTime convertToDomain(KshstZeroTimeSet kshstOverDayCalcSet) {
		List<WeekdayHoliday> weekdayHoliday = kshstOverDayCalcSet.weekdayHd.stream().map(c -> convertToDomainWeekday(c))
				.collect(Collectors.toList());
		List<HdFromWeekday> overdayHolidayAtten = kshstOverDayCalcSet.overdayHdAttSet.stream().map(c -> c.toDomain())
				.collect(Collectors.toList());
		List<HdFromHd> overdayCalcHoliday = kshstOverDayCalcSet.overDayHdSet.stream()
				.map(c -> convertToDomainCalcHoliday(c)).collect(Collectors.toList());

		ZeroTime calcHoliday = ZeroTime.createFromJavaType(kshstOverDayCalcSet.kshstOverDayCalcSetPK.companyId,
				kshstOverDayCalcSet.calcFromZeroTime, kshstOverDayCalcSet.legalHd, kshstOverDayCalcSet.nonLegalHd,
				kshstOverDayCalcSet.nonLegalPublicHd, kshstOverDayCalcSet.weekday1, kshstOverDayCalcSet.nonLegalHd1,
				kshstOverDayCalcSet.nonLegalPublicHd1, kshstOverDayCalcSet.weekday2, kshstOverDayCalcSet.legalHd2,
				kshstOverDayCalcSet.nonLegalHd2, kshstOverDayCalcSet.weekday3, kshstOverDayCalcSet.legalHd3,
				kshstOverDayCalcSet.nonLegalPublicHd3, weekdayHoliday, overdayHolidayAtten, overdayCalcHoliday);

		return calcHoliday;
	}

	/**
	 * Convert to Database Overday Calc Set
	 * 
	 * @param overdayCalc
	 * @return
	 */
	private KshstZeroTimeSet convertToDbType(ZeroTime overdayCalc) {
		KshstZeroTimeSet calcSet = new KshstZeroTimeSet();
		KshstZeroTimeSetPK calcSetPK = new KshstZeroTimeSetPK(overdayCalc.getCompanyId());
		calcSet.calcFromZeroTime = overdayCalc.getCalcFromZeroTime();
		calcSet.legalHd = overdayCalc.getLegalHd();
		calcSet.nonLegalHd = overdayCalc.getNonLegalHd();
		calcSet.nonLegalPublicHd = overdayCalc.getNonLegalPublicHd();
		calcSet.weekday1 = overdayCalc.getWeekday1();
		calcSet.nonLegalHd1 = overdayCalc.getNonLegalHd1();
		calcSet.nonLegalPublicHd1 = overdayCalc.getNonLegalPublicHd1();
		calcSet.weekday2 = overdayCalc.getWeekday2();
		calcSet.legalHd2 = overdayCalc.getLegalHd2();
		calcSet.nonLegalHd2 = overdayCalc.getNonLegalHd2();
		calcSet.weekday3 = overdayCalc.getWeekday3();
		calcSet.legalHd3 = overdayCalc.getLegalHd3();
		calcSet.nonLegalPublicHd3 = overdayCalc.getNonLegalPublicHd3();
		calcSet.weekdayHd = overdayCalc.getWeekdayHoliday().stream().map(c -> convertToDbTypeWeekday(c))
				.collect(Collectors.toList());
		calcSet.overdayHdAttSet = overdayCalc.getOverdayHolidayAtten().stream().map(c -> convertToDbTypeHolidayAtten(c))
				.collect(Collectors.toList());
		calcSet.overDayHdSet = overdayCalc.getOverdayCalcHoliday().stream().map(c -> convertToDbTypeCalcHoliday(c))
				.collect(Collectors.toList());
		calcSet.kshstOverDayCalcSetPK = calcSetPK;

		return calcSet;
	}

	/**
	 * Convert to Domain Weekday From Holiday
	 * 
	 * @param kshstWeekdayHd
	 * @return
	 */
	private WeekdayHoliday convertToDomainWeekday(KshstWeekdayFromHd kshstWeekdayHd) {
		WeekdayHoliday weekdayHoliday = WeekdayHoliday.createFromJavaType(kshstWeekdayHd.kshstWeekdayHdPK.companyId,
				kshstWeekdayHd.kshstWeekdayHdPK.overworkFrameNo, kshstWeekdayHd.weekdayNo,
				kshstWeekdayHd.excessHolidayNo, kshstWeekdayHd.excessSphdNo);

		return weekdayHoliday;
	}

	/**
	 * Convert to Database Weekday From Holiday
	 * 
	 * @param holiday
	 * @return
	 */
	private KshstWeekdayFromHd convertToDbTypeWeekday(WeekdayHoliday holiday) {
		KshstWeekdayFromHdPK weekdayHdPK = new KshstWeekdayFromHdPK(holiday.getCompanyId(),
				holiday.getOverworkFrameNo());
		KshstWeekdayFromHd newEntity = KshstWeekdayFromHd.toEntity(holiday);
		Optional<KshstWeekdayFromHd> optUpdateEntity = this.queryProxy().find(weekdayHdPK, KshstWeekdayFromHd.class);
		if (optUpdateEntity.isPresent()) {
			KshstWeekdayFromHd updateEntity = optUpdateEntity.get();
			updateEntity.excessHolidayNo = holiday.getExcessHolidayNo();
			updateEntity.excessSphdNo = holiday.getExcessSphdNo();
			updateEntity.weekdayNo = holiday.getWeekdayNo();
			updateEntity.kshstWeekdayHdPK = weekdayHdPK;
			return updateEntity;
		}
		return newEntity;
	}

	private KshstHdFromWeekday convertToDbTypeHolidayAtten(HdFromWeekday atten) {
		KshstHdFromWeekday newEntity = KshstHdFromWeekday.toEntity(atten);
		KshstHdFromWeekdayPK attSetPK = new KshstHdFromWeekdayPK(atten.getCompanyId(),
				atten.getHolidayWorkFrameNo());
		Optional<KshstHdFromWeekday> optUpdateEntity = this.queryProxy().find(attSetPK, KshstHdFromWeekday.class);
		if (optUpdateEntity.isPresent()) {
			KshstHdFromWeekday updateEntity = optUpdateEntity.get();
			updateEntity.overWorkNo = atten.getOverWorkNo();
			updateEntity.kshstOverdayHdAttSetPK = attSetPK;
			return updateEntity;
		}
		return newEntity;
	}

	private HdFromHd convertToDomainCalcHoliday(KshstHdFromHd dayHdSet) {
		HdFromHd calcHoliday = HdFromHd.createFromJavaType(dayHdSet.kshstOverDayHdSetPK.companyId,
				dayHdSet.kshstOverDayHdSetPK.holidayWorkFrameNo, dayHdSet.calcOverDayEnd, dayHdSet.statutoryHd,
				dayHdSet.excessHd);
		return calcHoliday;
	}

	private KshstHdFromHd convertToDbTypeCalcHoliday(HdFromHd overdayCalcHoliday) {
		KshstHdFromHd newEntity = KshstHdFromHd.toEntity(overdayCalcHoliday);
		KshstHdFromHdPK dayHdSetPK = new KshstHdFromHdPK(overdayCalcHoliday.getCompanyId(),
				overdayCalcHoliday.getHolidayWorkFrameNo());
		Optional<KshstHdFromHd> optUpdateEntity = this.queryProxy().find(dayHdSetPK, KshstHdFromHd.class);
		if (optUpdateEntity.isPresent()) {
			KshstHdFromHd updateEntity = optUpdateEntity.get();
			updateEntity.calcOverDayEnd = overdayCalcHoliday.getCalcOverDayEnd();
			updateEntity.excessHd = overdayCalcHoliday.getExcessHd();
			updateEntity.statutoryHd = overdayCalcHoliday.getStatutoryHd();
			updateEntity.kshstOverDayHdSetPK = dayHdSetPK;
			return updateEntity;
		}
		return newEntity;
	}

	/**
	 * Find by Overday Calc
	 */
	@Override
	public List<ZeroTime> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstZeroTimeSet.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}

	/**
	 * Add Overday Calc
	 */
	@Override
	public void add(ZeroTime overdayCalc) {
		this.commandProxy().insert(convertToDbType(overdayCalc));
	}

	/**
	 * Update Overday Calc
	 */
	@Override
	public void update(ZeroTime overdayCalc) {
		KshstZeroTimeSetPK primaryKey = new KshstZeroTimeSetPK(overdayCalc.getCompanyId());
		KshstZeroTimeSet entity = this.queryProxy().find(primaryKey, KshstZeroTimeSet.class).get();
		entity.calcFromZeroTime = overdayCalc.getCalcFromZeroTime();
		entity.legalHd = overdayCalc.getLegalHd();
		entity.nonLegalHd = overdayCalc.getNonLegalHd();
		entity.nonLegalPublicHd = overdayCalc.getNonLegalPublicHd();
		entity.weekday1 = overdayCalc.getWeekday1();
		entity.nonLegalHd1 = overdayCalc.getNonLegalHd1();
		entity.nonLegalPublicHd1 = overdayCalc.getNonLegalPublicHd1();
		entity.weekday2 = overdayCalc.getWeekday2();
		entity.legalHd2 = overdayCalc.getLegalHd2();
		entity.nonLegalHd2 = overdayCalc.getNonLegalHd2();
		entity.weekday3 = overdayCalc.getWeekday3();
		entity.legalHd3 = overdayCalc.getLegalHd3();
		entity.nonLegalPublicHd3 = overdayCalc.getNonLegalPublicHd3();

		entity.weekdayHd = overdayCalc.getWeekdayHoliday().stream().map(c -> convertToDbTypeWeekday(c))
				.collect(Collectors.toList());
		entity.overdayHdAttSet = overdayCalc.getOverdayHolidayAtten().stream().map(c -> convertToDbTypeHolidayAtten(c))
				.collect(Collectors.toList());
		entity.overDayHdSet = overdayCalc.getOverdayCalcHoliday().stream().map(c -> convertToDbTypeCalcHoliday(c))
				.collect(Collectors.toList());

		entity.kshstOverDayCalcSetPK = primaryKey;
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<ZeroTime> findByCId(String companyId) {
		return this.queryProxy().find(new KshstZeroTimeSetPK(companyId), KshstZeroTimeSet.class)
				.map(c -> convertToDomain(c));
	}
}
