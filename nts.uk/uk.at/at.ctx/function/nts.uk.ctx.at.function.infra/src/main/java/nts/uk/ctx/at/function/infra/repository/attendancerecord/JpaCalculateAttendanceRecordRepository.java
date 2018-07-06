package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;
import java.rmi.server.UID;
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

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author tuannt-nws
 *
 */
@Stateless
public class JpaCalculateAttendanceRecordRepository extends JpaAttendanceRecordRepository
		implements CalculateAttendanceRecordRepositoty {

	/** The add formula type. */
	private static final int ADD_FORMULA_TYPE = 1;

	/** The subtract formula type. */
	private static final int SUBTRACT_FORMULA_TYPE = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * CalculateAttendanceRecordRepositoty#getCalculateAttendanceRecord(java.
	 * lang. String, nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * ExportSettingCode, long, long, long)
	 */
	@Override
	public Optional<CalculateAttendanceRecord> getCalculateAttendanceRecord(String companyId,
			ExportSettingCode exportSettingCode, long columnIndex, long position, long exportArt) {
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt,
				position);
		return this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class).map(e -> this.toDomain(e));
	}

	@Override
	public void addCalculateAttendanceRecord(String CompanyId, ExportSettingCode code, int columnIndex, int position,
			long exportArt, CalculateAttendanceRecord calculateAttendanceRecord) {
	}

	@Override
	public void updateCalculateAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, int columnIndex,
			int position, long exportArt, boolean useAtr, CalculateAttendanceRecord calculateAttendanceRecord) {

		// check and update AttendanceRecord
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt,
				position);
		Optional<KfnstAttndRec> kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class);
		if (kfnstAttndRec.isPresent()) {
			this.commandProxy().update(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt,
					useAtr, calculateAttendanceRecord));
		} else {
			this.commandProxy().insert(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt,
					useAtr, calculateAttendanceRecord));
		}

		// get listItemAdded, listItemSubtracted
		List<KfnstAttndRecItem> listKfnstAttndRecItemAdded = calculateAttendanceRecord.getAddedItem().stream()
				.map(e -> toEntityAttndRecItemAdded(exportSettingCode, columnIndex, position, exportArt, e))
				.collect(Collectors.toList());

		List<KfnstAttndRecItem> listKfnstAttndRecItemSubtracted = calculateAttendanceRecord.getSubtractedItem().stream()
				.map(e -> toEntityAttndRecItemSubtracted(exportSettingCode, columnIndex, position, exportArt, e))
				.collect(Collectors.toList());

		List<KfnstAttndRecItem> kfnstAttndRecItems = new ArrayList<KfnstAttndRecItem>();
		kfnstAttndRecItems.addAll(listKfnstAttndRecItemAdded);
		kfnstAttndRecItems.addAll(listKfnstAttndRecItemSubtracted);

		// check and update attendanceRecordItems
		List<KfnstAttndRecItem> kfnstAttndRecItemsOld = this.findAttendanceRecordItems(kfnstAttndRecPK);
		if (kfnstAttndRecItemsOld != null) {
			this.removeAllAttndRecItem(kfnstAttndRecItemsOld);
		}
		this.commandProxy().insertAll(kfnstAttndRecItems);

		this.getEntityManager().flush();

	}

	@Override
	public void deleteCalculateAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, int columnIndex,
			int position, long exportArt, CalculateAttendanceRecord calculateAttendanceRecord) {
		// find and delete KfnstAttndRec, KfnstAttndRecItem
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, Long.valueOf(exportSettingCode.v()),
				columnIndex, exportArt, position);
		Optional<KfnstAttndRec> optionalKfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class);
		optionalKfnstAttndRec.ifPresent(kfnstAttndRec -> this.commandProxy().remove(kfnstAttndRec));

		// find and delete KfnstAttndRecItem
		List<KfnstAttndRecItem> kfnstAttndRecItems = this.findAttendanceRecordItems(kfnstAttndRecPK);
		if (kfnstAttndRecItems != null && !kfnstAttndRecItems.isEmpty()) {
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
		if(kfnstAttndRec.getId().getCid()==null)
			return new CalculateAttendanceRecord();
		// get KfnstAttndRecItem by KfnstAttndRecPK
		KfnstAttndRecPK kfnstAttndRecPK = kfnstAttndRec.getId();
		List<KfnstAttndRecItem> listKfnstAttndRecItem = this.findAttendanceRecordItems(kfnstAttndRecPK);

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
		KfnstAttndRecPK kfnstAttndRecPk = new KfnstAttndRecPK(companyId, Long.valueOf(exportSettingCode.v()),
				columnIndex, exportArt, position);
		KfnstAttndRec kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPk, KfnstAttndRec.class)
				.orElse(new KfnstAttndRec());
		if (kfnstAttndRec.getId() == null) {
			kfnstAttndRec.setId(kfnstAttndRecPk);
		}

		// get listItemAdded, listItemSubtracted
		List<KfnstAttndRecItem> listKfnstAttndRecItemAdded = calculateAttendanceRecord.getAddedItem().stream()
				.map(e -> toEntityAttndRecItemAdded(exportSettingCode, columnIndex, position, exportArt, e))
				.collect(Collectors.toList());
		List<KfnstAttndRecItem> listKfnstAttndRecItemSubtracted = calculateAttendanceRecord.getSubtractedItem().stream()
				.map(e -> toEntityAttndRecItemSubtracted(exportSettingCode, columnIndex, position, exportArt, e))
				.collect(Collectors.toList());
		List<KfnstAttndRecItem> kfnstAttndRecItems = new ArrayList<KfnstAttndRecItem>();
		kfnstAttndRecItems.addAll(listKfnstAttndRecItemAdded);
		kfnstAttndRecItems.addAll(listKfnstAttndRecItemSubtracted);

		calculateAttendanceRecord
				.saveToMemento(new JpaCalculateAttendanceRecordSetMemento(kfnstAttndRec, kfnstAttndRecItems));
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
	 * @param timeItemId
	 *            the time item id
	 * @return the kfnst attnd rec item
	 */
	private KfnstAttndRecItem toEntityAttndRecItemSubtracted(ExportSettingCode exportSettingCode, long columnIndex,
			long position, long exportArt, int timeItemId) {
		UID uid = new UID();
		KfnstAttndRecItem kfnstAttendRecItem = new KfnstAttndRecItem(uid.toString(), AppContexts.user().companyId(),
				columnIndex, exportSettingCode.v(), new BigDecimal(SUBTRACT_FORMULA_TYPE), exportArt, position,
				timeItemId);
		return kfnstAttendRecItem;
	}

	private KfnstAttndRecItem toEntityAttndRecItemAdded(ExportSettingCode exportSettingCode, long columnIndex,
			long position, long exportArt, int timeItemId) {
		// KfnstAttndRecItemPK kfnstAttendRecItemPK = new
		// KfnstAttndRecItemPK(AppContexts.user().companyId(),
		// exportSettingCode.v(), columnIndex, position, exportArt, timeItemId);
		UID uid = new UID();
		KfnstAttndRecItem kfnstAttendRecItem = new KfnstAttndRecItem(uid.toString(), AppContexts.user().companyId(),
				columnIndex, exportSettingCode.v(), new BigDecimal(ADD_FORMULA_TYPE), exportArt, position, timeItemId);
		return kfnstAttendRecItem;
	}

	/**
	 * Removes the all attnd rec item.
	 *
	 * @param listKfnstAttndRecItem
	 *            the list kfnst attnd rec item
	 */
	public void removeAllAttndRecItem(List<KfnstAttndRecItem> listKfnstAttndRecItem) {
		if (!listKfnstAttndRecItem.isEmpty()) {
			this.commandProxy().removeAll(listKfnstAttndRecItem);
			this.getEntityManager().flush();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * CalculateAttendanceRecordRepositoty#
	 * getIdCalculateAttendanceRecordDailyByPosition(java.lang.String, long,
	 * long)
	 */
	@Override
	public List<CalculateAttendanceRecord> getIdCalculateAttendanceRecordDailyByPosition(String companyId,
			long exportCode, long position) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRec> criteriaQuery = criteriaBuilder.createQuery(KfnstAttndRec.class);
		Root<KfnstAttndRec> root = criteriaQuery.from(KfnstAttndRec.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.exportCd), exportCode));
		predicates.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.columnIndex), (long) 7));
		predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.columnIndex),
				(long) 9));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.position), position));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.outputAtr), 1));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRec> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		List<KfnstAttndRec> kfnstAttndRecItemsTotal = new ArrayList<>();

		for (int i = 7; i <= 9; i++) {
			if (this.findIndexInList(i, kfnstAttndRecItems) == null) {
				KfnstAttndRec item = new KfnstAttndRec();
				item.setId(new KfnstAttndRecPK());
				kfnstAttndRecItemsTotal.add(item);
			} else {
				kfnstAttndRecItemsTotal.add(this.findIndexInList(i, kfnstAttndRecItems));
			}
		}
		return kfnstAttndRecItemsTotal.isEmpty() ? new ArrayList<CalculateAttendanceRecord>()
				: kfnstAttndRecItemsTotal.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	private KfnstAttndRec findIndexInList(int i, List<KfnstAttndRec> list) {
		for (KfnstAttndRec item : list) {
			if (item.getId().getColumnIndex() == i)
				return item;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * CalculateAttendanceRecordRepositoty#
	 * getIdCalculateAttendanceRecordMonthlyByPosition(java.lang.String, long,
	 * long)
	 */
	@Override
	public List<CalculateAttendanceRecord> getIdCalculateAttendanceRecordMonthlyByPosition(String companyId,
			long exportCode, long position) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRec> criteriaQuery = criteriaBuilder.createQuery(KfnstAttndRec.class);
		Root<KfnstAttndRec> root = criteriaQuery.from(KfnstAttndRec.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.exportCd), exportCode));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.position), position));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRec_.id).get(KfnstAttndRecPK_.outputAtr), 2));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRec> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		List<KfnstAttndRec> kfnstAttndRecItemsTotal = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			if (this.findIndexInList(i, kfnstAttndRecItems) == null) {
				KfnstAttndRec item = new KfnstAttndRec();
				item.setId(new KfnstAttndRecPK());
				kfnstAttndRecItemsTotal.add(item);
			} else {
				kfnstAttndRecItemsTotal.add(this.findIndexInList(i, kfnstAttndRecItems));
			}
		}
		return kfnstAttndRecItems.isEmpty() ? new ArrayList<CalculateAttendanceRecord>()
				: kfnstAttndRecItemsTotal.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}
}
