package nts.uk.ctx.at.record.infra.repository.daily.ouen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.WorkDetailData;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTimeSheet;
import nts.uk.ctx.at.record.infra.entity.daily.timezone.KrcdtDayTsSupSupplInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class OuenWorkTimeSheetOfDailyRepoImpl extends JpaRepository implements OuenWorkTimeSheetOfDailyRepo {
	@Override
	public OuenWorkTimeSheetOfDaily find(String empId, GeneralDate ymd) {
		
		List<KrcdtDayOuenTimeSheet> entitis = queryProxy()
				.query("SELECT o FROM KrcdtDayOuenTimeSheet o WHERE o.pk.sid = :sid AND o.pk.ymd = :ymd", KrcdtDayOuenTimeSheet.class)
				.setParameter("sid", empId).setParameter("ymd", ymd).getList();
		
		if(entitis.isEmpty())
			return null;
		
		OuenWorkTimeSheetOfDaily rs = toDomain(entitis);
		
		return rs;
	}
	
	@Override
	public List<OuenWorkTimeSheetOfDaily> find(String sid, DatePeriod period) {
		
		List<KrcdtDayOuenTimeSheet> entitis = this.queryProxy().query("SELECT s FROM KrcdtDayOuenTimeSheet s WHERE s.pk.sid = :sid"
				+ " AND s.pk.ymd >= :start AND s.pk.ymd <= :end", KrcdtDayOuenTimeSheet.class)
				.setParameter("sid", sid)
				.setParameter("start", period.start())
				.setParameter("end", period.end())
				.getList();
		
		if(entitis.isEmpty())
			return new ArrayList<>();
		List<OuenWorkTimeSheetOfDaily> rs = new ArrayList<>();
		
		period.datesBetween().forEach(ymd -> {
			List<KrcdtDayOuenTimeSheet> entitisBySidAndDate = entitis.stream().filter(i -> i.pk.sid.equals(sid) && i.pk.ymd.equals(ymd)).collect(Collectors.toList());
			if(!entitisBySidAndDate.isEmpty()){
				rs.add(toDomain(entitisBySidAndDate));
				
			}
		});
		return rs;
	}
	
	@Override
	public boolean findPK(String empId, GeneralDate ymd, int ouenNo ) {
		
		Optional<KrcdtDayOuenTimeSheet> entitis = queryProxy()
				.query("SELECT o FROM KrcdtDayOuenTimeSheet o WHERE o.pk.sid = :sid AND o.pk.ymd = :ymd AND o.pk.ouenNo = :ouenNo", KrcdtDayOuenTimeSheet.class)
				.setParameter("sid", empId)
				.setParameter("ymd", ymd)
				.setParameter("ouenNo", ouenNo)
				.getSingle();
		
		if(entitis.isPresent())
			return true;
		
		return false;
	}
	
	public Optional<KrcdtDayOuenTimeSheet> getEntity(String empId, GeneralDate ymd, int ouenNo ) {
		Optional<KrcdtDayOuenTimeSheet> entitis = queryProxy()
				.query("SELECT o FROM KrcdtDayOuenTimeSheet o WHERE o.pk.sid = :sid AND o.pk.ymd = :ymd AND o.pk.ouenNo = :ouenNo", KrcdtDayOuenTimeSheet.class)
				.setParameter("sid", empId)
				.setParameter("ymd", ymd)
				.setParameter("ouenNo", ouenNo)
				.getSingle();
		
		return entitis;
	}

	@Override
	public void update(List<OuenWorkTimeSheetOfDaily> domain) {
		List<KrcdtDayOuenTimeSheet> lstEntity = new ArrayList<>();
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(entities -> {
			
			lstEntity.addAll(entities);
			
		});
		
		lstEntity.forEach(i -> {
			Optional<KrcdtDayOuenTimeSheet> entityOld = getEntity(i.pk.sid, i.pk.ymd, i.pk.ouenNo);
			if (!entityOld.isPresent()) {
				commandProxy().insert(i);
			} else {
				updateData(entityOld.get(), i);
				commandProxy().update(entityOld.get());
			}
		});
	}

	@Override
	public void insert(List<OuenWorkTimeSheetOfDaily> domain) {
		if(domain.isEmpty()) return;
		List<KrcdtDayOuenTimeSheet> lstEntity = new ArrayList<>();
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			lstEntity.addAll(e);
		});
		lstEntity.forEach(i -> {
		OuenWorkTimeSheetOfDaily lstDomainOld = this.find(domain.get(0).getEmpId(), domain.get(0).getYmd());
		if(lstDomainOld != null) {
			List<OuenWorkTimeSheetOfDailyAttendance> dataOld = lstDomainOld.getOuenTimeSheet().stream().filter(x -> {
				return x.getWorkNo().v() != i.pk.ouenNo;
			}).collect(Collectors.toList());
			
			if(!dataOld.isEmpty()) {
				for (OuenWorkTimeSheetOfDailyAttendance atd : dataOld) {
					this.removePK(i.pk.sid, i.pk.ymd, atd.getWorkNo().v());
				}
			}
		}
		});
		this.update(domain);
	}
	
	@Override
	public void persist(List<OuenWorkTimeSheetOfDaily> domain) {
		this.insert(domain);
	}

	private void updateData(KrcdtDayOuenTimeSheet entityOld, KrcdtDayOuenTimeSheet dataUpdate) {
		entityOld.startTime = dataUpdate.startTime;
		entityOld.endTime = dataUpdate.endTime;
		entityOld.workCd1 = StringUtil.isNullOrEmpty(dataUpdate.workCd1, true) ? null : dataUpdate.workCd1;
		entityOld.workCd2 = dataUpdate.workCd2;
		entityOld.workCd3 = dataUpdate.workCd3;
		entityOld.workCd4 = dataUpdate.workCd4;
		entityOld.workCd5 = dataUpdate.workCd5;
		entityOld.workLocationCode = dataUpdate.workLocationCode;
		entityOld.workNo = dataUpdate.workNo;
		entityOld.startTimeChangeWay = dataUpdate.startTimeChangeWay;
		entityOld.endTimeChangeWay = dataUpdate.endTimeChangeWay;
		entityOld.workplaceId = dataUpdate.workplaceId;
		entityOld.startStampMethod = dataUpdate.startStampMethod;
		entityOld.endStampMethod = dataUpdate.endStampMethod;
		//entityOld.workplaceId      = dataUpdate.workplaceId;
		if(entityOld.krcdtDayTsSupSupplInfo ==null) {
			entityOld.krcdtDayTsSupSupplInfo = dataUpdate.krcdtDayTsSupSupplInfo;
		}else {
			
			entityOld.krcdtDayTsSupSupplInfo.supplInfoTime1 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoTime1;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoTime2 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoTime2;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoTime3 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoTime3;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoTime4 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoTime4;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoTime5 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoTime5;
			
			entityOld.krcdtDayTsSupSupplInfo.supplInfoNumber1 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoNumber1;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoNumber2 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoNumber2;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoNumber3 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoNumber3;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoNumber4 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoNumber4;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoNumber5 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoNumber5;
			
			entityOld.krcdtDayTsSupSupplInfo.supplInfoComment1 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoComment1;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoComment2 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoComment2;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoComment3 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoComment3;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoComment4 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoComment4;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoComment5 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoComment5;
			
			entityOld.krcdtDayTsSupSupplInfo.supplInfoCode1 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoCode1;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoCode2 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoCode2;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoCode3 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoCode3;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoCode4 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoCode4;
			entityOld.krcdtDayTsSupSupplInfo.supplInfoCode5 = dataUpdate.krcdtDayTsSupSupplInfo == null ? null : dataUpdate.krcdtDayTsSupSupplInfo.supplInfoCode5;
			
		}
	}

	@Override
	public void delete(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(lstE -> {
			lstE.forEach(e -> {
				this.queryProxy().find(e.pk, KrcdtDayOuenTimeSheet.class).ifPresent(entity -> {
					commandProxy().remove(entity);
				});
			});

		});
	}
	
	public OuenWorkTimeSheetOfDaily toDomain(List<KrcdtDayOuenTimeSheet> es) {
		
		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet = es.stream().map(ots -> {
			
			Optional<KrcdtDayTsSupSupplInfo> krcdtDayTsSupSupplInfo = queryProxy()
					.query("SELECT o FROM KrcdtDayTsSupSupplInfo o WHERE o.pk.sid = :sid AND o.pk.date = :ymd AND o.pk.supNo = :supNo", KrcdtDayTsSupSupplInfo.class)
					.setParameter("sid", ots.pk.sid)
					.setParameter("ymd", ots.pk.ymd)
					.setParameter("supNo", ots.pk.ouenNo)
					.getSingle();
			
			return ots.domain(krcdtDayTsSupSupplInfo);
			}).collect(Collectors.toList());
		
		return OuenWorkTimeSheetOfDaily.create(es.get(0).pk.sid, es.get(0).pk.ymd, ouenTimeSheet);
	}
	
	

	@Override
	public void remove(String sid, GeneralDate ymd) {
		String delete = "delete from KrcdtDayOuenTimeSheet o " + " where o.pk.sid = :sid "
				+ " and o.pk.ymd = :ymd ";
		this.getEntityManager().createQuery(delete).setParameter("sid", sid)
												   .setParameter("ymd", ymd)
												   .executeUpdate();
		String deleteKrcdtDayTsSupSupplInfo = "delete from KrcdtDayTsSupSupplInfo o " + " where o.pk.sid = :sid "
				+ " and o.pk.date = :ymd ";
		this.getEntityManager().createQuery(deleteKrcdtDayTsSupSupplInfo).setParameter("sid", sid)
												   .setParameter("ymd", ymd)
												   .executeUpdate();
	}
	
	@Override
	public void removePK(String sid, GeneralDate ymd, int ouenNo) {
		String delete = "delete from KrcdtDayOuenTimeSheet o " + " where o.pk.sid = :sid "
				+ " and o.pk.ymd = :ymd "
				+ " and o.pk.ouenNo = :ouenNo ";
		this.getEntityManager().createQuery(delete).setParameter("sid", sid)
												   .setParameter("ymd", ymd)
												   .setParameter("ouenNo", ouenNo)
												   .executeUpdate();
		String deleteKrcdtDayTsSupSupplInfo = "delete from KrcdtDayTsSupSupplInfo o " + " where o.pk.sid = :sid "
				+ " and o.pk.date = :ymd "
				+ " and o.pk.supNo = :supNo ";
		this.getEntityManager().createQuery(deleteKrcdtDayTsSupSupplInfo).setParameter("sid", sid)
												   .setParameter("ymd", ymd)
												   .setParameter("supNo", ouenNo)
												   .executeUpdate();
	}

	@Override
	public List<OuenWorkTimeSheetOfDaily> find(Map<String, List<GeneralDate>> param) {
		List<KrcdtDayOuenTimeSheet> supports = new ArrayList<>();
		String query = new StringBuilder("SELECT sp FROM KrcdtDayOuenTimeSheet sp")
								.append(" WHERE sp.pk.sid IN :sid")
								.append(" AND sp.pk.ymd IN :date")
								.toString();
		TypedQueryWrapper<KrcdtDayOuenTimeSheet> tpQuery = queryProxy().query(query, KrcdtDayOuenTimeSheet.class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			supports.addAll(tpQuery.setParameter("sid", p.keySet())
					.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
					.getList().stream()
					.collect(Collectors.toList()));
		});
		
		List<OuenWorkTimeSheetOfDaily> domains = new ArrayList<>();
		param.forEach((sid, dates) -> {
			dates.forEach(date -> {
				List<KrcdtDayOuenTimeSheet> supportsByEmp = supports.stream().filter(c -> c.pk.sid.equals(sid) && c.pk.ymd.equals(date)).collect(Collectors.toList());
				if (!supportsByEmp.isEmpty()) {
					domains.add(toDomain(supportsByEmp));
				}
			});
		});

		return domains;
	}

	@Override
	public List<WorkDetailData> getWorkDetailData(List<String> empIdList, List<String> wkplIdList, DatePeriod period) {
		String sql =
				" SELECT " +
						" t.SID, t.YMD, t.SUP_NO, i.WKP_ID, t.WORKPLACE_ID, t.WORK_CD1, " +
						" t.WORK_CD2, t.WORK_CD3, t.WORK_CD4, t.WORK_CD5, s.TOTAL_TIME " +
						" FROM KRCDT_DAY_TS_SUP t " +
						" INNER JOIN KRCDT_DAY_TIME_SUP s " +
						"   ON t.SID = s.SID AND t.YMD = s.YMD AND t.SUP_NO = s.SUP_NO " +
						" INNER JOIN KRCDT_DAY_AFF_INFO i " +
						" 	ON t.SID = i.SID AND t.YMD = i.YMD" +
						" WHERE " +
						" t.YMD BETWEEN @startDate AND @endDate ";

		if (!CollectionUtil.isEmpty(empIdList)) {
			sql += " AND t.SID IN @empIdList ";
		}

		if (!CollectionUtil.isEmpty(wkplIdList)) {
			sql += " AND i.WKP_ID in @wkplIdList ";
		}

		if (!empIdList.isEmpty() && wkplIdList.isEmpty()) {
			return new NtsStatement(sql, this.jdbcProxy())
					.paramDate("startDate", period.start())
					.paramDate("endDate", period.end())
					.paramString("empIdList", empIdList)
					.getList(OuenWorkTimeSheetOfDailyRepoImpl::toWorkDetailData);
		} else if (empIdList.isEmpty() && !wkplIdList.isEmpty()) {
			return new NtsStatement(sql, this.jdbcProxy())
					.paramDate("startDate", period.start())
					.paramDate("endDate", period.end())
					.paramString("wkplIdList", wkplIdList)
					.getList(OuenWorkTimeSheetOfDailyRepoImpl::toWorkDetailData);
		} else if (empIdList.isEmpty() && wkplIdList.isEmpty()) {
			return new NtsStatement(sql, this.jdbcProxy())
					.paramDate("startDate", period.start())
					.paramDate("endDate", period.end())
					.getList(OuenWorkTimeSheetOfDailyRepoImpl::toWorkDetailData);
		} else {
			return new NtsStatement(sql, this.jdbcProxy())
					.paramDate("startDate", period.start())
					.paramDate("endDate", period.end())
					.paramString("wkplIdList", wkplIdList)
					.paramString("empIdList", empIdList)
					.getList(OuenWorkTimeSheetOfDailyRepoImpl::toWorkDetailData);
		}
	}

	private static WorkDetailData toWorkDetailData(NtsResultSet.NtsResultRecord rec) {
		return new WorkDetailData(
				rec.getString("SID"),
				rec.getGeneralDate("YMD"),
				rec.getInt("SUP_NO"),
				rec.getString("WKP_ID"),
				rec.getString("WORKPLACE_ID"),
				rec.getString("WORK_CD1") != null ? rec.getString("WORK_CD1").trim() : rec.getString("WORK_CD1"),
				rec.getString("WORK_CD2") != null ? rec.getString("WORK_CD2").trim() : rec.getString("WORK_CD2"),
				rec.getString("WORK_CD3") != null ? rec.getString("WORK_CD3").trim() : rec.getString("WORK_CD3"),
				rec.getString("WORK_CD4") != null ? rec.getString("WORK_CD4").trim() : rec.getString("WORK_CD4"),
				rec.getString("WORK_CD5") != null ? rec.getString("WORK_CD5").trim() : rec.getString("WORK_CD5"),
				rec.getInt("TOTAL_TIME"));
	}
	
	@Override
	public List<OuenWorkTimeSheetOfDaily> find(List<String> sid, DatePeriod ymd) {
		List<KrcdtDayOuenTimeSheet> entitis = new ArrayList<>();
		CollectionUtil.split(sid, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			entitis.addAll(this.queryProxy().query("SELECT s FROM KrcdtDayOuenTimeSheet s WHERE s.pk.sid IN :sid"
				+ " AND s.pk.ymd >= :start AND s.pk.ymd <= :end", KrcdtDayOuenTimeSheet.class)
				.setParameter("sid", p)
				.setParameter("start", ymd.start())
				.setParameter("end", ymd.end())
				.getList()
			);
		});
		if(entitis.isEmpty())
			return new ArrayList<>();
		List<OuenWorkTimeSheetOfDaily> rs = new ArrayList<>();
		sid.forEach(id -> {
			ymd.datesBetween().forEach(item -> {
				List<KrcdtDayOuenTimeSheet> entitisBySidAndDate = entitis.stream().filter(i -> i.pk.sid.equals(id) && i.pk.ymd.equals(item)).collect(Collectors.toList());
				if(!entitisBySidAndDate.isEmpty()){
					rs.add(toDomain(entitisBySidAndDate));
				}
			});
		});
		return rs;
	}
	
	// [2]  削除する
	// 指定したキー条件に該当するレコードを削除する
	@Override
	public void deleteBySupFrameNo(String sId, GeneralDate ymd, SupportFrameNo supportFrameNo) {
		
		int ouenNo = supportFrameNo.v();
		
		String deleteTimeSheet = "DELETE FROM KrcdtDayOuenTimeSheet o " + " WHERE o.pk.sid = :sid "
				+ " AND o.pk.ymd = :ymd "
				+ " AND o.pk.ouenNo = :ouenNo ";
		this.getEntityManager().createQuery(deleteTimeSheet).setParameter("sid", sId)
												   .setParameter("ymd", ymd)
												   .setParameter("ouenNo", ouenNo)
												   .executeUpdate();
		
		String deleteTime = "DELETE FROM KrcdtDayOuenTime o " + " WHERE o.pk.sid = :sid "
				+ " AND o.pk.ymd = :ymd "
				+ " AND o.pk.ouenNo = :ouenNo ";
		this.getEntityManager().createQuery(deleteTime).setParameter("sid", sId)
												   .setParameter("ymd", ymd)
												   .setParameter("ouenNo", ouenNo)
												   .executeUpdate();
		
		String deleteInfo = "DELETE FROM KrcdtDayTsSupSupplInfo o " + " WHERE o.pk.sid = :sid "
				+ " AND o.pk.date = :ymd "
				+ " AND o.pk.supNo = :ouenNo ";
		this.getEntityManager().createQuery(deleteInfo).setParameter("sid", sId)
												   .setParameter("ymd", ymd)
												   .setParameter("ouenNo", ouenNo)
												   .executeUpdate();
	}

	// [9]  日別実績の応援作業別勤怠時間帯を取得する
	@Override
	public List<OuenWorkTimeSheetOfDaily> get(String sId, List<GeneralDate> dates) {
		if (CollectionUtil.isEmpty(dates)) return Collections.emptyList();
		
		List<KrcdtDayOuenTimeSheet> timeSheets = new ArrayList<>();
		CollectionUtil.split(dates, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			timeSheets.addAll(this.queryProxy().query(
					"SELECT o FROM KrcdtDayOuenTimeSheet o WHERE o.pk.sid = :sId AND o.pk.ymd IN :dates", KrcdtDayOuenTimeSheet.class)
					  .setParameter("sId", sId)
					  .setParameter("dates", subList).getList());
		});
		
		List<OuenWorkTimeSheetOfDaily> domains = new ArrayList<>();
		dates.forEach(date -> {
			List<KrcdtDayOuenTimeSheet> entitisByDate = timeSheets.stream().filter(i -> i.pk.ymd.equals(date))
					.collect(Collectors.toList());
			if (!entitisByDate.isEmpty()) {
				domains.add(toDomain(entitisByDate));
			}
		});
		
		
		return domains;
	}

	@Override
	public List<String> getListEmp(String companyId, List<String> workplaceIds, DatePeriod period) {
		if (CollectionUtil.isEmpty(workplaceIds))
            return new ArrayList<>();
        List<KrcdtDayOuenTimeSheet> ouenTimeSheets = this.queryProxy()
                .query("select o from KrcdtDayOuenTimeSheet o, KrcdtDayAffInfo i where o.pk.sid = i.krcdtDaiAffiliationInfPK.employeeId and o.pk.ymd = i.krcdtDaiAffiliationInfPK.ymd " +
                        "and o.companyId = :companyId and o.pk.ymd >= :startDate and o.pk.ymd <= :endDate and o.workplaceId in :workplaceIds and o.workplaceId != i.workplaceID", KrcdtDayOuenTimeSheet.class)
                .setParameter("companyId", companyId)
                .setParameter("startDate", period.start())
                .setParameter("endDate", period.end())
                .setParameter("workplaceIds", workplaceIds)
                .getList();
        return ouenTimeSheets.stream().map(x -> x.pk.sid).collect(Collectors.toList());
	}
}