package nts.uk.ctx.at.record.infra.repository.stampmanagement.stamp;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.management.ReservationArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.KrcdtStampRecord;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.KrcdtStampRecordPk;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 *
 *         打刻記録Repository
 */
@Stateless
public class JpaStampRecordRepository extends JpaRepository implements StampRecordRepository {

	private static final String GET_STAMP_RECORD = "select s from KrcdtStampRecord s "
			+ " where s.pk.cardNumber in  :cardNumbers " + " and s.pk.stampDateTime >= :startStampDate "
			+ " and s.pk.stampDateTime <= :endStampDate " + " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";

	private static final String GET_NOT_STAMP_NUMBER = "select s from KrcdtStampRecord s left join KwkdtStampCard k on s.pk.cardNumber = k.cardNo"
			+ " where k.cardNo is NULL " + " and s.pk.stampDateTime >= :startStampDate "
			+ " and s.pk.stampDateTime <= :endStampDate " + " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";

	// [1] insert(打刻記録)
	@Override
	public void insert(StampRecord stampRecord) {
		this.commandProxy().insert(toEntity(stampRecord));
	}

	// [2] delete(打刻記録)
	@Override
	public void delete(String stampNumber, GeneralDateTime stampDateTime) {
		this.commandProxy().remove(KrcdtStampRecord.class, new KrcdtStampRecordPk(stampNumber, stampDateTime));
	}

	// [3] update(打刻記録)
	@Override
	public void update(StampRecord stampRecord) {
		Optional<KrcdtStampRecord> entity = this.queryProxy().find(
				new KrcdtStampRecordPk(stampRecord.getStampNumber().v(), stampRecord.getStampDateTime()),
				KrcdtStampRecord.class);
		if(!entity.isPresent()) return;
		this.commandProxy().update(entity.get().toUpdateEntity(stampRecord));
	}

	// [4] 取得する
	@Override
	public List<StampRecord> get(List<StampNumber> stampNumbers, GeneralDate stampDateTime) {
		Set<String> lstCard = stampNumbers.stream().map(x -> x.v()).collect(Collectors.toSet());
		GeneralDateTime start = GeneralDateTime.ymdhms(stampDateTime.year(), stampDateTime.month(), stampDateTime.day(),
				0, 0, 0);

		GeneralDateTime end = GeneralDateTime.ymdhms(stampDateTime.year(), stampDateTime.month(), stampDateTime.day(),
				23, 59, 59);
		return this.queryProxy().query(GET_STAMP_RECORD, KrcdtStampRecord.class).setParameter("cardNumbers", lstCard)
				.setParameter("startStampDate", start).setParameter("endStampDate", end).getList(x -> toDomain(x));
	}

	// [5] 打刻カード未登録の打刻記録データを取得する
	@Override
	public List<StampRecord> getStempRcNotResgistNumber(DatePeriod period) {
		GeneralDateTime start = GeneralDateTime.ymdhms(period.start().year(), period.start().month(),
				period.start().day(), 0, 0, 0);

		GeneralDateTime end = GeneralDateTime.ymdhms(period.end().year(), period.end().month(), period.end().day(), 23,
				59, 59);

		return this.queryProxy().query(GET_NOT_STAMP_NUMBER, KrcdtStampRecord.class)
				.setParameter("startStampDate", start).setParameter("endStampDate", end).getList(x -> toDomain(x));
	}

	public KrcdtStampRecord toEntity(StampRecord domain) {
		String cid = AppContexts.user().companyId();
		return new KrcdtStampRecord(new KrcdtStampRecordPk(domain.getStampNumber().v(), domain.getStampDateTime()), cid,
				domain.isStampArt(), domain.getRevervationAtr().value,
				domain.getEmpInfoTerCode().isPresent() ? domain.getEmpInfoTerCode().get().v() : null);
	}

	public StampRecord toDomain(KrcdtStampRecord entity) {
		return new StampRecord(new StampNumber(entity.pk.cardNumber), entity.pk.stampDateTime, entity.stampArt,
				ReservationArt.valueOf(entity.reservationArt), Optional.ofNullable(
						entity.workTerminalInfoCd == null ? null : new EmpInfoTerminalCode(entity.workTerminalInfoCd)));
	}

}
