package nts.uk.ctx.at.schedule.infra.repository.shift.specificdaysetting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.specificdate.KscmtSpecDateItem;

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
		List<SpecificDateItem> resultList = new ArrayList<>();
/*		TODO dev fix
 * 		CollectionUtil.split(lstSpecificDateItem, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(GET_BY_LIST_CODE, KscmtSpecDateItem.class)
								  .setParameter("companyId", companyId)
								  .setParameter("lstSpecificDateItem", subList)
								  .getList(c->toBonusPaySettingDomain(c)));
		});*/
		return resultList;
	}

	@Override
	public void update(SpecificDateItem domain) {
		// TODO Auto-generated method stub
		
	}

}
