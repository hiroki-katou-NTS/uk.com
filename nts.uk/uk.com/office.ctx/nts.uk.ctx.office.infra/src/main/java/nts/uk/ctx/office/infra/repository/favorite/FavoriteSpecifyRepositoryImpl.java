package nts.uk.ctx.office.infra.repository.favorite;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecifyRepository;
import nts.uk.ctx.office.infra.entity.favorite.OfimtFavorite;
import nts.uk.ctx.office.infra.entity.favorite.OfimtFavoritePK;
import nts.uk.shr.com.context.AppContexts;

/*
 * Repository Implements UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.お気に入りの指定
 */
@Stateless
public class FavoriteSpecifyRepositoryImpl extends JpaRepository implements FavoriteSpecifyRepository {

	// select by Sid
	private static final String SELECT_BY_SID = "SELECT m FROM OfimtFavorite m WHERE m.pk.creatorId = :sid";
	
	//get list by key
	private static final String SELECT_BY_CREATER_IDS = "SELECT m FROM OfimtFavorite m "
			+ " WHERE m.pk.creatorId IN :creatorIds";

	private static OfimtFavorite toEntity(FavoriteSpecify domain) {
		OfimtFavorite entity = new OfimtFavorite();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public void insert(FavoriteSpecify domain) {
		OfimtFavorite entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
		entity.setVersion(0);
		entity.setContractCd(AppContexts.user().contractCode());
		this.commandProxy().insert(entity);
	}

	@Override
	public void insertAll(List<FavoriteSpecify> domains) {
		List<OfimtFavorite> entities = domains.stream().map(domain -> {
			OfimtFavorite entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
			entity.setVersion(0);
			entity.setContractCd(AppContexts.user().contractCode());
			return entity;
		}).collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}
	
	@Override
	public void update(FavoriteSpecify domain) {}
	
	@Override
	public void updateAll(List<FavoriteSpecify> domains) {
		//all of entity from client
		List<String> creatorIds = domains.stream().map(domain -> domain.getCreatorId()).collect(Collectors.toList());
		
		//get all old entity from database
		List<OfimtFavorite> oldEntities = this.queryProxy()
				.query(SELECT_BY_CREATER_IDS, OfimtFavorite.class)
				.setParameter("creatorIds", creatorIds)
				.getList();
		
		//remove all
		this.commandProxy().removeAll(oldEntities);
		this.getEntityManager().flush();

		//insert all
		this.insertAll(domains);
	}

	@Override
	public void delete(FavoriteSpecify domain) {
		OfimtFavorite entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
		this.commandProxy().remove(OfimtFavorite.class, entity.getPk());
	}

	@Override
	public List<FavoriteSpecify> getBySid(String sid) {
		return this.queryProxy()
				.query(SELECT_BY_SID, OfimtFavorite.class)
				.setParameter("sid", sid)
				.getList(FavoriteSpecify::createFromMemento);
	}

	@Override
	public Optional<FavoriteSpecify> getBySidAndDate(String sid, GeneralDateTime date) {
		return this.queryProxy()
				.find(new OfimtFavoritePK(sid, date), OfimtFavorite.class)
				.map(FavoriteSpecify::createFromMemento);
	}
}
