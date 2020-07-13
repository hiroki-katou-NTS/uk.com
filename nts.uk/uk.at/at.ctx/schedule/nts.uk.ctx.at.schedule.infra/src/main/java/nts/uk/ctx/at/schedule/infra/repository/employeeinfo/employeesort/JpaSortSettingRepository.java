package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.employeesort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.employeesort.KscmtSyaOrderPriority;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.employeesort.KscmtSyaOrderPriorityPk;

/**
 * 
 * @author HieuLt
 *
 */
@Stateless
public class JpaSortSettingRepository extends JpaRepository implements SortSettingRepository{

	@Override
	public void insert(SortSetting SortSetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SortSetting SortSetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<SortSetting> get(String companyID) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<KscmtSyaOrderPriority> toEntity(SortSetting domain){
		KscmtSyaOrderPriorityPk pk = new KscmtSyaOrderPriorityPk(domain.getCompanyID(), domain.getLstOrderedList().size());
		
		// Đang để tạm PRIORITY là 1 vì ko biết lấy ở đâu
		List<KscmtSyaOrderPriority> entity = domain.getLstOrderedList().stream().map(mapper-> new KscmtSyaOrderPriority(
				new KscmtSyaOrderPriorityPk(domain.getCompanyID(), 1)
				, mapper.getSortOrder().value, mapper.getType().value)).collect(Collectors.toList());
		
		return entity;
		
	}
	
}
