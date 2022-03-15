package nts.uk.ctx.at.shared.infra.repository.remainingnumber.reserveleave;

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
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMngRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KshdtInterimHdpaid;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.reserveleave.KshdtInterimHDSTK;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.reserveleave.KshdtInterimHDSTKPK;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaTmpResereLeaveMngRepository extends JpaRepository implements TmpResereLeaveMngRepository{
	
	private static final String DELETE_BY_SID_CD_BEFORETHEYMD = "DELETE FROM KshdtInterimHDSTK a"
			+ " WHERE a.pk.sid = :sid "
			+ " AND a.pk.ymd <= :ymd";
	
	
	@Override
	public Optional<TmpResereLeaveMng> getById(String resereMngId) {
		Optional<TmpResereLeaveMng> optKrcmtInterimReserveMng = this.queryProxy().find(resereMngId, KshdtInterimHDSTK.class)
				.map(x -> toDomain(x));
		return optKrcmtInterimReserveMng;
	}

	private TmpResereLeaveMng toDomain(KshdtInterimHDSTK x) {
		return new TmpResereLeaveMng(
				x.remainMngId ,
				x.getPk().sid,
				x.getPk().ymd ,
				EnumAdaptor.valueOf(x.createAtr, CreateAtr.class),
				RemainType.FUNDINGANNUAL,
				new UseDay(x.usedDays));
	}

	@Override
	public void deleteById(String resereMngId) {
		Optional<KshdtInterimHDSTK> optKrcmtInterimReserveMng = this.queryProxy().find(resereMngId, KshdtInterimHDSTK.class);
		optKrcmtInterimReserveMng.ifPresent(x -> {
			this.commandProxy().remove(x);
		});
	}

	@Override
	public void persistAndUpdate(TmpResereLeaveMng dataMng) {
		
		KshdtInterimHDSTKPK pk = new KshdtInterimHDSTKPK(AppContexts.user().companyId(), dataMng.getSID(), dataMng.getYmd());
		Optional<KshdtInterimHDSTK> optKrcmtInterimReserveMng = this.queryProxy().find(
				pk, 
				KshdtInterimHDSTK.class);
		if(optKrcmtInterimReserveMng.isPresent()) {
			KshdtInterimHDSTK entity = optKrcmtInterimReserveMng.get();
			entity.createAtr = dataMng.getCreatorAtr().value;
			entity.remainMngId = dataMng.getRemainManaID();
			entity.usedDays = dataMng.getUseDays().v();
			this.commandProxy().update(entity);
		} else {
			KshdtInterimHDSTK entity = new KshdtInterimHDSTK();
			entity.setPk(pk);
			entity.createAtr = dataMng.getCreatorAtr().value;
			entity.remainMngId = dataMng.getRemainManaID();
			entity.usedDays = dataMng.getUseDays().v();
			this.getEntityManager().persist(entity);
		}
		this.getEntityManager().flush();
	}
	@SneakyThrows
	@Override
	public List<TmpResereLeaveMng> findBySidPriod(String sid, DatePeriod period) {
		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KSHDT_INTERIM_HDSTK a"
				+ " WHERE a.SID = ?"
				+ " AND a.YMD >= ? and a.YMD <= ?"
				+ " ORDER BY a.YMD");
		)
		{
			sql.setString(1, sid);
			sql.setDate(2, Date.valueOf(period.start().localDate()));
			sql.setDate(3, Date.valueOf(period.end().localDate()));
			List<TmpResereLeaveMng> lstOutput = new NtsResultSet(sql.executeQuery())
					.getList(x -> toDomainNts(x));
			return lstOutput;
		}
	}
	private TmpResereLeaveMng toDomainNts(NtsResultRecord x) {
		
		return new TmpResereLeaveMng(x.getString("REMAIN_MNG_ID"),
				x.getString("SID"),
				x.getGeneralDate("YMD"),
				EnumAdaptor.valueOf(x.getInt("CREATOR_ATR"), CreateAtr.class),
				RemainType.FUNDINGANNUAL,
				new UseDay(x.getBigDecimal("USED_DAYS") == null ? 0 : x.getBigDecimal("USED_DAYS").doubleValue()));
	}

	@Override
	public void deleteSidPeriod(String sid, DatePeriod period) {
		
		this.getEntityManager().createQuery("DELETE FROM KshdtInterimHDSTK a WHERE a.pk.sid = :id"
				+ " AND a.pk.ymd <= :start AND a.pk.ymd >= :end", KshdtInterimHdpaid.class)
		.setParameter("id", sid)
		.setParameter("start", period.start())
		.setParameter("end", period.end())
		.executeUpdate();

	}


	
	@Override
	public void deleteBySidBeforeTheYmd(String sid, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_BY_SID_CD_BEFORETHEYMD)
		.setParameter("sid", sid)
		.setParameter("ymd", ymd)
		.executeUpdate();
		
	}

}