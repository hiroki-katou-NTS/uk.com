package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.AppRouteUpdateDaily;

/**
 * The class App router update daily dto.<br>
 * Dto 承認ルート更新（日次）
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class AppRouteUpdateDailyDto {

	/**
	 * 承認ルート更新区分
	 */
	private int appRouteUpdateAtr;

	/**
	 * 新入社員を作成する
	 */
	private Integer createNewEmp;

	/**
	 * No args constructor.
	 */
	private AppRouteUpdateDailyDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the App router update daily dto
	 */
	public static AppRouteUpdateDailyDto createFromDomain(AppRouteUpdateDaily domain) {
		if (domain == null) {
			return null;
		}
		AppRouteUpdateDailyDto dto = new AppRouteUpdateDailyDto();
		dto.appRouteUpdateAtr = domain.getAppRouteUpdateAtr().value;
		dto.createNewEmp = domain.getCreateNewEmpApp()
								 .map(createNewEmp -> createNewEmp.value)
								 .orElse(null);
		return dto;
	}

}
