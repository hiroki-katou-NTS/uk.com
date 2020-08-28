package nts.uk.ctx.at.request.infra.repository.setting.company.request.stamp;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSetting_Old;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.request.stamp.KrqstStampRequestSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaStampRequestSettingRepository extends JpaRepository implements StampRequestSettingRepository {

	@Override
	public Optional<StampRequestSetting_Old> findByCompanyID(String companyID) {
		return this.queryProxy().find(companyID, KrqstStampRequestSetting.class)
				.map(x -> x.toDomain());
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private KrqstStampRequestSetting toEntity(StampRequestSetting_Old domain){
		val entity = new KrqstStampRequestSetting();
		String companyId = AppContexts.user().companyId();
		entity.bottomComment = domain.getBottomComment().getComment().v();
		entity.bottomCommentFontColor = domain.getBottomComment().getFontColor();
		entity.bottomCommentFontWeight = domain.getBottomComment().getFontWeight() == true ? 1 : 0;
		entity.companyID = companyId;
		entity.resultDisp = domain.getResultDisp().value;
		entity.stampAtr_Care_Disp = domain.getStampDisplayControl().getStampAtrCareDisp().value;
		entity.stampAtr_Child_Care_Disp = domain.getStampDisplayControl().getStampAtrChildCareDisp().value;
		entity.stampAtr_GoOut_Disp = domain.getStampDisplayControl().getStampAtrGoOutDisp().value;
		entity.stampAtr_Sup_Disp = domain.getStampDisplayControl().getStampAtrSupDisp().value;
		entity.stampAtr_Work_Disp = domain.getStampDisplayControl().getStampAtrWorkDisp().value;
		entity.stampGoOutAtr_Compensation_Disp = domain.getGoOutTypeDisplayControl().getStampGoOutAtrCompensationDisp().value;
		entity.stampGoOutAtr_Private_Disp = domain.getGoOutTypeDisplayControl().getStampGoOutAtrPrivateDisp().value;
		entity.stampGoOutAtr_Public_Disp = domain.getGoOutTypeDisplayControl().getStampGoOutAtrPublicDisp().value;
		entity.stampGoOutAtr_Union_Disp = domain.getGoOutTypeDisplayControl().getStampGoOutAtrUnionDisp().value;
		entity.stampPlaceDisp = domain.getStampPlaceDisp().value;
		entity.supFrameDispNO = domain.getSupFrameDispNO().v();
		entity.topComment = domain.getTopComment().getComment().v();
		entity.topCommentFontColor = domain.getTopComment().getFontColor();
		entity.topCommentFontWeight = domain.getTopComment().getFontWeight() == true ? 1 : 0;
		return entity;
	}
//	private StampRequestSetting toDomainApplicationSetting(KrqstStampRequestSetting entity){
//		StampRequestSetting stamp = StampRequestSetting.createFromJavaType(entity.companyID, entity.topComment, entity.topCommentFontColor, 
//																entity.topCommentFontWeight == 1 ? true : false, entity.bottomComment, 
//																entity.bottomCommentFontColor, 
//																entity.bottomCommentFontWeight == 1 ? true : false, 
//																entity.resultDisp, entity.supFrameDispNO, entity.stampPlaceDisp, 
//																entity.stampAtr_Work_Disp, entity.stampAtr_GoOut_Disp, 
//																entity.stampAtr_Care_Disp, entity.stampAtr_Sup_Disp, entity.stampAtr_Child_Care_Disp, 
//																entity.stampGoOutAtr_Private_Disp, entity.stampGoOutAtr_Public_Disp, 
//																entity.stampGoOutAtr_Compensation_Disp, entity.stampGoOutAtr_Union_Disp);
//		return stamp;
//	}
	/**
	 * update stamp request setting
	 * @author yennth
	 */
	@Override
	public void updateStamp(StampRequestSetting_Old stamp) {
		KrqstStampRequestSetting entity = toEntity(stamp);
		KrqstStampRequestSetting oldEntity = this.queryProxy().find(entity.companyID, KrqstStampRequestSetting.class).get();
		oldEntity.bottomComment = entity.bottomComment;
		oldEntity.bottomCommentFontColor = entity.bottomCommentFontColor;
		oldEntity.bottomCommentFontWeight = entity.bottomCommentFontWeight;
		oldEntity.resultDisp = entity.resultDisp;
		oldEntity.stampAtr_Care_Disp = entity.stampAtr_Care_Disp;
		oldEntity.stampAtr_GoOut_Disp = entity.stampAtr_GoOut_Disp;
		oldEntity.stampAtr_Sup_Disp = entity.stampAtr_Sup_Disp;
		oldEntity.stampAtr_Work_Disp = entity.stampAtr_Work_Disp;
		oldEntity.stampGoOutAtr_Compensation_Disp = entity.stampGoOutAtr_Compensation_Disp;
		oldEntity.stampGoOutAtr_Private_Disp = entity.stampGoOutAtr_Private_Disp;
		oldEntity.stampGoOutAtr_Public_Disp = entity.stampGoOutAtr_Public_Disp;
		oldEntity.stampGoOutAtr_Union_Disp = entity.stampGoOutAtr_Union_Disp;
		oldEntity.stampPlaceDisp = oldEntity.stampPlaceDisp;
		oldEntity.supFrameDispNO = entity.supFrameDispNO;
		oldEntity.topComment = entity.topComment;
		oldEntity.topCommentFontColor = entity.topCommentFontColor;
		oldEntity.topCommentFontWeight = entity.topCommentFontWeight;
		oldEntity.stampAtr_Child_Care_Disp = entity.stampAtr_Child_Care_Disp;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert stamp request setting
	 * @author yennth
	 */
	@Override
	public void insertStamp(StampRequestSetting_Old stamp) {
		KrqstStampRequestSetting entity = toEntity(stamp);
		this.commandProxy().insert(entity);
	}
	/**
	 * delete stamp request setting
	 * @author tanlv
	 */
	@Override
	public void deleteStamp(String companyID) {
		this.commandProxy().remove(KrqstStampRequestSetting.class, companyID);
	}
}
