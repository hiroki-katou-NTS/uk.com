package nts.uk.ctx.office.infra.repository.favorite;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecifyRepository;
import nts.uk.ctx.office.infra.entity.favorite.FavoriteSpecifyEntity;
import nts.uk.ctx.office.infra.entity.favorite.FavoriteSpecifyEntityPK;
import nts.uk.shr.com.context.AppContexts;

/*
 * Repository Implements UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.お気に入りの指定
 */
@Stateless
public class FavoriteSpecifyRepositoryImpl extends JpaRepository implements FavoriteSpecifyRepository {

	// select by Sid
	private static final String SELECT_BY_SID = "SELECT m FROM FavoriteSpecifyEntity m WHERE m.pk.creatorId = :sid";

	private static FavoriteSpecifyEntity toEntity(FavoriteSpecify domain) {
		FavoriteSpecifyEntity entity = new FavoriteSpecifyEntity();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public void insert(FavoriteSpecify domain) {
		FavoriteSpecifyEntity entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
		entity.setContractCd(AppContexts.user().contractCode());
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(FavoriteSpecify domain) {
		FavoriteSpecifyEntity entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
		Optional<FavoriteSpecifyEntity> oldEntity = this.queryProxy().find(entity.getPk(), FavoriteSpecifyEntity.class);
		oldEntity.ifPresent(updateEntity -> {
			updateEntity.setFavoriteName(entity.getFavoriteName());
			updateEntity.setOrder(entity.getOrder());
			updateEntity.setTargetSelection(entity.getTargetSelection());
			updateEntity.setListFavoriteSpecifyEntityDetail(entity.getListFavoriteSpecifyEntityDetail());
			this.commandProxy().update(updateEntity);
		});
	}

	@Override
	public void delete(FavoriteSpecify domain) {
		FavoriteSpecifyEntity entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
		this.commandProxy().remove(FavoriteSpecifyEntity.class, entity.getPk());
	}

	@Override
	public List<FavoriteSpecify> getBySid(String sid) {
		return this.queryProxy()
				.query(SELECT_BY_SID, FavoriteSpecifyEntity.class)
				.setParameter("sid", sid)
				.getList(FavoriteSpecify::createFromMemento);
	}

	@Override
	public Optional<FavoriteSpecify> getBySidAndDate(String sid, GeneralDateTime date) {
		return this.queryProxy()
				.find(new FavoriteSpecifyEntityPK(sid, date), FavoriteSpecifyEntity.class)
				.map(FavoriteSpecify::createFromMemento);
	}
}
