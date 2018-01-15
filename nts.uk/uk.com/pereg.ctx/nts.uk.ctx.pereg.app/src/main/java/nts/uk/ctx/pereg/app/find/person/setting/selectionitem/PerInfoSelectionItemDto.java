package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import org.apache.commons.lang3.StringUtils;

import lombok.Value;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Value
public class PerInfoSelectionItemDto {
	private String selectionItemId;
	private String selectionItemName;
	private String memo;
	private int selectionItemClassification;
	private String contractCode;
	private String integrationCode;
	private FormatSelectionDto formatSelection;
	private int ReflectedToAllCompanies;
	public static PerInfoSelectionItemDto fromDomain(PerInfoSelectionItem domain) {
		// システム管理者　かつ　選択している選択項目の「選択項目区分」＝社員のとき
		String roleId = AppContexts.user().roles().forSystemAdmin();
		return new PerInfoSelectionItemDto(domain.getSelectionItemId(), domain.getSelectionItemName().v(),
				domain.getMemo().v(), domain.getSelectionItemClassification().value, domain.getContractCode(),
				domain.getIntegrationCode().v(), FormatSelectionDto.fromDomain(domain.getFormatSelection()),
				(StringUtils.isNotEmpty(roleId) && domain.getSelectionItemClassification().value == 1) ? 1: 0);

	}
}
