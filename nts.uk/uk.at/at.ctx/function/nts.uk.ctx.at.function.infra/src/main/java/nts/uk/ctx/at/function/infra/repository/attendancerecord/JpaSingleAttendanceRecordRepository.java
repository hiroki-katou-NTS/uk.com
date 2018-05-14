package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;

public class JpaSingleAttendanceRecordRepository extends JpaRepository implements SingleAttendanceRecordRepository {

	@Override
	public SingleAttendanceRecord getSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode,
			int columnIndex, int position, int exportArt) {
		// TODO Auto-generated method stub
		KfnstAttndRecPK pk = new KfnstAttndRecPK(companyId, Long.parseLong(exportSettingCode.toString()), columnIndex,
				exportArt, position);
		return null;
	}

	@Override
	public void addSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, int columnIndex,
			int position, SingleAttendanceRecord singleAttendanceRecord) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, int columnIndex,
			int position, SingleAttendanceRecord singleAttendanceRecord) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, int columnIndex,
			int position, SingleAttendanceRecord singleAttendanceRecord) {
		// TODO Auto-generated method stub

	}

	private SingleAttendanceRecord toDomain(KfnstAttndRec kfnstAttndRec) {

		// List<KrcstDvgcAttendance> entityAttendance =
		// this.findAttendance(entityDvgcTime.getId().getCid(),
		// entityDvgcTime.getId().getNo());
		//
		// DivergenceTimeGetMemento memento = new
		// JpaDivergenceTimeGetMemento(entityDvgcTime, entityAttendance);
		//
		// return new DivergenceTime(memento);
		return null;
	}

	public List<KfnstAttndRecItem> findAttendanceRecordItem(String companyId, ExportSettingCode exportSettingCode,
			int columnIndex, int position, SingleAttendanceRecord singleAttendanceRecord) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRecItem> criteriaQuery = criteriaBuilder.createQuery(KfnstAttndRecItem.class);
		Root<KfnstAttndRecItem> root = criteriaQuery.from(KfnstAttndRecItem.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
		// predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem), y))

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRecItem> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		return kfnstAttndRecItems.isEmpty() ? new ArrayList<KfnstAttndRecItem>() : kfnstAttndRecItems;
	}

}
