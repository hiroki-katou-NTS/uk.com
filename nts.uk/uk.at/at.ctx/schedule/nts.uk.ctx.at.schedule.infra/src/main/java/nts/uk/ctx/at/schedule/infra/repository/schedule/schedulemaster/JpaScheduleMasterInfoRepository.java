package nts.uk.ctx.at.schedule.infra.repository.schedule.schedulemaster;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfoRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster.KscdtScheMasterInfo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster.KscdtScheMasterInfoPK;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class JpaScheduleMasterInfoRepository extends JpaRepository implements ScheMasterInfoRepository {
	
	private static final String GET_ALL_BY_PERIOD = "SELECT c FROM KscdtScheMasterInfo c "
			+ " WHERE c.kscdtScheMasterInfoPk.sId = :sId"
			+ " AND c.kscdtScheMasterInfoPk.generalDate >= :startDate"
			+ " AND c.kscdtScheMasterInfoPk.generalDate <= :endDate";

	@Override
	public Optional<ScheMasterInfo> getScheMasterInfo(String sId, GeneralDate generalDate) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(sId, generalDate);

		return this.queryProxy().find(primaryKey, KscdtScheMasterInfo.class).map(x -> toDomain(x));
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ScheMasterInfo> getScheMasterInfoByPeriod(String sId, DatePeriod period) {
		List<ScheMasterInfo> listScheMasterInfo = new ArrayList<>();
//			listScheMasterInfo.addAll(
//					this.queryProxy().query(GET_ALL_BY_PERIOD, KscdtScheMasterInfo.class)
//						.setParameter("sId", sId)
//						.setParameter("startDate", period.start())
//						.setParameter("endDate", period.end())
//						.getList(x -> toDomain(x)));
		String sql = "select * from KSCDT_SCHE_MASTER h"
				+ " where h.SID = ? "
				+ " and h.YMD >= ?"
				+ " and h.YMD <= ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1 , sId);
			stmt.setDate(2 , Date.valueOf(period.start().localDate()));
			stmt.setDate(3 , Date.valueOf(period.end().localDate()));
			
			listScheMasterInfo = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
				ScheMasterInfo ent = new ScheMasterInfo(
						rec.getString("SID"),
						rec.getGeneralDate("YMD"),
						rec.getString("EMP_CD"),
						rec.getString("CLS_CD"),
						rec.getString("BUSINESS_TYPE_CD"),
						rec.getString("JOB_ID"),
						rec.getString("WKP_ID")
						);
				return ent;
			});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return listScheMasterInfo;
	}
	private static ScheMasterInfo toDomain(KscdtScheMasterInfo entity) {
		if (entity == null) {
			return null;
		}
		
		ScheMasterInfo domain = ScheMasterInfo.createFromJavaType(entity.kscdtScheMasterInfoPk.sId, entity.kscdtScheMasterInfoPk.generalDate,
				entity.employmentCd, entity.classificationCd, entity.businessTypeCd, entity.jobId, entity.workplaceId);
		
		return domain;
	}

	@Override
	public void addScheMasterInfo(ScheMasterInfo scheMasterInfo) {
		this.commandProxy().insert(convertToDbType(scheMasterInfo));
	}

	private KscdtScheMasterInfo convertToDbType(ScheMasterInfo scheMasterInfo) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(scheMasterInfo.getSId(), scheMasterInfo.getGeneralDate());
		
		return new KscdtScheMasterInfo(
				primaryKey,
				scheMasterInfo.getEmploymentCd(),
				scheMasterInfo.getClassificationCd(),
				scheMasterInfo.getBusinessTypeCd(),
				scheMasterInfo.getJobId(),
				scheMasterInfo.getWorkplaceId());
	}

	@Override
	public void updateScheMasterInfo(ScheMasterInfo scheMasterInfo) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(scheMasterInfo.getSId(), scheMasterInfo.getGeneralDate());		
		KscdtScheMasterInfo kscdtScheMasterInfo = this.queryProxy().find(primaryKey, KscdtScheMasterInfo.class).get();
		
		kscdtScheMasterInfo.employmentCd = scheMasterInfo.getEmploymentCd();
		kscdtScheMasterInfo.classificationCd = scheMasterInfo.getClassificationCd();
		kscdtScheMasterInfo.businessTypeCd = scheMasterInfo.getBusinessTypeCd();
		kscdtScheMasterInfo.jobId = scheMasterInfo.getJobId();
		kscdtScheMasterInfo.workplaceId = scheMasterInfo.getWorkplaceId();
		
		this.commandProxy().update(kscdtScheMasterInfo);
	}

	@Override
	public void deleteScheMasterInfo(String sId, GeneralDate generalDate) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(sId, generalDate);		
		this.commandProxy().remove(KscdtScheMasterInfo.class, primaryKey);
	}

}
