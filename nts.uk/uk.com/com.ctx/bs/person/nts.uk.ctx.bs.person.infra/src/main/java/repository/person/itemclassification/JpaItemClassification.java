package repository.person.itemclassification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.person.itemclassification.PpemtLayoutItemCls;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutPersonInfoClassification;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.ILayoutPersonInfoClsRepository;

@Stateless
public class JpaItemClassification extends JpaRepository implements ILayoutPersonInfoClsRepository {

	private static final String GET_ALL_ITEM_CLASSIFICATION;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM PpemtLayoutItemCls e");
		builderString.append(" WHERE e.ppemtLayoutItemClsPk.layoutId = :layoutId ORDERBY e.ppemtLayoutItemClsPk.dispOrder ASC ");
		GET_ALL_ITEM_CLASSIFICATION = builderString.toString();
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
	public List<PersonInfoItemDefinition> getAllPerInfoItemDefByLayoutId(String layoutId) {
		return null;
	}

}
