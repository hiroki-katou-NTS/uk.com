package nts.uk.ctx.at.request.app.find.application.requestofearch;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEachWorkplaceRepository;

@Stateless
public class GetDataAppCfDetailFinder {
	@Inject
	private RequestOfEachCompanyRepository detailCompanyRepo;

	@Inject
	private RequestOfEachWorkplaceRepository detailWorkplaceRepo;

	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepo;

	public OutputMessageDeadline getDataConfigDetail(InputMessageDeadline inputMessageDeadline) {
		String message = "";
		String deadline = "";
		GeneralDate date1 = GeneralDate.today();
		GeneralDate date2 = GeneralDate.today();
		GeneralDate date3 = GeneralDate.today();

		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepo
				.getAppTypeDiscreteSettingByAppType(inputMessageDeadline.getCompanyID(),
						inputMessageDeadline.getAppType());
		// 事後申請の受付は7月27日分まで。
		// 「事後の受付制限」．未来日許可しないがtrue、その他は利用しない
		// if : RetrictPostAllowFutureFlg = true(allow)s và RetrictPreUseFlg = false(not
		// use)
		if(!appTypeDiscreteSetting.isPresent()) {
			return new  OutputMessageDeadline("","");
		}
		if (appTypeDiscreteSetting.get().getRetrictPostAllowFutureFlg().value == 1
				&& appTypeDiscreteSetting.get().getRetrictPreUseFlg().value == 0) {
			deadline = "事後申請の受付は" + date2 + "分まで";
			// deadline ="事前申請の受付は"+date+"分から。事後申請の受付は"+date+"分まで。当月の申請は"+date+"まで";
		}

		if (inputMessageDeadline.getWorkplaceID()==null) {
			// this is company
			Optional<AppConfigDetailDto> appConfigDetailCom = detailCompanyRepo
					.getRequestDetail(inputMessageDeadline.getCompanyID(), inputMessageDeadline.getAppType())
					.map(c -> AppConfigDetailDto.fromDomain(c));
			if (appConfigDetailCom.isPresent()) { // ton tai
				if (!appConfigDetailCom.get().getMemo().isEmpty()) {
					message = appConfigDetailCom.get().getMemo();
				}
			}
			// 条件：「申請利用設定」．備考に内容なし && 「申請締切設定」．利用区分が利用しない && 「事前の受付制限」．利用区分が利用しない
			// && 「事後の受付制限」．未来日許可しないがfalse
			if (appConfigDetailCom.get().getMemo().isEmpty() == true
					// && appConfigDetailCom.get().getUserAtr() ==0 // use Atr = 0
					&& appTypeDiscreteSetting.get().getRetrictPreUseFlg().value == 0
					&& appTypeDiscreteSetting.get().getRetrictPostAllowFutureFlg().value == 0) {
				message = "";
				deadline = "";
			}

			if (appConfigDetailCom.get().getMemo().isEmpty() == false
					// && appConfigDetailCom.get().getUserAtr() ==0 // use Atr = 1
					&& appTypeDiscreteSetting.get().getRetrictPreUseFlg().value == 1
					&& appTypeDiscreteSetting.get().getRetrictPostAllowFutureFlg().value == 1) {
				// if retrictPreMethodFlg is timecheck
				if (appTypeDiscreteSetting.get().getRetrictPreMethodFlg().value == 0) {
					int a = Integer.parseInt(appTypeDiscreteSetting.get().getRetrictPreTimeDay().v().toString());
					int minute = a % 60;
					int hours = a % (60 * 24);
					int date = hours / 24;
					date1.addDays(date);

					deadline = "事前申請の受付は" + date1.month() + "月" + date1.day() + "日 " + hours + ":" + minute + " 分から。"
							+ "事後申請の受付は" + date2.month() + "月" + date2.day() + "日" + "分まで。" + "当月の申請は" + date3.month()
							+ "月" + date3.day() + "日" + "まで";
				}
				// if retrictPreMethodFlg is daycheck
				if (appTypeDiscreteSetting.get().getRetrictPreMethodFlg().value == 1) {
					date1 = date1.addDays(appTypeDiscreteSetting.get().getRetrictPreDay().value);

					deadline = "事前申請の受付は" + date1.month() + "月" + date1.day() + "日  分から。" + "事後申請の受付は" + date2.month()
							+ "月" + date2.day() + "日" + "分まで。" + "当月の申請は" + date3.month() + "月" + date3.day() + "日"
							+ "まで";
				}

			}

		} else {
			// this is workplace
			Optional<AppConfigDetailDto> appConfigDetailWP = detailWorkplaceRepo
					.getRequestDetail(inputMessageDeadline.getCompanyID(), inputMessageDeadline.getWorkplaceID(),
							inputMessageDeadline.getAppType())
					.map(c -> AppConfigDetailDto.fromDomain(c));
			if (appConfigDetailWP.isPresent()) { // ton tai
				if (!appConfigDetailWP.get().getMemo().isEmpty()) {
					message = appConfigDetailWP.get().getMemo();
				}
			}

			// 条件：「申請利用設定」．備考に内容なし && 「申請締切設定」．利用区分が利用しない && 「事前の受付制限」．利用区分が利用しない
			// && 「事後の受付制限」．未来日許可しないがfalse
			if (appConfigDetailWP.get().getMemo().isEmpty() == true
					// && appConfigDetailCom.get().getUserAtr() ==0 // use Atr = 0
					&& appTypeDiscreteSetting.get().getRetrictPreUseFlg().value == 0
					&& appTypeDiscreteSetting.get().getRetrictPostAllowFutureFlg().value == 0) {
				message = "";
				deadline = "";
			}

			if (appConfigDetailWP.get().getMemo().isEmpty() == false
					// && appConfigDetailCom.get().getUserAtr() ==0 // use Atr = 1
					&& appTypeDiscreteSetting.get().getRetrictPreUseFlg().value == 1
					&& appTypeDiscreteSetting.get().getRetrictPostAllowFutureFlg().value == 1) {
				// if retrictPreMethodFlg is timecheck
				if (appTypeDiscreteSetting.get().getRetrictPreMethodFlg().value == 0) {
					int a = Integer.parseInt(appTypeDiscreteSetting.get().getRetrictPreTimeDay().v().toString());
					int minute = a % 60;
					int hours = a % (60 * 24);
					int date = hours / 24;
					date1.addDays(date);

					deadline = "事前申請の受付は" + date1.month() + "月" + date1.day() + "日 " + hours + ":" + minute + " 分から。"
							+ "事後申請の受付は" + date2.month() + "月" + date2.day() + "日" + "分まで。" 
							+ "当月の申請は" + date3.month() + "月" + date3.day() + "日" + "まで";
				}
				// if retrictPreMethodFlg is daycheck
				if (appTypeDiscreteSetting.get().getRetrictPreMethodFlg().value == 1) {
					date1.addDays(Integer.parseInt(appTypeDiscreteSetting.get().getRetrictPreDay().toString()));

					deadline = "事前申請の受付は" + date1.month() + "月" + date1.day() + "日  分から。" 
							+ "事後申請の受付は" + date2.month() + "月" + date2.day() + "日" + "分まで。"
							+ "当月の申請は" + date3.month() + "月" + date3.day() + "日"+ "まで";
				}

			}
		}

		return new OutputMessageDeadline(message, deadline);
	}
}
