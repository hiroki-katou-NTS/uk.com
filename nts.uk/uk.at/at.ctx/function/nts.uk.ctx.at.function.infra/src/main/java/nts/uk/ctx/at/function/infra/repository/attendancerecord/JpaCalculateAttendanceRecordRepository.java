package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItemPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItemPK_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem_;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaCalculateAttendanceRecordRepository extends JpaRepository
		implements CalculateAttendanceRecordRepositoty {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * CalculateAttendanceRecordRepositoty#getCalculateAttendanceRecord(java.lang.
	 * String,
	 * nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode,
	 * long, long, long)
	 */
	@Override
	public Optional<CalculateAttendanceRecord> getCalculateAttendanceRecord(String companyId,
			ExportSettingCode exportSettingCode, long columnIndex, long position, long exportArt) {
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, Long.valueOf(exportSettingCode.v()), columnIndex, exportArt,
				position);
		return this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class).map(e -> this.toDomain(e));
	}

	@Override
	public void addCalculateAttendanceRecord(String CompanyId, ExportSettingCode code, int columnIndex, int position,
			long exportArt, CalculateAttendanceRecord calculateAttendanceRecord) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCalculateAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, int columnIndex,
			int position, long exportArt, boolean useAtr, CalculateAttendanceRecord calculateAttendanceRecord) {
		// update attendanceRecord
		this.commandProxy().update(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt, useAtr,
				calculateAttendanceRecord));
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, Long.valueOf(exportSettingCode.v()), columnIndex, exportArt,
				position);

		Optional<KfnstAttndRec> kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class);
		if (kfnstAttndRec.isPresent()) {
			this.commandProxy().update(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt,
					useAtr, calculateAttendanceRecord));
		} else {
			this.commandProxy().insert(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt,
					useAtr, calculateAttendanceRecord));
		}
		//get listItemAdded, listItemSubtracted
		List<KfnstAttndRecItem> listKfnstAttndRecItemAdded = calculateAttendanceRecord.getAddedItem().stream().map(e -> 
		toEntityAttndRecItemAdded(exportSettingCode, columnIndex, position, exportArt, e.intValue())).collect(Collectors.toList());
		
		List<KfnstAttndRecItem> listKfnstAttndRecItemSubtracted = calculateAttendanceRecord.getAddedItem().stream().map(e -> 
		toEntityAttndRecItemSubtracted(exportSettingCode, columnIndex, position, exportArt, e.intValue())).collect(Collectors.toList());
		
		List<KfnstAttndRecItem> kfnstAttndRecItems = new ArrayList<KfnstAttndRecItem>();
		kfnstAttndRecItems.addAll(listKfnstAttndRecItemAdded);
		kfnstAttndRecItems.addAll(listKfnstAttndRecItemSubtracted);
		
		// check and update attendanceRecordItems
		List<KfnstAttndRecItem> kfnstAttndRecItemsOld = this.findAttendanceRecordItems(companyId, exportSettingCode,
				columnIndex, position, exportArt);
		if (kfnstAttndRecItemsOld != null && !kfnstAttndRecItemsOld.isEmpty()) {
			this.removeAllAttndRecItem(kfnstAttndRecItemsOld);
		} 
		this.commandProxy().insertAll(kfnstAttndRecItems);
		
		this.getEntityManager().flush();

	}

	@Override
	public void deleteCalculateAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, int columnIndex,
		int position, long exportArt, CalculateAttendanceRecord calculateAttendanceRecord) {
		//find and delete KfnstAttndRec, KfnstAttndRecItem
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, Long.valueOf(exportSettingCode.v()), columnIndex, exportArt, position);
		Optional<KfnstAttndRec> optionalKfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class);
		if(optionalKfnstAttndRec.isPresent()) this.commandProxy().remove(optionalKfnstAttndRec.get());

		//find and delete KfnstAttndRecItem
		List<KfnstAttndRecItem> kfnstAttndRecItems = this.findAttendanceRecordItems(companyId, exportSettingCode, columnIndex, position, exportArt);
		if(kfnstAttndRecItems!=null && !kfnstAttndRecItems.isEmpty()) {
			this.commandProxy().removeAll(kfnstAttndRecItems);
		}
		this.getEntityManager().flush();
	}

	/**
	 * To domain.
	 *
	 * @param kfnstAttndRec
	 *            the kfnst attnd rec
	 * @return the calculate attendance record
	 */
	private CalculateAttendanceRecord toDomain(KfnstAttndRec kfnstAttndRec) {
		// get KfnstAttndRecItem by KfnstAttndRecPK
		List<KfnstAttndRecItem> listKfnstAttndRecItem = this.findAttendanceRecordItems(AppContexts.user().companyId(),
				new ExportSettingCode((kfnstAttndRec.getId().getExportCd())), kfnstAttndRec.getId().getColumnIndex(),
				kfnstAttndRec.getId().getPosition(), kfnstAttndRec.getId().getOutputAtr());

		// create getMemento
		CalculateAttendanceRecordGetMemento getMemento = new JpaCalculateAttendanceRecordGetMemento(kfnstAttndRec,
				listKfnstAttndRecItem);

		return new CalculateAttendanceRecord(getMemento);

	}

	/**
	 * To entity attnd rec.
	 *
	 * @param exportSettingCode
	 *            the export setting code
	 * @param columnIndex
	 *            the column index
	 * @param position
	 *            the position
	 * @param exportArt
	 *            the export art
	 * @param useAtr
	 *            the use atr
	 * @param calculateAttendanceRecord
	 *            the calculate attendance record
	 * @return the kfnst attnd rec
	 */
	private KfnstAttndRec toEntityAttndRec(ExportSettingCode exportSettingCode, long columnIndex, long position,
			long exportArt, boolean useAtr, CalculateAttendanceRecord calculateAttendanceRecord) {
		// find entity KfnstAttndRec by pk
		String companyId = AppContexts.user().companyId();
		KfnstAttndRecPK kfnstAttndRecPk = new KfnstAttndRecPK(companyId, Long.valueOf(exportSettingCode.v()), columnIndex, exportArt,
				position);
		KfnstAttndRec kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPk, KfnstAttndRec.class)
				.orElse(new KfnstAttndRec());

