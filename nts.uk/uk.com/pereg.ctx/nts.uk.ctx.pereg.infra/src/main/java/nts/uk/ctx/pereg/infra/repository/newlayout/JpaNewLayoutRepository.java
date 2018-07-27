package nts.uk.ctx.pereg.infra.repository.newlayout;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayout;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayoutPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaNewLayoutRepository extends JpaRepository implements INewLayoutReposotory {
	private static final String GET_FIRST_LAYOUT = "SELECT l FROM PpemtNewLayout l WHERE l.companyId = :companyId";

	@Override
	public void save(NewLayout domain) {
		String companyId = AppContexts.user().companyId();

		Optional<PpemtNewLayout> entity = this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class)
				.setParameter("companyId", companyId).getSingle();

		if (!entity.isPresent()) {
			commandProxy().insert(new PpemtNewLayout(new PpemtNewLayoutPk(domain.getLayoutID()), domain.getCompanyId(),
					domain.getLayoutCode().v(), domain.getLayoutName().v()));
		} else {
			entity.ifPresent(ent -> {
				ent.layoutCode = domain.getLayoutCode().v();
				ent.layoutName = domain.getLayoutName().v();

				this.commandProxy().update(ent);
			});
		}
	}

	@Override
	public Optional<NewLayout> getLayout() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class).setParameter("companyId", companyId)
				.getSingle().map(m -> toDomain(m));
	}

	private NewLayout toDomain(PpemtNewLayout entity) {
		return NewLayout.createFromJavaType(entity.companyId, entity.ppemtNewLayoutPk.layoutId, entity.layoutCode,
				entity.layoutName);
	}
}
