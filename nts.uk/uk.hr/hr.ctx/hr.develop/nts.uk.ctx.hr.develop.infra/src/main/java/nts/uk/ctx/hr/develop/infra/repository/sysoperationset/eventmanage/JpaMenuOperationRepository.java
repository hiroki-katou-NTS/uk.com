package nts.uk.ctx.hr.develop.infra.repository.sysoperationset.eventmanage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
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
												x.useApproval, x.useNotice, x.noRankOrder, x.ccd);
	}
	
	@Override
	@SneakyThrows
	public List<MenuInfoEx> findByApprUse(String companyId) {
		String qr = "select menuO.PROGRAM_ID, PROGRAM_NAME, USE_APPROVAL, menuO.NO_RANK_ORDER from JCMST_MENU_OPERATION menuO inner join" + 
				"	( select PROGRAM_ID, PROGRAM_NAME from JCMMT_HRDEV_MENU where AVAILABLE_APPROVAL = 1 and AVAILABLE = 1 and EVENT_ID in " + 
				"		(select EVENT_ID from JCMST_EVENT_OPERATION WHERE CID = 'companyId' and USE_EVENT = 1)" + 
				"	) op" + 
				"	on menuO.PROGRAM_ID = op.PROGRAM_ID" + 
				"	where  CID = 'companyId' and USE_APPROVAL = 1  ";
		qr = qr.replaceAll("companyId", companyId);
		List<MenuInfoEx> lst = new ArrayList<>();
		try (PreparedStatement pstatement = this.connection().prepareStatement(qr)) {
			lst =  cv(pstatement.executeQuery());
		}
		return lst;
	}
	private static List<MenuInfoEx> cv(ResultSet rs) {
		return new NtsResultSet(rs).getList(x -> {
			return new MenuInfoEx(
					x.getString("PROGRAM_ID"), 
					x.getString("PROGRAM_NAME"), 
					x.getInt("USE_APPROVAL"),
					x.getInt("NO_RANK_ORDER"));
		});
	}
}
