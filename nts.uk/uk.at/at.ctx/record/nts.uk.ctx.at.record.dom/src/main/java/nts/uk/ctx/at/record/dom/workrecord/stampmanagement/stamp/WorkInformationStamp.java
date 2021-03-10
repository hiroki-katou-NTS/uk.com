/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.DeleteEmpInfoTerminalService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

/**
 * ValueObject : 勤務先情報 
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻.勤務先情報
 * @author laitv
 */
public class WorkInformationStamp implements DomainValue {

	// 職場ID
	@Getter
	private final Optional<String> workplaceID;

	// 就業情報端末コード
	@Getter
	private final Optional<EmpInfoTerminalCode> empInfoTerCode;

	// 打刻場所コード
	@Getter
	private final Optional<WorkLocationCD> workLocationCD;

	// 	応援カード番号
	@Getter
	private final Optional<SupportCardNumber> cardNumberSupport;
	
	public WorkInformationStamp(String workplaceID, EmpInfoTerminalCode empInfoTerCode, WorkLocationCD workLocationCD,
			SupportCardNumber cardNumberSupport) {
		super();
		this.workplaceID = Optional.ofNullable(workplaceID);
		this.empInfoTerCode = Optional.ofNullable(empInfoTerCode);
		this.workLocationCD = Optional.ofNullable(workLocationCD);
		this.cardNumberSupport = Optional.ofNullable(cardNumberSupport);
	}

	public static WorkInformationStamp create(String workplaceID, EmpInfoTerminalCode empInfoTerCode,
			WorkLocationCD workLocationCD, SupportCardNumber cardNumberSupport) {

		return new WorkInformationStamp(workplaceID, empInfoTerCode, workLocationCD, cardNumberSupport);
	}
	
	
	// [1] 勤務場所と職場を取得する 
	// path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻.勤務先情報.勤務場所と職場を取得する
	/**
	 * 「パラメータ」 
	 * 	・Require｛ 応援カードを取得する, 就業情報端末を取得する ｝
	 *  ・会社ID 
	 * 「Output」
	 * 	・勤務先情報Temporary
	 */
	public WorkInformationTemporary getPlaceOfWorkAndWorkplace(DeleteEmpInfoTerminalService.Require require,String cid) {
		
		return null;
		
	}

}
