package nts.uk.ctx.at.record.infra.repository.stampmanagement.stamp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.KrcdtStamp;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.KrcdtStampPk;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
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
			+ " where s.pk.contractCode = :contractCode" + " and s.pk.cardNumber in  :cardNumbers " + " and s.pk.stampDateTime >= :startStampDate "
			+ " and s.pk.stampDateTime <= :endStampDate " + " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";
	
	private static final String GET_STAMP_RECORD_BY_NUMBER = "select s from KrcdtStamp s "
			+ " where s.pk.contractCode = :contractCode" + " and s.pk.cardNumber = :cardNumbers " + " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";
	
	
	private static final String GET_NOT_STAMP_NUMBER = "select s from KrcdtStamp s left join KwkdtStampCard k on s.pk.cardNumber = k.cardNo"
			+ " where k.cardNo is NULL " +" and s.pk.contractCode = :contractCode" + " and s.pk.stampDateTime >= :startStampDate "
			+ " and s.pk.stampDateTime <= :endStampDate " + " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";

	private static final String GET_STAMP_BY_LIST_CARD = "select s from KrcdtStamp s "
			+ " where s.pk.cardNumber in  :cardNumbers ";
	
	private static final String GET_STAMP_BY_DATEPERIOD = "select d.workLocationName, s from KrcdtStamp s "
			+ " LEFT JOIN KwlmtWorkLocation d ON s.stampPlace = d.kwlmtWorkLocationPK.workLocationCD"
			+ " AND d.kwlmtWorkLocationPK.companyID = :cid"
			+ " where s.pk.stampDateTime >= :startStampDate "
			+ " and s.pk.stampDateTime <= :endStampDate " 
			+ " and s.cid = :cid"
			+ " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";
	
	private static final String GET_STAMP_BY_DATEPERIOD_AND_CARDS = "select  e.sid, d.workLocationName, from KrcdtStamp s "
			+ " LEFT JOIN KwlmtWorkLocation d ON c.workLocationCd = d.kwlmtWorkLocationPK.workLocationCD"
			+ " AND d.kwlmtWorkLocationPK.companyID = :cid"
			+ " INNER JOIN KwkdtStampCard e ON e.cardNo = s.pk.cardNumber"
			+ " where s.pk.stampDateTime >= :startStampDate "
			+ " and s.pk.stampDateTime <= :endStampDate " 
			+ " and s.cid = :cid"
			+ " and s.pk.cardNumber in :listCard"
			+ " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";
	private static final String GET_STAMP_BY_DATEPERIOD_AND_CARDS_2 = "select s from KrcdtStamp s "
			+ " INNER JOIN KwkdtStampCard e ON e.cardNo = s.pk.cardNumber"
			+ " where s.pk.stampDateTime >= :startStampDate "
			+ " and s.pk.stampDateTime <= :endStampDate " 
			+ " and s.cid = :cid"
			+ " and s.pk.cardNumber in :listCard"
			+ " order by s.pk.cardNumber asc, s.pk.stampDateTime asc";
	
	// [1] insert(打刻)
	@Override
	public void insert(Stamp stamp) {
		KrcdtStamp entity = toEntity(stamp);
		this.commandProxy().insert(entity);
	}

	// [2] delete(打刻)
	@Override
	public void delete(String contractCode, String stampNumber, GeneralDateTime stampDateTime, int changeClockArt) {
		this.commandProxy().remove(KrcdtStamp.class, new KrcdtStampPk(contractCode, stampNumber, stampDateTime, changeClockArt));
	}

	// [3] update(打刻)
	@Override
	public void update(Stamp stamp) {
		Optional<KrcdtStamp> entity = this.queryProxy().find(new KrcdtStampPk(stamp.getContractCode().v(), stamp.getCardNumber().v(), stamp.getStampDateTime(), stamp.getType().getChangeClockArt().value), KrcdtStamp.class);
		if(!entity.isPresent()) {
			return;
		}
//		KrcdtStamp entityUpdate = toEntity(stamp);
		this.commandProxy().update(entity.get().toEntityUpdate(stamp));
	}

	// [4] 取得する
	@Override
	public List<Stamp> get(String contractCode, List<StampNumber> stampNumbers, GeneralDate stampDateTime) {
		Set<String> lstCard = stampNumbers.stream().map(x -> x.v()).collect(Collectors.toSet());
		GeneralDateTime start = GeneralDateTime.ymdhms(stampDateTime.year(), stampDateTime.month(), stampDateTime.day(),
				0, 0, 0);

		GeneralDateTime end = GeneralDateTime.ymdhms(stampDateTime.year(), stampDateTime.month(), stampDateTime.day(),
				23, 59, 59);
		return this.queryProxy().query(GET_STAMP_RECORD, KrcdtStamp.class).setParameter("contractCode", contractCode)
				.setParameter("cardNumbers", lstCard).setParameter("startStampDate", start)
				.setParameter("endStampDate", end).getList(x -> toDomain(x));
	}

	// [5] 打刻カード未登録の打刻データを取得する
	@Override
	public List<Stamp> getStempRcNotResgistNumberStamp(String contractCode, DatePeriod period) {
		GeneralDateTime start = GeneralDateTime.ymdhms(period.start().year(), period.start().month(),
				period.start().day(), 0, 0, 0);

		GeneralDateTime end = GeneralDateTime.ymdhms(period.end().year(), period.end().month(), period.end().day(), 23,
				59, 59);

		List<Stamp> list = this.queryProxy().query(GET_NOT_STAMP_NUMBER, KrcdtStamp.class)
				.setParameter("contractCode", contractCode).setParameter("startStampDate", start)
				.setParameter("endStampDate", end).getList(x -> toDomain(x));
		return list;
	}

	private KrcdtStamp toEntity(Stamp stamp) {
		String cid = AppContexts.user().companyId();
		Optional<StampLocationInfor> LocationInfoOpt = stamp.getLocationInfor();
		GeoCoordinate positionInfor = LocationInfoOpt.isPresent() ? LocationInfoOpt.get().getPositionInfor() : null;
		return new KrcdtStamp(new KrcdtStampPk(stamp.getContractCode().v(), stamp.getCardNumber().v(), stamp.getStampDateTime(), stamp.getType().getChangeClockArt().value), cid,
				stamp.getRelieve().getAuthcMethod().value, stamp.getRelieve().getStampMeans().value,
				stamp.getType().getChangeCalArt().value,
				stamp.getType().getSetPreClockArt().value, stamp.getType().isChangeHalfDay(),
				stamp.getType().getGoOutArt().isPresent() ? stamp.getType().getGoOutArt().get().value : null,
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
				positionInfor != null? new BigDecimal(positionInfor.getLongitude()).setScale(6, BigDecimal.ROUND_HALF_DOWN): null,
				positionInfor != null? new BigDecimal(positionInfor.getLatitude()).setScale(6, BigDecimal.ROUND_HALF_DOWN): null,
				LocationInfoOpt.isPresent() ? stamp.getLocationInfor().get().isOutsideAreaAtr() : null);
		
		
		
	}

	private Stamp toDomain(KrcdtStamp entity) {
		GeoCoordinate geoLocation = null;
		if (entity.locationLat !=  null && entity.locationLon != null){
			geoLocation = new GeoCoordinate(entity.locationLat.doubleValue(), entity.locationLon.doubleValue()); 
		}
		val stampNumber = new StampNumber(entity.pk.cardNumber);
		val relieve = new Relieve(AuthcMethod.valueOf(entity.autcMethod), StampMeans.valueOf(entity.stampMeans));
		val stampType = StampType.getStampType(entity.changeHalfDay,
				entity.goOutArt == null ? null : GoingOutReason.valueOf(entity.goOutArt),
				SetPreClockArt.valueOf(entity.preClockArt), ChangeClockArt.valueOf(entity.pk.changeClockArt),
				ChangeCalArt.valueOf(entity.changeCalArt));
		
		OvertimeDeclaration overtime = entity.overTime == null ? null
				: new OvertimeDeclaration(new AttendanceTime(entity.overTime),
						new AttendanceTime(entity.lateNightOverTime));
						
		val refectActualResult = new RefectActualResult(entity.suportCard,
				entity.stampPlace == null ? null : new WorkLocationCD(entity.stampPlace),
				entity.workTime == null ? null : new WorkTimeCode(entity.workTime),
				overtime );
		
		val locationInfor = new StampLocationInfor(geoLocation,
				entity.outsideAreaArt == null ? false : entity.outsideAreaArt);
		
		return new Stamp(new ContractCode(entity.pk.contractCode) ,
						stampNumber, 
						entity.pk.stampDateTime,
						relieve, stampType, refectActualResult,
						entity.reflectedAtr, Optional.ofNullable(locationInfor), Optional.empty());

	}
	
	private Stamp toDomainVer2(Object[] object) {
		String workLocationName = (String) object[0];
		KrcdtStamp entity = (KrcdtStamp) object[1];

		Stamp stamp = new Stamp(new ContractCode(entity.pk.contractCode), new StampNumber(entity.pk.cardNumber),
				entity.pk.stampDateTime,
				new Relieve(AuthcMethod.valueOf(entity.autcMethod), StampMeans.valueOf(entity.stampMeans)),
				new StampType(entity.changeHalfDay,
						entity.goOutArt == null ? null : GoingOutReason.valueOf(entity.goOutArt),
						SetPreClockArt.valueOf(entity.preClockArt), ChangeClockArt.valueOf(entity.pk.changeClockArt),
						ChangeCalArt.valueOf(entity.changeCalArt)),
				new RefectActualResult(entity.suportCard,
						entity.stampPlace == null ? null : new WorkLocationCD(entity.stampPlace),
						entity.workTime == null ? null : new WorkTimeCode(entity.workTime),
						entity.overTime == null ? null
								: new OvertimeDeclaration(new AttendanceTime(entity.overTime),
										new AttendanceTime(entity.lateNightOverTime))),
				entity.reflectedAtr,
				Optional.ofNullable(entity.outsideAreaArt == null || ( entity.locationLat == null && entity.locationLon == null) ? null
						: new StampLocationInfor(
								new GeoCoordinate(entity.locationLat.doubleValue(), entity.locationLon.doubleValue()),
								entity.outsideAreaArt)), Optional.empty())
			;
		return stamp;
	}

	private Stamp toDomainVer3(Object[] object) {
		String personId = (String) object[0];
		String workLocationName = (String) object[1];
		KrcdtStamp entity = (KrcdtStamp) object[2];
		ContractCode contractCd = new ContractCode(AppContexts.user().contractCode());
		
		Stamp stamp =  new Stamp(contractCd, new StampNumber(entity.pk.cardNumber), entity.pk.stampDateTime,
					new Relieve(AuthcMethod.valueOf(entity.autcMethod), StampMeans.valueOf(entity.stampMeans)),
					StampType.getStampType(entity.changeHalfDay,
							entity.goOutArt == null ? null : GoingOutReason.valueOf(entity.goOutArt),
							SetPreClockArt.valueOf(entity.preClockArt), ChangeClockArt.valueOf(entity.pk.changeClockArt),
							ChangeCalArt.valueOf(entity.changeCalArt)),

					new RefectActualResult(entity.suportCard,
							entity.stampPlace == null ? null : new WorkLocationCD(entity.stampPlace),
							entity.workTime == null ? null : new WorkTimeCode(entity.workTime),
							entity.overTime == null ? null
									: new OvertimeDeclaration(new AttendanceTime(entity.overTime),
											new AttendanceTime(entity.lateNightOverTime))),

					entity.reflectedAtr,
					Optional.ofNullable(entity.outsideAreaArt == null || ( entity.locationLat == null && entity.locationLon == null) ? null :
						new StampLocationInfor(new GeoCoordinate(entity.locationLat.doubleValue(),entity.locationLon.doubleValue()),entity.outsideAreaArt)),
					Optional.empty()
			);
		return stamp;
	}

	@Override
	public List<Stamp> getByListCard(List<String> stampNumbers) {
		if(stampNumbers.isEmpty())
			return Collections.emptyList();
		List<Stamp> data = this.queryProxy().query(GET_STAMP_BY_LIST_CARD, KrcdtStamp.class).setParameter("cardNumbers", stampNumbers)
				.getList(x -> toDomain(x));
		
		return data;
	}

	@Override
	public List<Stamp> getByDateperiod(String companyId, DatePeriod period) {
		GeneralDateTime start = GeneralDateTime.ymdhms(period.start().year(), period.start().month(),
				period.start().day(), 0, 0, 0);

		GeneralDateTime end = GeneralDateTime.ymdhms(period.end().year(), period.end().month(), period.end().day(), 23,
				59, 59);

		return this.queryProxy().query(GET_STAMP_BY_DATEPERIOD, Object[].class)
				.setParameter("startStampDate", start)
				.setParameter("endStampDate", end)
				.setParameter("cid", companyId)
				.getList(x -> toDomainVer2(x));

	}
	
	@Override
	public List<Stamp> getByDateTimeperiod(List<String> listCard,String companyId, GeneralDateTime startDate, GeneralDateTime endDate) {
		List<Stamp> data =  this.queryProxy().query(GET_STAMP_BY_DATEPERIOD_AND_CARDS_2, KrcdtStamp.class)
				.setParameter("startStampDate", startDate)
				.setParameter("endStampDate", endDate)
				.setParameter("cid", companyId)
				.setParameter("listCard", listCard)
				.getList(x -> toDomain(x));
		return data;
	}

	@Override
	public List<Stamp> getByCardAndPeriod(String companyId, List<String> listCard, DatePeriod period) {
		if (CollectionUtil.isEmpty(listCard)) {
			return Collections.emptyList();
		}
		GeneralDateTime start = GeneralDateTime.ymdhms(period.start().year(), period.start().month(),
				period.start().day(), 0, 0, 0);

		GeneralDateTime end = GeneralDateTime.ymdhms(period.end().year(), period.end().month(), period.end().day(), 23,
				59, 59);
		List<Stamp> data = new ArrayList<>();
		CollectionUtil.split(listCard, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			data.addAll(this.queryProxy().query(GET_STAMP_BY_DATEPERIOD_AND_CARDS, Object[].class)
				.setParameter("startStampDate", start)
				.setParameter("endStampDate", end)
				.setParameter("cid", companyId)
				.setParameter("listCard", listCard)
				.getList(x -> toDomainVer3(x)));
		});
		return data;
	}

	@Override
	public Optional<Stamp> get(String contractCode, StampNumber stampNumber) {
		
		return this.queryProxy().query(GET_STAMP_RECORD_BY_NUMBER, KrcdtStamp.class).setParameter("contractCode", contractCode)
				.setParameter("cardNumbers", stampNumber).getList().stream().findFirst().map(x -> toDomain(x));
	}

}
