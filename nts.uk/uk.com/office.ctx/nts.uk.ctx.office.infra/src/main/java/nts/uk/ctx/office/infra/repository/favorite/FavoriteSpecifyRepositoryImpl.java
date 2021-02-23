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
	
	//get list by key
	private static final String SELECT_BY_KEY = "SELECT m FROM FavoriteSpecifyEntity m "
			+ " WHERE m.pk.creatorId IN :creatorIds"
			+ " AND m.pk.inputDate IN :inputDates";

	private static FavoriteSpecifyEntity toEntity(FavoriteSpecify domain) {
		FavoriteSpecifyEntity entity = new FavoriteSpecifyEntity();
		domain.setMemento(entity);
		return entity;
	}
	
	private boolean filterEntity(FavoriteSpecifyEntityPK e1, FavoriteSpecifyEntityPK e2) {
		return (e1.getCreatorId().equals(e2.getCreatorId()) && e1.getInputDate().equals(e2.getInputDate()));
	}

	@Override
	public void insert(FavoriteSpecify domain) {
		FavoriteSpecifyEntity entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
		entity.setVersion(0);
		entity.setContractCd(AppContexts.user().contractCode());
		this.commandProxy().insert(entity);
	}

	@Override
	public void insertAll(List<FavoriteSpecify> domains) {
		List<FavoriteSpecifyEntity> entities = domains.stream().map(domain -> {
			FavoriteSpecifyEntity entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
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
		List<FavoriteSpecifyEntity> entities = domains.stream().map(domain -> {
			FavoriteSpecifyEntity entity = FavoriteSpecifyRepositoryImpl.toEntity(domain);
			creatorIds.add(entity.getPk().getCreatorId());
			inputDates.add(entity.getPk().getInputDate());
			return entity;
		})
		.collect(Collectors.toList());
		
		//get all old entity from database
		List<FavoriteSpecifyEntity> oldEntities = this.queryProxy()
				.query(SELECT_BY_KEY, FavoriteSpecifyEntity.class)
				.setParameter("creatorIds", creatorIds)
				.setParameter("inputDates", inputDates)
				.getList();
		
		//create list entity that new (exist in entity from client but don't exist in database)
		List<FavoriteSpecifyEntity> newEntities = new ArrayList<>();
		entities.forEach(allEntity -> {
			Optional<FavoriteSpecifyEntity> entity = oldEntities.stream()
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
		List<FavoriteSpecifyEntity> updateEntities = oldEntities.stream()
				.map(oldEntity -> {
					Optional<FavoriteSpecifyEntity> entity = entities.stream()
							.filter(e -> this.filterEntity(e.getPk(), oldEntity.getPk()))
							.findFirst();
					if(entity.isPresent()){
						this.commandProxy().removeAll(oldEntity.getListFavoriteSpecifyEntityDetail());
						this.getEntityManager().flush();
						oldEntity.setListFavoriteSpecifyEntityDetail(entity.get().getListFavoriteSpecifyEntityDetail());
						oldEntity.setVersion(entity.get().getVersion() + 1);
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
