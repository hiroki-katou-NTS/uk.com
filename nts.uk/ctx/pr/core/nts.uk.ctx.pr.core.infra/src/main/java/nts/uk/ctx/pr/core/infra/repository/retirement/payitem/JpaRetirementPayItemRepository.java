package nts.uk.ctx.pr.core.infra.repository.retirement.payitem;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItem;
import nts.uk.ctx.pr.core.dom.retirement.payitem.RetirementPayItemRepository;
import nts.uk.ctx.pr.core.infra.entity.retirement.payitem.QremtRetirePayItem;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class JpaRetirementPayItemRepository extends JpaRepository implements RetirementPayItemRepository{
	private final String select_no_where = "select a from QremtRetirePayItem a";
	
	@Override
	public void add(RetirementPayItem itemSetting) {
		// TODO Auto-generated method stub
		this.commandProxy().insert(itemSetting);
	}
	
	@Override
	public List<RetirementPayItem> findAll() {
		// TODO Auto-generated method stub
		return this.queryProxy().query(select_no_where, QremtRetirePayItem.class)
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
	public void update(RetirementPayItem itemSetting) {
		// TODO Auto-generated method stub
		this.commandProxy().update(itemSetting);
	}
	
	@Override
	public void remove(RetirementPayItem itemSetting) {
		// TODO Auto-generated method stub
		this.commandProxy().remove(itemSetting);
	}
}
