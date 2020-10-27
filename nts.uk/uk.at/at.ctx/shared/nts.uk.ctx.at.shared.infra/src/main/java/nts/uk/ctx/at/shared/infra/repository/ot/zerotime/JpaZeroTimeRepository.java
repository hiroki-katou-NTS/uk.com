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
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshmtCalcDOvdHtoh;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshmtCalcDOvdHtohPK;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshmtCalcDOvdHtow;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshmtCalcDOvdHtowPK;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshmtCalcDOvdWtoh;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshmtCalcDOvdWtohPK;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshmtCalcDOvd;
import nts.uk.ctx.at.shared.infra.entity.ot.zerotime.KshmtCalcDOvdPK;

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
		builderString.append(" FROM KshmtCalcDOvd e");
		builderString.append(" WHERE e.kshstOverDayCalcSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Overday Calc Set
	 * 
	 * @param kshstOverDayCalcSet
	 * @return
	 */
	private ZeroTime convertToDomain(KshmtCalcDOvd kshstOverDayCalcSet) {
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
	private KshmtCalcDOvd convertToDbType(ZeroTime overdayCalc) {
		KshmtCalcDOvd calcSet = new KshmtCalcDOvd();
		KshmtCalcDOvdPK calcSetPK = new KshmtCalcDOvdPK(overdayCalc.getCompanyId());
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
	private WeekdayHoliday convertToDomainWeekday(KshmtCalcDOvdWtoh kshstWeekdayHd) {
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
	private KshmtCalcDOvdWtoh convertToDbTypeWeekday(WeekdayHoliday holiday) {
		KshmtCalcDOvdWtohPK weekdayHdPK = new KshmtCalcDOvdWtohPK(holiday.getCompanyId(),
				holiday.getOverworkFrameNo());
		KshmtCalcDOvdWtoh newEntity = KshmtCalcDOvdWtoh.toEntity(holiday);
		Optional<KshmtCalcDOvdWtoh> optUpdateEntity = this.queryProxy().find(weekdayHdPK, KshmtCalcDOvdWtoh.class);
		if (optUpdateEntity.isPresent()) {
			KshmtCalcDOvdWtoh updateEntity = optUpdateEntity.get();
			updateEntity.excessHolidayNo = holiday.getExcessHolidayNo();
			updateEntity.excessSphdNo = holiday.getExcessSphdNo();
			updateEntity.weekdayNo = holiday.getWeekdayNo();
			updateEntity.kshstWeekdayHdPK = weekdayHdPK;
			return updateEntity;
		}
		return newEntity;
	}

	private KshmtCalcDOvdHtow convertToDbTypeHolidayAtten(HdFromWeekday atten) {
		KshmtCalcDOvdHtow newEntity = KshmtCalcDOvdHtow.toEntity(atten);
		KshmtCalcDOvdHtowPK attSetPK = new KshmtCalcDOvdHtowPK(atten.getCompanyId(),
				atten.getHolidayWorkFrameNo());
		Optional<KshmtCalcDOvdHtow> optUpdateEntity = this.queryProxy().find(attSetPK, KshmtCalcDOvdHtow.class);
		if (optUpdateEntity.isPresent()) {
			KshmtCalcDOvdHtow updateEntity = optUpdateEntity.get();
			updateEntity.overWorkNo = atten.getOverWorkNo();
			updateEntity.kshstOverdayHdAttSetPK = attSetPK;
			return updateEntity;
		}
		return newEntity;
	}

	private HdFromHd convertToDomainCalcHoliday(KshmtCalcDOvdHtoh dayHdSet) {
		HdFromHd calcHoliday = HdFromHd.createFromJavaType(dayHdSet.kshstOverDayHdSetPK.companyId,
				dayHdSet.kshstOverDayHdSetPK.holidayWorkFrameNo, dayHdSet.calcOverDayEnd, dayHdSet.statutoryHd,
				dayHdSet.excessHd);
		return calcHoliday;
	}

	private KshmtCalcDOvdHtoh convertToDbTypeCalcHoliday(HdFromHd overdayCalcHoliday) {
		KshmtCalcDOvdHtoh newEntity = KshmtCalcDOvdHtoh.toEntity(overdayCalcHoliday);
		KshmtCalcDOvdHtohPK dayHdSetPK = new KshmtCalcDOvdHtohPK(overdayCalcHoliday.getCompanyId(),
				overdayCalcHoliday.getHolidayWorkFrameNo());
		Optional<KshmtCalcDOvdHtoh> optUpdateEntity = this.queryProxy().find(dayHdSetPK, KshmtCalcDOvdHtoh.class);
		if (optUpdateEntity.isPresent()) {
			KshmtCalcDOvdHtoh updateEntity = optUpdateEntity.get();
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
		return this.queryProxy().query(SELECT_BY_CID, KshmtCalcDOvd.class).setParameter("companyId", companyId)
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
		KshmtCalcDOvdPK primaryKey = new KshmtCalcDOvdPK(overdayCalc.getCompanyId());
		KshmtCalcDOvd entity = this.queryProxy().find(primaryKey, KshmtCalcDOvd.class).get();
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
		return this.queryProxy().find(new KshmtCalcDOvdPK(companyId), KshmtCalcDOvd.class)
				.map(c -> convertToDomain(c));
	}
}
