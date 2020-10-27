package nts.uk.ctx.at.shared.infra.repository.remainingnumber.absencerecruitment.interim;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
//import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcdtInterimHdSubMng;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcdtInterimRecHdSub;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcdtInterimRecHdSubPK;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim.KrcdtInterimRecMng;
import nts.arc.time.calendar.period.DatePeriod;

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
	private static final String QUERY_ABS_BY_SID_MNGID = "SELECT c FROM KrcdtInterimRecHdSub c"
			+ " WHERE c.recAbsPk.absenceMngID = :absenceMngID"
			+ " AND c.absenceMngAtr = :absenceMngAtr"
			+ " AND c.recruitmentMngAtr = :recruitmentMngAtr";
	private static final String DELETE_ABS_BY_MNGID = "DELETE FROM KrcdtInterimRecHdSub c "
			+ " WHERE c.recAbsPk.absenceMngID = :mngId";
	private static final String DELETE_REC_BY_MNGID = "DELETE FROM KrcdtInterimRecHdSub c "
			+ " WHERE c.recAbsPk.recruitmentMngId = :mngId";
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
		return new InterimRecMng(x.recruitmentMngId, 
				x.expirationDate, 
				new OccurrenceDay(x.occurrenceDays),
				EnumAdaptor.valueOf(x.statutoryAtr, StatutoryAtr.class),
				new UnUsedDay(x.unUsedDays));
	}

	@Override
	public Optional<InterimAbsMng> getAbsById(String absId) {
		return this.queryProxy().find(absId, KrcdtInterimHdSubMng.class)
				.map(x -> toDomainAbsMng(x));
	}

	private InterimAbsMng toDomainAbsMng(KrcdtInterimHdSubMng x) {		
		return new InterimAbsMng(x.absenceMngId, new RequiredDay(x.requiredDays), new UnOffsetDay(x.unOffsetDay));
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
		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KRCDT_INTERIM_REC_MNG a1"
				+ " INNER JOIN KRCDT_INTERIM_REMAIN_MNG a2 "
				+ " ON a1.RECRUITMENT_MNG_ID = a2.REMAIN_MNG_ID"
				+ " WHERE a2.SID = ?"
				+ " AND a2.REMAIN_TYPE = " + RemainType.PICKINGUP.value
				+ " AND a2.YMD >= ? and a2.YMD <= ?"
				+ " AND a1.UNUSED_DAYS > ?"
				+ " AND a1.EXPIRATION_DAYS >= ? and a1.EXPIRATION_DAYS <= ?"
				+ " ORDER BY a2.YMD");
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
		val key = domain.getRecruitmentMngId();
		
		// 登録・更新
		KrcdtInterimRecMng entity = this.getEntityManager().find(KrcdtInterimRecMng.class, key);
		if (entity == null){
			entity = new KrcdtInterimRecMng();
			entity.recruitmentMngId = domain.getRecruitmentMngId();
			entity.expirationDate = domain.getExpirationDate();
			entity.occurrenceDays = domain.getOccurrenceDays().v();
			entity.statutoryAtr = domain.getStatutoryAtr().value;
			entity.unUsedDays = domain.getUnUsedDays().v();
			this.getEntityManager().persist(entity);
		}
		else {
			entity.expirationDate = domain.getExpirationDate();
			entity.occurrenceDays = domain.getOccurrenceDays().v();
			entity.statutoryAtr = domain.getStatutoryAtr().value;
			entity.unUsedDays = domain.getUnUsedDays().v();
			this.commandProxy().update(entity);
		}
		this.getEntityManager().flush();
	}
	
	@Override
	public void persistAndUpdateInterimAbsMng(InterimAbsMng domain) {
		
		// キー
		val key = domain.getAbsenceMngId();
		
		// 登録・更新
		KrcdtInterimHdSubMng entity = this.getEntityManager().find(KrcdtInterimHdSubMng.class, key);
		if (entity == null){
			entity = new KrcdtInterimHdSubMng();
			entity.absenceMngId = domain.getAbsenceMngId();
			entity.requiredDays = domain.getRequeiredDays().v();
			entity.unOffsetDay = domain.getUnOffsetDays().v();
			this.getEntityManager().persist(entity);
		}
		else {
			entity.requiredDays = domain.getRequeiredDays().v();
			entity.unOffsetDay = domain.getUnOffsetDays().v();
			this.commandProxy().update(entity);
		}
		this.getEntityManager().flush();
	}
	
	@Override
	public void persistAndUpdateInterimRecAbsMng(InterimRecAbsMng domain) {
		
		// キー
		val key = new KrcdtInterimRecHdSubPK(domain.getAbsenceMngId(), domain.getRecruitmentMngId());
		
		// 登録・更新
		KrcdtInterimRecHdSub entity = this.getEntityManager().find(KrcdtInterimRecHdSub.class, key);
		if (entity == null){
			entity = new KrcdtInterimRecHdSub();
			entity.recAbsPk = new KrcdtInterimRecHdSubPK();
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
		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KRCDT_INTERIM_REC_MNG a1"
				+ " INNER JOIN KRCDT_INTERIM_REMAIN_MNG a2 ON a1.RECRUITMENT_MNG_ID = a2.REMAIN_MNG_ID"
				+ " WHERE a2.SID = ?"
				+ " AND a2.REMAIN_TYPE = " + RemainType.PICKINGUP.value
				+ " AND a2.YMD >= ? and a2.YMD <= ?"
				+ " ORDER BY a2.YMD");
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
		return new InterimRecMng(x.getString("RECRUITMENT_MNG_ID"),
				x.getGeneralDate("EXPIRATION_DAYS"),
				new OccurrenceDay(x.getBigDecimal("OCCURRENCE_DAYS") == null ? 0 : x.getBigDecimal("OCCURRENCE_DAYS").doubleValue()),
				x.getEnum("STATUTORY_ATR", StatutoryAtr.class),
				new UnUsedDay(x.getBigDecimal("UNUSED_DAYS") == null ? 0 : x.getBigDecimal("UNUSED_DAYS").doubleValue()));
	}

	@SneakyThrows
	@Override
	public List<InterimAbsMng> getAbsBySidDatePeriod(String sid, DatePeriod period) {
		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KRCDT_INTERIM_HD_SUB_MNG a1"
				+ " INNER JOIN KRCDT_INTERIM_REMAIN_MNG a2 ON a1.ABSENCE_MNG_ID = a2.REMAIN_MNG_ID"
				+ " WHERE a2.SID = ?"
				+ " AND a2.REMAIN_TYPE = " + RemainType.PAUSE.value
				+ " AND a2.YMD >= ? and a2.YMD <= ?"
				+ " ORDER BY a2.YMD");
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
		return new InterimAbsMng(x.getString("ABSENCE_MNG_ID"), 
				new RequiredDay(x.getBigDecimal("REQUIRED_DAYS") == null ? 0 : x.getBigDecimal("REQUIRED_DAYS").doubleValue()), 
				new UnOffsetDay(x.getBigDecimal("UNOFFSET_DAYS") == null ? 0 : x.getBigDecimal("UNOFFSET_DAYS").doubleValue()));
	}
}
