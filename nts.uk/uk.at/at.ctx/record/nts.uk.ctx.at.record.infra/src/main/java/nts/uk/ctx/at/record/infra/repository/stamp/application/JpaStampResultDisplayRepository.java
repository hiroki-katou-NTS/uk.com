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
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrccpStampFunction;
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrccpStampRecordDis;
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrccpStampRecordDisPk;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaStampResultDisplayRepository  extends JpaRepository implements  StampResultDisplayRepository{
	
	private static final String SELECT_ALL_PAGE = "SELECT c FROM KrccpStampFunction c ";
	private static final String SELECT_BY_CID_PAGE = SELECT_ALL_PAGE + " WHERE c.pk.companyId = :companyId";
	
	private static final String SELECT_ALL_ATEN = "SELECT c FROM KrccpStampRecordDis c ";
	private static final String SELECT_ALL_ATEN_CID = SELECT_ALL_ATEN + " WHERE c.pk.companyId = :companyId";

	/**
	 * 打刻の前準備(オプション)を登録する  of JpaStamPromptAppRepository
	 */
	@Override
	public void insert(StampResultDisplay application) {
		commandProxy().insert(KrccpStampFunction.toEntity(application));
		
	}

	/**
	 * 打刻の前準備(オプション)を登録する   of JpaStamPromptAppRepository
	 */
	@Override
	public void update(StampResultDisplay application) {
		String companyId = AppContexts.user().companyId();
		KrccpStampFunction oldData = this.queryProxy().query(SELECT_BY_CID_PAGE, KrccpStampFunction.class)
				.setParameter("companyId", companyId).getSingle().get();
		KrccpStampFunction newData = KrccpStampFunction.toEntity(application);
		oldData.recordDisplayArt = newData.recordDisplayArt;
		newData.lstRecordDis.forEach(x->{
			Optional<StampAttenDisplay> optional2 = application.getLstDisplayItemId().stream().filter(z-> z.getDisplayItemId() == x.pk.dAtdItemId).findAny();
				StampAttenDisplay dis = new StampAttenDisplay(companyId, optional2.get().getDisplayItemId());
				commandProxy().insert(KrccpStampRecordDis.toEntity(dis));
		});
		commandProxy().update(oldData);
	}
	
	/**
	 * delete KrccpStampRecordDis
	 */
	@Override
	public void delete() {
		String companyId = AppContexts.user().companyId();
		List<KrccpStampRecordDisPk> deleteListPK = getStampSets(companyId)
				.stream().map(c -> KrccpStampRecordDis.toEntity(c).pk).collect(Collectors.toList());
		this.commandProxy().removeAll(KrccpStampRecordDis.class, deleteListPK);
		
	}
	
	
	/**
	 * get Stamp Result Display
	 */
	@Override
	public Optional<StampResultDisplay> getStampSet(String companyId) {
		Optional<StampResultDisplay> data = this.queryProxy().query(SELECT_BY_CID_PAGE, KrccpStampFunction.class)
				.setParameter("companyId", companyId).getSingle(c -> c.toDomain());
		return data;
	}
	
	/**
	 * 打刻の前準備(オプション)を取得する by getStampSet of JpaStamPromptAppRepository
	 */
	@Override
	public List<StampAttenDisplay> getStampSets(String companyId) {
		List<StampAttenDisplay> data = this.queryProxy().query(SELECT_ALL_ATEN_CID, KrccpStampRecordDis.class)
				.setParameter("companyId", companyId).getList(c -> c.toDomain());
		return data;
	}

}
