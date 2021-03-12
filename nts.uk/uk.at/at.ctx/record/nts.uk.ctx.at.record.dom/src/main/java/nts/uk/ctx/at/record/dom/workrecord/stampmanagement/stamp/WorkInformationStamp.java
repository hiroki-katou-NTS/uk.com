/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.shr.com.context.AppContexts;

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
	 * 	・Require｛ 応援カードを取得する (SupportCardRepo), 就業情報端末を取得する(EmpInfoTerminalRepository) ｝
	 *  ・会社ID 
	 * 「Output」
	 * 	・勤務先情報Temporary
	 */
	public WorkInformationTemporary getWorkInformation(SupportCardRepo supportCardRepo, EmpInfoTerminalRepository empInfoTerminalRepo, String cid) {
		
		WorkInformationTemporary result = new WorkInformationTemporary();
		
		// 場所コードと職場IDを確認する
		if(this.workplaceID.isPresent() && this.cardNumberSupport.isPresent()){
			// 両方がある場合
			// 打刻場所コードと職場IDを返す
			result = new WorkInformationTemporary(this.workplaceID, this.workLocationCD);
			
			return result;
		}
		
		// 就業情報端末で場所と職場を取得する
		result =  getWorkInformationWithEmploymentInfoTerminal(empInfoTerminalRepo);
		if(result.getWorkplaceID().isPresent() && result.getWorkLocationCD().isPresent()){
			return result;
		}
		
		// 応援カード情報で職場IDを補正する
		correctWorkplaceIdWithSupportCardInformation(supportCardRepo, result, cid);
		
		// 勤務先情報の打刻場所コードで補正する
		if(this.workLocationCD.isPresent()){
			result.setWorkLocationCD(this.workLocationCD);
		}
		
		// 勤務先情報の職場IDで補正する
		if(this.workplaceID.isPresent()){
			result.setWorkplaceID(this.workplaceID);
		}
		
		return result;
		
	}

	// 就業情報端末で場所と職場を取得する  --> 就業情報端末で補正する
	public WorkInformationTemporary getWorkInformationWithEmploymentInfoTerminal(EmpInfoTerminalRepository empInfoTerminalRepo) {

		// 就業情報端末コードを確認する
		if(!this.empInfoTerCode.isPresent()){
			// Emptyの場合
			return new WorkInformationTemporary(Optional.empty(), Optional.empty());
		}

		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		EmpInfoTerminalCode empInfoTerCode = this.empInfoTerCode.get();
		
		// Requireで「就業情報端末」を取得する
		Optional<EmpInfoTerminal> empInfoTerminal = empInfoTerminalRepo.getEmpInfoTerminal(empInfoTerCode, contractCode);
		
		if(!empInfoTerminal.isPresent() || ( empInfoTerminal.isPresent() && empInfoTerminal.get().getCreateStampInfo() == null) ){
			return new WorkInformationTemporary(Optional.empty(), Optional.empty());
		}
		// 勤務場所と職場を返す
		CreateStampInfo createStampInfo = empInfoTerminal.get().getCreateStampInfo();
		return new WorkInformationTemporary(createStampInfo.getWorkPlaceId(), createStampInfo.getWorkLocationCd());
	}
	
	// 応援カード情報で職場IDを補正する
	public void correctWorkplaceIdWithSupportCardInformation(SupportCardRepo supportCardRepo, WorkInformationTemporary tempo, String cid) {
		
		// 応援カード番号を確認する
		if(!this.cardNumberSupport.isPresent()){
			return;
		}
		
		// Requireで「応援カード」を取得する
		Optional<SupportCard> supportCard = supportCardRepo.get(cid, this.cardNumberSupport.get().v());
		if(!supportCard.isPresent()){
			return;
		}
		
		// 職場IDを上書きする
		tempo.setWorkplaceID(Optional.of(supportCard.get().getWorkplaceId()));
	}
	
}
