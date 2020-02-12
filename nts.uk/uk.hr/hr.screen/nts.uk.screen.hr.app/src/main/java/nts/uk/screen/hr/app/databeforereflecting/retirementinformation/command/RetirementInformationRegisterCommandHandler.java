package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.RetirementCategory;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retirementinformation.RetirementInformation_NewService;

@Stateless
public class RetirementInformationRegisterCommandHandler extends CommandHandler<RetirementInformationRegisterCommand> {

	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Inject
	private RetirementInformation_NewService service;

	@Override
	protected void handle(CommandHandlerContext<RetirementInformationRegisterCommand> context) {
		RetirementInformationRegisterCommand cmd = context.getCommand();
		// アルゴリズム[定年退職者情報の新規登録_変更]を実行する(thực hiện thuật toán [tạo mới/thay đổi
		// thông tin người nghỉ hưu])
		registerRetirementInformation(cmd.getRetiInfos());
	}

	private void registerRetirementInformation(List<RetiInforRegisInfoCommand> list) {
		// 定年退職者情報リストを個人情報反映前データリストへ変換する(Chuyển đổi RetirementInfoList thành
		// list data trước khi phản ánh thông tin cá nhân)
		List<DataBeforeReflectingPerInfo> listDomain = createRetirementInfoList(list);

		this.service.registerRetirementInformation(listDomain);

	}

	private List<DataBeforeReflectingPerInfo> createRetirementInfoList(List<RetiInforRegisInfoCommand> list) {

		return list.stream().map(x -> createNew(x)).collect(Collectors.toList());
	}

	private DataBeforeReflectingPerInfo createNew(RetiInforRegisInfoCommand cmd) {
		
		String historyId = cmd.getHistoryId();
		String contractCode = cmd.getContractCode();
		String companyId = cmd.getCompanyId();
		String companyCode =cmd.getCompanyCode();
		String pId = cmd.getPId();
		String sId = cmd.getSId();
		String scd = cmd.getScd();
		Integer workId =cmd.getWorkId();
		String personName = cmd.getPersonName();
		String workName = cmd.getWorkName();
		int requestFlag = cmd.getNotificationCategory();
		GeneralDate registerDate = GeneralDate.today();
		GeneralDateTime releaseDate = GeneralDateTime
				.fromString(cmd.getReleaseDate().toString("yyyy-MM-dd") + TIME_DAY_START, DATE_TIME_FORMAT);
		int onHoldFlag = cmd.getPendingFlag();
		int status = cmd.getStatus();
		String histId_Refer = cmd.getDst_HistId();

		GeneralDateTime date_01 = GeneralDateTime
				.fromString(cmd.getRetirementDate().toString("yyyy-MM-dd") + TIME_DAY_START, DATE_TIME_FORMAT);
		String select_code_01 = String.valueOf(RetirementCategory.retirementAge.value);
		String select_code_02 = cmd.getDesiredWorkingCourseCd();
		String select_code_03 = cmd.getRetirementReasonCtgCd1();
		String select_code_04 = String.valueOf(cmd.getExtendEmploymentFlg());

		String select_name_01 = RetirementCategory.retirementAge.name;
		String select_name_02 = cmd.getDesiredWorkingCourseName();
		String select_name_03 = cmd.getRetirementReasonCtgName1();

		DataBeforeReflectingPerInfo domain = new DataBeforeReflectingPerInfo(historyId, contractCode, companyId,
				companyCode, pId, sId, scd, workId, personName, workName, requestFlag, registerDate, releaseDate,
				onHoldFlag, status, histId_Refer, date_01, select_code_01, select_code_02, select_code_03,
				select_code_04, select_name_01, select_name_02, select_name_03);
		
		domain.setSelect_id_02(cmd.getDesiredWorkingCourseId());
		domain.setSelect_id_03(cmd.getRetirementReasonCtgID1());
		domain.setSelect_code_04(select_code_04);

		return domain;
	}

}
