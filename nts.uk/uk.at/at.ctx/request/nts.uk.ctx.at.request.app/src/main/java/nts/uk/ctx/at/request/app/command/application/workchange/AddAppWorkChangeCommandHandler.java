package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.SpecHdFrameForWkTypeSetService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddAppWorkChangeCommandHandler extends CommandHandlerWithResult<AddAppWorkChangeCommand, ProcessResult> {

	private static final String COLON_STRING = ":";
	@Inject
	private IWorkChangeRegisterService workChangeRegisterService;
	@Inject
	private IFactoryApplication IfacApp;
	@Inject
	private CollectAchievement collectAchievement;
	@Inject
	private SpecHdFrameForWkTypeSetService specHdWkpTypeSv;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<AddAppWorkChangeCommand> context) {
		AddAppWorkChangeCommand addCommand = context.getCommand();

		// Application command
		CreateApplicationCommand appCommand = addCommand.getApplication();
		// Work change command
		AppWorkChangeCommand workChangeCommand = addCommand.getWorkChange();
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		// 入力者 = 申請者
		// 申請者
		String applicantSID = addCommand.getEmployeeID()!=null?addCommand.getEmployeeID(): AppContexts.user().employeeId();
		// 申請
		Application_New app = IfacApp.buildApplication(appID, appCommand.getStartDate(), appCommand.getPrePostAtr(), null, appCommand.getApplicationReason().replaceFirst(COLON_STRING, System.lineSeparator()), ApplicationType.WORK_CHANGE_APPLICATION, appCommand.getStartDate(), appCommand.getEndDate(), applicantSID);
					
		// 勤務変更申請
		AppWorkChange workChangeDomain = AppWorkChange.createFromJavaType(
				companyId, 
				appID,
				workChangeCommand.getWorkTypeCd(), 
				workChangeCommand.getWorkTimeCd(),
				workChangeCommand.getExcludeHolidayAtr(), 
				workChangeCommand.getWorkChangeAtr(),
				workChangeCommand.getGoWorkAtr1(), 
				workChangeCommand.getBackHomeAtr1(),
				workChangeCommand.getBreakTimeStart1(), 
				workChangeCommand.getBreakTimeEnd1(),
				workChangeCommand.getWorkTimeStart1(), 
				workChangeCommand.getWorkTimeEnd1(),
				workChangeCommand.getWorkTimeStart2(), 
				workChangeCommand.getWorkTimeEnd2(),
				workChangeCommand.getGoWorkAtr2(), 
				workChangeCommand.getBackHomeAtr2());
		
		//1日休日のチェック
		checkHoliday(applicantSID,addCommand);
		//ドメインモデル「勤務変更申請設定」の新規登録をする
		return workChangeRegisterService.registerData(workChangeDomain, app);
	}

	
	/**
	 * 1日休日のチェック
	 * @param SID
	 * @param AddAppWorkChangeCommand
	 */
	private void checkHoliday(String applicantSID, AddAppWorkChangeCommand addCommand) {
		boolean isCheck = addCommand.getWorkChange().getExcludeHolidayAtr() == 1;
		// INPUT．休日除くチェック区分をチェックする
		if (isCheck) {
			//申請期間から休日の申請日を取得する
			List<GeneralDate> dateClears = getHolidayFromApp(applicantSID, addCommand);

			if (!CollectionUtil.isEmpty(dateClears)) {
				//日付一覧(output)の件数 > 0
				String dateListString = "";

				for (int i = 0; i < dateClears.size(); i++) {
					if (dateListString != "") {
						dateListString += "、";
					}
					dateListString += dateClears.get(i).toString("yyyy/MM/dd");
				}
				throw new BusinessException("Msg_1459",dateListString);
			}
		}
	}

	
	/**
	 * 申請期間から休日の申請日を取得する
	 * @param SID
	 * @param AddAppWorkChangeCommand
	 * @return dateClears
	 */
	private List<GeneralDate> getHolidayFromApp(String applicantSID, AddAppWorkChangeCommand addCommand) {
		// 日付一覧をクリアする（初期化）
		List<GeneralDate> dateClears = new ArrayList<GeneralDate>();

		GeneralDate curentDate = addCommand.getApplication().getStartDate();

		GeneralDate endDate = addCommand.getApplication().getEndDate();

		String companyID = AppContexts.user().companyId();
		// INPUT．期間．開始日から期間．終了日までループする
		do {
			// 実績の取得
			AchievementOutput achi = collectAchievement.getAchievement(companyID, applicantSID, curentDate);

			if (achi != null && !StringUtil.isNullOrEmpty(achi.getWorkType().getWorkTypeCode(), true)) {
				// 1日休日の判定
				boolean checkOneDay = specHdWkpTypeSv.jubgeHdOneDay(companyID, achi.getWorkType().getWorkTypeCode());
				if (checkOneDay) {
					// 日付一覧.Add(ループする日)
					dateClears.add(curentDate);
				}
			}

			curentDate = curentDate.addDays(1);
		} while (!curentDate.equals(endDate));
		
		return dateClears;
	}
}
