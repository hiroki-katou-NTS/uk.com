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

import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframePK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd_;
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
	public Optional<SingleAttendanceRecord> getSingleAttendanceRecord(String layoutId, long columnIndex, long position, long exportArt) {
		KfnmtRptWkAtdOutframePK kfnstAttndRecPK = new KfnmtRptWkAtdOutframePK(layoutId, columnIndex, exportArt,
				position);
		return this.queryProxy().find(kfnstAttndRecPK, KfnmtRptWkAtdOutframe.class).map(e -> this.toDomain(e));
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
	public void addSingleAttendanceRecord(String layoutId, long columnIndex,
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
	public void updateSingleAttendanceRecord(String layoutId, long columnIndex,
			long position, long exportArt, boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		// update attendanceRecord
		KfnmtRptWkAtdOutframePK kfnstAttndRecPK = new KfnmtRptWkAtdOutframePK(layoutId, columnIndex, exportArt,
				position);
		// check and update AttendanceRecord
		Optional<KfnmtRptWkAtdOutframe> kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnmtRptWkAtdOutframe.class);
		if (kfnstAttndRec.isPresent()) {
			KfnmtRptWkAtdOutframe kfnstAttndRecUpdate = kfnstAttndRec.get();
			kfnstAttndRecUpdate.setItemName(singleAttendanceRecord.getName().toString());
			kfnstAttndRecUpdate.setAttribute(new BigDecimal(singleAttendanceRecord.getAttribute().value));
			int useAtrValue = useAtr ? USE_ATTRIBUTE : NOT_USE_ATTRIBUTE;
			kfnstAttndRecUpdate.setUseAtr(new BigDecimal(useAtrValue));
			this.commandProxy().update(kfnstAttndRecUpdate);
		} else {
			this.commandProxy().insert(this.toEntityAttndRec(layoutId, columnIndex, position, exportArt,
					useAtr, singleAttendanceRecord));
		}
		// check and update attendanceRecordItem
		List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItem = this.findAttendanceRecordItems(kfnstAttndRecPK);
		KfnmtRptWkAtdOutatd kfnmtRptWkAtdOutatd = (listKfnstAttndRecItem.size() != 0) ? listKfnstAttndRecItem.get(0) : null;
		if (kfnmtRptWkAtdOutatd != null) {
			this.commandProxy().remove(kfnmtRptWkAtdOutatd);
			this.getEntityManager().flush();
			this.commandProxy().insert(this.toEntityAttndRecItem(layoutId, columnIndex, position, exportArt,
					singleAttendanceRecord));
		} else {
			UID uid = new UID();
			
			kfnmtRptWkAtdOutatd.setRecordItemId(uid.toString());
			kfnmtRptWkAtdOutatd.setExclusVer(1);
			kfnmtRptWkAtdOutatd.setContractCd(AppContexts.user().contractCode());
			kfnmtRptWkAtdOutatd.setCid(AppContexts.user().companyId());
			kfnmtRptWkAtdOutatd.setLayoutId(layoutId);
			kfnmtRptWkAtdOutatd.setColumnIndex(columnIndex);
			kfnmtRptWkAtdOutatd.setPosition(position);
			kfnmtRptWkAtdOutatd.setOutputAtr(exportArt);
			kfnmtRptWkAtdOutatd.setTimeItemId(singleAttendanceRecord.getTimeItemId());
			kfnmtRptWkAtdOutatd.setFormulaType(new BigDecimal(SINGLE_FORMULA_TYPE));
			
			this.commandProxy().insert(kfnmtRptWkAtdOutatd);
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
	public void deleteSingleAttendanceRecord(String layoutId, long columnIndex,
			long position, long exportArt, SingleAttendanceRecord singleAttendanceRecord) {
		// find and delete KfnmtRptWkAtdOutframe, KfnmtRptWkAtdOutatd
		KfnmtRptWkAtdOutframePK kfnstAttndRecPK = new KfnmtRptWkAtdOutframePK(layoutId, columnIndex, exportArt,
				position);
		Optional<KfnmtRptWkAtdOutframe> optionalKfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnmtRptWkAtdOutframe.class);
		optionalKfnstAttndRec.ifPresent(kfnstAttndRec -> this.commandProxy().remove(kfnstAttndRec));

		// find and delete KfnmtRptWkAtdOutatd
		List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItem = this.findAttendanceRecordItems(kfnstAttndRecPK);
		KfnmtRptWkAtdOutatd kfnmtRptWkAtdOutatd = (listKfnstAttndRecItem.size() != 0) ? listKfnstAttndRecItem.get(0) : null;
		if (kfnmtRptWkAtdOutatd != null)
			this.commandProxy().remove(kfnmtRptWkAtdOutatd);
		this.getEntityManager().flush();
	}

	/**
	 * To domain.
	 *
	 * @param kfnstAttndRec
	 *            the kfnst attnd rec
	 * @return the single attendance record
	 */
	private SingleAttendanceRecord toDomain(KfnmtRptWkAtdOutframe kfnstAttndRec) {
		// get KfnmtRptWkAtdOutatd by KfnmtRptWkAtdOutframePK
		KfnmtRptWkAtdOutframePK attendanceRecordPK = kfnstAttndRec.getId();
		List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItem = this.findAttendanceRecordItems(attendanceRecordPK);

		// check value
		KfnmtRptWkAtdOutatd kfnmtRptWkAtdOutatd = listKfnstAttndRecItem.isEmpty() ? new KfnmtRptWkAtdOutatd()
				: listKfnstAttndRecItem.get(0);
		// create getMemento
		SingleAttendanceRecordGetMemento getMemento = new JpaSingleAttendanceRecordGetMemento(kfnstAttndRec,
				kfnmtRptWkAtdOutatd);
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
	private KfnmtRptWkAtdOutframe toEntityAttndRec(String layoutId, long columnIndex, long position,
			long exportArt, boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		// find entity KfnmtRptWkAtdOutframe by pk
		String companyId = AppContexts.user().companyId();
		KfnmtRptWkAtdOutframePK kfnstAttndRecPk = new KfnmtRptWkAtdOutframePK(layoutId, columnIndex, exportArt,
				position);
		KfnmtRptWkAtdOutframe kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPk, KfnmtRptWkAtdOutframe.class)
				.orElse(new KfnmtRptWkAtdOutframe(kfnstAttndRecPk, 1, AppContexts.user().contractCode(), companyId,
						new BigDecimal(0), null, new BigDecimal(0)));
		// find entites KfnmtRptWkAtdOutatd by attendanceRecordPK
		List<KfnmtRptWkAtdOutatd> listAttndRecItemEntity = this.findAttendanceRecordItems(kfnstAttndRecPk);
		KfnmtRptWkAtdOutatd attendanceRecItemEntity = listAttndRecItemEntity.isEmpty() ? new KfnmtRptWkAtdOutatd()
				: listAttndRecItemEntity.get(0);
		if (attendanceRecItemEntity.getRecordItemId() == null) {
			UID uid = new UID();
			// set another property for attendanceRecordItem
			attendanceRecItemEntity.setLayoutId(layoutId);
			attendanceRecItemEntity.setRecordItemId(uid.toString());
			attendanceRecItemEntity.setCid(companyId);
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
	private KfnmtRptWkAtdOutatd toEntityAttndRecItem(String layoutId, long columnIndex, long position,
			long exportArt, SingleAttendanceRecord singleAttendanceRecord) {
		UID uid = new UID();
		
		KfnmtRptWkAtdOutatd kfnmtRptWkAtdOutatd = new KfnmtRptWkAtdOutatd();
		kfnmtRptWkAtdOutatd.setRecordItemId(uid.toString());
		kfnmtRptWkAtdOutatd.setExclusVer(1);
		kfnmtRptWkAtdOutatd.setContractCd(AppContexts.user().contractCode());
		kfnmtRptWkAtdOutatd.setCid(AppContexts.user().companyId());
		kfnmtRptWkAtdOutatd.setLayoutId(layoutId);
		kfnmtRptWkAtdOutatd.setColumnIndex(columnIndex);
		kfnmtRptWkAtdOutatd.setPosition(position);
		kfnmtRptWkAtdOutatd.setOutputAtr(exportArt);
		kfnmtRptWkAtdOutatd.setTimeItemId(singleAttendanceRecord.getTimeItemId());
		kfnmtRptWkAtdOutatd.setFormulaType(new BigDecimal(SINGLE_FORMULA_TYPE));
		return kfnmtRptWkAtdOutatd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#getIdSingleAttendanceRecordByPosition(
	 * java. lang.String, long, long)
	 */
	@Override
	public List<Integer> getIdSingleAttendanceRecordByPosition(String layoutId, long position) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KfnmtRptWkAtdOutatd> criteriaQuery = criteriaBuilder.createQuery(KfnmtRptWkAtdOutatd.class);
		Root<KfnmtRptWkAtdOutatd> root = criteriaQuery.from(KfnmtRptWkAtdOutatd.class);

		// Build query
		criteriaQuery.select(root);

		// create condition
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutatd_.layoutId), layoutId));
		predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(KfnmtRptWkAtdOutatd_.columnIndex), (long) 6));
		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutatd_.position), position));
		predicates.add(criteriaBuilder.equal(root.get(KfnmtRptWkAtdOutatd_.outputAtr), 1));

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItems = em.createQuery(criteriaQuery).getResultList();

		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItemsTotal = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			if (this.findIndexInList(i, kfnstAttndRecItems)==null) {
				KfnmtRptWkAtdOutatd item = new KfnmtRptWkAtdOutatd();
				item.setTimeItemId(0);
				kfnstAttndRecItemsTotal.add(item);
			} else
				kfnstAttndRecItemsTotal.add(this.findIndexInList(i, kfnstAttndRecItems));
		}

		return kfnstAttndRecItems.isEmpty() ? new ArrayList<Integer>()
				: kfnstAttndRecItemsTotal.stream().map(item -> (int) item.getTimeItemId()).collect(Collectors.toList());

	}

	private KfnmtRptWkAtdOutatd findIndexInList(int i, List<KfnmtRptWkAtdOutatd> list) {
		for (KfnmtRptWkAtdOutatd item : list) {
			if (item.getColumnIndex() == i)
				return item;
		}

		return null;
	}
}
