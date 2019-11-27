package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author thanhpv
 * 共通項目使用しない会社リスト
 */
@AllArgsConstructor
@Getter
public class NotUseCompanyList extends DomainObject{

	/** 会社ID */
	private String companyId;
	
}
