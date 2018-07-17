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
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem_;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaSingleAttendanceRecordRepository.
 */
/**
 * @author tuannt-nws
 *
 */
@Stateless
public class JpaSingleAttendanceRecordRepository extends JpaAttendanceRecordRepository
		implements SingleAttendanceRecordRepository {

	/** The dont use attribute. */
	private static final int NOT_USE_ATTRIBUTE = 0;

	/** The use attribute. */
	private static final int USE_ATTRIBUTE = 1;

	/** The single formula type. */
	private static final int SINGLE_FORMULA_TYPE = 3;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#getSingleAttendanceRecord(java.lang.
	 * String, nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * ExportSettingCode, long, long, long)
	 */
	@Override
	public Optional<SingleAttendanceRecord> getSingleAttendanceRecord(String companyId,
			ExportSettingCode exportSettingCode, long columnIndex, long position, long exportArt) {
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt,
				position);
		return this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class).map(e -> this.toDomain(e));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#addSingleAttendanceRecord(java.lang.
	 * String, nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * ExportSettingCode, long, long,
	 * nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord)
	 */
	@Override
	public void addSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position, long exportArt, boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		// No Code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#updateSingleAttendanceRecord(nts.uk.ctx.
	 * at. function.dom.attendancerecord.export.setting.ExportSettingCode, long,
	 * long, long, boolean,
	 * nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord)
	 */
	@Override
	public void updateSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position, long exportArt, boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		// update attendanceRecord
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt,
				position);
		// check and update AttendanceRecord
		Optional<KfnstAttndRec> kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class);
		if (kfnstAttndRec.isPresent()) {
			KfnstAttndRec kfnstAttndRecUpdate = kfnstAttndRec.get();
			kfnstAttndRecUpdate.setItemName(singleAttendanceRecord.getName().toString());
			kfnstAttndRecUpdate.setAttribute(new BigDecimal(singleAttendanceRecord.getAttribute().value));
			int useAtrValue = useAtr ? USE_ATTRIBUTE : NOT_USE_ATTRIBUTE;
			kfnstAttndRecUpdate.setUseAtr(new BigDecimal(useAtrValue));
			this.commandProxy().update(kfnstAttndRecUpdate);
		} else {
			this.commandProxy().insert(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt,
					useAtr, singleAttendanceRecord));
		}
		// check and update attendanceRecordItem
		List<KfnstAttndRecItem> listKfnstAttndRecItem = this.findAttendanceRecordItems(kfnstAttndRecPK);
		KfnstAttndRecItem kfnstAttndRecItem = (listKfnstAttndRecItem.size() != 0) ? listKfnstAttndRecItem.get(0) : null;
		if (kfnstAttndRecItem != null) {
			this.commandProxy().remove(kfnstAttndRecItem);
			this.getEntityManager().flush();
			this.commandProxy().insert(this.toEntityAttndRecItem(exportSettingCode, columnIndex, position, exportArt,
					singleAttendanceRecord));
		} else {
			UID uid = new UID();
			kfnstAttndRecItem = new KfnstAttndRecItem(uid.toString(), companyId, columnIndex, exportSettingCode.v(),
					new BigDecimal(SINGLE_FORMULA_TYPE), exportArt, position, singleAttendanceRecord.getTimeItemId());
			this.commandProxy().insert(kfnstAttndRecItem);
		}
		this.getEntityManager().flush();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#deleteSingleAttendanceRecord(java.lang.
	 * String, nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * ExportSettingCode, long, long,
	 * nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord)
	 */
	@Override
	public void deleteSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position, long exportArt, SingleAttendanceRecord singleAttendanceRecord) {
		// find and delete KfnstAttndRec, KfnstAttndRecItem
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt,
				position);
		Optional<KfnstAttndRec> optionalKfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class);
		optionalKfnstAttndRec.ifPresent(kfnstAttndRec -> this.commandProxy().remove(kfnstAttndRec));

		// find and delete KfnstAttndRecItem
		List<KfnstAttndRecItem> listKfnstAttndRecItem = this.findAttendanceRecordItems(kfnstAttndRecPK);
		KfnstAttndRecItem kfnstAttndRecItem = (listKfnstAttndRecItem.size() != 0) ? listKfnstAttndRecItem.get(0) : null;
		if (kfnstAttndRecItem != null)
			this.commandProxy().remove(kfnstAttndRecItem);
		this.getEntityManager().flush();
	}

	/**
	 * To domain.
	 *
	 * @param kfnstAttndRec
	 *            the kfnst attnd rec
	 * @return the single attendance record
	 */
	private SingleAttendanceRecord toDomain(KfnstAttndRec kfnstAttndRec) {
		// get KfnstAttndRecItem by KfnstAttndRecPK
		KfnstAttndRecPK attendanceRecordPK = kfnstAttndRec.getId();
		List<KfnstAttndRecItem> listKfnstAttndRecItem = this.findAttendanceRecordItems(attendanceRecordPK);

		// check value
		KfnstAttndRecItem kfnstAttndRecItem = listKfnstAttndRecItem.isEmpty() ? new KfnstAttndRecItem()
				: listKfnstAttndRecItem.get(0);
		// create getMemento
		SingleAttendanceRecordGetMemento getMemento = new JpaSingleAttendanceRecordGetMemento(kfnstAttndRec,
				kfnstAttndRecItem);
		return new SingleAttendanceRecord(getMemento);

	}

	/**
	 * To attnd rec entity.
	 *
	 * @param exportSettingCode
	 *            the export setting code
	 * @param columnIndex
	 *            the column index
	 * @param position
	 *            the position
	 * @param exportArt
	 *            the export art
	 * @param singleAttendanceRecord
	 *            the single attendance record
	 * @return the kfnst attnd rec
	 */
	private KfnstAttndRec toEntityAttndRec(ExportSettingCode exportSettingCode, long columnIndex, long position,
			long exportArt, boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		// find entity KfnstAttndRec by pk
		String companyId = AppContexts.user().companyId();
		KfnstAttndRecPK kfnstAttndRecPk = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt,
				position);
		KfnstAttndRec kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPk, KfnstAttndRec.class)
				.orElse(new KfnstAttndRec(kfnstAttndRecPk, new BigDecimal(0), null, new BigDecimal(0)));
		// find entites KfnstAttndRecItem by attendanceRecordPK
		List<KfnstAttndRecItem> listAttndRecItemEntity = this.findAttendanceRecordItems(kfnstAttndRecPk);
		KfnstAttndRecItem attendanceRecItemEntity = listAttndRecItemEntity.isEmpty() ? new KfnstAttndRecItem()
				: listAttndRecItemEntity.get(0);
		if (attendanceRecItemEntity.getRecordItemId() == null) {
			UID uid = new UID();
			// set another property for attendanceRecordItem
			attendanceRecItemEntity.setRecordItemId(uid.toString());
			attendanceRecItemEntity.setCid(companyId);
			attendanceRecItemEntity.setExportCd(exportSettingCode.v());
			attendanceRecItemEntity.setColumnIndex(columnIndex);
			attendanceRecItemEntity.setPosition(position);
			attendanceRecItemEntity.setOutputAtr(exportArt);

		}
		singleAttendanceRecord
				.saveToMemento(new JpaSingleAttendanceRecordSetMemento(kfnstAttndRec, attendanceRecItemEntity));
		int useAtrValue = useAtr ? USE_ATTRIBUTE : NOT_USE_ATTRIBUTE;
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
	 * @param singleAttendanceRecord
	 *            the single attendance record
	 * @return the kfnst attnd rec item
	 */
	private KfnstAttndRecItem toEntityAttndRecItem(ExportSettingCode exportSettingCode, long columnIndex, long position,
			long exportArt, SingleAttendanceRecord singleAttendanceRecord) {
		String companyId = AppContexts.user().companyId();
		UID uid = new UID();
		KfnstAttndRecItem kfnstAttndRecItem = new KfnstAttndRecItem(uid.toString(), companyId, columnIndex,
				exportSettingCode.v(), new BigDecimal(SINGLE_FORMULA_TYPE), exportArt, position,
				singleAttendanceRecord.getTimeItemId());
		return kfnstAttndRecItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#getIdSingleAttendanceRecordByPosition(
	 * java. lang.String, long, long)
	 */
	@Override
	public List<Integer> getIdSingleAttendanceRecordByPosition(String companyId, long exportCode, long position) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnstAttndRecItem> criteriaQuery = criteriaBuilder.createQuery(KfnstAttndRecItem.class);
		Root<KfnstAttndRecItem> root = criteriaQuery.from(KfnstAttndRecItem.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.exportCd), exportCode));
		predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(KfnstAttndRecItem_.columnIndex), (long) 6));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.position), position));
		predicates.add(criteriaBuilder.equal(root.get(KfnstAttndRecItem_.outputAtr), 1));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnstAttndRecItem> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();

		List<KfnstAttndRecItem> kfnstAttndRecItemsTotal = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			if (this.findIndexInList(i, kfnstAttndRecItems)==null) {
				KfnstAttndRecItem item = new KfnstAttndRecItem();
				item.setTimeItemId(0);
				kfnstAttndRecItemsTotal.add(item);
			} else
				kfnstAttndRecItemsTotal.add(this.findIndexInList(i, kfnstAttndRecItems));
		}

		return kfnstAttndRecItems.isEmpty() ? new ArrayList<Integer>()
				: kfnstAttndRecItemsTotal.stream().map(item -> (int) item.getTimeItemId()).collect(Collectors.toList());

	}

	private KfnstAttndRecItem findIndexInList(int i, List<KfnstAttndRecItem> list) {
		for (KfnstAttndRecItem item : list) {
			if (item.getColumnIndex() == i)
				return item;
		}

		return null;
	}
}
