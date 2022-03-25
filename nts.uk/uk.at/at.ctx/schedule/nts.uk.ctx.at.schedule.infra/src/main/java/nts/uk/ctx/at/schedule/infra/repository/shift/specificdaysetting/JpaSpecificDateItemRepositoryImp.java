package nts.uk.ctx.at.schedule.infra.repository.shift.specificdaysetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.specificdate.KscmtSpecDateItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.specificdate.KsmstSpecificDateItemPK;

@Stateless
public class JpaSpecificDateItemRepositoryImp extends JpaRepository implements SpecificDateItemRepository {

	private static final String SELECT_NO_WHERE = "SELECT s FROM KscmtSpecDateItem s";
	private static final String GET_ALL = SELECT_NO_WHERE
			+ " WHERE s.ksmstSpecificDateItemPK.companyId = :companyId";
	private static final String GET_BY_USE = SELECT_NO_WHERE
			+" WHERE s.ksmstSpecificDateItemPK.companyId = :companyId"
			+" AND s.useAtr = :useAtr";
	private static final String GET_BY_LIST_CODE = GET_ALL 
			+" AND s.ksmstSpecificDateItemPK.itemNo IN :lstSpecificDateItem";
	/**
	 * Entity to Domain
	 * 
	 * @param ksmstSpecificDateItem
	 * @return
	 */
	private SpecificDateItem toBonusPaySettingDomain(KscmtSpecDateItem ksmstSpecificDateItem) {
		return SpecificDateItem.createFromJavaType(ksmstSpecificDateItem.ksmstSpecificDateItemPK.companyId,
				ksmstSpecificDateItem.useAtr,
				ksmstSpecificDateItem.ksmstSpecificDateItemPK.itemNo, ksmstSpecificDateItem.name);
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<SpecificDateItem> getAll(String companyId) {
		return this.queryProxy().query(GET_ALL, KscmtSpecDateItem.class).setParameter("companyId", companyId)
				.getList(x -> this.toBonusPaySettingDomain(x));
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<SpecificDateItem> getByUseAtr(String companyId, int useAtr) {
		return this.queryProxy().query(GET_BY_USE, KscmtSpecDateItem.class).setParameter("companyId", companyId)
				.setParameter("useAtr", useAtr).getList(x -> this.toBonusPaySettingDomain(x));
	}
    
	/**
	 * get list Specifi Date By List Code
	 * @param companyId
	 * @param lstSpecificDateItem
	 * @return
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<SpecificDateItem> getSpecifiDateByListCode(String companyId, List<SpecificDateItemNo> lstSpecificDateItem) {
    	List<Integer> lstSpecificDateItemNo = lstSpecificDateItem.stream()
    			.map(t -> t.v())
    			.collect(Collectors.toList());
    	return this.queryProxy().query(GET_BY_LIST_CODE, KscmtSpecDateItem.class).setParameter("companyId", companyId)
				.setParameter("lstSpecificDateItem", lstSpecificDateItemNo).getList(x -> this.toBonusPaySettingDomain(x));
	}

	@Override
	public void update(SpecificDateItem domain) {
		this.commandProxy().update(toEntity(domain));
	}
	
	private KscmtSpecDateItem toEntity(SpecificDateItem domain) {
		return new KscmtSpecDateItem(
				new KsmstSpecificDateItemPK(domain.getCompanyId(), domain.getSpecificDateItemNo().v()),
				domain.getUseAtr().value,
				domain.getSpecificName().v()
				);
	}

}
