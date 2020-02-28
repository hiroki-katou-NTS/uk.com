package nts.uk.ctx.hr.shared.dom.employee;

import java.util.Optional;

public interface GrpCommonMasterAdaptor {
	/**
	 * get domain グループ会社共通マスタ by 共通マスタID and 契約コード
	 * @author yennth
	 */
	Optional<GrpCmonMasterImport> findCommonMasterByContract(String contractCd, String cmMasterId);
}
