package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.employeesort;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.OrderedList;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortOrder;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.employeesort.KscmtSyaOrderPriority;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.employeesort.KscmtSyaOrderPriorityPk;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortOrder;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortType;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.employeesort.KscmtSyaOrderPriority;

/**
 * 
 * @author HieuLt
 *
 */
@Stateless
public class JpaSortSettingRepository extends JpaRepository implements SortSettingRepository {

	private static final String GET_ALL = "Select a From KscmtSyaOrderPriority a "
			+ " Where a.pk.companyId = :companyId Order by a.pk.priority asc";

	private static final String DELETE = "DELETE FROM KSCMT_SYA_ORDER_PRIORITY WHERE CID = ?";

	@Override
	public void insert(SortSetting domain) {
		this.commandProxy().insertAll(KscmtSyaOrderPriority.toEntity(domain));

	}

	@Override
	public void update(SortSetting domain) {
		List<KscmtSyaOrderPriority> entitiesNew = new ArrayList<>();

		for (int i = 0; i < domain.getOrderedList().size(); i++) {
			KscmtSyaOrderPriority priority = new KscmtSyaOrderPriority(
					new KscmtSyaOrderPriorityPk(domain.getCompanyID(), i + 1),
					domain.getOrderedList().get(i).getType().value,
					domain.getOrderedList().get(i).getSortOrder().value);
			entitiesNew.add(priority);
		}

		delete(domain.getCompanyID());
		this.commandProxy().insertAll(entitiesNew);

	}

	@SneakyThrows
	@Override
	public void delete(String companyID) {
		PreparedStatement ps1 = this.connection().prepareStatement(DELETE);
		ps1.setString(1, companyID);
		ps1.executeUpdate();

	}

	@Override
	public Optional<SortSetting> get(String companyID) {
		List<KscmtSyaOrderPriority> results = this.queryProxy().query(GET_ALL, KscmtSyaOrderPriority.class)
				.setParameter("companyId", companyID).getList();
		if (results.isEmpty()) {
			return Optional.empty();
		}
		List<OrderedList> orderedList = results.stream()
				.map(i -> new OrderedList(SortType.valueOf(i.itemType), SortOrder.valueOf(i.orderDirection)))
				.collect(Collectors.toList());
		return Optional.of(SortSetting.create(companyID, orderedList));
	}

}
