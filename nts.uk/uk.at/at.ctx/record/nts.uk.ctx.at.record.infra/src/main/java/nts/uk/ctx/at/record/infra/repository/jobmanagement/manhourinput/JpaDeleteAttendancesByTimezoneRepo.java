package nts.uk.ctx.at.record.infra.repository.jobmanagement.manhourinput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourinput.AttendanceByTimezoneDeletion;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourinput.DeleteAttendancesByTimezone;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourinput.DeleteAttendancesByTimezoneRepo;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.manhourinput.KrcdtDaySupDelete;

@Stateless
public class JpaDeleteAttendancesByTimezoneRepo extends JpaRepository implements DeleteAttendancesByTimezoneRepo {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT d FROM KrcdtDaySupDelete d";
	private static final String SELECT_BY_SID = SELECT_ALL_QUERY_STRING + " WHERE d.pk.sId = :sId";
	private static final String SELECT_BY_SID_AND_YMD = SELECT_BY_SID + " AND d.pk.ymd IN :ymdList";

	@Override
	public void register(DeleteAttendancesByTimezone deleteAttendancese) {
		if (deleteAttendancese.getAttendanceDeletionLst().isEmpty()) {
			return;
		}

		for (AttendanceByTimezoneDeletion deletion : deleteAttendancese.getAttendanceDeletionLst()) {
			this.commandProxy().insert(
					KrcdtDaySupDelete.toEntity(deleteAttendancese.getSId(), deleteAttendancese.getYmd(), deletion));
		}
	}

	@Override
	public List<DeleteAttendancesByTimezone> get(String sId, List<GeneralDate> ymdList) {
		List<DeleteAttendancesByTimezone> deletedAttLst = new ArrayList<>();

		List<KrcdtDaySupDelete> entities = new ArrayList<>();
		CollectionUtil.split(ymdList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subYmdList -> {
			entities.addAll(this.queryProxy().query(SELECT_BY_SID_AND_YMD, KrcdtDaySupDelete.class)
					.setParameter("sId", sId).setParameter("ymdList", subYmdList).getList());
		});

		if (entities.isEmpty()) {
			return deletedAttLst;
		}

		for (KrcdtDaySupDelete entity : entities) {

			if (deletedAttLst.isEmpty()) {
				deletedAttLst.add(new DeleteAttendancesByTimezone(entity.pk.sId, entity.pk.ymd, new ArrayList<>()));

			} else {

				for (DeleteAttendancesByTimezone del : deletedAttLst) {
					if (entity.pk.sId.equals(del.getSId()) && entity.pk.ymd.equals(del.getYmd())) {
						del.getAttendanceDeletionLst().add(entity.toDomain());
						
					} else {
						deletedAttLst.add(new DeleteAttendancesByTimezone(entity.pk.sId, entity.pk.ymd,
								Arrays.asList(entity.toDomain())));
					}
				}

			}

		}

		return deletedAttLst;
	}

	@Override
	public void deleteDays(String sId, List<GeneralDate> ymdList) {
		List<KrcdtDaySupDelete> entities = new ArrayList<>();
		CollectionUtil.split(ymdList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subYmdList -> {
			entities.addAll(this.queryProxy().query(SELECT_BY_SID_AND_YMD, KrcdtDaySupDelete.class)
					.setParameter("sId", sId).setParameter("ymdList", subYmdList).getList());
		});

		if (entities.isEmpty()) {
			return;
		}

		for (KrcdtDaySupDelete entity : entities) {
			this.commandProxy().remove(entity);
		}
	}

}
