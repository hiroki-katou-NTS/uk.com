package nts.uk.ctx.pereg.infra.repository.person.itemcls.definition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.layout.classification.definition.ILayoutPersonInfoClsDefRepository;
import nts.uk.ctx.pereg.dom.person.layout.classification.definition.LayoutPersonInfoClsDefinition;
import nts.uk.ctx.pereg.infra.entity.layout.cls.definition.PpemtLayoutItemClsDf;
import nts.uk.ctx.pereg.infra.entity.layout.cls.definition.PpemtLayoutItemClsDfPk;

@Stateless
public class JpaItemClassificationDifination extends JpaRepository implements ILayoutPersonInfoClsDefRepository {

	private static final String REMOVE_ALL = "DELETE FROM PpemtLayoutItemClsDf cd";
	private static final String REMOVE_ALL_BY_LAYOUT_ID = String.join(" ", REMOVE_ALL,
			"WHERE cd.ppemtLayoutItemClsDfPk.layoutId = :layoutId");

	private static final String SELECT_ALL = "SELECT cd FROM PpemtLayoutItemClsDf cd";
	private static final String SELECT_ALL_BY_CLASSIFID = String.join(" ", SELECT_ALL,
			"WHERE cd.ppemtLayoutItemClsDfPk.layoutId = :layoutId",
			"AND cd.ppemtLayoutItemClsDfPk.layoutDispOrder = :classDispOrder",
			"ORDER BY cd.ppemtLayoutItemClsDfPk.dispOrder ASC");

	private static final String CHECK_EXIT_ITEMCLS_DF = String.join(" ", "SELECT e FROM PpemtLayoutItemClsDf e",
			"WHERE e.ppemtLayoutItemClsDfPk.layoutId = :layoutId");

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

	@Override
	public boolean checkExitItemClsDf(String layoutId) {
		List<PpemtLayoutItemClsDf> list = this.queryProxy().query(CHECK_EXIT_ITEMCLS_DF, PpemtLayoutItemClsDf.class)
				.setParameter("layoutId", layoutId).getList();
		return !list.isEmpty();
	}
}
