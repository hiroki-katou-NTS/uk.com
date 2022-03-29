package nts.uk.ctx.at.shared.infra.repository.remainingnumber.absencerecruitment.interim;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
//import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcdtInterimHdSubMng;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcdtInterimHdSubMngPK;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcdtInterimRecHdSub;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcdtInterimRecMng;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcdtInterimRecMngPK;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcmtInterimRecAbsPK;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class JpaInterimRecAbasMngRepository extends JpaRepository implements InterimRecAbasMngRepository{


	private static final String QUERY_REC_BY_ID = "SELECT c FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.recruitmentMngId = :remainID"
			+ " AND c.recruitmentMngAtr = :mngAtr";
	private static final String QUERY_ABS_BY_ID = "SELECT c FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.absenceMngID = :remainID"
			+ " AND c.recruitmentMngAtr = :mngAtr";
	private static final String QUERY_REC_BY_IDS = "SELECT c FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.recruitmentMngId IN :remainID"
			+ " AND c.recruitmentMngAtr = :mngAtr";
	private static final String QUERY_ABS_BY_IDS = "SELECT c FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.absenceMngID IN :remainID"
			+ " AND c.recruitmentMngAtr = :mngAtr";
	private static final String QUERY_REC_BY_DATEPERIOD = "SELECT c FROM KrcdtInterimRecMng c"
			+ " WHERE c.recruitmentMngId in :mngIds"
			+ " AND c.unUsedDays > :unUsedDays"
			+ " AND c.expirationDate >= :startDate"
			+ " AND c.expirationDate <= :endDate";
	private static final String DELETE_RECMNG_BY_ID = "DELETE FROM KrcdtInterimRecMng c"
			+ " WHERE c.recruitmentMngId = :mngId";
	private static final String DELETE_ABSMNG_BY_ID = "DELETE FROM KrcdtInterimHdSubMng c"
			+ " WHERE c.absenceMngId = :mngId";
	private static final String DELETE_ABSMNG_BY_SID_AND_YMD = "DELETE FROM KrcdtInterimHdSubMng c"
			+ " WHERE c.pk.sid = :sid AND c.pk.ymd = :ymd";
	private static final String QUERY_ABS_BY_SID_MNGID = "SELECT c FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.absenceMngID = :absenceMngID"
			+ " AND c.absenceMngAtr = :absenceMngAtr"
			+ " AND c.recruitmentMngAtr = :recruitmentMngAtr";
	private static final String DELETE_ABS_BY_MNGID = "DELETE FROM KrcdtInterimRecHdSub c "
			+ " WHERE c.recAbsPk.absenceMngID = :mngId";
	private static final String DELETE_REC_BY_MNGID = "DELETE FROM KrcdtInterimRecHdSub c "
			+ " WHERE c.recAbsPk.recruitmentMngId = :mngId";
	private static final String DELETE_RECMNG_BY_SID_AND_YMD = "DELETE FROM KrcdtInterimRecMng c "
			+ " WHERE c.pk.sid = :sid AND c.pk.ymd = :ymd";
	private static final String DELETE_BY_ID_ATR = "DELETE FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.absenceMngID = :absId"
			+ " AND c.recAbsPk.recruitmentMngId = :recId"
			+ " AND c.absenceMngAtr = :absAtr"
			+ " AND c.recruitmentMngAtr = :recAtr";
	private static final String QUERY_REC_BY_SID_MNGID = "SELECT c FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.recruitmentMngId = :recruitmentMngId"
			+ " AND c.absenceMngAtr = :absenceMngAtr"
			+ " AND c.recruitmentMngAtr = :recruitmentMngAtr";
	private static final String DELETE_REC_BY_ID = "DELETE FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.recruitmentMngId = :remainID"
			+ " AND c.recruitmentMngAtr = :mngAtr";
	private static final String DELETE_ABS_BY_ID = "DELETE FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.absenceMngID = :remainID"
			+ " AND c.recruitmentMngAtr = :mngAtr";
	private static final String QUERY_REC_BY_IDS_ATR = "SELECT c FROM KrcdtInterimRecHdSub c "
			+ " WHERE c.recAbsPk.recruitmentMngId IN :recruitmentMngId"
			+ " AND c.recruitmentMngAtr = :recruitmentMngAtr";
	private static final String QUERY_ABS_BY_IDS_ATR = "SELECT c FROM KrcdtInterimRecHdSub c "
			+ " WHERE c.recAbsPk.absenceMngID IN :absenceMngIds"
			+ " AND c.absenceMngAtr = :absenceMngAtr";


	@Override
	public Optional<InterimRecMng> getReruitmentById(String recId) {
		return this.queryProxy().find(recId, KrcdtInterimRecMng.class)
				.map(x -> toDomainRecMng(x));
	}

	private InterimRecMng toDomainRecMng(KrcdtInterimRecMng x) {
		return new InterimRecMng(x.remainMngId,
				x.pk.sid,
				x.pk.ymd,
				EnumAdaptor.valueOf(x.createAtr, nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr.class),
				RemainType.PICKINGUP,
				x.expirationDate,
				new OccurrenceDay(x.occurrenceDays),
				new UnUsedDay(x.unUsedDays));
	}

	@Override
	public Optional<InterimAbsMng> getAbsById(String absId) {
		return this.queryProxy().find(absId, KrcdtInterimHdSubMng.class)
				.map(x -> toDomainAbsMng(x));
	}

	private InterimAbsMng toDomainAbsMng(KrcdtInterimHdSubMng x) {
		return new InterimAbsMng(x.remainMngId,
				x.pk.sid,
				x.pk.ymd,
				EnumAdaptor.valueOf(x.createAtr, nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr.class),
				RemainType.PAUSE,
				new RequiredDay(x.requiredDays),
				new UnOffsetDay(x.unOffsetDay));
	}

	@Override
	public List<InterimRecAbsMng> getRecOrAbsMng(String interimId, boolean isRec, DataManagementAtr mngAtr) {
		return this.queryProxy().query(isRec ? QUERY_REC_BY_ID : QUERY_ABS_BY_ID, KrcdtInterimRecHdSub.class)
				.setParameter("remainID", interimId)
				.setParameter("mngAtr", mngAtr.value)
				.getList(x -> toDomainRecAbs(x));
	}

	@Override
	public List<InterimRecAbsMng> getRecOrAbsMngs(List<String> interimIds, boolean isRec, DataManagementAtr mngAtr) {
		if(interimIds.isEmpty()) return new ArrayList<>();
		return this.queryProxy().query(isRec ? QUERY_REC_BY_IDS : QUERY_ABS_BY_IDS, KrcdtInterimRecHdSub.class)
				.setParameter("remainID", interimIds)
				.setParameter("mngAtr", mngAtr.value)
				.getList(x -> toDomainRecAbs(x));
	}

	private InterimRecAbsMng toDomainRecAbs(KrcdtInterimRecHdSub x) {
		return new InterimRecAbsMng(x.recAbsPk.absenceMngID,
				EnumAdaptor.valueOf(x.absenceMngAtr, DataManagementAtr.class),
				x.recAbsPk.recruitmentMngId,
				EnumAdaptor.valueOf(x.recruitmentMngAtr,DataManagementAtr.class),
				new UseDay(x.useDays),
				EnumAdaptor.valueOf(x.selectedAtr, SelectedAtr.class));
	}
	@SneakyThrows
	@Override
	public List<InterimRecMng> getRecByIdPeriod(String sid, DatePeriod ymdPeriod, double unUseDays, DatePeriod dateData) {
		/*if(recId.isEmpty()) {
			return Collections.emptyList();
		}
		List<InterimRecMng> resultList = new ArrayList<>();
		CollectionUtil.split(recId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(QUERY_REC_BY_DATEPERIOD, KrcdtInterimRecMng.class)
								.setParameter("mngIds", subList)
								.setParameter("unUsedDays", unUseDays)
								.setParameter("startDate", dateData.start())
								.setParameter("endDate", dateData.end())
								.getList(c -> toDomainRecMng(c)));
		});
		return resultList;*/
		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KSHDT_INTERIM_REC a1"
				+ " WHERE a1.SID = ?"
				+ " AND a1.YMD >= ? and a1.YMD <= ?"
				+ " AND a1.UNUSED_DAYS > ?"
				+ " AND a1.EXPIRATION_DAYS >= ? and a1.EXPIRATION_DAYS <= ?"
				+ " ORDER BY a1.YMD");
				)
		{
			sql.setString(1, sid);
			sql.setDate(2, Date.valueOf(ymdPeriod.start().localDate()));
			sql.setDate(3, Date.valueOf(ymdPeriod.end().localDate()));
			sql.setDouble(4, unUseDays);
			sql.setDate(5, Date.valueOf(dateData.start().localDate()));
			sql.setDate(6, Date.valueOf(dateData.end().localDate()));
			List<InterimRecMng> lstOutput = new NtsResultSet(sql.executeQuery())
					.getList(x -> toDomain(x));
			return lstOutput;
		}
	}

	@Override
	public List<InterimRecAbsMng> getBySidMng(DataManagementAtr recAtr, DataManagementAtr absAtr,
			String absId) {
		return this.queryProxy().query(QUERY_ABS_BY_SID_MNGID, KrcdtInterimRecHdSub.class)
				.setParameter("absenceMngID", absId)
				.setParameter("absenceMngAtr", absAtr.value)
				.setParameter("recruitmentMngAtr", recAtr.value)
				.getList(x -> toDomainRecAbs(x));
	}

	@Override
	public void persistAndUpdateInterimRecMng(InterimRecMng domain) {

		// キー
		KrcdtInterimRecMngPK pk = new KrcdtInterimRecMngPK(AppContexts.user().companyId(), domain.getSID(), domain.getYmd());

		// 登録・更新
		KrcdtInterimRecMng entity = this.getEntityManager().find(KrcdtInterimRecMng.class, pk);
		if (entity == null){
			entity = new KrcdtInterimRecMng();
			entity.pk = pk;
			entity.remainMngId = domain.getRemainManaID();
			entity.createAtr = domain.getCreatorAtr().value;
			entity.expirationDate = domain.getExpirationDate();
			entity.occurrenceDays = domain.getOccurrenceDays().v();
			entity.unUsedDays = domain.getUnUsedDays().v();
			this.getEntityManager().persist(entity);
		}
		else {
			entity.remainMngId = domain.getRemainManaID();
			entity.createAtr = domain.getCreatorAtr().value;
			entity.expirationDate = domain.getExpirationDate();
			entity.occurrenceDays = domain.getOccurrenceDays().v();
			entity.unUsedDays = domain.getUnUsedDays().v();
			this.commandProxy().update(entity);
		}
		this.getEntityManager().flush();
	}

	@Override
	public void persistAndUpdateInterimAbsMng(InterimAbsMng domain) {

		// キー
		KrcdtInterimHdSubMngPK pk = new KrcdtInterimHdSubMngPK(AppContexts.user().companyId(), domain.getSID(), domain.getYmd());

		// 登録・更新
		KrcdtInterimHdSubMng entity = this.getEntityManager().find(KrcdtInterimHdSubMng.class, pk);
		if (entity == null){
			entity = new KrcdtInterimHdSubMng();
			entity.pk = pk;
			entity.remainMngId = domain.getRemainManaID();
			entity.requiredDays = domain.getRequeiredDays().v();
			entity.unOffsetDay = domain.getUnOffsetDays().v();
			entity.createAtr = domain.getCreatorAtr().value;
			this.getEntityManager().persist(entity);
		}
		else {
			entity.remainMngId = domain.getRemainManaID();
			entity.requiredDays = domain.getRequeiredDays().v();
			entity.unOffsetDay = domain.getUnOffsetDays().v();
			entity.createAtr = domain.getCreatorAtr().value;
			this.commandProxy().update(entity);
		}
		this.getEntityManager().flush();
	}

	@Override
	public void persistAndUpdateInterimRecAbsMng(InterimRecAbsMng domain) {

		// キー
		val key = new KrcmtInterimRecAbsPK(domain.getAbsenceMngId(), domain.getRecruitmentMngId());

		// 登録・更新
		KrcdtInterimRecHdSub entity = this.getEntityManager().find(KrcdtInterimRecHdSub.class, key);
		if (entity == null){
			entity = new KrcdtInterimRecHdSub();
			entity.recAbsPk = new KrcmtInterimRecAbsPK();
			entity.recAbsPk.absenceMngID = domain.getAbsenceMngId();
			entity.recAbsPk.recruitmentMngId = domain.getRecruitmentMngId();
			entity.absenceMngAtr = domain.getAbsenceMngAtr().value;
			entity.recruitmentMngAtr = domain.getRecruitmentMngAtr().value;
			entity.useDays = domain.getUseDays().v();
			entity.selectedAtr = domain.getSelectedAtr().value;
			this.getEntityManager().persist(entity);
		}
		else {
			entity.absenceMngAtr = domain.getAbsenceMngAtr().value;
			entity.recruitmentMngAtr = domain.getRecruitmentMngAtr().value;
			entity.useDays = domain.getUseDays().v();
			entity.selectedAtr = domain.getSelectedAtr().value;
			this.commandProxy().update(entity);
		}
		this.getEntityManager().flush();
	}

	@Override
	public void deleteInterimRecMng(String recruitmentMngId) {
		this.getEntityManager().createQuery(DELETE_RECMNG_BY_ID).setParameter("mngId", recruitmentMngId).executeUpdate();
	}

	@Override
	public void deleteInterimAbsMng(String absenceMngId) {
		this.getEntityManager().createQuery(DELETE_ABSMNG_BY_ID).setParameter("mngId", absenceMngId).executeUpdate();
	}

	@Override
	public void deleteInterimAbsMngBySidAndYmd(String sId, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_ABSMNG_BY_SID_AND_YMD)
		.setParameter("sid", sId)
		.setParameter("ymd", ymd)
		.executeUpdate();
	}

	@Override
	public void deleteInterimRecMngBySidAndYmd(String sId, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_RECMNG_BY_SID_AND_YMD)
		.setParameter("sid", sId)
		.setParameter("ymd", ymd)
		.executeUpdate();
	}

	@Override
	public void deleteInterimRecAbsMng(String mndId, boolean isRec) {
		this.getEntityManager().createQuery(isRec ? DELETE_REC_BY_MNGID : DELETE_ABS_BY_MNGID)
				.setParameter("mngId", mndId).executeUpdate();
	}

	@Override
	public void deleteRecAbsMngByIdAndAtr(String recId, String absId, DataManagementAtr recAtr,
			DataManagementAtr absAtr) {
		this.getEntityManager().createQuery(DELETE_BY_ID_ATR, KrcdtInterimRecHdSub.class)
				.setParameter("absId", absId)
				.setParameter("recId", recId)
				.setParameter("absAtr", absAtr.value)
				.setParameter("recAtr", recAtr.value)
				.executeUpdate();
	}

	@Override
	public void deleteRecAbsMngByIDAtr(String mngId, DataManagementAtr mngAtr, boolean isRec) {
		this.getEntityManager().createQuery(isRec ? DELETE_REC_BY_ID : DELETE_ABS_BY_ID, KrcdtInterimRecHdSub.class)
			.setParameter("remainID", mngId)
			.setParameter("mngAtr", mngAtr.value)
			.executeUpdate();

	}

	@Override
	public List<InterimRecAbsMng> getRecBySidMngAtr(DataManagementAtr recAtr, DataManagementAtr absAtr, String recId) {
		return this.queryProxy().query(QUERY_REC_BY_SID_MNGID, KrcdtInterimRecHdSub.class)
				.setParameter("recruitmentMngId", recId)
				.setParameter("absenceMngAtr", absAtr.value)
				.setParameter("recruitmentMngAtr", recAtr.value)
				.getList(x -> toDomainRecAbs(x));
	}

	@Override
	public List<InterimRecAbsMng> getRecByIdsMngAtr(List<String> recIds, DataManagementAtr recMngAtr) {
		List<InterimRecAbsMng> resultList = new ArrayList<>();
		CollectionUtil.split(recIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(QUERY_REC_BY_IDS_ATR, KrcdtInterimRecHdSub.class)
								.setParameter("recruitmentMngId", subList)
								.setParameter("recruitmentMngAtr", recMngAtr.value)
								.getList(x -> toDomainRecAbs(x)));
		});
		return resultList;
	}

	@Override
	public void deleteInterimRecMng(List<String> listRecMngId) {
		if(!listRecMngId.isEmpty()) {
			String sql = "delete  from KrcdtInterimRecMng a where a.recruitmentMngId IN :listRecMngId";
			CollectionUtil.split(listRecMngId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				this.getEntityManager().createQuery(sql).setParameter("listRecMngId", subList).executeUpdate();
			});
			this.getEntityManager().flush();
		}
	}

	@Override
	public void deleteInterimAbsMng(List<String> listAbsMngId) {
		if(!listAbsMngId.isEmpty()) {
			String sql = "delete  from KrcdtInterimHdSubMng a where a.absenceMngId IN :listAbsMngId";
			CollectionUtil.split(listAbsMngId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
				this.getEntityManager().createQuery(sql).setParameter("listAbsMngId", subList).executeUpdate();
			});
			this.getEntityManager().flush();
		}
	}

	@Override
	public List<InterimRecAbsMng> getAbsByIdsMngAtr(List<String> absIds, DataManagementAtr absMngAtr) {
		List<InterimRecAbsMng> resultList = new ArrayList<>();
		CollectionUtil.split(absIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(QUERY_ABS_BY_IDS_ATR, KrcdtInterimRecHdSub.class)
								.setParameter("absenceMngIds", subList)
								.setParameter("absenceMngAtr", absMngAtr.value)
								.getList(x -> toDomainRecAbs(x)));
		});
		return resultList;
	}

	@SneakyThrows
	@Override
	public List<InterimRecMng> getRecBySidDatePeriod(String sid, DatePeriod period) {
		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KSHDT_INTERIM_REC a1"
				+ " WHERE a1.SID = ?"
				+ " AND a1.YMD >= ? and a1.YMD <= ?"
				+ " ORDER BY a1.YMD");
		)
		{
			sql.setString(1, sid);
			sql.setDate(2, Date.valueOf(period.start().localDate()));
			sql.setDate(3, Date.valueOf(period.end().localDate()));
			List<InterimRecMng> lstOutput = new NtsResultSet(sql.executeQuery())
					.getList(x -> toDomain(x));
			return lstOutput;
		}
	}

	private InterimRecMng toDomain(NtsResultRecord x) {
		return new InterimRecMng(x.getString("REMAIN_MNG_ID"),
				x.getString("SID"),
				x.getGeneralDate("YMD"),
				x.getEnum("CREATOR_ATR", CreateAtr.class),
				RemainType.PICKINGUP,
				x.getGeneralDate("EXPIRATION_DAYS"),
				new OccurrenceDay(x.getBigDecimal("OCCURRENCE_DAYS") == null ? 0 : x.getBigDecimal("OCCURRENCE_DAYS").doubleValue()),
				new UnUsedDay(x.getBigDecimal("UNUSED_DAYS") == null ? 0 : x.getBigDecimal("UNUSED_DAYS").doubleValue()));
	}

	@SneakyThrows
	@Override
	public List<InterimAbsMng> getAbsBySidDatePeriod(String sid, DatePeriod period) {
		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KSHDT_INTERIM_HDSUB a"
				+ " WHERE a.SID = ?"
				+ " AND a.YMD >= ? and a.YMD <= ?"
				+ " ORDER BY a.YMD");
				)
		{
			sql.setString(1, sid);
			sql.setDate(2, Date.valueOf(period.start().localDate()));
			sql.setDate(3, Date.valueOf(period.end().localDate()));
			List<InterimAbsMng> lstOutput = new NtsResultSet(sql.executeQuery())
					.getList(x -> toDomainAbs(x));
			return lstOutput;
		}
	}

	private InterimAbsMng toDomainAbs(NtsResultRecord x) {
		return new InterimAbsMng(
				x.getString("REMAIN_MNG_ID"),
				x.getString("SID"),
				x.getGeneralDate("YMD"),
				EnumAdaptor.valueOf(x.getInt("CREATOR_ATR"), CreateAtr.class) ,
				RemainType.PAUSE,
				new RequiredDay(x.getBigDecimal("REQUIRED_DAYS") == null ? 0 : x.getBigDecimal("REQUIRED_DAYS").doubleValue()),
				new UnOffsetDay(x.getBigDecimal("UNOFFSET_DAYS") == null ? 0 : x.getBigDecimal("UNOFFSET_DAYS").doubleValue()));
	}

	private static final String DELETE_FURISYUTSU_PERIOD = "DELETE FROM KrcdtInterimRecMng c WHERE c.pk.sid = :sid AND c.pk.ymd between :startDate and :endDate";

	@Override
	public void deleteRecMngWithPeriod(String sid, DatePeriod period) {
		this.getEntityManager().createQuery(DELETE_FURISYUTSU_PERIOD).setParameter("sid", sid)
		.setParameter("startDate", period.start()).setParameter("endDate", period.end()).executeUpdate();
	}

	@Override
	public void insertRecMngList(List<InterimRecMng> lstDomain) {
		this.commandProxy().insertAll(lstDomain.stream().map(c -> toEntityRecMng(c)).collect(Collectors.toList()));
	}

	private KrcdtInterimRecMng toEntityRecMng(InterimRecMng domain) {
		// キー
		KrcdtInterimRecMngPK pk = new KrcdtInterimRecMngPK(AppContexts.user().companyId(), domain.getSID(),
				domain.getYmd());

		// 登録・更新
		KrcdtInterimRecMng entity = new KrcdtInterimRecMng();
		entity.pk = pk;
		entity.remainMngId = domain.getRemainManaID();
		entity.createAtr = domain.getCreatorAtr().value;
		entity.expirationDate = domain.getExpirationDate();
		entity.occurrenceDays = domain.getOccurrenceDays().v();
		entity.unUsedDays = domain.getUnUsedDays().v();
		return entity;
	}
	
	private static final String DELETE_FURIKYU_PERIOD = "DELETE FROM KrcdtInterimHdSubMng c WHERE c.pk.sid = :sid AND c.pk.ymd between :startDate and :endDate";
	@Override
	public void deleteAbsMngWithPeriod(String sid, DatePeriod period) {
		this.getEntityManager().createQuery(DELETE_FURIKYU_PERIOD).setParameter("sid", sid)
				.setParameter("startDate", period.start()).setParameter("endDate", period.end()).executeUpdate();
	}

	@Override
	public void insertAbsMngList(List<InterimAbsMng> lstDomain) {
		this.commandProxy().insertAll(lstDomain.stream().map(c -> toEntityAbsMng(c)).collect(Collectors.toList()));
	}
	
	private KrcdtInterimHdSubMng toEntityAbsMng(InterimAbsMng domain) {
		// キー
		KrcdtInterimHdSubMngPK pk = new KrcdtInterimHdSubMngPK(AppContexts.user().companyId(), domain.getSID(),
				domain.getYmd());
		// 登録・更新
		KrcdtInterimHdSubMng entity = new KrcdtInterimHdSubMng();
		entity.pk = pk;
		entity.remainMngId = domain.getRemainManaID();
		entity.requiredDays = domain.getRequeiredDays().v();
		entity.unOffsetDay = domain.getUnOffsetDays().v();
		entity.createAtr = domain.getCreatorAtr().value;
		return entity;
	}

	
	private static final String DELETE_FURISYUTSU_DATE = "DELETE FROM KrcdtInterimRecMng c WHERE c.pk.sid = :sid AND c.pk.ymd IN :lstDate";

	@Override
	public void deleteRecMngWithDateList(String sid, List<GeneralDate> lstDate) {
		if (lstDate.isEmpty())
			return;
		this.getEntityManager().createQuery(DELETE_FURISYUTSU_DATE).setParameter("sid", sid)
				.setParameter("lstDate", lstDate).executeUpdate();
	}

	private static final String DELETE_FURIKYU_DATE = "DELETE FROM KrcdtInterimHdSubMng c WHERE c.pk.sid = :sid AND c.pk.ymd IN :lstDate";
	
	@Override
	public void deleteAbsMngWithDateList(String sid, List<GeneralDate> lstDate) {
		if (lstDate.isEmpty())
			return;
		this.getEntityManager().createQuery(DELETE_FURIKYU_DATE).setParameter("sid", sid)
		.setParameter("lstDate", lstDate).executeUpdate();
		
	}

	private static final String GET_REC_DATE = "SELECT c FROM KrcdtInterimRecMng c "
			+ " WHERE c.pk.sid = :sid"
			+ " AND c.pk.ymd IN :ymd";

	@Override
	public List<InterimRecMng> getRecBySidDateList(String sid, List<GeneralDate> lstDate) {
		if(lstDate.isEmpty()) return new ArrayList<>();
		return this.queryProxy().query(GET_REC_DATE, KrcdtInterimRecMng.class).setParameter("sid", sid)
				.setParameter("ymd", lstDate).getList().stream().map(x -> toDomainRecMng(x)).collect(Collectors.toList());
	}

	private static final String GET_ABS_DATE = "SELECT c FROM KrcdtInterimHdSubMng c "
			+ " WHERE c.pk.sid = :sid"
			+ " AND c.pk.ymd IN :ymd";
	@Override
	public List<InterimAbsMng> getAbsBySidDateList(String sid, List<GeneralDate> lstDate) {
		if(lstDate.isEmpty()) return new ArrayList<>();
		return this.queryProxy().query(GET_ABS_DATE, KrcdtInterimHdSubMng.class).setParameter("sid", sid)
				.setParameter("ymd", lstDate).getList().stream().map(x -> toDomainAbsMng(x)).collect(Collectors.toList());
	}

	private static final String DELETE_FURIKYU_BY_SID_BEFORETHEYMD = "DELETE FROM KrcdtInterimHdSubMng c "
			+ " WHERE c.pk.sid = :sid AND c.pk.ymd <= :ymd";
	
	@Override
	public void deleteAbsMngBySidBeforeTheYmd(String sid, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_FURIKYU_BY_SID_BEFORETHEYMD)
		.setParameter("sid", sid)
		.setParameter("ymd", ymd)
		.executeUpdate();
		
	}
	

	private static final String DELETE_FURISYUTSU_BY_SID_BEFORETHEYMD = "DELETE FROM KrcdtInterimRecMng c "
			+ " WHERE c.pk.sid = :sid AND c.pk.ymd <= :ymd";

	@Override
	public void deleteRecMngBySidBeforeTheYmd(String sid, GeneralDate ymd) {
		this.getEntityManager().createQuery(DELETE_FURISYUTSU_BY_SID_BEFORETHEYMD)
		.setParameter("sid", sid)
		.setParameter("ymd", ymd)
		.executeUpdate();
		
	}
}
