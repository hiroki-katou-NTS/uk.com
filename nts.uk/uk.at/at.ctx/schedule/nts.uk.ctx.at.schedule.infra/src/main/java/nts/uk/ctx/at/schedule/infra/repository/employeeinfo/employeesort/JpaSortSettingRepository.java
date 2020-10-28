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
		List<Integer> entities = this.queryProxy()
				.query("Select a.pk.priority From KscmtSyaOrderPriority a "
						+ "Where a.pk.companyId = :companyId Order by a.pk.priority asc", Integer.class)
				.setParameter("companyId", domain.getCompanyID()).getList();
		List<KscmtSyaOrderPriority> entitiesNew = new ArrayList<>();

		for (int i = 0; i < domain.getOrderedList().size(); i++) {
			KscmtSyaOrderPriority priority = new KscmtSyaOrderPriority(
					new KscmtSyaOrderPriorityPk(domain.getCompanyID(), i + 1),
					domain.getOrderedList().get(i).getType().value,
					domain.getOrderedList().get(i).getSortOrder().value);
			entitiesNew.add(priority);
		}

		// deleteAll
		if (domain.getOrderedList().isEmpty()) {
			String deleteAll = "delete from KscmtSyaOrderPriority o " + " where o.pk.companyId = :companyId ";
			this.getEntityManager().createQuery(deleteAll).setParameter("companyId", domain.getCompanyID())
					.executeUpdate();
		}
		// insert
		else if (entitiesNew.size() > entities.size()) {
			List<KscmtSyaOrderPriority> updates = entitiesNew.subList(0, entities.size());
			List<KscmtSyaOrderPriority> inserts = entitiesNew.subList(entities.isEmpty() ? 0 : entities.size(),
					entitiesNew.size());
			if (!inserts.isEmpty()) {
				this.commandProxy().insertAll(inserts);
			}
			if (!updates.isEmpty()) {
				String update = "select o from KscmtSyaOrderPriority o " + " where o.pk.companyId = :companyId "
						+ " and o.pk.priority IN :priority Order by o.pk.priority asc";
				List<KscmtSyaOrderPriority> entitieUpdate = this.queryProxy().query(update, KscmtSyaOrderPriority.class)
						.setParameter("companyId", entitiesNew.get(0).pk.companyId)
						.setParameter("priority", updates.stream().map(i -> i.pk.priority).collect(Collectors.toList()))
						.getList();
				for (int i = 0; i < updates.size(); i++) {
					entitieUpdate.get(i).itemType = updates.get(i).itemType;
					entitieUpdate.get(i).orderDirection = updates.get(i).orderDirection;
					this.commandProxy().update(entitieUpdate.get(i));
				}
			}

		}
		// update
		else if (entitiesNew.size() == entities.size()) {
			List<KscmtSyaOrderPriority> entitieUpdate = this.queryProxy().query(GET_ALL, KscmtSyaOrderPriority.class)
					.setParameter("companyId", domain.getCompanyID()).getList();
			for (int i = 0; i < entitiesNew.size(); i++) {
				entitieUpdate.get(i).itemType = entitiesNew.get(i).itemType;
				entitieUpdate.get(i).orderDirection = entitiesNew.get(i).orderDirection;
			}

		}
		// delete
		else if (entitiesNew.size() < entities.size()) {
			List<Integer> deletes = entities.stream()
					.filter(i -> !entitiesNew.stream().map(o -> o.pk.priority).collect(Collectors.toList()).contains(i))
					.collect(Collectors.toList());
			deletes.forEach(x -> {
				String delete = "delete from KscmtSyaOrderPriority o " + " where o.pk.companyId = :companyId "
						+ " and o.pk.priority = :priority ";
				this.getEntityManager().createQuery(delete).setParameter("companyId", entitiesNew.get(0).pk.companyId)
						.setParameter("priority", x).executeUpdate();
			});
			String update = "select o from KscmtSyaOrderPriority o " + " where o.pk.companyId = :companyId "
					+ " and o.pk.priority IN :priority Order by o.pk.priority asc";
			List<KscmtSyaOrderPriority> entitieUpdate = this.queryProxy().query(update, KscmtSyaOrderPriority.class)
					.setParameter("companyId", entitiesNew.get(0).pk.companyId)
					.setParameter("priority", entitiesNew.stream().map(i -> i.pk.priority).collect(Collectors.toList()))
					.getList();
			for (int i = 0; i < entitiesNew.size(); i++) {
				entitieUpdate.get(i).itemType = entitiesNew.get(i).itemType;
				entitieUpdate.get(i).orderDirection = entitiesNew.get(i).orderDirection;
				this.commandProxy().update(entitieUpdate.get(i));
			}

		}

		// for (KscmtSyaOrderPriority entity : entities) {
		// Optional<KscmtSyaOrderPriority> chek = entitiesNew.stream().filter(x
		// -> x.pk.priority == entity.pk.priority)
		// .findFirst();
		// if (chek.isPresent()) {
		// entity.itemType = chek.get().itemType;
		// entity.orderDirection = chek.get().orderDirection;
		// this.commandProxy().update(entity);
		// } else {
		// KscmtSyaOrderPriority dataChange = new KscmtSyaOrderPriority(
		// new KscmtSyaOrderPriorityPk(domain.getCompanyID(),
		// chek.get().pk.priority + 1),
		// chek.get().itemType, chek.get().orderDirection);
		// this.commandProxy().insert(dataChange);
		// }
		//
		// }
		//
		// for (KscmtSyaOrderPriority entity : entitiesNew) {
		// List<KscmtSyaOrderPriority> chekDel = entities.stream().filter(x ->
		// x.pk.priority != entity.pk.priority)
		// .collect(Collectors.toList());
		// if (!chekDel.isEmpty()) {
		// chekDel.forEach(x -> {
		// String delete = "delete from KscmtSyaOrderPriority o " + " where
		// o.pk.companyId = :companyId "
		// + " and o.pk.priority = :priority ";
		// this.getEntityManager().createQuery(delete).setParameter("companyId",
		// x.pk.companyId)
		// .setParameter("priority", x.pk.priority).executeUpdate();
		// });
		// }
		// }

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
