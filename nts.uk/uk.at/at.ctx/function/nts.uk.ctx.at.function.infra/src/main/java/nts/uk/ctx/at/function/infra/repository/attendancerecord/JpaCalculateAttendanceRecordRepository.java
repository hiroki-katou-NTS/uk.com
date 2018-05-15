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
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordGetMemento;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRec;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnstAttndRecPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItemPK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItemPK_;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnstAttndRecItem_;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaCalculateAttendanceRecordRepository extends JpaRepository implements CalculateAttendanceRecordRepositoty {

	@Override
	public Optional<CalculateAttendanceRecord> getCalculateAttendanceRecord(String CompanyId, ExportSettingCode code,
			long columnIndex, long position, long exportArt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCalculateAttendanceRecord(String CompanyId, ExportSettingCode code, int columnIndex, int position,
			long exportArt, CalculateAttendanceRecord calculateAttendanceRecord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCalculateAttendanceRecord(String CompanyId, ExportSettingCode code, int columnIndex, int position,
			long exportArt, CalculateAttendanceRecord calculateAttendanceRecord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCalculateAttendanceRecord(String CompanyId, ExportSettingCode code, int columnIndex, int position,
			long exportArt, CalculateAttendanceRecord calculateAttendanceRecord) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * To domain.
	 *
	 * @param kfnstAttndRec the kfnst attnd rec
	 * @return the calculate attendance record
	 */
	private CalculateAttendanceRecord toDomain(KfnstAttndRec kfnstAttndRec) {
		// get KfnstAttndRecItem by KfnstAttndRecPK
		List<KfnstAttndRecItem> listKfnstAttndRecItem = this.findAttendanceRecordItems(AppContexts.user().companyId(),
				new ExportSettingCode(kfnstAttndRec.getId().getExportCd()), kfnstAttndRec.getId().getColumnIndex(),
				kfnstAttndRec.getId().getPosition(), kfnstAttndRec.getId().getOutputAtr());
		
		// create getMemento
		CalculateAttendanceRecordGetMemento getMemento = new JpaCalculateAttendanceRecordGetMemento(kfnstAttndRec, listKfnstAttndRecItem);
		
		return new CalculateAttendanceRecord(getMemento);

	}
	
	/**
	 * To entity attnd rec.
	 *
	 * @param exportSettingCode the export setting code
	 * @param columnIndex the column index
	 * @param position the position
	 * @param exportArt the export art
	 * @param useAtr the use atr
	 * @param calculateAttendanceRecord the calculate attendance record
	 * @return the kfnst attnd rec
	 */
	private KfnstAttndRec toEntityAttndRec(ExportSettingCode exportSettingCode, long columnIndex, long position,
			long exportArt,boolean useAtr, CalculateAttendanceRecord calculateAttendanceRecord) {
		//find entity KfnstAttndRec by pk
		String companyId = AppContexts.user().companyId();
		KfnstAttndRecPK kfnstAttndRecPk = new KfnstAttndRecPK(companyId, exportSettingCode.v(), columnIndex, exportArt, position);
		KfnstAttndRec kfnstAttndRec = this.queryProxy().find(kfnstAttndRecPk, KfnstAttndRec.class).orElse(new KfnstAttndRec());
		
		//find entity KfnstAttndRecItem by pk
		
		
		List<KfnstAttndRecItem> kfnstAttndRecItems = this.findAttendanceRecordItems(companyId, exportSettingCode, columnIndex, position, exportArt);
		//4
		calculateAttendanceRecord.saveToMemento(new JpaCalculateAttendanceRecordSetMemento(kfnstAttndRec, kfnstAttndRecItems));

		int useAtrValue = useAtr? 1 : 0;
		kfnstAttndRec.setUseAtr(new BigDecimal(useAtrValue));
		return kfnstAttndRec;
		
	}
//	private KfnstAttndRecItem toEntityAttndRecItem() {
//		return null;
//	}
	
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

}
