package nts.uk.screen.at.infra.correctionofdailyperformance.errorreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecordPK;
import nts.uk.ctx.at.shared.infra.entity.attendance.KmnmtAttendanceItem;
import nts.uk.ctx.at.shared.infra.entity.attendance.KmnmtAttendanceItemPK;
import nts.uk.ctx.at.shared.infra.entity.correctionofdailyperformance.errorreference.KrcdtEDPErrorList;
import nts.uk.screen.at.app.correctionofdailyperformance.errorreference.ErrorReferenceDto;
import nts.uk.screen.at.app.correctionofdailyperformance.errorreference.ErrorReferenceParams;
import nts.uk.screen.at.app.correctionofdailyperformance.errorreference.ErrorReferenceRepository;

@Stateless
public class JpaEDPErrorListRepository extends JpaRepository implements ErrorReferenceRepository {

	private static final String SELECT_EDPEL_BY_STARTDATE_ENDDATE_EMPLIST = "SELECT c FROM KrcdtEDPErrorList c WHERE  c.KrcdtEDPErrorListPK.resolveErrorDate >= :startDate AND c.KrcdtEDPErrorListPK.resolveErrorDate <= :endDate AND c.KrcdtEDPErrorListPK.employeeEmergency IN (:lstEmployeeId)";

	@Override
	public List<ErrorReferenceDto> getErrorReferences(ErrorReferenceParams params, String companyId) {
		List<ErrorReferenceDto> lstErrorReferenceDto = new ArrayList<ErrorReferenceDto>();

		List<KrcdtEDPErrorList> lstKrcdtEDPErrorList = this.queryProxy()
				.query(SELECT_EDPEL_BY_STARTDATE_ENDDATE_EMPLIST, KrcdtEDPErrorList.class)
				.setParameter("startDate", params.startDate).setParameter("endDate", params.enddate)
				.setParameter("lstEmployeeId", params.lstEmployeeId).getList();
		lstKrcdtEDPErrorList.forEach(e -> {
			Optional<KwrmtErAlWorkRecord> kwrmtErAlWorkRecordOptional = this.queryProxy()
					.find(new KwrmtErAlWorkRecordPK(companyId, e.krcdtEDPErrorListPK.error), KwrmtErAlWorkRecord.class);
			String messageDisplay = "";
			if (kwrmtErAlWorkRecordOptional.isPresent()) {
				messageDisplay = kwrmtErAlWorkRecordOptional.get().messageDisplay;
			}
			String itemName = "";
			Optional<KmnmtAttendanceItem> kmnmtAttendanceItemOptional = this.queryProxy()
					.find(new KmnmtAttendanceItemPK(companyId, e.timeItemId.intValue()), KmnmtAttendanceItem.class);
			if (kmnmtAttendanceItemOptional.isPresent()) {
				itemName = kmnmtAttendanceItemOptional.get().attendanceItemName;
			}
			// todo get employeeCode,
			ErrorReferenceDto errorReferenceDto = new ErrorReferenceDto(IdentifierUtil.randomUniqueId(), "employeeCode",
					"employeeName", e.krcdtEDPErrorListPK.resolveErrorDate, e.krcdtEDPErrorListPK.error, messageDisplay,
					itemName);
			lstErrorReferenceDto.add(errorReferenceDto);
		});
		return lstErrorReferenceDto;
	}

}
