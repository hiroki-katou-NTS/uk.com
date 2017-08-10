package repository.person.itemclassification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.itemclassification.PpemtLayoutItemCls;
import entity.itemclassification.PpemtLayoutItemClsPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutPersonInfoClassification;

@Stateless
public class JpaItemClassification extends JpaRepository implements ILayoutPersonInfoClsRepository {

	private static final String GET_ALL_ITEM_CLASSIFICATION;

	private static final String GET_ALL_ITEM_DIFINATION;

	private static final String GET_ONE_ITEM_DIFINATION;

	static {
		StringBuilder builderString = new StringBuilder();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM PpemtLayoutItemCls e");
		builderString.append(" WHERE e.ppemtLayoutItemClsPk.layoutId = :layoutId ");
		builderString.append(" ORDER BY e.ppemtLayoutItemClsPk.dispOrder ASC ");
		GET_ALL_ITEM_CLASSIFICATION = builderString.toString();

		builderString = new StringBuilder();

		builderString.append("SELECT e.ppemtPerInfoItemPK.perInfoItemDefId");
		builderString.append(" FROM PpemtLayoutItemClsDf d");
		builderString.append(" INNER JOIN PpemtPerInfoItem e");
		builderString.append(" ON d.itemDfID = e.ppemtPerInfoItemPK.perInfoItemDefId");
		builderString.append(" AND d.ppemtLayoutItemClsDfPk.layoutId = :layoutId");
		builderString.append(" AND d.ppemtLayoutItemClsDfPk.layoutDispOrder = :disPOrder");
		GET_ALL_ITEM_DIFINATION = builderString.toString();

		builderString = new StringBuilder();

		builderString.append("SELECT e.ppemtPerInfoItemPK.perInfoItemDefId");
		builderString.append(" FROM PpemtLayoutItemClsDf d");
		builderString.append(" INNER JOIN PpemtPerInfoItem e");
		builderString.append(" ON d.itemDfID = e.ppemtPerInfoItemPK.perInfoItemDefId");
		builderString.append(" AND d.ppemtLayoutItemClsDfPk.layoutId = :layoutId");
		builderString.append(" AND d.ppemtLayoutItemClsDfPk.layoutDispOrder = :disPOrder");
		GET_ONE_ITEM_DIFINATION = builderString.toString();

	}

	@Override
	public List<LayoutPersonInfoClassification> getAllItemClsById(String layoutId) {
		List<PpemtLayoutItemCls> resultList = this.queryProxy()
				.query(GET_ALL_ITEM_CLASSIFICATION, PpemtLayoutItemCls.class).setParameter("layoutId", layoutId)
				.getList();

		if (resultList.isEmpty()) {
			return new ArrayList<LayoutPersonInfoClassification>();
		}

		return resultList.stream().map(item -> toDomain(item)).collect(Collectors.toList());
	}

	@Override
	public List<String> getAllItemDefIdByLayoutId(String layoutId, String disPOrder) {
		List<Object> resultList = this.queryProxy().query(GET_ALL_ITEM_DIFINATION, Object.class)
				.setParameter("layoutId", layoutId).setParameter("disPOrder", disPOrder).getList();

		return !resultList.isEmpty() ? resultList.stream().map(item -> item.toString()).collect(Collectors.toList())
				: new ArrayList<>();
	}

	@Override
	public String getOneItemDfId(String layoutId, String disPOrder) {
		Object obj = this.queryProxy().query(GET_ONE_ITEM_DIFINATION, Object.class).setParameter("layoutId", layoutId)
				.setParameter("disPOrder", disPOrder).getSingleOrNull();
		if (obj != null) {
			return obj.toString();
		} else {
			return null;
		}
	}

	@Override
	public void addListItemCls(List<LayoutPersonInfoClassification> listItemCls) {
		List<PpemtLayoutItemCls> list = listItemCls.stream().map(item -> toEntity(item)).collect(Collectors.toList());
		this.commandProxy().insertAll(list);
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
