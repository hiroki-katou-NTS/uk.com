package nts.uk.ctx.at.record.infra.repository.stampmanagement.stamp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.management.GoingOutReason;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.management.ChangeCalArt;
import nts.uk.ctx.at.record.dom.stamp.management.ChangeClockArt;
import nts.uk.ctx.at.record.dom.stamp.management.SetPreClockArt;
import nts.uk.ctx.at.record.dom.stamp.management.StampType;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.OvertimeDeclaration;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.KrcdtStamp;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.KrcdtStampPk;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 *
 *         打刻Repository
 */
@Stateless
public class JpaStampDakokuRepository extends JpaRepository implements StampDakokuRepository {

	private static final String GET_STAMP_RECORD = "select s from KrcdtStamp s "
			+ " where s.pk.cardNumber in  :cardNumbers " + " and s.pk.stampDateTime >= :startStampDate "
			+ " and s.pk.stampDateTime <= :endStampDate " + " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";

	private static final String GET_NOT_STAMP_NUMBER = "select s from KrcdtStamp s left join KwkdtStampCard k on s.pk.cardNumber = k.cardNo"
			+ " where k.cardNo is NULL " + " and s.pk.stampDateTime >= :startStampDate "
			+ " and s.pk.stampDateTime <= :endStampDate " + " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";

	// [1] insert(打刻)
	@Override
	public void insert(Stamp stamp) {
		this.commandProxy().insert(toEntity(stamp));
	}

	// [2] delete(打刻)
	@Override
	public void delete(String stampNumber, GeneralDateTime stampDateTime) {
		this.commandProxy().remove(KrcdtStamp.class, new KrcdtStampPk(stampNumber, stampDateTime));
	}

	// [3] update(打刻)
	@Override
	public void update(Stamp stamp) {
		Optional<KrcdtStamp> entity = this.queryProxy().find(new KrcdtStampPk(stamp.getCardNumber().v(), stamp.getStampDateTime()), KrcdtStamp.class);
		if(!entity.isPresent()) {
			return;
		}
		KrcdtStamp entityUpdate = toEntity(stamp);
		this.commandProxy().update(entityUpdate.toEntityUpdate(stamp));
	}

	// [4] 取得する
	@Override
	public List<Stamp> get(List<StampNumber> stampNumbers, GeneralDateTime stampDateTime) {
		Set<String> lstCard = stampNumbers.stream().map(x -> x.v()).collect(Collectors.toSet());
		GeneralDateTime start = GeneralDateTime.ymdhms(stampDateTime.year(), stampDateTime.month(), stampDateTime.day(),
				0, 0, 0);

		GeneralDateTime end = GeneralDateTime.ymdhms(stampDateTime.year(), stampDateTime.month(), stampDateTime.day(),
				23, 59, 59);
		return this.queryProxy().query(GET_STAMP_RECORD, KrcdtStamp.class).setParameter("cardNumbers", lstCard)
				.setParameter("startStampDate", start).setParameter("endStampDate", end).getList(x -> toDomain(x));
	}

	// [5] 打刻カード未登録の打刻データを取得する
	@Override
	public List<Stamp> getStempRcNotResgistNumber(DatePeriod period) {
		GeneralDateTime start = GeneralDateTime.ymdhms(period.start().year(), period.start().month(),
				period.start().day(), 0, 0, 0);

		GeneralDateTime end = GeneralDateTime.ymdhms(period.end().year(), period.end().month(), period.end().day(), 23,
				59, 59);

		return this.queryProxy().query(GET_NOT_STAMP_NUMBER, KrcdtStamp.class).setParameter("startStampDate", start)
				.setParameter("endStampDate", end).getList(x -> toDomain(x));
	}

	private KrcdtStamp toEntity(Stamp stamp) {
		String cid = AppContexts.user().companyId();
		return new KrcdtStamp(new KrcdtStampPk(stamp.getCardNumber().v(), stamp.getStampDateTime()), cid,
				stamp.getRelieve().getAuthcMethod().value, stamp.getRelieve().getStampMeans().value,
				stamp.getType().getChangeClockArt().value, stamp.getType().getChangeCalArt().value,
				stamp.getType().getSetPreClockArt().value, stamp.getType().isChangeHalfDay(),
				stamp.getType().getGoOutArt().value,
				stamp.isReflectedCategory(),
				stamp.getRefActualResults().getCardNumberSupport().isPresent()
						? stamp.getRefActualResults().getCardNumberSupport().get()
						: null,
				stamp.getRefActualResults().getWorkLocationCD().isPresent()
						? stamp.getRefActualResults().getWorkLocationCD().get().v()
						: null,
				stamp.getRefActualResults().getWorkTimeCode().isPresent()
						? stamp.getRefActualResults().getWorkTimeCode().get().v()
						: null,
				stamp.getRefActualResults().getOvertimeDeclaration().isPresent()
						? stamp.getRefActualResults().getOvertimeDeclaration().get().getOverTime().v()
						: null, // overTime
				stamp.getRefActualResults().getOvertimeDeclaration().isPresent()
						? stamp.getRefActualResults().getOvertimeDeclaration().get().getOverLateNightTime().v()
						: null, // lateNightOverTime
				new BigDecimal(0), // TODO
				new BigDecimal(0), // TODO
				stamp.getLocationInfor().isPresent() ? stamp.getLocationInfor().get().isOutsideAreaAtr() : null);
	}

	private Stamp toDomain(KrcdtStamp entity) {
		return new Stamp(new StampNumber(entity.pk.cardNumber), entity.pk.stampDateTime,
				new Relieve(AuthcMethod.valueOf(entity.autcMethod), StampMeans.valueOf(entity.stampMeans)),
				new StampType(entity.changeHalfDay,
						GoingOutReason.valueOf(entity.goOutArt),
						SetPreClockArt.valueOf(entity.preClockArt), ChangeClockArt.valueOf(entity.changeClockArt),
						ChangeCalArt.valueOf(entity.changeCalArt)),

				new RefectActualResult(entity.suportCard,
						entity.stampPlace == null ? null : new WorkLocationCD(entity.stampPlace),
						entity.workTime == null ? null : new WorkTimeCode(entity.workTime),
						entity.overTime == null ? null
								: new OvertimeDeclaration(new AttendanceTime(entity.overTime),
										new AttendanceTime(entity.lateNightOverTime))),

				entity.reflectedAtr,
				entity.outsideAreaArt == null ? null : new StampLocationInfor(entity.outsideAreaArt, 0)// TODO location
		);
	}

}
