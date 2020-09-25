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

import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframePK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;
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
	public Optional<CalculateAttendanceRecord> getCalculateAttendanceRecord(String layoutId, long columnIndex,
			long exportArt, long position) {
		KfnmtRptWkAtdOutframePK kfnmtRptWkAtdOutframePK = new KfnmtRptWkAtdOutframePK(layoutId, columnIndex, exportArt,
				position);
		return this.queryProxy().find(kfnmtRptWkAtdOutframePK, KfnmtRptWkAtdOutframe.class).map(e -> this.toDomain(e));
	}

	@Override
	public void addCalculateAttendanceRecord(String layoutId, int columnIndex, int position,
			long exportArt, CalculateAttendanceRecord calculateAttendanceRecord) {
	}

	@Override
	public void updateCalculateAttendanceRecord(String layoutId, int columnIndex,
			int position, long exportArt, boolean useAtr, CalculateAttendanceRecord calculateAttendanceRecord) {

		// check and update AttendanceRecord
		KfnmtRptWkAtdOutframePK KfnmtRptWkAtdOutframePK = new KfnmtRptWkAtdOutframePK(layoutId, columnIndex, exportArt,
				position);
		Optional<KfnmtRptWkAtdOutframe> kfnstAttndRec = this.queryProxy().find(KfnmtRptWkAtdOutframePK, KfnmtRptWkAtdOutframe.class);
		if (kfnstAttndRec.isPresent()) {
			this.commandProxy().update(this.toEntityAtdOutFrame(layoutId, columnIndex, position, exportArt,
					useAtr, calculateAttendanceRecord));
		} else {
			this.commandProxy().insert(this.toEntityAtdOutFrame(layoutId, columnIndex, position, exportArt,
					useAtr, calculateAttendanceRecord));
		}

		// get listItemAdded, listItemSubtracted
		List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItemAdded = calculateAttendanceRecord.getAddedItem().stream()
				.map(e -> toEntityAttndRecItemAdded(layoutId, columnIndex, position, exportArt, e))
				.collect(Collectors.toList());

		List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItemSubtracted = calculateAttendanceRecord.getSubtractedItem().stream()
				.map(e -> toEntityAttndRecItemSubtracted(layoutId, columnIndex, position, exportArt, e))
				.collect(Collectors.toList());

		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItems = new ArrayList<KfnmtRptWkAtdOutatd>();
		kfnstAttndRecItems.addAll(listKfnstAttndRecItemAdded);
		kfnstAttndRecItems.addAll(listKfnstAttndRecItemSubtracted);

		// check and update attendanceRecordItems
		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItemsOld = this.findAttendanceRecordItems(KfnmtRptWkAtdOutframePK);
		if (kfnstAttndRecItemsOld != null) {
			this.removeAllAttndRecItem(kfnstAttndRecItemsOld);
		}
		this.commandProxy().insertAll(kfnstAttndRecItems);

		this.getEntityManager().flush();

	}

	@Override
	public void deleteCalculateAttendanceRecord(String layoutId, int columnIndex,
			int position, long exportArt, CalculateAttendanceRecord calculateAttendanceRecord) {
		// find and delete KfnmtRptWkAtdOutframe, KfnmtRptWkAtdOutatd
		KfnmtRptWkAtdOutframePK KfnmtRptWkAtdOutframePK = new KfnmtRptWkAtdOutframePK(layoutId,
				columnIndex, exportArt, position);
		Optional<KfnmtRptWkAtdOutframe> optionalKfnstAttndRec = this.queryProxy().find(KfnmtRptWkAtdOutframePK, KfnmtRptWkAtdOutframe.class);
		optionalKfnstAttndRec.ifPresent(kfnstAttndRec -> this.commandProxy().remove(kfnstAttndRec));

		// find and delete KfnmtRptWkAtdOutatd
		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItems = this.findAttendanceRecordItems(KfnmtRptWkAtdOutframePK);
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
	private CalculateAttendanceRecord toDomain(KfnmtRptWkAtdOutframe kfnstAttndRec) {
		if(kfnstAttndRec.getId().getLayoutId()==null)
			return new CalculateAttendanceRecord();
		// get KfnmtRptWkAtdOutatd by KfnmtRptWkAtdOutframePK
		KfnmtRptWkAtdOutframePK KfnmtRptWkAtdOutframePK = kfnstAttndRec.getId();
		List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItem = this.findAttendanceRecordItems(KfnmtRptWkAtdOutframePK);

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
	private KfnmtRptWkAtdOutframe toEntityAtdOutFrame(String layoutId, long columnIndex, long position,
			long exportArt, boolean useAtr, CalculateAttendanceRecord calculateAttendanceRecord) {
		// find entity KfnmtRptWkAtdOutframe by pk
		KfnmtRptWkAtdOutframePK kfnmtRptWkAtdOutframePK = new KfnmtRptWkAtdOutframePK(layoutId, columnIndex, exportArt,
				position);
		KfnmtRptWkAtdOutframe kfnstAttndRec = this.queryProxy().find(kfnmtRptWkAtdOutframePK, KfnmtRptWkAtdOutframe.class)
				.orElse(new KfnmtRptWkAtdOutframe());
		if (kfnstAttndRec.getId() == null) {
			kfnstAttndRec.setId(kfnmtRptWkAtdOutframePK);
		}
		
		if (kfnstAttndRec.getCid() == null) {
			kfnstAttndRec.setCid(AppContexts.user().companyId());
		}
		
		if (kfnstAttndRec.getContractCd() == null) {
			kfnstAttndRec.setContractCd(AppContexts.user().contractCode());
		}

		// get listItemAdded, listItemSubtracted
		List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItemAdded = calculateAttendanceRecord.getAddedItem().stream()
				.map(e -> toEntityAttndRecItemAdded(layoutId, columnIndex, position, exportArt, e))
				.collect(Collectors.toList());
		List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItemSubtracted = calculateAttendanceRecord.getSubtractedItem().stream()
				.map(e -> toEntityAttndRecItemSubtracted(layoutId, columnIndex, position, exportArt, e))
				.collect(Collectors.toList());
		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItems = new ArrayList<KfnmtRptWkAtdOutatd>();
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
	private KfnmtRptWkAtdOutatd toEntityAttndRecItemSubtracted(String layoutId, long columnIndex,
			long position, long exportArt, int timeItemId) {
		UID uid = new UID();
		
		KfnmtRptWkAtdOutatd item = new KfnmtRptWkAtdOutatd();
		item.setRecordItemId(uid.toString());
		item.setExclusVer(1);
		item.setContractCd(AppContexts.user().contractCode());
		item.setCid(AppContexts.user().companyId());
		item.setLayoutId(layoutId);
		item.setColumnIndex(columnIndex);
		item.setPosition(position);
		item.setOutputAtr(exportArt);
		item.setTimeItemId(timeItemId);
		item.setFormulaType(new BigDecimal(SUBTRACT_FORMULA_TYPE));
		

		return item;
	}

	private KfnmtRptWkAtdOutatd toEntityAttndRecItemAdded(String layoutId, long columnIndex,
			long position, long exportArt, int timeItemId) {
		UID uid = new UID();
		
		KfnmtRptWkAtdOutatd item = new KfnmtRptWkAtdOutatd();
		item.setRecordItemId(uid.toString());
		item.setExclusVer(1);
		item.setContractCd(AppContexts.user().contractCode());
		item.setCid(AppContexts.user().companyId());
		item.setLayoutId(layoutId);
		item.setColumnIndex(columnIndex);
		item.setPosition(position);
		item.setOutputAtr(exportArt);
		item.setTimeItemId(timeItemId);
		item.setFormulaType(new BigDecimal(ADD_FORMULA_TYPE));
		return item;
	}

	/**
	 * Removes the all attnd rec item.
	 *
	 * @param listKfnstAttndRecItem
	 *            the list kfnst attnd rec item
	 */
	public void removeAllAttndRecItem(List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItem) {
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
	public List<CalculateAttendanceRecord> getIdCalculateAttendanceRecordDailyByPosition(String layoutId, long position) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutframe> criteriaQuery = criteriaBuilder.createQuery(KfnmtRptWkAtdOutframe.class);
		Root<KfnmtRptWkAtdOutframe> root = criteriaQuery.from(KfnmtRptWkAtdOutframe.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.layoutId), layoutId));
//		predicates.add(criteriaBuilder
//				.greaterThanOrEqualTo(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.columnIndex), (long) 7));
//		predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.columnIndex),
//				(long) 9));
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.position), position));
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.outputAtr), 1));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnmtRptWkAtdOutframe> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		List<KfnmtRptWkAtdOutframe> kfnstAttndRecItemsTotal = new ArrayList<>();

		for (int i = 7; i <= 9; i++) {
			if (this.findIndexInList(i, kfnstAttndRecItems) == null) {
				KfnmtRptWkAtdOutframe item = new KfnmtRptWkAtdOutframe();
				item.setId(new KfnmtRptWkAtdOutframePK());
				kfnstAttndRecItemsTotal.add(item);
			} else {
				kfnstAttndRecItemsTotal.add(this.findIndexInList(i, kfnstAttndRecItems));
			}
		}
		return kfnstAttndRecItemsTotal.isEmpty() ? new ArrayList<CalculateAttendanceRecord>()
				: kfnstAttndRecItemsTotal.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	private KfnmtRptWkAtdOutframe findIndexInList(int i, List<KfnmtRptWkAtdOutframe> list) {
		for (KfnmtRptWkAtdOutframe item : list) {
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
	public List<CalculateAttendanceRecord> getIdCalculateAttendanceRecordMonthlyByPosition(String layoutId, long position) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutframe> criteriaQuery = criteriaBuilder.createQuery(KfnmtRptWkAtdOutframe.class);
		Root<KfnmtRptWkAtdOutframe> root = criteriaQuery.from(KfnmtRptWkAtdOutframe.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.layoutId), layoutId));
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.position), position));
//		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutframe_.id).get(KfnmtRptWkAtdOutframePK_.outputAtr), 2));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnmtRptWkAtdOutframe> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();
		List<KfnmtRptWkAtdOutframe> kfnstAttndRecItemsTotal = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			if (this.findIndexInList(i, kfnstAttndRecItems) == null) {
				KfnmtRptWkAtdOutframe item = new KfnmtRptWkAtdOutframe();
				item.setId(new KfnmtRptWkAtdOutframePK());
				kfnstAttndRecItemsTotal.add(item);
			} else {
				kfnstAttndRecItemsTotal.add(this.findIndexInList(i, kfnstAttndRecItems));
			}
		}
		return kfnstAttndRecItems.isEmpty() ? new ArrayList<CalculateAttendanceRecord>()
				: kfnstAttndRecItemsTotal.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

}
