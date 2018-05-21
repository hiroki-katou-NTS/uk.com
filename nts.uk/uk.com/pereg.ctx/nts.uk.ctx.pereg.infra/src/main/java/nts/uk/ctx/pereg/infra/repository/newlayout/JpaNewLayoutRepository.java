package nts.uk.ctx.pereg.infra.repository.newlayout;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayout;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayoutPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaNewLayoutRepository extends JpaRepository implements INewLayoutReposotory {
	private static final String GET_FIRST_LAYOUT = "SELECT l FROM  PpemtNewLayout l WHERE l.companyId = :companyId";

	@Override
	public void update(NewLayout domain) {
		String companyId = AppContexts.user().companyId();
		Optional<NewLayout> update = this.getLayoutWithCreateNew();

		if (update.isPresent()) {
			PpemtNewLayout entity = this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class)
					.setParameter("companyId", companyId).getSingleOrNull();

			if (entity != null) {
				entity.layoutCode = domain.getLayoutCode().v();
				entity.layoutName = domain.getLayoutName().v();

				this.commandProxy().update(entity);
			}
		}
	}
	
	

	@Override
	public Optional<NewLayout> getLayoutWithCreateNew() {
		String companyId = AppContexts.user().companyId();
		PpemtNewLayout entity = this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class)
				.setParameter("companyId", companyId).getSingleOrNull();

		if (entity == null) {
			// initial new data (if isn't present)
			commandProxy().insert(new PpemtNewLayout(new PpemtNewLayoutPk(IdentifierUtil.randomUniqueId()),
					companyId, "001", "レイアウト"));

			entity = this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class)
					.setParameter("companyId", companyId).getSingleOrNull();
		}

		return Optional.of(toDomain(entity));
	}

	private NewLayout toDomain(PpemtNewLayout entity) {
		return NewLayout.createFromJavaType(entity.companyId, entity.ppemtNewLayoutPk.layoutId, entity.layoutCode,
				entity.layoutName);
	}



	@Override
	public Optional<NewLayout> getLayout() {
		String companyId = AppContexts.user().companyId();
		PpemtNewLayout entity = this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class)
				.setParameter("companyId", companyId).getSingleOrNull();
		if (entity == null) {
			Optional.empty();
		}
		return Optional.of(toDomain(entity));
	}
}
