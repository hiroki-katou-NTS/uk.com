package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtOvertimeInput;

@Stateless
public class OvertimeInputImpl extends JpaRepository implements OvertimeInputRepository {
	private static final String FIND_ALL ="SELECT e FROM KrqdtOvertimeInput e";
	private static final String FIND_BY_APPID;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE e.krqdtOvertimeInputPK.cid = :companyID");
		query.append(" AND e.krqdtOvertimeInputPK.appId = :appID");
		FIND_BY_APPID = query.toString();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository#getOvertimeInput(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OverTimeInput> getOvertimeInput(String companyID, String appID) {
		
		return this.queryProxy().query(FIND_BY_APPID, KrqdtOvertimeInput.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID).getList(e -> convertToDomain(e));
	}
	private OverTimeInput convertToDomain(KrqdtOvertimeInput entity){
		return OverTimeInput.createSimpleFromJavaType(entity.getKrqdtOvertimeInputPK().getCid(),
				entity.getKrqdtOvertimeInputPK().getAppId(),
				entity.getKrqdtOvertimeInputPK().getAttendanceId(),
				entity.getKrqdtOvertimeInputPK().getFrameNo(),
				entity.getStartTime(),
				entity.getEndTime(),
				entity.getApplicationTime());
	}

}
