package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
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
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItemPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItemPK_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem_;
import nts.uk.shr.com.context.AppContexts;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaSingleAttendanceRecordRepository.
 */
@Stateless
public class JpaSingleAttendanceRecordRepository extends JpaRepository implements SingleAttendanceRecordRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#getSingleAttendanceRecord(java.lang.String,
	 * nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode,
	 * long, long, long)
	 */
	@Override
	public Optional<SingleAttendanceRecord> getSingleAttendanceRecord(String companyId,
			ExportSettingCode exportSettingCode, long columnIndex, long position, long exportArt) {
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt,
				position);
		return this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class).map(e -> this.toDomain(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository#addSingleAttendanceRecord(java.lang.String, nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode, long, long, nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord)
	 */
	@Override
	public void addSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position,long exportArt,boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		
		
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository#updateSingleAttendanceRecord(nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode, long, long, long, boolean, nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord)
	 */
	@Override
	public void updateSingleAttendanceRecord(String companyId,ExportSettingCode exportSettingCode, long columnIndex, long position,
			long exportArt, boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		//update attendanceRecord
		this.commandProxy().update(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt,useAtr, singleAttendanceRecord));
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt, position);
		Optional<KfnstAttndRec> kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class);
		if(kfnstAttndRec.isPresent()) {
			this.commandProxy().update(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt, useAtr, singleAttendanceRecord));
		}else {
			this.commandProxy().insert(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt, useAtr, singleAttendanceRecord));
		}
		//check and update attendanceRecordItem
		Optional<KfnstAttndRecItem> kfnstAttndRecItem = this.findAttendanceRecordItemByPK(
														companyId,
														exportSettingCode,
														columnIndex,
														position,
														exportArt,
														singleAttendanceRecord.getTimeItemId());
		if(kfnstAttndRecItem.isPresent()) {
			this.commandProxy().remove(kfnstAttndRecItem.get());
			this.commandProxy().insert(this.toEntityAttndRecItem(exportSettingCode, columnIndex, position, exportArt, singleAttendanceRecord));
		}else {
			this.commandProxy().insert(kfnstAttndRecItem.get());
		}
		this.getEntityManager().flush();
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository#deleteSingleAttendanceRecord(java.lang.String, nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode, long, long, nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord)
	 */
	@Override
	public void deleteSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position,long exportArt, SingleAttendanceRecord singleAttendanceRecord) {
		//find and delete KfnstAttndRec, KfnstAttndRecItem
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt, position);
		Optional<KfnstAttndRec> optionalKfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class);
		if(optionalKfnstAttndRec.isPresent()) this.commandProxy().remove(optionalKfnstAttndRec.get());
		
		//find and delete KfnstAttndRecItem
		Optional<KfnstAttndRecItem> optionalkfnstAttndRecItem = this.findAttendanceRecordItemByPK(companyId, exportSettingCode, columnIndex, position, exportArt, singleAttendanceRecord.getTimeItemId());
		if(optionalkfnstAttndRecItem.isPresent()) this.commandProxy().remove(optionalkfnstAttndRecItem.get());
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
		List<KfnstAttndRecItem> listKfnstAttndRecItem = this.findAttendanceRecordItems(AppContexts.user().companyId(),
				new ExportSettingCode(kfnstAttndRec.getId().getExportCd()), kfnstAttndRec.getId().getColumnIndex(),
				kfnstAttndRec.getId().getPosition(), kfnstAttndRec.getId().getOutputAtr());
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
	 * @param exportSettingCode the export setting code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param exportArt the export art
	 * @param singleAttendanceRecord the single attendance record
	 * @return the kfnst attnd rec
	 */
	private KfnstAttndRec toEntityAttndRec(ExportSettingCode exportSettingCode, long columnIndex, long position,
			long exportArt,boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		//find entity KfnstAttndRec by pk
		String companyId = AppContexts.user().companyId();
		KfnstAttndRecPK kfnstAttndRecPk = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt, position);
		KfnstAttndRec kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPk, KfnstAttndRec.class).orElse(new KfnstAttndRec());
		
		//find entity KfnstAttndRecItem by pk
		KfnstAttndRecItemPK kfnstAttndRecItemPk = new KfnstAttndRecItemPK(companyId, exportSettingCode.v(), columnIndex, position, exportArt, singleAttendanceRecord.getTimeItemId());
		KfnstAttndRecItem kfnstAttndRecItem = this.queryProxy().find(kfnstAttndRecItemPk, KfnstAttndRecItem.class).orElse(new KfnstAttndRecItem());
		
		singleAttendanceRecord.saveToMemento(new JpaSingleAttendanceRecordSetMemento(kfnstAttndRec, kfnstAttndRecItem));
		int useAtrValue = useAtr? 1 : 0;
		kfnstAttndRec.setUseAtr(new BigDecimal(useAtrValue));
		
		return kfnstAttndRec;
	}
	
	/**
	 * To attnd rec item entity.
	 *
	 * @param exportSettingCode the export setting code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param exportArt the export art
	 * @param singleAttendanceRecord the single attendance record
	 * @return the kfnst attnd rec item
	 */
	private KfnstAttndRecItem toEntityAttndRecItem(ExportSettingCode exportSettingCode, long columnIndex, long position,
			long exportArt, SingleAttendanceRecord singleAttendanceRecord) {
		//find entity by pk
		String companyId = AppContexts.user().companyId();
		KfnstAttndRecPK kfnstAttndRecPk = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt, position);
		KfnstAttndRec kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPk, KfnstAttndRec.class).orElse(new KfnstAttndRec());
		
		KfnstAttndRecItemPK kfnstAttndRecItemPk = new KfnstAttndRecItemPK(companyId, exportSettingCode.v(), columnIndex, position, exportArt, singleAttendanceRecord.getTimeItemId());
		KfnstAttndRecItem kfnstAttndRecItem = this.queryProxy().find(kfnstAttndRecItemPk, KfnstAttndRecItem.class).orElse(new KfnstAttndRecItem());
		
		singleAttendanceRecord.saveToMemento(new JpaSingleAttendanceRecordSetMemento(kfnstAttndRec, kfnstAttndRecItem));
		return kfnstAttndRecItem;
	}

	/**
	 * Find attendance record item.
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
				exportSettingCode));
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
	 * Find attendance record item by PK.
	 *
	 * @param companyId the company id
	 * @param exportSettingCode the export setting code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param exportArt the export art
	 * @param timeItemId the time item id
	 * @return the optional
	 */
	public Optional<KfnstAttndRecItem> findAttendanceRecordItemByPK(String companyId, ExportSettingCode exportSettingCode,
			long columnIndex, long position, long exportArt,long timeItemId ) {
		KfnstAttndRecItemPK kfnstAttndRecItemPK = new KfnstAttndRecItemPK(companyId, exportSettingCode.v(), columnIndex, position, exportArt, timeItemId);
		return this.queryProxy().find(kfnstAttndRecItemPK, KfnstAttndRecItem.class);
	}

}
