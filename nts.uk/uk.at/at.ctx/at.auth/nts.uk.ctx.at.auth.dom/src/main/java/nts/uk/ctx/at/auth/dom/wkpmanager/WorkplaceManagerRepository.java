package nts.uk.ctx.at.auth.dom.wkpmanager;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface WorkplaceManagerRepository {
	// アルゴリズム「職場情報の取得（起動時用）」を実行する
	List<String> getAllWorkplaceList();
	// アルゴリズム「職場管理者情報の取得」を実行する
	List<String> getAllWorkplaceManagerList(String companyId, String workplaceId, GeneralDate refDate);
	// アルゴリズム「社員所属職場履歴」を起動する
	List<String> getEmployeeWorkplaceHistory(String employeeId, GeneralDate baseDate);
	// アルゴリズム「職場IDから職場情報を取得」を実行する
	List<String> getWorkplaceByWorkplaceId(String companyId, String workplaceHstId, GeneralDate refDate);
}
