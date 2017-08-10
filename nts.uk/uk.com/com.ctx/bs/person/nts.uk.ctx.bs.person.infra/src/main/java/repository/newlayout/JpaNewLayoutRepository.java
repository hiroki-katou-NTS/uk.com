package repository.newlayout;

import java.util.Optional;

import javax.ejb.Stateless;

import entity.newlayout.PpemtNewLayout;
import entity.newlayout.PpemtNewLayoutPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.bs.person.dom.person.layout.NewLayout;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaNewLayoutRepository extends JpaRepository implements INewLayoutReposotory {

	private static final String GET_FIRST_LAYOUT = "SELECT l FROM  PpemtNewLayout l";

	@Override
	public void update(NewLayout domain) {
		Optional<NewLayout> update = this.getLayout();
		if (update.isPresent()) {
			NewLayout _update = update.get();
			_update.setLayoutCode(domain.getLayoutCode());
			_update.setLayoutName(domain.getLayoutName());

			this.commandProxy().update(toEntity(_update));
		}
	}

	@Override
	public Optional<NewLayout> getLayout() {
		PpemtNewLayout entity = this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class).getSingleOrNull();

		if (entity == null) {
			commandProxy().insert(new PpemtNewLayout(new PpemtNewLayoutPk(IdentifierUtil.randomUniqueId()),
					AppContexts.user().companyId(), "0001", "レイアウト"));

			entity = this.queryProxy().query(GET_FIRST_LAYOUT, PpemtNewLayout.class).getSingleOrNull();
		}

		return Optional.of(toDomain(entity));
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
