package nts.uk.ctx.at.record.infra.repository.stamp.management;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageLayout;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcctStampDisplay;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcctStampPageLayout;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcctStampPageLayoutPk;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaStampSetPerRepository extends JpaRepository implements StampSetPerRepository{
	
	private static final String SELECT_ALL = "SELECT c FROM KrcctStampDisplay c ";

	private static final String SELECT_BY_CID = SELECT_ALL + " WHERE c.krcctStampDisplayPk.companyId = :companyId";

	private static final String SELECT_BY_CID_METHOD = SELECT_BY_CID
			+ " AND c.krcctStampDisplayPk.operationMethod = :operationMethod";

	private static final String SELECT_ALL_PAGE = "SELECT c FROM KrcctStampPageLayout c ";

	private static final String SELECT_BY_CID_PAGE = SELECT_ALL_PAGE
			+ " WHERE c.krcctStampDisplayPk.companyId = :companyId";


	/**
	 * insert KrcctStampDisplay
	 */
	@Override
	public void insert(StampSettingPerson stampSettingPerson) {
		commandProxy().insert(KrcctStampDisplay.toEntity(stampSettingPerson));
	}

	/**
	 * update KrcctStampDisplay
	 */
	@Override
	public void update(StampSettingPerson stampSettingPerson) {
		KrcctStampDisplay oldData = this.queryProxy().query(SELECT_BY_CID_METHOD, KrcctStampDisplay.class)
				.setParameter("companyId", stampSettingPerson.getCompanyId())
				.setParameter("operationMethod", 1).getSingle().get();
		KrcctStampDisplay newData = KrcctStampDisplay.toEntity(stampSettingPerson);
		
		oldData.correctionInterval = newData.correctionInterval;
		oldData.histDisplayMethod = newData.histDisplayMethod;
		oldData.resultDisplayTime = newData.resultDisplayTime;
		oldData.textColor = newData.textColor;
		oldData.backGroundColor = newData.backGroundColor;
		oldData.buttonEmphasisArt = newData.buttonEmphasisArt;
		
		this.commandProxy().update(oldData);
	}

	/**
	 * 取得する
	 */
	@Override
	public Optional<StampSettingPerson> getStampSet(String companyId) {
		Optional<StampSettingPerson> data = this.queryProxy().query(SELECT_BY_CID, KrcctStampDisplay.class)
				.setParameter("companyId", companyId)
				.getSingle(c -> c.toDomain());
		return data;
	}
	
	/**
	 * insert KrcctStampPageLayout
	 */
	@Override
	public void insertPage(StampPageLayout layout) {
		String companyId = AppContexts.user().companyId();
		commandProxy().insert(KrcctStampPageLayout.toEntity(layout,companyId));
	}

	/**
	 * update KrcctStampPageLayout
	 */
	@Override
	public void updatePage(StampPageLayout layout) {
		String companyId = AppContexts.user().companyId();
		KrcctStampPageLayoutPk key = new KrcctStampPageLayoutPk(companyId, 1, layout.getPageNo().v());
		Optional<KrcctStampPageLayout> entity = this.queryProxy().find(key, KrcctStampPageLayout.class);
		if(entity.isPresent())
		this.commandProxy().update(KrcctStampPageLayout.toEntity(layout, companyId));
	}

	/**
	 * 取得する
	 */
	@Override
	public Optional<StampPageLayout> getStampSetPage(String companyId) {
		Optional<StampPageLayout> data = this.queryProxy().query(SELECT_BY_CID_PAGE, KrcctStampPageLayout.class)
				.setParameter("companyId", companyId)
				.getSingle(c -> c.toDomain());
		return data;
	}
	
	/**
	 * 取得する
	 */
	@Override
	public List<StampPageLayout> getAllStampSetPage(String companyId) {
		List<StampPageLayout> data = this.queryProxy().query(SELECT_BY_CID_PAGE, KrcctStampPageLayout.class)
				.setParameter("companyId", companyId)
				.getList(c -> c.toDomain());
		if(data.isEmpty())
			return Collections.emptyList();
		
		return data.stream().collect(Collectors.toList());
	}

}
