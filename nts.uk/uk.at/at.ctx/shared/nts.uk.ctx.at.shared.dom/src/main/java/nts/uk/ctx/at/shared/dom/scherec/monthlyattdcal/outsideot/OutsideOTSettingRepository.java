/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;

/**
 * The Interface OutsideOTSettingRepository.
 */
public interface OutsideOTSettingRepository {
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<OutsideOTSetting> findById(String companyId);
	
	/**
	 * Find all by cid
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<OutsideOTSetting> findByIdV2(String companyId);
	
	/**
	 * Report by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<OutsideOTSetting> reportById(String companyId);
	
	
	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void save(OutsideOTSetting domain);
	
	
	/**
	 * Save all BRD item.
	 *
	 * @param overtimeBreakdownItems the overtime breakdown items
	 * @param companyId the company id
	 */
	public void saveAllBRDItem(List<OutsideOTBRDItem> overtimeBreakdownItems, String companyId);
	
	
	/**
	 * Find all BRD item.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OutsideOTBRDItem> findAllBRDItem(String companyId);
	
	/**
	 * Find all use BRD item.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OutsideOTBRDItem> findAllUseBRDItem(String companyId);
	
	/**
	 * Find all overtime.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<Overtime> findAllOvertime(String companyId);
	
	/**
	 * Find all use overtime.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<Overtime> findAllUseOvertime(String companyId);
	
	
	/**
	 * Save all overtime.
	 *
	 * @param overtimes the overtimes
	 * @param companyId the company id
	 */
	public void saveAllOvertime(List<Overtime> overtimes, String companyId);

	/**
	 *対応するドメインモデル「超過時間」を取得する Nhận domain model 「超過時間」 tương ứng
	 *
	 * @param companyId the company id
	 * @param useClassification the use classification
	 * @return the over time by company id and use classification
	 */
	public List<Overtime> getOverTimeByCompanyIdAndUseClassification(String companyId, int useClassification);
	
	/**
	 * 対応するドメインモデル「時間外超過の内訳項目」を取得する Nhận domain model  「時間外超過の内訳項目」 tương ứng
	 *
	 * @param companyId the company id
	 * @param useClassification the use classification
	 * @return the by company id and use classification
	 */
	public List<OutsideOTBRDItem> getByCompanyIdAndUseClassification(String companyId, int useClassification);
}
