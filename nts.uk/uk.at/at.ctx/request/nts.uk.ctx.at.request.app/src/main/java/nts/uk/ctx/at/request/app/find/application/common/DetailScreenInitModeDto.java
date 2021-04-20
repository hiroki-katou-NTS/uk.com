package nts.uk.ctx.at.request.app.find.application.common;

/*import nts.uk.ctx.at.request.dom.setting.request.application.common.AprovalPersonFlg;*/
import lombok.Value;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.NumDaysOfWeek;
import nts.uk.ctx.at.request.dom.setting.request.application.common.PriorityFLg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.ReflectionFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;

/**
 * 
 * @author hieult
 *
 */
/** 14-3.詳細画面の初期モード */
@Value
public class DetailScreenInitModeDto {
	
	private String companyID;
	
	private BaseDateFlg baseDateFlg;

	private AppDisplayAtr advanceExcessMessDispAtr;

	private AppDisplayAtr hwAdvanceDispAtr;

	private AppDisplayAtr hwActualDispAtr;

	private AppDisplayAtr actualExcessMessDispAtr;

	private AppDisplayAtr otAdvanceDispAtr;

	private AppDisplayAtr otActualDispAtr;

	private NumDaysOfWeek warningDateDispAtr;

	private AppDisplayAtr appReasonDispAtr;

	private AppCanAtr appContentChangeFlg;

	private ReflectionFlg scheReflectFlg;

	private PriorityFLg priorityTimeReflectFlg;

	private ReflectionFlg attendentTimeReflectFlg;

//	public static DetailScreenInitModeDto fromDomain (ApplicationSetting domain){
//		return new DetailScreenInitModeDto (
//				domain.getCompanyID(),
//				domain.getBaseDateFlg(),
//				domain.getAdvanceExcessMessDispAtr(),
//				domain.getHwAdvanceDispAtr(),
//				domain.getHwActualDispAtr(),
//				domain.getActualExcessMessDispAtr(),
//				domain.getOtAdvanceDispAtr(),
//				domain.getOtActualDispAtr(),
//				domain.getWarningDateDispAtr(),
//				domain.getAppReasonDispAtr(),
//				domain.getAppContentChangeFlg(),
//				domain.getScheReflectFlg(),
//				domain.getPriorityTimeReflectFlg(),
//				domain.getAttendentTimeReflectFlg());
//				}
}
