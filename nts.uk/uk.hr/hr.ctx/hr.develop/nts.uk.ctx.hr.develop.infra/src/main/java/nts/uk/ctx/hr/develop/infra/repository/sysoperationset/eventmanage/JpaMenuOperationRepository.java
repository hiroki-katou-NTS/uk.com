package nts.uk.ctx.hr.develop.infra.repository.sysoperationset.eventmanage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuInfoEx;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperation;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperationRepository;
import nts.uk.ctx.hr.develop.infra.entity.sysoperationset.eventoperation.JcmctMenuOperation;
import nts.uk.ctx.hr.develop.infra.entity.sysoperationset.eventoperation.JcmctMenuOperationPK;

@Stateless
public class JpaMenuOperationRepository extends JpaRepository implements MenuOperationRepository{
	
	/**
	 * find item by key
	 * @author yennth
	 */
	@Override
	public Optional<MenuOperation> findByKey(String companyId, String programId) {
		Optional<MenuOperation> result = this.queryProxy().find(new JcmctMenuOperationPK(companyId, programId), JcmctMenuOperation.class)
				.map(x -> convertToDomain(x));
		return result;
	}
	
	/**
	 * insert a item
	 * @author yennth
	 */
	@Override
	public void add(MenuOperation menuOperation) {
		this.commandProxy().insert(convertToEntity(menuOperation));
	}
	
	/**
	 * update a item
	 * @author yennth
	 */
	@Override
	public void update(MenuOperation menuOperation) {
		this.commandProxy().update(convertToEntity(menuOperation));
		
	}

	/**
	 * convert data from entity to domain
	 * @param domain x
	 * @return
	 * @author yennth
	 */
	private JcmctMenuOperation convertToEntity(MenuOperation x){
		val entity = new JcmctMenuOperation();
		entity.jcmctMenuOperationPK = new JcmctMenuOperationPK(x.getCompanyId(), x.getProgramId().v());
		entity.useApproval = x.getUseApproval().value;
		entity.useMenu = x.getUseMenu().value;
		entity.useNotice = x.getUseNotice().value;
		entity.ccd = x.getCcd();
		return entity;
	}
	
	/**
	 * convert data from entity to domain
	 * @param entity x
	 * @return
	 * @author yennth
	 */
	private MenuOperation convertToDomain(JcmctMenuOperation x){
		return MenuOperation.createFromJavaType(x.jcmctMenuOperationPK.programId, x.useMenu, x.jcmctMenuOperationPK.companyId, 
												x.useApproval, x.useNotice, x.ccd);
	}
	
	@Override
	public List<MenuInfoEx> findByApprUse(String companyId) {
		String qr = "SELECT menuO.jcmctMenuOperationPK.programId, menuD.programName, menuO.useApproval"
				+ " FROM JcmctMenuOperation menuO"
				+ " INNER JOIN JcmmtHRDevMenu menuD"
				+ " ON menuO.jcmctMenuOperationPK.programId = menuD.programId"
				+ " WHERE menuO.jcmctMenuOperationPK.companyId = :companyId"
				+ " AND menuO.useApproval = 1 and menuD.availableMenu = 1"
				+ " ORDER BY menuO.jcmctMenuOperationPK.programId ASC";
		return this.queryProxy().query(qr, Object[].class)
				.setParameter("companyId", companyId)
				.getList(c -> convert(c));
	}
	private static MenuInfoEx convert(Object[] object) {
		String programId = (String) object[0];
		String programName = (String) object[1];
		int useApproval = (int) object[2];
		return new MenuInfoEx(programId, programName, useApproval);
	}
}
