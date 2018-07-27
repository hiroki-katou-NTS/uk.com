package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init;

import java.util.List;

/**
 * 14-1.詳細画面起動前申請共通設定を取得する
 * @author Doan Duy Hung
 *
 */
public interface DetailAppCommonSetService {
	
	public ApplicationMetaOutput getDetailAppCommonSet(String companyID, String applicationID);
	
	public List<ApplicationMetaOutput> getListDetailAppCommonSet(String companyID, List<String> listAppID);
	
}
