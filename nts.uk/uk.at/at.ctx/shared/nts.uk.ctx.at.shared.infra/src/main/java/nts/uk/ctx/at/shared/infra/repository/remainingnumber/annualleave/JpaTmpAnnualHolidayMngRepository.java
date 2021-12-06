package nts.uk.ctx.at.shared.infra.repository.remainingnumber.annualleave;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KshdtInterimHdpaid;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KshdtInterimHdpaidPK;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpDailyLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpDailyLeaveUsedTime;

@Stateless
public class JpaTmpAnnualHolidayMngRepository extends JpaRepository implements TmpAnnualHolidayMngRepository{

	private TempAnnualLeaveMngs toDomain(String mngId, String sid, GeneralDate ymd,
			int creatorAtr, int timeDigestAtr, int timeHdType, String workTypeCode,
			Double useDays, Integer useTime) {
		return new TempAnnualLeaveMngs(mngId, sid, ymd,
				EnumAdaptor.valueOf(creatorAtr, CreateAtr.class),
				RemainType.ANNUAL,
				new WorkTypeCode(workTypeCode),
				new TempAnnualLeaveUsedNumber(
						Optional.of(new TmpDailyLeaveUsedDayNumber(useDays)),
						useTime == null ? Optional.empty() : Optional.of(new TmpDailyLeaveUsedTime(useTime))),
				Optional.ofNullable(DigestionHourlyTimeType.of(
						timeDigestAtr == 1,
						timeHdType == 0 ? Optional.empty():
						Optional.ofNullable(EnumAdaptor.valueOf(timeHdType - 1, AppTimeType.class)))));
	}

	@Override
	public void deleteById(String mngId) {
		this.getEntityManager().createQuery("DELETE FROM KshdtInterimHdpaid a WHERE a.remainMngId = :id", KshdtInterimHdpaid.class)
			.setParameter("id", mngId).executeUpdate();

	}

	@Override
	public void persistAndUpdate(TempAnnualLeaveMngs dataMng) {
		KshdtInterimHdpaidPK pk = new KshdtInterimHdpaidPK(
				AppContexts.user().companyId(),
				dataMng.getSID(),
				dataMng.getYmd(),
				dataMng.getAppTimeType().map(x -> x.isHourlyTimeType() ? 1 : 0).orElse(0),
				dataMng.getAppTimeType().map(x-> x.getAppTimeType().map(appTime-> appTime.value + 1).orElse(0)).orElse(0)
				);

		Optional<KshdtInterimHdpaid> optTmpAnnualHolidayMng = this.queryProxy().find(pk, KshdtInterimHdpaid.class);
		if(optTmpAnnualHolidayMng.isPresent()) {
			KshdtInterimHdpaid entity = optTmpAnnualHolidayMng.get();
			entity.update(dataMng);
			this.commandProxy().update(entity);
		} else {
			KshdtInterimHdpaid entity = new KshdtInterimHdpaid();
			entity.pk = pk;
			entity.update(dataMng);
			this.commandProxy().insert(entity);
		}
		this.getEntityManager().flush();
	}
	@SneakyThrows
	@Override
	public List<TempAnnualLeaveMngs> getBySidPeriod(String sid, DatePeriod period) {
		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KSHDT_INTERIM_HDPAID a"
				+ " WHERE a.SID = ? AND a.YMD >= ? and a.YMD <= ? ORDER BY a.YMD")) {
			sql.setString(1, sid);
			sql.setDate(2, Date.valueOf(period.start().localDate()));
			sql.setDate(3, Date.valueOf(period.end().localDate()));

			return new NtsResultSet(sql.executeQuery()).getList(x -> toDomain(x));
		}
	}

	private TempAnnualLeaveMngs toDomain(NtsResultRecord x) {
		return toDomain(x.getString("REMAIN_MNG_ID"), x.getString("SID"), x.getGeneralDate("YMD"),
						x.getInt("CREATOR_ATR"), x.getInt("TIME_DIGESTIVE_ATR"), x.getInt("TIME_HD_TYPE"),
						x.getString("WORKTYPE_CODE"), x.getDouble("USED_DAYS"), x.getInt("USED_TIME"));
	}

	@Override
	public void deleteSidPeriod(String sid, DatePeriod period) {

		this.getEntityManager().createQuery("DELETE FROM KshdtInterimHdpaid a WHERE a.pk.sid = :id"
					+ " AND a.pk.ymd <= :end AND a.pk.ymd >= :start", KshdtInterimHdpaid.class)
			.setParameter("id", sid)
			.setParameter("start", period.start())
			.setParameter("end", period.end())
			.executeUpdate();
	}

	@Override
	public void deleteSidAndYmd(String sid, GeneralDate ymd) {
		this.getEntityManager().createQuery("DELETE FROM KshdtInterimHdpaid a WHERE a.pk.sid = :id"
				+ " AND a.pk.ymd = :ymd", KshdtInterimHdpaid.class)
		.setParameter("id", sid)
		.setParameter("ymd", ymd)
		.executeUpdate();
	}

}
