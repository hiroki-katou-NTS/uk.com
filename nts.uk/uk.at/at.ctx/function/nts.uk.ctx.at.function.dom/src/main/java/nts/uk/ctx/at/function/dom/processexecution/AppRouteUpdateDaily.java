package nts.uk.ctx.at.function.dom.processexecution;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.Optional;

/**
 * The class Approval route update daily.<br>
 * Domain 承認ルート更新（日次）
 *
 * @author nws-minhnb
 */
@Getter
public class AppRouteUpdateDaily extends DomainObject {

//	/** 承認ルート更新区分 */
//	private NotUseAtr appRouteUpdateAtr;
//
//	/** 新入社員を作成する */
//	private Optional<NotUseAtr> createNewEmp;

	/**
	 * The Approval route update attribute.<br>
	 * 承認ルート更新区分
	 */
	private NotUseAtr appRouteUpdateAtr;

	/**
	 * The Create new employee approval.<br>
	 * 新入社員を作成する
	 */
	private Optional<NotUseAtr> createNewEmpApp;

	/**
	 * Instantiates a new App route update daily.
	 *
	 * @param appRouteUpdateAtr the approval route update attribute
	 * @param createNewEmpApp   the create new employee approval
	 */
	public AppRouteUpdateDaily(NotUseAtr appRouteUpdateAtr, NotUseAtr createNewEmpApp) {
		super();
		this.appRouteUpdateAtr = appRouteUpdateAtr;
		this.createNewEmpApp = Optional.ofNullable(createNewEmpApp);
	}

	/**
	 * Instantiates a new App route update daily.
	 *
	 * @param appRouteUpdateAtr the approval route update attribute
	 * @param createNewEmpApp   the create new employee approval
	 */
	public AppRouteUpdateDaily(int appRouteUpdateAtr, Integer createNewEmpApp) {
		this.appRouteUpdateAtr = EnumAdaptor.valueOf(appRouteUpdateAtr, NotUseAtr.class);
		this.createNewEmpApp = Optional.ofNullable(createNewEmpApp).map(value -> EnumAdaptor.valueOf(value, NotUseAtr.class));
	}

}
