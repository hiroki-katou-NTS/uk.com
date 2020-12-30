package nts.uk.screen.at.app.query.knr.knr002.d;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｄ：リモート設定複写ダイアログ.メニュー別OCD.Ｄ：リモート設定を他の端末へ複写可能かチェックする
 * @author xuannt
 *
 */

@Stateless
public class CheckRemoteSettingsToCopy {
	//	タイムレコード設定更新リストRepository.[2]取得する
	@Inject
	private TimeRecordSetUpdateListRepository timeRecordSetUpdateListRepository;
	
	/**
	 * 取得する
	 * @param listEmpInfoTerCode
	 * @return
	 */
	public void checkRemoteSettingsToCopy (List<String> listEmpInfoTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		//	1. get*(契約コード、就業情報端末コード<List>): タイムレコード設定更新リスト<List>
		List<TimeRecordSetUpdateList> getTimeRecordSetUpdateList = this.timeRecordSetUpdateListRepository
																	   .get(contractCode, listEmpInfoTerCode.stream().map(e -> new EmpInfoTerminalCode(e)).collect(Collectors.toList()));
		
		if (getTimeRecordSetUpdateList.size() > 0) {
			String result = getTimeRecordSetUpdateList.stream().map(e -> "「" + e.getEmpInfoTerCode().v() + "」").collect(Collectors.joining());
			throw new BusinessException("Msg_1986", result);
		}
	}
}
