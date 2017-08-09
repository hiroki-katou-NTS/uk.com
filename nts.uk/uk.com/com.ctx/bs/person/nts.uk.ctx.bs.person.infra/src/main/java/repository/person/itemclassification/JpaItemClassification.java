package repository.person.itemclassification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.person.itemclassification.PpemtLayoutItemCls;
import lombok.val;
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

	private LayoutPersonInfoClassification convertToDomain(PpemtLayoutItemCls entity) {
		val domain = LayoutPersonInfoClassification.createFromJaveType(entity.ppemtLayoutItemClsPk.layoutId,
				Integer.parseInt(entity.ppemtLayoutItemClsPk.dispOrder), entity.categoryId,
				Integer.parseInt(entity.itemType));
		return domain;
	}

	@Override
	public List<LayoutPersonInfoClassification> getAllItemClsById(String layoutId) {
		List<PpemtLayoutItemCls> resultList = this.queryProxy()
				.query(GET_ALL_ITEM_CLASSIFICATION, PpemtLayoutItemCls.class).setParameter("layoutId", layoutId)
				.getList();
		System.out.println(resultList);
		return !resultList.isEmpty() ? resultList.stream().map(item -> {
			return convertToDomain(item);
		}).collect(Collectors.toList()) : new ArrayList<>();
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

}
