package nts.uk.ctx.at.record.infra.repository.stamp.application;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptAppRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptApplication;
import nts.uk.ctx.at.record.dom.stamp.application.StampRecordDis;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrccpStampFunction;
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrcmtPromptApplication;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaStamPromptAppRepository extends JpaRepository implements StampPromptAppRepository{
	
	private static final String SELECT_ALL_PAGE = "SELECT c FROM KrcmtPromptApplication c ";
	
	private static final String SELECT_BY_CID_PAGE = SELECT_ALL_PAGE + " WHERE c.pk.companyId = :companyId";
	
	private static final String SELECT_BY_CID_TYPE = SELECT_BY_CID_PAGE + " AND c.pk.errorType = :errorType";

	/**
	 * 打刻の前準備(オプション)を登録する
	 */
	@Override
	public void insert(StampPromptApplication application) {
		commandProxy().insertAll(KrcmtPromptApplication.toEntity(application));
	}

	/**
	 * 打刻の前準備(オプション)を登録する
	 */
	@Override
	public void update(StampPromptApplication application) {
		String companyId = AppContexts.user().companyId();
		List<KrcmtPromptApplication> oldData = this.queryProxy().query(SELECT_BY_CID_PAGE, KrcmtPromptApplication.class)
				.setParameter("companyId", companyId).getList();
		List<KrcmtPromptApplication> newData = KrcmtPromptApplication.toEntity(application);
		if(!newData.isEmpty()){
			newData.stream().forEach(mapper->{
				Optional<KrcmtPromptApplication> optional = oldData.stream().filter(x->x.pk.errorType == mapper.pk.errorType).findAny();
				if(optional.isPresent()){
					optional.get().useArt = mapper.useArt;
					optional.get().messageContent = mapper.messageContent;
					optional.get().messageColor = mapper.messageColor;
					commandProxy().updateAll(oldData);
				}
			});
			
		}
	}

	/**
	 * 打刻の前準備(オプション)を取得する
	 */
	@Override
	public Optional<StampResultDisplay> getStampSet(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID_TYPE, KrccpStampFunction.class)
				.setParameter("companyId", companyId)
				.setParameter("errorType", 1)
				.getSingle(c -> c.toDomain());
	}
	
	/**
	 * get all Stamp Set Page
	 */
	@Override
	public List<StampRecordDis> getAllStampSetPage(String companyId) {
		List<StampRecordDis> data = this.queryProxy().query(SELECT_BY_CID_PAGE, KrcmtPromptApplication.class)
				.setParameter("companyId", companyId).getList(c -> c.toDomainRecord());
		if (data.isEmpty())
			return Collections.emptyList();

		return data.stream().collect(Collectors.toList());
	}
	
	/**
	 * get all Stamp Set Page
	 */
	@Override
	public Optional<StampPromptApplication> getStampPromptApp(String companyId) {
		List<StampRecordDis> data = getAllStampSetPage(companyId);
		return Optional.ofNullable(new StampPromptApplication(companyId, data));
	}
}
