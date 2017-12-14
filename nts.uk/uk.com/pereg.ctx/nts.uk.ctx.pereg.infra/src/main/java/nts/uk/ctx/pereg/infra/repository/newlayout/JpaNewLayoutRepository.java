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
		Optional<NewLayout> update = this.getLayout(true);
		if (update.isPresent()) {
			NewLayout _update = update.get();
			_update.setLayoutCode(domain.getLayoutCode());
			_update.setLayoutName(domain.getLayoutName());

			this.commandProxy().update(toEntity(_update));
		}
	}

	@Override
	public Optional<NewLayout> getLayout(boolean createNewIfNull) {
		String companyId = AppContexts.user().companyId();
		PpemtNewLayout entity = this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class)
				.setParameter("companyId", companyId).getSingleOrNull();

		if (createNewIfNull) {
			if (entity == null) {
				// initial new data (if isn't present)
				commandProxy().insert(new PpemtNewLayout(new PpemtNewLayoutPk(IdentifierUtil.randomUniqueId()),
						companyId, "001", "レイアウト"));

				entity = this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class)
						.setParameter("companyId", companyId).getSingleOrNull();
			}
		}
		if (entity == null) {
			return Optional.empty();
		} else {

			return Optional.of(toDomain(entity));
		}

	}

	private NewLayout toDomain(PpemtNewLayout entity) {
		return NewLayout.createFromJavaType(entity.companyId, entity.ppemtNewLayoutPk.layoutId, entity.layoutCode,
				entity.layoutName);
	}

	private PpemtNewLayout toEntity(NewLayout domain) {
		PpemtNewLayoutPk primary = new PpemtNewLayoutPk(domain.getLayoutID());

		return new PpemtNewLayout(primary, domain.getCompanyId(), domain.getLayoutCode().v(),
				domain.getLayoutName().v());
	}
}
