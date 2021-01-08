package nts.uk.ctx.office.dom.goout;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/*
 * Repository UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.社員の外出情報
 */
public interface GoOutEmployeeInformationRepository {
	/**
	 * [1] insert(社員の外出情報)
	 * 
	 * @param domain 社員の外出情報
	 */
	public void insert(GoOutEmployeeInformation domain);

	/**
	 * [2] update(社員の外出情報)
	 * 
	 * @param domain 社員の外出情報
	 */
	public void update(GoOutEmployeeInformation domain);

	/**
	 * [3] delete(社員の外出情報)
	 * 
	 * @param domain 社員の外出情報
	 */
	public void delete(GoOutEmployeeInformation domain);

	/**
	 * [4]取得する
	 * 
	 * @param sids List<社員ID>
	 * @param date 年月日
	 * @return List<社社員の外出情報>
	 */
	public List<GoOutEmployeeInformation> getByListSidAndDate(List<String> sids, GeneralDate date);

	/**
	 * [5]取得する
	 * 
	 * @param sid  社員ID
	 * @param date 年月日
	 * @return Optional<社員の外出情報>
	 */
	public Optional<GoOutEmployeeInformation> getBySidAndDate(String sid, GeneralDate date);
}
