package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItemPK_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem_;
import nts.uk.shr.com.context.AppContexts;

public class JpaSingleAttendanceRecordRepository extends JpaRepository implements SingleAttendanceRecordRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository#getSingleAttendanceRecord(java.lang.String, nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode, long, long, long)
	 */
	@Override
	public Optional<SingleAttendanceRecord> getSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode,
			long columnIndex, long position, long exportArt) {
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId,exportSettingCode.v(), columnIndex,exportArt, position);
		return this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class).map(e -> this.toDomain(e));
	}

	@Override
	public void addSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position, SingleAttendanceRecord singleAttendanceRecord) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position, SingleAttendanceRecord singleAttendanceRecord) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position, SingleAttendanceRecord singleAttendanceRecord) {
		// TODO Auto-generated method stub

	}

	/**
	 * To domain.
	 *
	 * @param kfnstAttndRec the kfnst attnd rec
	 * @return the single attendance record
	 */
	private SingleAttendanceRecord toDomain(KfnstAttndRec kfnstAttndRec) {
		//get KfnstAttndRecItem by KfnstAttndRecPK
		List<KfnstAttndRecItem> listKfnstAttndRecItem = this.findAttendanceRecordItem(
																AppContexts.user().companyId(),
																new ExportSettingCode(kfnstAttndRec.getId().getExportCd()),
																kfnstAttndRec.getId().getColumnIndex(),
																kfnstAttndRec.getId().getPosition(),
																kfnstAttndRec.getId().getOutputAtr());
		//check value
		KfnstAttndRecItem kfnstAttndRecItem = listKfnstAttndRecItem.isEmpty()? new KfnstAttndRecItem() : listKfnstAttndRecItem.get(0);
		//create getMemento
		SingleAttendanceRecordGetMemento  getMemento = new JpaSingleAttendanceRecordGetMemento(kfnstAttndRec, kfnstAttndRecItem);
		return new SingleAttendanceRecord(getMemento);
	
	}

	/**
	 * Find attendance record item.
	 *
	 * @param companyId the company id
	 * @param exportSettingCode the export setting code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param exportArt the export art
	 * @return the list
	 */
	public List<KfnstAttndRecItem> findAttendanceRecordItem(String companyId, ExportSettingCode exportSettingCode,
			long columnIndex, long position, long exportArt) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRecItem> criteriaQuery = criteriaBuilder.createQuery(KfnstAttndRecItem.class);
		Root<KfnstAttndRecItem> root = criteriaQuery.from(KfnstAttndRecItem.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.id).get(KfnstAttndRecItemPK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.id).get(KfnstAttndRecItemPK_.exportCd), exportSettingCode));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.id).get(KfnstAttndRecItemPK_.columnIndex), columnIndex));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.id).get(KfnstAttndRecItemPK_.position), position));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.id).get(KfnstAttndRecItemPK_.outputAtr), exportArt));
		

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRecItem> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		return kfnstAttndRecItems.isEmpty() ? new ArrayList<KfnstAttndRecItem>() : kfnstAttndRecItems;
	}

}
