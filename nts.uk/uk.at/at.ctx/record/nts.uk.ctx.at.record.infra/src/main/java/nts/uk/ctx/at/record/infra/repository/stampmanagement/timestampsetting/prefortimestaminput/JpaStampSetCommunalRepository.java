package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonLayoutType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampCommunal;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampPageLayout;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * 共有打刻の打刻設定Repository
 * 
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaStampSetCommunalRepository extends JpaRepository implements StampSetCommunalRepository {

	private static final String SELECT_ALL_PAGE_LAYOUT = "SELECT r FROM KrcmtStampPageLayout r WHERE r.pk.companyId = :companyId";

	@Override
	public void insert(StampSetCommunal domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(StampSetCommunal domain) {

		Optional<KrcmtStampCommunal> entityOpt = this.queryProxy().find(domain.getCid(), KrcmtStampCommunal.class);

		if (!entityOpt.isPresent()) {
			return;
		}
		
		KrcmtStampCommunal entity = entityOpt.get();
//		entity.update(domain);
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<StampSetCommunal> get(String comppanyID) {
		
		Optional<KrcmtStampCommunal> entityOpt = this.queryProxy().find(comppanyID,
				KrcmtStampCommunal.class);
		
		if (!entityOpt.isPresent()) {
			return Optional.empty();
		}
		
		List<StampPageLayout> lstStampPageLayout = this.queryProxy().query(SELECT_ALL_PAGE_LAYOUT, KrcmtStampPageLayout.class)

				.getList()
				.stream()
				
				.map(r -> new StampPageLayout(new PageNo(r.pk.pageNo), new StampPageName(r.pageName),
						new StampPageComment(new PageComment(r.pageComment), new ColorCode(r.commentColor)),
						EnumAdaptor.valueOf(r.buttonLayoutType, ButtonLayoutType.class), 
						r.lstButtonSet.stream().map(mapper->mapper.toDomain()).collect(Collectors.toList())))
				
				.collect(Collectors.toList());
		
		StampSetCommunal doamin = this.toDomain(entityOpt.get(), lstStampPageLayout);
		
		return Optional.of(doamin);
	}

	public KrcmtStampCommunal toEntity(StampSetCommunal domain) {
		KrcmtStampCommunal entity = new KrcmtStampCommunal();
		
//		entity.cid = domain.getCid();
//		entity.correctionInterval = domain.getDisplaySetStampScreen().getServerCorrectionInterval().v();
//		entity.resultDisplayTime = domain.getDisplaySetStampScreen().getResultDisplayTime().v();
//		entity.textColor = domain.getDisplaySetStampScreen().getSettingDateTimeColor().getTextColor().v();
//		entity.backGroundColor = domain.getDisplaySetStampScreen().getSettingDateTimeColor().getBackgroundColor().v();
//		entity.nameSelectArt = domain.isUseSelectName();
//		entity.passwordRequiredArt = domain.isPasswordInputReq();
//		entity.employeeAuthcUseArt = domain.getUseEmpCodeToAuthen().value;
//		entity.authcFailCnt = domain.getNumberAuthenfailures().get().v();

		return entity;
	}

	public StampSetCommunal toDomain(KrcmtStampCommunal entity, List<StampPageLayout> lstStampPageLayout) {
//		DisplaySettingsStampScreen displaySetStampScreen = new DisplaySettingsStampScreen(new CorrectionInterval(entity.correctionInterval),
//				new SettingDateTimeColorOfStampScreen(new ColorCode(entity.textColor), new ColorCode(entity.backGroundColor)),
//				new ResultDisplayTime(entity.resultDispayTime));
//	
//		Optional<NumberAuthenfailures> numberAuthenfailures = Optional.of(new NumberAuthenfailures(entity.authcFailCnt));
				
//		StampSetCommunal setShareTStamp = new StampSetCommunal(entity.cid,
//				displaySetStampScreen,
//				lstStampPageLayout,
//				entity.nameSelectArt,
//				entity.passwordRequiredArt,
//				entity.employeeAuthcUseArt == 1 ? NotUseAtr.USE : NotUseAtr.NOT_USE,
//				numberAuthenfailures);
		
		return null;
	}

}
