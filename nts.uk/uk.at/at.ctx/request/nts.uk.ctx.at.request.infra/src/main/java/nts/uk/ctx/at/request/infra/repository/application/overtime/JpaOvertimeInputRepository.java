package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtOvertimeInput_Old;

@Stateless
public class JpaOvertimeInputRepository extends JpaRepository implements OvertimeInputRepository {
	private static final String FIND_ALL ="SELECT e FROM KrqdtOvertimeInput e";
	private static final String FIND_BY_APPID;
	private static final String FIND_BY_APPID_AND_ATTENDANCEID;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE e.krqdtOvertimeInputPK.cid = :companyID");
		query.append(" AND e.krqdtOvertimeInputPK.appId = :appID");
		FIND_BY_APPID = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_BY_APPID);
		query.append(" AND e.krqdtOvertimeInputPK.attendanceId = :attendanceId");
		FIND_BY_APPID_AND_ATTENDANCEID = query.toString();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository#getOvertimeInput(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OverTimeInput> getOvertimeInput(String companyID, String appID) {
		
		return this.queryProxy().query(FIND_BY_APPID, KrqdtOvertimeInput_Old.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID).getList(e -> convertToDomain(e));
	}
	
	@Override
	public List<OverTimeInput> getOvertimeInputByAttendanceId(String companyID, String appID, int attendanceId) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(FIND_BY_APPID_AND_ATTENDANCEID,KrqdtOvertimeInput_Old.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID)
				.setParameter("attendanceId", attendanceId).getList(e -> convertToDomain(e));
	}
	
	private OverTimeInput convertToDomain(KrqdtOvertimeInput_Old entity){
		return OverTimeInput.createSimpleFromJavaType(entity.getKrqdtOvertimeInputPK().getCid(),
				entity.getKrqdtOvertimeInputPK().getAppId(),
				entity.getKrqdtOvertimeInputPK().getAttendanceId(),
				entity.getKrqdtOvertimeInputPK().getFrameNo(),
				entity.getStartTime(),
				entity.getEndTime(),
				entity.getApplicationTime(),
				entity.getKrqdtOvertimeInputPK().getTimeItemTypeAtr());
	}

	

}
