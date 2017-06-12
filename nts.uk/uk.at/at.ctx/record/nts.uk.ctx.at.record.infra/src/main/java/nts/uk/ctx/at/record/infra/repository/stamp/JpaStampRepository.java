package nts.uk.ctx.at.record.infra.repository.stamp;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.StampItem;
import nts.uk.ctx.at.record.dom.stamp.StampRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.KwkdtStamp;

@Stateless
public class JpaStampRepository extends JpaRepository implements StampRepository {
	private final String SELECT_NO_WHERE = "SELECT d.workLocationName, c FROM KwkdtStamp c ";
	private final String SELECT_BY_EMPPLOYEE_CODE = SELECT_NO_WHERE
			+ " JOIN KwlmtWorkLocation d ON c.workLocationCd = d.kwlmtWorkLocationPK.workLocationCD"
			+ " WHERE c.kwkdtStampPK.companyId = :companyId" 
			+ " AND c.kwkdtStampPK.date > :startDate"
			+ " AND c.kwkdtStampPK.date < :endDate" 
			+ " AND c.kwkdtStampPK.cardNumber IN :lstCardNumber";

	private static StampItem toDomain(Object[] object) {
		String workLocationName = (String) object[0];
		KwkdtStamp entity = (KwkdtStamp) object[1];
		StampItem domain = StampItem.createFromJavaType(entity.kwkdtStampPK.companyId, 
				entity.kwkdtStampPK.cardNumber,
				entity.kwkdtStampPK.attendanceTime, 
				entity.stampCombinationAtr, 
				entity.workTimeCd, 
				entity.stampMethod,
				entity.kwkdtStampPK.stampAtr, 
				entity.workLocationCd, 
				workLocationName, 
				entity.stampReason,
				entity.kwkdtStampPK.date);
		return domain;
	}

	@Override
	public List<StampItem> findByEmployeeCode(String companyId, List<String> lstCardNumber, String startDate, String endDate) {
		List<StampItem> list = this.queryProxy().query(SELECT_BY_EMPPLOYEE_CODE, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("startDate", GeneralDate.fromString(startDate, "yyyyMMdd"))
				.setParameter("endDate", GeneralDate.fromString(endDate, "yyyyMMdd"))
				.setParameter("lstCardNumber", lstCardNumber)
				.getList(c -> toDomain(c));
		return list;
	}
	
	@Override
	public List<StampItem> findByDate(String companyId, String cardNumber, String startDate, String endDate) {
		// TODO Auto-generated method stubs
		return null;
	}

}
