package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting;

import java.util.List;

public interface SpecificDateItemRepository {
	
	/**
	 * *get
	 * @param companyId 会社ID
	 * @return
	 */
	List<SpecificDateItem> getAll(String companyId);
	
	/**
	 * 使用区分で取得する
	 * @param companyId 会社ID
	 * @param useAtr 使用区分
	 * @return
	 */
	List<SpecificDateItem> getByUseAtr(String companyId, int useAtr);

	/**
	 * update
	 * @param domain 特定日項目
	 */
	void update ( SpecificDateItem domain );
	
	/**
	 * 特定日項目NOリストで取得する
	 * @param companyId 会社ID
	 * @param specificDateItemNos 特定日項目NOリスト
	 * @return
	 */
	List<SpecificDateItem> getSpecifiDateByListCode(String companyId, List<SpecificDateItemNo> specificDateItemNos);

}
