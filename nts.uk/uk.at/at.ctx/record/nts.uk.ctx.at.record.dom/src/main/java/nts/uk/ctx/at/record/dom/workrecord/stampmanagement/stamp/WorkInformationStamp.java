/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

/**
 * ValueObject : 勤務先情報 
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻.勤務先情報
 * @author laitv
 */
@AllArgsConstructor
public class WorkInformationStamp implements DomainValue {

	// 職場ID
	@Getter
	private final Optional<String> workplaceID;

	// 就業情報端末コード
	@Getter
	private final Optional<EmpInfoTerminalCode> empInfoTerCode;

	// 打刻場所コード
	@Getter
	@Setter
	private Optional<WorkLocationCD> workLocationCD;

	// 	応援カード番号
	@Getter
	private final Optional<SupportCardNumber> cardNumberSupport;
	
	// [1] 勤務場所と職場を取得する 
	// path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻.勤務先情報.勤務場所と職場を取得する
	/**
	 * 「パラメータ」 
	 * 	・Require｛ 応援カードを取得する (SupportCardRepo), 就業情報端末を取得する(EmpInfoTerminalRepository) ｝
	 *  ・会社ID 
	 * 「Output」
	 * 	・勤務先情報Temporary
	 */
	public WorkInformationTemporary getWorkInformation(SupportCardRepository supportCardRepo, EmpInfoTerminalRepository empInfoTerminalRepo, String cid) {
		
		WorkInformationTemporary result = new WorkInformationTemporary(); 
		
		// 場所コードと職場IDを確認する
		if(this.workplaceID.isPresent() && this.workLocationCD.isPresent()){
			// 両方がある場合
			// 打刻場所コードと職場IDを返す
			result = new WorkInformationTemporary(this.workplaceID, this.workLocationCD);
			
			return result;
		}
		
		// 就業情報端末で場所と職場を取得する
		result =  getWorkInformationWithEmploymentInfoTerminal(empInfoTerminalRepo);
		
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

		EmpInfoTerminalCode empInfoTerCode = this.empInfoTerCode.get();
		
		// Requireで「就業情報端末」を取得する
		Optional<EmpInfoTerminal> empInfoTerminal = empInfoTerminalRepo.getEmpInfoTerminal(empInfoTerCode);
		
		if(!empInfoTerminal.isPresent()){
			return new WorkInformationTemporary(Optional.empty(), Optional.empty());
		}
		
		if(empInfoTerminal.get().getCreateStampInfo() == null){
			return new WorkInformationTemporary(Optional.empty(), Optional.empty());
		}
		// 勤務場所と職場を返す
		CreateStampInfo createStampInfo = empInfoTerminal.get().getCreateStampInfo();
		return new WorkInformationTemporary(
				createStampInfo.getWorkPlaceId().isPresent() ?  Optional.of(createStampInfo.getWorkPlaceId().get().toString()) : Optional.empty(), 
				createStampInfo.getWorkLocationCd());
	}
	
	// 応援カード情報で職場IDを補正する
	public void correctWorkplaceIdWithSupportCardInformation(SupportCardRepository supportCardRepo, WorkInformationTemporary tempo, String cid) {
		
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
	
	@Override
	public WorkInformationStamp clone() {
		return new WorkInformationStamp(this.getWorkplaceID(), empInfoTerCode.map(x -> new EmpInfoTerminalCode(x.v())), 
				workLocationCD.map(x -> new WorkLocationCD(x.v())), 
				cardNumberSupport.map(x -> new SupportCardNumber(x.v())));
	}
}