//		// find entity KfnstAttndRecItem by pk
//		List<KfnstAttndRecItem> kfnstAttndRecItems = this.findAttendanceRecordItems(companyId, exportSettingCode,
//				columnIndex, position, exportArt);

		calculateAttendanceRecord
				.saveToMemento(new JpaCalculateAttendanceRecordSetMemento(kfnstAttndRec));
		int useAtrValue = useAtr ? 1 : 0;
		kfnstAttndRec.setUseAtr(new BigDecimal(useAtrValue));
		return kfnstAttndRec;

	}

	/**
	 * To entity attnd rec item.
	 *
	 * @param exportSettingCode
	 *            the export setting code
	 * @param columnIndex
	 *            the column index
	 * @param position
	 *            the position
	 * @param exportArt
	 *            the export art
	 * @param formulaType
	 *            the formula type
	 * @param timeItemId
	 *            the time item id
	 * @return the kfnst attnd rec item
	 */
	private KfnstAttndRecItem toEntityAttndRecItemSubtracted(ExportSettingCode exportSettingCode, long columnIndex, long position,
			long exportArt, int timeItemId) {
		KfnstAttndRecItemPK kfnstAttendRecItemPK = new KfnstAttndRecItemPK(AppContexts.user().companyId(),
				Long.valueOf(exportSettingCode.v()), columnIndex, position, exportArt, timeItemId);
		KfnstAttndRecItem kfnstAttendRecItem = new KfnstAttndRecItem(kfnstAttendRecItemPK, new BigDecimal(2));
		return kfnstAttendRecItem;
	}
	
	private KfnstAttndRecItem toEntityAttndRecItemAdded(ExportSettingCode exportSettingCode, long columnIndex, long position,
			long exportArt, int timeItemId) {
		KfnstAttndRecItemPK kfnstAttendRecItemPK = new KfnstAttndRecItemPK(AppContexts.user().companyId(),
				Long.valueOf(exportSettingCode.v()), columnIndex, position, exportArt, timeItemId);
		KfnstAttndRecItem kfnstAttendRecItem = new KfnstAttndRecItem(kfnstAttendRecItemPK, new BigDecimal(1));
		return kfnstAttendRecItem;
	}

	/**
	 * Find attendance record items.
	 *
	 * @param companyId
	 *            the company id
	 * @param exportSettingCode
	 *            the export setting code
	 * @param columnIndex
	 *            the column index
	 * @param position
	 *            the position
	 * @param exportArt
	 *            the export art
	 * @return the list
	 */
	public List<KfnstAttndRecItem> findAttendanceRecordItems(String companyId, ExportSettingCode exportSettingCode,
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
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.id).get(KfnstAttndRecItemPK_.exportCd),
				exportSettingCode.v()));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.id).get(KfnstAttndRecItemPK_.columnIndex),
				columnIndex));
		predicates.add(
				criteriaBuilder.equal(root.get(KfnstAttndRecItem_.id).get(KfnstAttndRecItemPK_.position), position));
		predicates.add(
				criteriaBuilder.equal(root.get(KfnstAttndRecItem_.id).get(KfnstAttndRecItemPK_.outputAtr), exportArt));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRecItem> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		return kfnstAttndRecItems.isEmpty() ? new ArrayList<KfnstAttndRecItem>() : kfnstAttndRecItems;
	}

	/**
	 * Removes the all attnd rec item.
	 *
	 * @param listKfnstAttndRecItem
	 *            the list kfnst attnd rec item
	 */
	public void removeAllAttndRecItem(List<KfnstAttndRecItem> listKfnstAttndRecItem) {
		if (!listKfnstAttndRecItem.isEmpty() && listKfnstAttndRecItem != null) {
			this.commandProxy().removeAll(listKfnstAttndRecItem);
			this.getEntityManager().flush();
		}

	}

}
