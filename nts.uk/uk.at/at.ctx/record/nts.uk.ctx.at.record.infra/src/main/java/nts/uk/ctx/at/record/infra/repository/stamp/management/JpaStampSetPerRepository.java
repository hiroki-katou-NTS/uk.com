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
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcctStampLayoutDetail;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcctStampPageLayout;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaStampSetPerRepository extends JpaRepository implements StampSetPerRepository {

	private static final String SELECT_ALL = "SELECT c FROM KrcctStampDisplay c ";

	private static final String SELECT_BY_CID = SELECT_ALL + " WHERE c.pk.companyId = :companyId";

	private static final String SELECT_BY_CID_METHOD = SELECT_BY_CID + " AND c.pk.operationMethod = :operationMethod";

	private static final String SELECT_ALL_PAGE = "SELECT c FROM KrcctStampPageLayout c ";

	private static final String SELECT_BY_CID_PAGE = SELECT_ALL_PAGE + " WHERE c.pk.companyId = :companyId";
	
	private static final String SELECT_BY_CID_PAGE_METHOD = SELECT_BY_CID_PAGE + " AND c.pk.operationMethod = :operationMethod";
	
	private static final String SELECT_BY_CID_PAGENO = SELECT_BY_CID_PAGE 
			+ " AND c.pk.operationMethod = :operationMethod"
			+ " AND c.pk.pageNo = :pageNo";


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
				.setParameter("companyId", stampSettingPerson.getCompanyId()).setParameter("operationMethod", 1)
				.getSingle().get();
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
	 * get Stamp Setting Person
	 */
	@Override
	public Optional<StampSettingPerson> getStampSet(String companyId) {
		Optional<StampSettingPerson> data = this.queryProxy().query(SELECT_BY_CID, KrcctStampDisplay.class)
				.setParameter("companyId", companyId).getSingle(c -> c.toDomain());
		return data;
	}

	/**
	 * insert KrcctStampPageLayout
	 */
	@Override
	public void insertPage(StampPageLayout layout) {
		String companyId = AppContexts.user().companyId();
		commandProxy().insert(KrcctStampPageLayout.toEntity(layout, companyId));
	}

	/**
	 * update KrcctStampPageLayout
	 */
	@Override
	public void updatePage(StampPageLayout layout) {
		String companyId = AppContexts.user().companyId();

		KrcctStampPageLayout oldData = this.queryProxy().query(SELECT_BY_CID_PAGENO, KrcctStampPageLayout.class)
				.setParameter("companyId", companyId)
				.setParameter("operationMethod", 1)
				.setParameter("pageNo", layout.getPageNo().v()).getSingle().get();
		KrcctStampPageLayout newData = KrcctStampPageLayout.toEntity(layout, companyId);
		oldData.pageName = newData.pageName;
		oldData.buttonLayoutType = newData.buttonLayoutType;
		oldData.pageComment = newData.pageComment;
		oldData.commentColor = newData.commentColor;

		oldData.lstButtonSet.stream().forEach(x -> {
			Optional<KrcctStampLayoutDetail> optional = newData.lstButtonSet.stream()
					.filter(i -> i.pk.pageNo == x.pk.pageNo).findAny();
			x.useArt = optional.get().useArt;
			x.buttonName = optional.get().buttonName;
			x.reservationArt = optional.get().reservationArt;
			x.changeClockArt = optional.get().changeClockArt;
			x.changeCalArt = optional.get().changeCalArt;
			x.setPreClockArt = optional.get().setPreClockArt;
			x.changeHalfDay = optional.get().changeHalfDay;
			x.goOutArt = optional.get().goOutArt;
			x.textColor = optional.get().textColor;
			x.backGroundColor = optional.get().backGroundColor;
			x.aidioType = optional.get().aidioType;
		});

		this.commandProxy().update(oldData);
	}

	/**
	 * get Stamp Page Layout
	 */
	@Override
	public Optional<StampPageLayout> getStampSetPage(String companyId, int pageNo) {
		Optional<StampPageLayout> data = this.queryProxy().query(SELECT_BY_CID_PAGENO, KrcctStampPageLayout.class)
				.setParameter("companyId", companyId)
				.setParameter("operationMethod", 1)
				.setParameter("pageNo", pageNo)
				.getSingle(c -> c.toDomain());
		return data;
	}
	
	/**
	 * get Stamp Page Layout
	 */
	@Override
	public Optional<StampPageLayout> getStampSetPageByCid(String companyId) {
		Optional<StampPageLayout> data = this.queryProxy().query(SELECT_BY_CID_PAGE_METHOD, KrcctStampPageLayout.class)
				.setParameter("companyId", companyId)
				.setParameter("operationMethod", 1)
				.getSingle(c -> c.toDomain());
		return data;
	}

	/**
	 * get All Stamp Page Layout
	 */
	@Override
	public List<StampPageLayout> getAllStampSetPage(String companyId) {
		List<StampPageLayout> data = this.queryProxy().query(SELECT_BY_CID_PAGE, KrcctStampPageLayout.class)
				.setParameter("companyId", companyId).getList(c -> c.toDomain());
		if (data.isEmpty())
			return Collections.emptyList();

		return data.stream().collect(Collectors.toList());
	}

}
