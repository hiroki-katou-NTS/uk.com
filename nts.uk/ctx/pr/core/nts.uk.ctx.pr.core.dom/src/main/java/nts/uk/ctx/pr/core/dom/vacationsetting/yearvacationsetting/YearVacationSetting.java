package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

// TODO: Auto-generated Javadoc
/**
 * Gets the year vacation manage.
 *
 * @return the year vacation manage
 */
@Getter
public class YearVacationSetting extends DomainObject {

	/** The company id. */
	private String companyId;
	
	/** The time manage. */
	private Manage timeManage;
	
	/** The time manage setting. */
	private YearVacationTimeManageSetting timeManageSetting;
	
	/** The year vacation manage. */
	private Manage yearVacationManage;
	
	/** The manage setting. */
	private YearVacationManageSetting manageSetting;
}
