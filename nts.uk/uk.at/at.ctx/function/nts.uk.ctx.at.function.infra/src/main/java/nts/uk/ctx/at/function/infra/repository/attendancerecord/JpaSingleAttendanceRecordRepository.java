package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItemPK;
import nts.uk.shr.com.context.AppContexts;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaSingleAttendanceRecordRepository.
 */
@Stateless
public class JpaSingleAttendanceRecordRepository extends JpaAttendanceRecordRepository implements SingleAttendanceRecordRepository {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#addSingleAttendanceRecord(java.lang.String,
	 * nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode,
	 * long, long,
	 * nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord)
	 */
	@Override
	public void addSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position, long exportArt, boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#updateSingleAttendanceRecord(nts.uk.ctx.at.
	 * function.dom.attendancerecord.export.setting.ExportSettingCode, long, long,
	 * long, boolean,
	 * nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord)
	 */
	@Override
	public void updateSingleAttendanceRecord(String companyId, ExportSettingCode exportSettingCode, long columnIndex,
			long position, long exportArt, boolean useAtr, SingleAttendanceRecord singleAttendanceRecord) {
		// update attendanceRecord
		KfnstAttndRecPK kfnstAttndRecPK = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt,
				position);
		//check and update AttendanceRecord
		Optional<KfnstAttndRec> kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPK, KfnstAttndRec.class);
		if (kfnstAttndRec.isPresent()) {
			KfnstAttndRec kfnstAttndRecUpdate = kfnstAttndRec.get();
			kfnstAttndRecUpdate.setItemName(singleAttendanceRecord.getName().toString());
			kfnstAttndRecUpdate.setAttribute(new BigDecimal(singleAttendanceRecord.getAttribute().value));
			int useAtrValue = useAtr ? 1 : 0;
			kfnstAttndRecUpdate.setAttribute(new BigDecimal(useAtrValue));
			this.commandProxy().update(kfnstAttndRecUpdate);
		} else {
			this.commandProxy().insert(this.toEntityAttndRec(exportSettingCode, columnIndex, position, exportArt,
					useAtr, singleAttendanceRecord));
		}
		// check and update attendanceRecordItem
		List<KfnstAttndRecItem> listKfnstAttndRecItem = this.findAttendanceRecordItems(kfnstAttndRecPK);
		KfnstAttndRecItem kfnstAttndRecItem = (listKfnstAttndRecItem!=null && listKfnstAttndRecItem.size()!=0) ? listKfnstAttndRecItem.get(0) : null;
		if (kfnstAttndRecItem!=null) {
			this.commandProxy().remove(kfnstAttndRecItem);
			this.commandProxy().insert(this.toEntityAttndRecItem(exportSettingCode, columnIndex, position, exportArt,
					singleAttendanceRecord));
		} else {
			KfnstAttndRecItemPK pk = new KfnstAttndRecItemPK(companyId, exportSettingCode.v(), columnIndex, position, exportArt, singleAttendanceRecord.getTimeItemId());
			kfnstAttndRecItem = new KfnstAttndRecItem(pk,new BigDecimal(3));
			this.commandProxy().insert(kfnstAttndRecItem);
		}
		this.getEntityManager().flush();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.item.
	 * SingleAttendanceRecordRepository#deleteSingleAttendanceRecord(java.lang.
	 * String,
	 * nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode,
	 * long, long,
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
		KfnstAttndRecItem kfnstAttndRecItem = (listKfnstAttndRecItem!=null && listKfnstAttndRecItem.size()!=0) ? listKfnstAttndRecItem.get(0) : null;
		if (kfnstAttndRecItem!=null)
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
		KfnstAttndRecItem attendanceRecItemEntity = listAttndRecItemEntity==null||listAttndRecItemEntity.isEmpty() ? new KfnstAttndRecItem() : listAttndRecItemEntity.get(0);
		if(attendanceRecItemEntity.getId()==null) {
			KfnstAttndRecItemPK attendanceRecItemPK = new KfnstAttndRecItemPK(companyId, exportSettingCode.v(), columnIndex, position, exportArt,singleAttendanceRecord.getAttribute().value );
			attendanceRecItemEntity.setId(attendanceRecItemPK);
		}
		singleAttendanceRecord.saveToMemento(new JpaSingleAttendanceRecordSetMemento(kfnstAttndRec, attendanceRecItemEntity));
		int useAtrValue = useAtr ? 1 : 0;
		kfnstAttndRec.setUseAtr(new BigDecimal(useAtrValue));

		return kfnstAttndRec;
	}

	/**
	 * To attnd rec item entity.
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
		// find entity by pk
		String companyId = AppContexts.user().companyId();
//		KfnstAttndRecPK kfnstAttndRecPk = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt,
//				position);
//		KfnstAttndRec kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPk, KfnstAttndRec.class)
//				.orElse(new KfnstAttndRec());

		KfnstAttndRecItemPK kfnstAttndRecItemPk = new KfnstAttndRecItemPK(companyId, exportSettingCode.v(), columnIndex,
				position, exportArt, singleAttendanceRecord.getTimeItemId());
		KfnstAttndRecItem kfnstAttndRecItem = new KfnstAttndRecItem(kfnstAttndRecItemPk, new BigDecimal(3));

//		singleAttendanceRecord.saveToMemento(new JpaSingleAttendanceRecordSetMemento(kfnstAttndRec, kfnstAttndRecItem));
		// set formulaType
		kfnstAttndRecItem.setFormulaType(new BigDecimal(1));
		return kfnstAttndRecItem;
	}


}
