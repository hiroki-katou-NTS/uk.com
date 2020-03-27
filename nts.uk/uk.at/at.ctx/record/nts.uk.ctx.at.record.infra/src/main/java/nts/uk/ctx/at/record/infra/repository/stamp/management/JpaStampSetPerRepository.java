package nts.uk.ctx.at.record.infra.repository.stamp.management;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonDisSet;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonNameSet;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonSettings;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonType;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageLayout;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.stamp.management.StampType;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcctStampDisplay;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcctStampLayoutDetail;
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
	 * 打刻の前準備(個人)を登録する
	 * insert KrcctStampDisplay
	 */
	@Override
	public void insert(StampSettingPerson stampSettingPerson) {
		commandProxy().insert(KrcctStampDisplay.toEntity(stampSettingPerson));
	}

	/**
	 * 打刻の前準備(個人)の設定を取得する
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
	 * 打刻の前準備(個人)を表示する
	 * get Stamp Setting Person
	 */
	@Override
	public Optional<StampSettingPerson> getStampSet(String companyId) {
		Optional<StampSettingPerson> data = this.queryProxy().query(SELECT_BY_CID, KrcctStampDisplay.class)
				.setParameter("companyId", companyId).getSingle(c -> c.toDomain());
		return data;
	}

	/**
	 * 打刻レイアウトの設定内容を追加する
	 * insert KrcctStampPageLayout
	 */
	@Override
	public void insertPage(StampPageLayout layout) {
		String companyId = AppContexts.user().companyId();
		commandProxy().insert(KrcctStampPageLayout.toEntity(layout, companyId));
	}

	/**
	 * 打刻レイアウトの設定内容を更新する
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

		newData.lstButtonSet.stream().forEach(x -> {
			Optional<KrcctStampLayoutDetail> optional = oldData.lstButtonSet.stream()
					.filter(i -> i.pk.buttonPositionNo == x.pk.buttonPositionNo).findAny();
			Optional<ButtonSettings> optional2 = layout.getLstButtonSet().stream()
					.filter(i -> i.getButtonPositionNo().v() == x.pk.buttonPositionNo).findFirst();
			if(optional.isPresent()){
				optional.get().useArt = x.useArt;
				optional.get().buttonName = x.buttonName;
				optional.get().reservationArt = x.reservationArt;
				optional.get().changeClockArt = x.changeClockArt;
				optional.get().changeCalArt = x.changeCalArt;
				optional.get().setPreClockArt = x.setPreClockArt;
				optional.get().changeHalfDay = x.changeHalfDay;
				optional.get().goOutArt = x.goOutArt;
				optional.get().textColor = x.textColor;
				optional.get().backGroundColor = x.backGroundColor;
				optional.get().aidioType = x.aidioType;
			}else {
				ButtonSettings settings = new ButtonSettings(
						optional2.get().getButtonPositionNo(), 
						new ButtonDisSet(
								new ButtonNameSet(
										optional2.get().getButtonDisSet().getButtonNameSet().getTextColor(),
										optional2.get().getButtonDisSet().getButtonNameSet().getButtonName().isPresent() ? optional2.get().getButtonDisSet().getButtonNameSet().getButtonName().get() : null),
								optional2.get().getButtonDisSet().getBackGroundColor()), 
						new ButtonType(
								optional2.get().getButtonType().getReservationArt(), 
								new StampType(
										optional2.get().getButtonType().getStampType().get().isChangeHalfDay(), 
										optional2.get().getButtonType().getStampType().get().getGoOutArt().get(), 
										optional2.get().getButtonType().getStampType().get().getSetPreClockArt(), 
										optional2.get().getButtonType().getStampType().get().getChangeClockArt(), 
										optional2.get().getButtonType().getStampType().get().getChangeCalArt())), 
						optional2.get().getUsrArt(), 
						optional2.get().getAudioType());
				commandProxy().insert(KrcctStampLayoutDetail.toEntity(settings, companyId, layout.getPageNo().v()));
			}
			
		});

		this.commandProxy().update(oldData);
	}

	/**
	 * 打刻レイアウトの設定内容を取得する & 打刻レイアウトのページ変更時の表示をする
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
	
	/**
	 * 打刻レイアウトの設定内容を削除する
	 * delete Stamp Page Layout
	 */
	@Override
	public void delete(String companyId, int pageNo) {
		Optional<StampPageLayout> newEntity = this.queryProxy().query(SELECT_BY_CID_PAGENO,KrcctStampPageLayout.class)
				.setParameter("companyId", companyId)
				.setParameter("operationMethod", 1)
				.setParameter("pageNo", pageNo)
				.getSingle(c->c.toDomain());
		if (newEntity.isPresent()) {
			this.commandProxy().remove(KrcctStampPageLayout.class, new KrcctStampPageLayoutPk(companyId,1, pageNo));
		}

	}

}
