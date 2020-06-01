package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonDisSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonNameSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcctStampDisplay;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcctStampLayoutDetail;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcctStampPageLayout;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcctStampPageLayoutPk;
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
	
	private static final String SELECT_BY_CID_LAYOUT = SELECT_BY_CID_PAGENO 
			+ " AND c.buttonLayoutType = :buttonLayoutType";

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
		
		Optional<KrcctStampDisplay> oldData = this.queryProxy().query(SELECT_BY_CID_METHOD, KrcctStampDisplay.class)
				.setParameter("companyId", stampSettingPerson.getCompanyId())
				.setParameter("operationMethod", 1)
				.getSingle();
		if(oldData.isPresent()){
			KrcctStampDisplay newData = KrcctStampDisplay.toEntity(stampSettingPerson);
			oldData.get().correctionInterval = newData.correctionInterval;
			oldData.get().histDisplayMethod = newData.histDisplayMethod;
			oldData.get().resultDisplayTime = newData.resultDisplayTime;
			oldData.get().textColor = newData.textColor;
			oldData.get().backGroundColor = newData.backGroundColor;
			oldData.get().buttonEmphasisArt = newData.buttonEmphasisArt;
		}

		this.commandProxy().update(oldData.get());
	}

	/**
	 * 打刻の前準備(個人)を表示する
	 * get Stamp Setting Person
	 */
	@Override
	public Optional<StampSettingPerson> getStampSet(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KrcctStampDisplay.class)
				.setParameter("companyId", companyId).getSingle(c -> c.toDomain());
	}
	
	/**
	 * 打刻の前準備(個人)を表示する
	 * get Stamp Setting Person
	 */
	@Override
	public Optional<StampSettingPerson> getStampSetting(String companyId) {
		List<StampPageLayout> layouts = getAllStampSetPage(companyId);
		return this.queryProxy().query(SELECT_BY_CID, KrcctStampDisplay.class)
				.setParameter("companyId", companyId).getSingle(c -> c.toDomain(layouts));
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
		Optional<KrcctStampPageLayout> oldData = this.queryProxy().query(SELECT_BY_CID_PAGENO, KrcctStampPageLayout.class)
				.setParameter("companyId", companyId)
				.setParameter("operationMethod", 1)
				.setParameter("pageNo", layout.getPageNo().v()).getSingle();
		if(oldData.isPresent()){
		KrcctStampPageLayout newData = KrcctStampPageLayout.toEntity(layout, companyId);
		oldData.get().pageName = newData.pageName;
		oldData.get().buttonLayoutType = newData.buttonLayoutType;
		oldData.get().pageComment = newData.pageComment;
		oldData.get().commentColor = newData.commentColor;

		newData.lstButtonSet.stream().forEach(x -> {
			Optional<KrcctStampLayoutDetail> optional = oldData.get().lstButtonSet.stream()
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
				StampType stampType = null;
				
				if(optional2.get().getButtonType().getStampType().isPresent() && !(optional2.get().getButtonType().getStampType().get().getChangeHalfDay() == null 
						&& (!optional2.get().getButtonType().getStampType().get().getGoOutArt().isPresent() && optional2.get().getButtonType().getStampType().get().getGoOutArt().get() == null) 
						&& optional2.get().getButtonType().getStampType().get().getSetPreClockArt() == null
						&& optional2.get().getButtonType().getStampType().get().getChangeClockArt() == null
						&& optional2.get().getButtonType().getStampType().get().getChangeCalArt() == null)) {
					
					StampType.getStampType(
							optional2.get().getButtonType().getStampType().isPresent() ? optional2.get().getButtonType().getStampType().get().getChangeHalfDay() : null, 
							optional2.get().getButtonType().getStampType().isPresent() ? optional2.get().getButtonType().getStampType().get().getGoOutArt().isPresent() ? optional2.get().getButtonType().getStampType().get().getGoOutArt().get() : null : null, 
							optional2.get().getButtonType().getStampType().isPresent() ? optional2.get().getButtonType().getStampType().get().getSetPreClockArt() : null, 
							optional2.get().getButtonType().getStampType().isPresent() ? optional2.get().getButtonType().getStampType().get().getChangeClockArt() : null, 
							optional2.get().getButtonType().getStampType().isPresent() ? optional2.get().getButtonType().getStampType().get().getChangeCalArt() : null);
					
				}
				ButtonSettings settings = new ButtonSettings(
						optional2.get().getButtonPositionNo(), 
						new ButtonDisSet(
								new ButtonNameSet(
										optional2.get().getButtonDisSet().getButtonNameSet().getTextColor(),
										optional2.get().getButtonDisSet().getButtonNameSet().getButtonName().isPresent() ? optional2.get().getButtonDisSet().getButtonNameSet().getButtonName().get() : null),
								optional2.get().getButtonDisSet().getBackGroundColor()), 
						new ButtonType(
								optional2.get().getButtonType().getReservationArt(), stampType
								), 
						optional2.get().getUsrArt(), 
						optional2.get().getAudioType());
				commandProxy().insert(KrcctStampLayoutDetail.toEntity(settings, companyId, layout.getPageNo().v()));
			}
			
		});
		}
		this.commandProxy().update(oldData.get());
	}

	/**
	 * 打刻レイアウトの設定内容を取得する & 打刻レイアウトのページ変更時の表示をする
	 * get Stamp Page Layout
	 */
	@Override
	public Optional<StampPageLayout> getStampSetPage(String companyId, int pageNo) {
		return this.queryProxy().query(SELECT_BY_CID_PAGENO, KrcctStampPageLayout.class)
				.setParameter("companyId", companyId)
				.setParameter("operationMethod", 1)
				.setParameter("pageNo", pageNo)
				.getSingle(c -> c.toDomain());
	}
	
	/**
	 * get Stamp Page Layout
	 */
	@Override
	public Optional<StampPageLayout> getStampSetPageByCid(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID_PAGE_METHOD, KrcctStampPageLayout.class)
				.setParameter("companyId", companyId)
				.setParameter("operationMethod", 1)
				.getSingle(c -> c.toDomain());
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
				.getSingle(c -> c.toDomain());
		if (newEntity.isPresent()) {
			this.commandProxy().remove(KrcctStampPageLayout.class, new KrcctStampPageLayoutPk(companyId,1, pageNo));
		}

	}

}
