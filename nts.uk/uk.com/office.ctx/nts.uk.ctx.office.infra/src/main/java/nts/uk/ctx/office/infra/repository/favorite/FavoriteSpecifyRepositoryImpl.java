package nts.uk.ctx.office.infra.repository.favorite;

import java.util.ArrayList;
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
	private static final String SELECT_BY_KEY = "SELECT m FROM OfimtFavorite m "
			+ " WHERE m.pk.creatorId IN :creatorIds"
			+ " AND m.pk.inputDate IN :inputDates";

	private static OfimtFavorite toEntity(FavoriteSpecify domain) {
		OfimtFavorite entity = new OfimtFavorite();
		domain.setMemento(entity);
		return entity;
	}
	
	private boolean filterEntity(OfimtFavoritePK e1, OfimtFavoritePK e2) {
		return (e1.getCreatorId().equals(e2.getCreatorId()) && e1.getInputDate().equals(e2.getInputDate()));
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
		List<String> creatorIds = new ArrayList<>();
		List<GeneralDateTime> inputDates = new ArrayList<>();
		
		//all of entity from client
		List<OfimtFavorite> entities = domains.stream().map(domain -> {
			OfimtFavorite entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
			creatorIds.add(entity.getPk().getCreatorId());
			inputDates.add(entity.getPk().getInputDate());
			return entity;
		})
		.collect(Collectors.toList());
		
		//get all old entity from database
		List<OfimtFavorite> oldEntities = this.queryProxy()
				.query(SELECT_BY_KEY, OfimtFavorite.class)
				.setParameter("creatorIds", creatorIds)
				.setParameter("inputDates", inputDates)
				.getList();
		
		//create list entity that new (exist in entity from client but don't exist in database)
		List<OfimtFavorite> newEntities = new ArrayList<>();
		entities.forEach(allEntity -> {
			Optional<OfimtFavorite> entity = oldEntities.stream()
					.filter(e -> this.filterEntity(e.getPk(), allEntity.getPk()))
					.findFirst();
			if(!entity.isPresent()) {
				newEntities.add(allEntity);
			}
		});
		// insert all new entity
		List<FavoriteSpecify> newDomains = newEntities.stream().map(FavoriteSpecify::createFromMemento).collect(Collectors.toList());
		this.insertAll(newDomains);
		
		//create list entity that need to update (exist in entity from client and exist in database)
		List<OfimtFavorite> updateEntities = oldEntities.stream()
				.map(oldEntity -> {
					Optional<OfimtFavorite> entity = entities.stream()
							.filter(e -> this.filterEntity(e.getPk(), oldEntity.getPk()))
							.findFirst();
					if(entity.isPresent()){
						this.commandProxy().removeAll(oldEntity.getListFavoriteSpecifyEntityDetail());
						this.getEntityManager().flush();
						oldEntity.setListFavoriteSpecifyEntityDetail(entity.get().getListFavoriteSpecifyEntityDetail());
						oldEntity.setFavoriteName(entity.get().getFavoriteName());
						oldEntity.setOrder(entity.get().getOrder());
						oldEntity.setTargetSelection(entity.get().getTargetSelection());
						oldEntity.setListFavoriteSpecifyEntityDetail(entity.get().getListFavoriteSpecifyEntityDetail());
					}
					return oldEntity;
				})
				.collect(Collectors.toList());

		// update all
		this.commandProxy().updateAll(updateEntities);
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
