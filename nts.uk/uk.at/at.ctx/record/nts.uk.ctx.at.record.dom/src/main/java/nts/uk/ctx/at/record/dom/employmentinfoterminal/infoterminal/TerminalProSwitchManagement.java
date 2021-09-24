package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * 
 * @author dungbn
 *	端末の本番切替管理
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.端末情報.端末の本番切替管理
 */
@AllArgsConstructor
@Data
public class TerminalProSwitchManagement implements DomainAggregate {

	// 	契約コード
	private final ContractCode contractCode;
	
	// 	管理区分	
	private ManageDistinct managementAtr;
	
}
