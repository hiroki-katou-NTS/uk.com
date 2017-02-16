package nts.uk.ctx.pr.core.infra.repository.retirement.payitem;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItem;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItemRepository;
import nts.uk.ctx.pr.core.infra.entity.retirement.payitem.QremtRetirePayItem;
import nts.uk.ctx.pr.core.infra.entity.retirement.payitem.QremtRetirePayItemPK;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
@Transactional
public class JpaRetirementPayItemRepository extends JpaRepository implements RetirementPayItemRepository{
	private final String findAll = "SELECT a FROM QremtRetirePayItem a";
	private final String find_By_companyCode = findAll + " WHERE a.qremtRetirePayItemPK.companyCode = :companyCode";
	@Override
	public void add(RetirementPayItem payItem) {
		// TODO Auto-generated method stub
		this.commandProxy().insert(convertToEntity(payItem));
	}
	
	@Override
	public List<RetirementPayItem> findAll() {
		// TODO Auto-generated method stub
		return this.queryProxy().query(findAll, QremtRetirePayItem.class)
				.getList(x -> RetirementPayItem.createFromJavaType(
						x.qremtRetirePayItemPK.companyCode, 
						x.qremtRetirePayItemPK.category, 
						x.qremtRetirePayItemPK.itemCode, 
						x.itemName, 
						x.printName, 
						x.englishName, 
						x.fullName, 
						x.memo));
	}
	
	@Override
	public List<RetirementPayItem> findByCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(find_By_companyCode, QremtRetirePayItem.class).setParameter("companyCode", companyCode.v())
				.getList(x -> RetirementPayItem.createFromJavaType(
						x.qremtRetirePayItemPK.companyCode, 
						x.qremtRetirePayItemPK.category, 
						x.qremtRetirePayItemPK.itemCode, 
						x.itemName, 
						x.printName, 
						x.englishName, 
						x.fullName, 
						x.memo));
	}
	
	@Override
	public void update(RetirementPayItem payItem) {
		// TODO Auto-generated method stub
		this.commandProxy().update(convertToEntity(payItem));
	}
	
	@Override
	public void remove(RetirementPayItem payItem) {
		// TODO Auto-generated method stub
		//this.commandProxy().remove(itemSetting);
		this.commandProxy().remove(
				QremtRetirePayItem.class, 
				new QremtRetirePayItemPK(
						payItem.getCompanyCode().v(),
						payItem.getCategory().value,
						payItem.getItemCode().v()
				)
		);
	}
	
	private QremtRetirePayItem convertToEntity(RetirementPayItem payItem) {
		QremtRetirePayItem entity = new QremtRetirePayItem(
				new QremtRetirePayItemPK(payItem.getCompanyCode().v(), payItem.getCategory().value, payItem.getItemCode().v()), 
				payItem.getItemName().v(), 
				payItem.getPrintName().v(), 
				payItem.getEnglishName().v(), 
				payItem.getFullName().v(), 
				payItem.getMemo().v());
		return entity;
	}
}
