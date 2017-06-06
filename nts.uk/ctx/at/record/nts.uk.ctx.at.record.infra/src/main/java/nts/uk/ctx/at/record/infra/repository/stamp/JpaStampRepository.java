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
			+ " WHERE c.kwkdtStampPK.companyId = :companyId" + " AND c.kwkdtStampPK.cardNumber = :cardNumber"
			+ " AND c.kwkdtStampPK.date > :startDate" + " AND c.kwkdtStampPK.date < :endDate";

	private static StampItem toDomain(Object[] object) {
		String workLocationName = (String) object[0];
		KwkdtStamp entity = (KwkdtStamp) object[1];
		StampItem domain = StampItem.createFromJavaType(
				entity.kwkdtStampPK.companyId, 
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

	// private static KwkdtStamp toEntity(StampItem domain) {
	// val entity = new KwkdtStamp();
	// entity.kwkdtStampPK = new KwkdtStampPK();
	// entity.kwkdtStampPK.companyId = domain.getCompanyId();
	// entity.kwkdtStampPK.attendanceTime = domain.getAttendanceTime().v();
	// entity.kwkdtStampPK.cardNumber = domain.getCardNumber().v();
	// entity.kwkdtStampPK.date = domain.getDate();
	// entity.kwkdtStampPK.stampAtr = domain.getStampAtr().value;
	// entity.stampCombinationAtr = domain.getStampCombinationAtr().value;
	// entity.stampMethod = domain.getStampMethod().value;
	// entity.stampReason = domain.getStampReason().value;
	// entity.workLocationCd = domain.getWorkLocationCd().v();
	// entity.kwlmtWorkLocation.workLocationName =
	// domain.getWorkLocationName().v();
	// entity.workTimeCd = domain.getWorkTimeCd().v();
	// return entity;
	// }

	@Override
	public List<StampItem> findByEmployeeCode(String companyId, String cardNumber, String startDate, String endDate) {
		return this.queryProxy().query(SELECT_BY_EMPPLOYEE_CODE, Object[].class).setParameter("companyId", companyId)
				.setParameter("cardNumber", cardNumber)
				.setParameter("startDate", GeneralDate.fromString(startDate, "yyyyMMdd"))
				.setParameter("endDate", GeneralDate.fromString(endDate, "yyyyMMdd"))
				.getList(c -> toDomain(c));
	}

	@Override
	public List<StampItem> findByDate(String companyId, String cardNumber, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
