package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.FuncEmpInfoTerminalImport;
import nts.uk.ctx.at.function.dom.adapter.stamp.StampCard;
import nts.uk.shr.com.system.property.UKServerSystemProperties;
import nts.uk.shr.infra.data.TenantLocatorService;

/**
 * @author thanh_nx
 *
 *         タイムレコーダの通信を認証する
 */
public class AuthenticateNRCommunicationQuery {

	//[1] 認証
	public static boolean process(Require require, String contractCode, String macAddr) {

		connectCloud(contractCode);
		return require.getEmpInfoTerminal(contractCode, macAddr).isPresent();

	}
	
	//	[2] NRWeb照会の認証
	public static Optional<String> nrWebAuthen(Require require, String contractCode, String stampNumber){
		
		connectCloud(contractCode);
		
		return require.getByCardNoAndContractCode(contractCode, stampNumber).map(x -> x.getEmployeeId());
	}

	private static void connectCloud(String contractCode) {
		if (UKServerSystemProperties.usesTenantLocator()) {
			TenantLocatorService.connect(contractCode);
		}
	}
	
	public static interface Require{
		
		//	[R-1] 就業情報端末を取得する
		public Optional<FuncEmpInfoTerminalImport> getEmpInfoTerminal(String empInfoTerCode, String contractCode);
		
		//	[R-2] 打刻カードを取得する
		public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber);
	}
}
