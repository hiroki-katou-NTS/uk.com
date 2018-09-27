package nts.uk.ctx.at.function.dom.processexecution;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
/**
 * 承認ルート更新（日次）
 * @author tutk
 *
 */
@Getter
public class AppRouteUpdateDaily extends DomainObject {
	
	/** 承認ルート更新区分 */
	private NotUseAtr appRouteUpdateAtr;
	
	/** 新入社員を作成する */
	private Optional<NotUseAtr> createNewEmp;

	public AppRouteUpdateDaily(NotUseAtr appRouteUpdateAtr, NotUseAtr createNewEmp) {
		super();
		this.appRouteUpdateAtr = appRouteUpdateAtr;
		this.createNewEmp = Optional.ofNullable(createNewEmp);
	}
	

}
