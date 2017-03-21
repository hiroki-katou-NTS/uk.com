package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttend;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemAttend;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemAttendPK;

@RequestScoped
@Transactional
public class JpaItemAttendRepository extends JpaRepository implements ItemAttendRespository {
	private final String SEL = "SELECT c FROM QcamtItemAttend c";
	private final String SEL_1 = SEL + " WHERE c.qcamtItemAttendPK.ccd = :companyCode";

	@Override
	public Optional<ItemAttend> find(String companyCode, String itemCode) {
		return this.queryProxy().find(new QcamtItemAttendPK(companyCode, itemCode), QcamtItemAttend.class)
				.map(x -> toDomain(x));
	}

	@Override
	public List<ItemAttend> findAll(String companyCode) {
		return this.queryProxy().query(SEL_1, QcamtItemAttend.class)
				.setParameter("companyCode", companyCode)
				.getList(c -> toDomain(c));
	}
	
	@Override
	public void update(ItemAttend item) {
		this.commandProxy().update(toEntity(item));
	}
	
	private ItemAttend toDomain(QcamtItemAttend entity) {

		val domain = ItemAttend.createFromJavaType(entity.qcamtItemAttendPK.ccd, entity.qcamtItemAttendPK.itemCd, entity.avePayAtr, entity.itemAtr, entity.errRangeLowAtr,
				entity.errRangeLow, entity.errRangeHighAtr, entity.errRangeHigh, entity.alRangeLowAtr,
				entity.alRangeLow, entity.alRangeHighAtr, entity.alRangeHigh, entity.workDaysScopeAtr, entity.memo);
		// TODO Auto-generated method stub
		return domain;
	}

	/**
	 * Convert domain to entity object
	 * @param companyCode company code
	 * @param domain domain object
	 * @return QcamtItemAttend
	 */
	private QcamtItemAttend toEntity(ItemAttend domain) {
		return new QcamtItemAttend(
				new QcamtItemAttendPK(domain.getCompanyCode().v(), domain.getItemCode().v()), 
				domain.getAvePayAtr().value, 
				domain.getItemAtr().value, 
				domain.getErrRangeLowAtr().value, 
				domain.getErrRangeLow().v(), 
				domain.getErrRangeHighAtr().value, 
				domain.getErrRangeHigh().v(), 
				domain.getAlRangeLowAtr().value, 
				domain.getAlRangeLow().v(), 
				domain.getAlRangeHighAtr().value, 
				domain.getAlRangeHigh().v(), 
				domain.getWorkDaysScopeAtr().value, 
				domain.getMemo().v());
	}

	@Override
	public void add(ItemAttend itemAttend) {
		this.commandProxy().insert(toEntity(itemAttend));
		
	}

	@Override
	public void delete(String companyCode, String itemCode) {
		this.commandProxy().remove(QcamtItemAttend.class, new QcamtItemAttendPK(companyCode, itemCode));
		
	}
}
