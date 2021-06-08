package nts.uk.ctx.at.record.infra.repository.daily.ouen;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.WorkDetailData;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTimeSheet;

@Stateless
public class OuenWorkTimeSheetOfDailyRepoImpl extends JpaRepository implements OuenWorkTimeSheetOfDailyRepo {
	@Override
	public List<OuenWorkTimeSheetOfDaily> find(String empId, GeneralDate ymd) {
		return queryProxy().query("SELECT o FROM KrcdtDayOuenTimeSheet o WHERE o.pk.sid = :sid AND o.ok.ymd = :ymd", 
									KrcdtDayOuenTimeSheet.class)
				.setParameter("sid", empId).setParameter("ymd", ymd)
				.getList(e -> e.domain());
	}

	@Override
	public void update(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			commandProxy().update(e);
		});
	}

	@Override
	public void insert(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			commandProxy().insert(e);
		});
	}

	@Override
	public void delete(List<OuenWorkTimeSheetOfDaily> domain) {
		domain.stream().map(c -> KrcdtDayOuenTimeSheet.convert(c)).forEach(e -> {
			commandProxy().remove(e);
		});
	}

	@Override
	public List<WorkDetailData> GetWorkDetailData(List<String> empIdList, List<String> wkplIdList, DatePeriod period) {
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
				rec.getString("WORK_CD1"),
				rec.getString("WORK_CD2"),
				rec.getString("WORK_CD3"),
				rec.getString("WORK_CD4"),
				rec.getString("WORK_CD5"),
				rec.getInt("TOTAL_TIME"));
	}

}
