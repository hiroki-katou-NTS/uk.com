package nts.uk.ctx.at.record.infra.repository.stamp.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StampAttenDisplay;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplayRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrccpStampRecordDis;
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrccpStampRecordDisPk;
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrcmtStampFunction;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaStampResultDisplayRepository extends JpaRepository implements StampResultDisplayRepository {

	private static final String SELECT_ALL_ATEN = "SELECT c FROM KrccpStampRecordDis c ";
	private static final String SELECT_ALL_ATEN_CID = SELECT_ALL_ATEN + " WHERE c.pk.companyId = :companyId";

	/**
	 * 打刻の前準備(オプション)を登録する of JpaStamPromptAppRepository
	 */
	@Override
	public void insert(StampResultDisplay application) {
		commandProxy().insert(this.toEntity(application));
	}

	/**
	 * 打刻の前準備(オプション)を登録する of JpaStamPromptAppRepository
	 */
	@Override
	public void update(StampResultDisplay application) {
		String companyId = AppContexts.user().companyId();
		Optional<KrcmtStampFunction> oldData = this.queryProxy().find(companyId, KrcmtStampFunction.class);
		if (oldData.isPresent()) {
			KrcmtStampFunction newData = this.toEntity(application);
			oldData.get().recordDisplayArt = newData.recordDisplayArt;
			newData.lstRecordDis.forEach(x -> {
				Optional<StampAttenDisplay> optional2 = application.getLstDisplayItemId().stream()
						.filter(z -> z.getDisplayItemId() == x.pk.dAtdItemId).findAny();
				StampAttenDisplay dis = new StampAttenDisplay(companyId, optional2.get().getDisplayItemId());
				commandProxy().insert(KrccpStampRecordDis.toEntity(dis));
			});
		}
		commandProxy().update(oldData.get());
	}

	/**
	 * delete KrccpStampRecordDis
	 */
	@Override
	public void delete() {
		String companyId = AppContexts.user().companyId();
		List<KrccpStampRecordDisPk> deleteListPK = getStampSets(companyId).stream()
				.map(c -> KrccpStampRecordDis.toEntity(c).pk).collect(Collectors.toList());
		this.commandProxy().removeAll(KrccpStampRecordDis.class, deleteListPK);
	}

	/**
	 * get Stamp Result Display
	 */
	@Override
	public Optional<StampResultDisplay> getStampSet(String companyId) {
		Optional<KrcmtStampFunction> c = this.queryProxy().find(companyId, KrcmtStampFunction.class);
		if(c.isPresent()) {
			return Optional.of(c.get().toDomain());
		}
		return Optional.empty();
	}

	/**
	 * 打刻の前準備(オプション)を取得する by getStampSet of JpaStamPromptAppRepository
	 */
	@Override
	public List<StampAttenDisplay> getStampSets(String companyId) {
		return this.queryProxy().query(SELECT_ALL_ATEN_CID, KrccpStampRecordDis.class)
				.setParameter("companyId", companyId).getList(c -> c.toDomain());
	}
	
	private Optional<KrcmtStampFunction> getEntity(String cId){
		return this.queryProxy().find(cId, KrcmtStampFunction.class);
	}

	public KrcmtStampFunction toEntity(StampResultDisplay domain){
		Optional<KrcmtStampFunction> oldEntity = this.getEntity(domain.getCompanyId());
		return new KrcmtStampFunction(
				domain.getCompanyId(), 
				domain.getUsrAtr().value, 
				oldEntity.isPresent() ? oldEntity.get().googleMapUseArt: 0,
				oldEntity.isPresent() ? oldEntity.get().mapAddress : null,
				oldEntity.isPresent() ? oldEntity.get().googleMapUseArt: 0,
				domain.getLstDisplayItemId().stream().map(mapper -> KrccpStampRecordDis.toEntity(mapper)).collect(Collectors.toList()));
	}
	
}
