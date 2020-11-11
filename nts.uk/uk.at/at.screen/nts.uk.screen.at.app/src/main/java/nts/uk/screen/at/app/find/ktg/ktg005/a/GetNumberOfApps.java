package nts.uk.screen.at.app.find.ktg.ktg005.a;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KTG_ウィジェット.KTG005_申請件数.アルゴリズム.申請件数起動.申請件数起動
 */
@Stateless
public class GetNumberOfApps {

	/**
	 * 申請件数取得
	 * 
	 * @param companyId  会社ID
	 * @param period     期間（開始日～終了日）
	 * @param employeeId 社員ID
	 */
	public static NumberOfAppDto getNumberOfApps(Require require, String companyId, DatePeriod period, String employeeId) {
		// アルゴリズム「指定する社員の期間内の申請を取得」を実行する
		List<Application> apps = require.getByListSID(companyId, Arrays.asList(employeeId), period.start(),
				period.end());
		// アルゴリズム「指定した複数の申請から申請件数のカウント」を実行する
		return coutingAppFromMultiApps(apps);

	}

	/**
	 * 指定した複数の申請から申請件数のカウント
	 * 
	 * @param apps ドメインモデル：申請（リスト）
	 * @return
	 */
	private static NumberOfAppDto coutingAppFromMultiApps(List<Application> apps) {

		NumberOfAppDto number = new NumberOfAppDto();

		apps.stream().forEach(app -> {
			// 反映状態を取得する
			ReflectedState reflectedState = app.getAppReflectedState();
			switch (reflectedState) {
			case REFLECTED:
			case WAITREFLECTION:
				// 反映済/反映待ちの場合
				number.setNumberApprovals(number.getNumberApprovals() + 1);
				break;

			case NOTREFLECTED:
				// 未反映の場合
				number.setNumberNotApprovals(number.getNumberNotApprovals() + 1);
				break;

			case DENIAL:
				// 否認の場合
				number.setNumberDenials(number.getNumberDenials() + 1);
				break;
			case REMAND:
				// 差し戻し
				number.setNumberRemand(number.getNumberRemand() + 1);
				break;
			}

		});

		return number;
	}

	public static interface Require {
		public List<Application> getByListSID(String companyId, List<String> lstSID, GeneralDate sDate,
				GeneralDate eDate);
	}

	public static Require createRequire(ApplicationRepository appRepo) {

		return new Require() {

			@Override
			public List<Application> getByListSID(String companyId, List<String> lstSID, GeneralDate sDate,
					GeneralDate eDate) {
				return appRepo.getApplicationBySIDs(lstSID, sDate, eDate);
			}

		};
	}
}
