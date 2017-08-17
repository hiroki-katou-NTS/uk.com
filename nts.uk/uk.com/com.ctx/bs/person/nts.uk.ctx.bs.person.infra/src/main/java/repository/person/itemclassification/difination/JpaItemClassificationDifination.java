package repository.person.itemclassification.difination;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.layout.classification.difination.PpemtLayoutItemClsDf;
import entity.layout.classification.difination.PpemtLayoutItemClsDfPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.definition.ILayoutPersonInfoClsDefRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.definition.LayoutPersonInfoClsDefinition;

@Stateless
public class JpaItemClassificationDifination extends JpaRepository implements ILayoutPersonInfoClsDefRepository {

	private static final String REMOVE_ALL = "DELETE FROM PpemtLayoutItemClsDf cd";
	private static final String REMOVE_ALL_BY_LAYOUT_ID = REMOVE_ALL
			+ " WHERE cd.ppemtLayoutItemClsDfPk.layoutId = :layoutId";

	private static final String SELECT_ALL = "SELECT cd FROM PpemtLayoutItemClsDf cd";
	private static final String SELECT_ALL_BY_CLASSIFID = SELECT_ALL
			+ " WHERE cd.ppemtLayoutItemClsDfPk.layoutId = :layoutId"
			+ " AND cd.ppemtLayoutItemClsDfPk.layoutDispOrder = :classDispOrder";

	@Override
	public List<String> getAllItemDefineIds(String layoutId, int classDispOrder) {
		return queryProxy().query(SELECT_ALL_BY_CLASSIFID, PpemtLayoutItemClsDf.class)
				.setParameter("layoutId", layoutId).setParameter("classDispOrder", classDispOrder).getList().stream()
				.map(m -> m.itemDfID).collect(Collectors.toList());
	}

	@Override
	public void removeAllByLayoutId(String layoutId) {
		getEntityManager().createQuery(REMOVE_ALL_BY_LAYOUT_ID).setParameter("layoutId", layoutId).executeUpdate();
	}

	@Override
	public void addClassificationItemDefines(List<LayoutPersonInfoClsDefinition> domains) {
		commandProxy().insertAll(domains.stream().map(m -> toEntity(m)).collect(Collectors.toList()));
	}

	public PpemtLayoutItemClsDf toEntity(LayoutPersonInfoClsDefinition domain) {
		PpemtLayoutItemClsDfPk ppemtLayoutItemClsDfPk = new PpemtLayoutItemClsDfPk(domain.getLayoutID(),
				domain.getLayoutDisPOrder().v(), domain.getDispOrder().v());

		return new PpemtLayoutItemClsDf(ppemtLayoutItemClsDfPk, domain.getPersonInfoItemDefinitionID());
	}
}
