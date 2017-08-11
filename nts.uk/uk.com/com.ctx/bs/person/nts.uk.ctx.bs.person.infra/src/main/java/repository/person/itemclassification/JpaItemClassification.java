package repository.person.itemclassification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.layout.classification.PpemtLayoutItemCls;
import entity.layout.classification.PpemtLayoutItemClsPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutPersonInfoClassification;

@Stateless
public class JpaItemClassification extends JpaRepository implements ILayoutPersonInfoClsRepository {

	private static final String REMOVE_ALL_BY_LAYOUT_ID = "DELETE FROM PpemtLayoutItemCls c WHERE c.ppemtLayoutItemClsPk.layoutId = :layoutId";
	private static final String GET_ALL_ITEM_CLASSIFICATION = "SELECT c FROM PpemtLayoutItemCls c WHERE c.ppemtLayoutItemClsPk.layoutId = :layoutId ORDER BY c.ppemtLayoutItemClsPk.dispOrder ASC";

	@Override
	public List<LayoutPersonInfoClassification> getAllByLayoutId(String layoutId) {
		List<PpemtLayoutItemCls> resultList = this.queryProxy()
				.query(GET_ALL_ITEM_CLASSIFICATION, PpemtLayoutItemCls.class).setParameter("layoutId", layoutId)
				.getList();

		if (resultList.isEmpty()) {
			return new ArrayList<LayoutPersonInfoClassification>();
		}

		return resultList.stream().map(item -> toDomain(item)).collect(Collectors.toList());
	}

	@Override
	public void addClassifications(List<LayoutPersonInfoClassification> domains) {
		// add all classification to db when save layout
		List<PpemtLayoutItemCls> entities = domains.stream().map(item -> toEntity(item)).collect(Collectors.toList());
		commandProxy().insertAll(entities);
	}

	@Override
	public void removeAllByLayoutId(String layoutId) {
		// remove all classifications when update or override layout
		getEntityManager().createQuery(REMOVE_ALL_BY_LAYOUT_ID).setParameter("layoutId", layoutId).executeUpdate();
	}

	private LayoutPersonInfoClassification toDomain(PpemtLayoutItemCls entity) {
		return LayoutPersonInfoClassification.createFromJaveType(entity.ppemtLayoutItemClsPk.layoutId,
				entity.ppemtLayoutItemClsPk.dispOrder, entity.categoryId, entity.itemType);
	}

	private PpemtLayoutItemCls toEntity(LayoutPersonInfoClassification domain) {
		PpemtLayoutItemClsPk primaryKey = new PpemtLayoutItemClsPk(domain.getLayoutID(), domain.getDispOrder().v());
		return new PpemtLayoutItemCls(primaryKey, domain.getPersonInfoCategoryID(), domain.getLayoutItemType().value);
	}
}
