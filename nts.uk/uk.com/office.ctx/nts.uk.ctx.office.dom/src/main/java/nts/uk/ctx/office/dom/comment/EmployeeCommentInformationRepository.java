package nts.uk.ctx.office.dom.comment;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import nts.arc.time.GeneralDate;

/*
 * Repository UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.社員のコメント情報
 */
public interface EmployeeCommentInformationRepository {

	/**
	 * [1] insert(社員のコメント情報)
	 * 
	 * @param domain 社員のコメント情報
	 */
	public void insert(EmployeeCommentInformation domain);

	/**
	 * [2] update(社員のコメント情報)
	 * 
	 * @param domain 社員のコメント情報
	 */
	public void update(EmployeeCommentInformation domain);

	/**
	 * [3] delete(社員のコメント情報)
	 * 
	 * @param domain 社員のコメント情報
	 */
	public void delete(EmployeeCommentInformation domain);

	/**
	 * [4]取得する
	 * 
	 * @param sids List<社員ID>
	 * @param date 年月日
	 * @return Map<String, EmployeeCommentInformation> Map<社員ID、社員のコメント情報>
	 */
	public Map<String, EmployeeCommentInformation> getByListSidAndDate(List<String> sids, GeneralDate date);

	/**
	 * [5]取得する
	 * 
	 * @param sid  社員ID
	 * @param date 年月日
	 * @return EmployeeCommentInformation 社員のコメント情報
	 */
	public Optional<EmployeeCommentInformation> getBySidAndDate(String sid, GeneralDate date);

	/**
	 * [6]最新のコメントを取得する
	 * 
	 * @param sid 社員ID
	 * @return Optional<EmployeeCommentInformation> Optional<社員のコメント情報>
	 */
	public Optional<EmployeeCommentInformation> getTop1BySid(String sid);
}
