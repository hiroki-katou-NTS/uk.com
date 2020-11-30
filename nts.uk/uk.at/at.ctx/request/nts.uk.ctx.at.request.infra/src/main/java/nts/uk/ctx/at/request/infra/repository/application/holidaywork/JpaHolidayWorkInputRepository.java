package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInputRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtHolidayWorkInput_Old;

@Stateless
public class JpaHolidayWorkInputRepository extends JpaRepository implements HolidayWorkInputRepository{
	private static final String FIND_ALL ="SELECT e FROM KrqdtHolidayWorkInput e";
	private static final String FIND_BY_APPID;
	private static final String FIND_BY_APPID_AND_ATTENDANCEID;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE e.krqdtHolidayWorkInputPK.cid = :companyID");
		query.append(" AND e.krqdtHolidayWorkInputPK.appId = :appID");
		FIND_BY_APPID = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_BY_APPID);
		query.append(" AND e.krqdtHolidayWorkInputPK.attendanceType = :attendanceType");
		FIND_BY_APPID_AND_ATTENDANCEID = query.toString();
	}
	@Override
	public List<HolidayWorkInput> getHolidayWorkInputByAttendanceType(String companyID, String appID, int attendanceType) {
			return this.queryProxy().query(FIND_BY_APPID_AND_ATTENDANCEID,KrqdtHolidayWorkInput_Old.class)
					.setParameter("companyID", companyID)
					.setParameter("appID", appID)
					.setParameter("attendanceType", attendanceType).getList(e -> convertToDomain(e));
	}
	private HolidayWorkInput convertToDomain(KrqdtHolidayWorkInput_Old entity){
		return HolidayWorkInput.createSimpleFromJavaType(entity.getKrqdtHolidayWorkInputPK().getCid(),
				entity.getKrqdtHolidayWorkInputPK().getAppId(),
				entity.getKrqdtHolidayWorkInputPK().getAttendanceType(),
				entity.getKrqdtHolidayWorkInputPK().getFrameNo(),
				entity.getStartTime(),
				entity.getEndTime(),
				entity.getApplicationTime());
	}
	@Override
	public List<HolidayWorkInput> getHolidayWorkInputByAppID(String companyID, String appID) {
		return this.queryProxy().query(FIND_BY_APPID,KrqdtHolidayWorkInput_Old.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID).getList(e -> convertToDomain(e));
	}
}
