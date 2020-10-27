package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInputRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtAppHdWorkTime;

@Stateless
public class JpaHolidayWorkInputRepository extends JpaRepository implements HolidayWorkInputRepository{
	private static final String FIND_ALL ="SELECT e FROM KrqdtAppHdWorkTime e";
	private static final String FIND_BY_APPID;
	private static final String FIND_BY_APPID_AND_ATTENDANCEID;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FIND_ALL);
		query.append(" WHERE e.krqdtAppHdWorkTimePK.cid = :companyID");
		query.append(" AND e.krqdtAppHdWorkTimePK.appId = :appID");
		FIND_BY_APPID = query.toString();
		
		query = new StringBuilder();
		query.append(FIND_BY_APPID);
		query.append(" AND e.krqdtAppHdWorkTimePK.attendanceType = :attendanceType");
		FIND_BY_APPID_AND_ATTENDANCEID = query.toString();
	}
	@Override
	public List<HolidayWorkInput> getHolidayWorkInputByAttendanceType(String companyID, String appID, int attendanceType) {
			return this.queryProxy().query(FIND_BY_APPID_AND_ATTENDANCEID,KrqdtAppHdWorkTime.class)
					.setParameter("companyID", companyID)
					.setParameter("appID", appID)
					.setParameter("attendanceType", attendanceType).getList(e -> convertToDomain(e));
	}
	private HolidayWorkInput convertToDomain(KrqdtAppHdWorkTime entity){
		return HolidayWorkInput.createSimpleFromJavaType(entity.getKrqdtAppHdWorkTimePK().getCid(),
				entity.getKrqdtAppHdWorkTimePK().getAppId(),
				entity.getKrqdtAppHdWorkTimePK().getAttendanceType(),
				entity.getKrqdtAppHdWorkTimePK().getFrameNo(),
				entity.getStartTime(),
				entity.getEndTime(),
				entity.getApplicationTime());
	}
	@Override
	public List<HolidayWorkInput> getHolidayWorkInputByAppID(String companyID, String appID) {
		return this.queryProxy().query(FIND_BY_APPID,KrqdtAppHdWorkTime.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID).getList(e -> convertToDomain(e));
	}
}
