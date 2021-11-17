package nts.uk.ctx.at.schedule.dom.shift.specificdayset;

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
	 * hoatt
	 * get list Specifi Date By List Code
	 * @param companyId
	 * @param lstSpecificDateItem
	 * @return
	 */
	List<SpecificDateItem> getSpecifiDateByListCode(String companyId, List<Integer> lstSpecificDateItem);
	
	/**
	 * 特定日項目NOで取得する
	 * @param companyId 会社ID
	 * @param specificDateItemNo 特定日項目NO
	 * @return
	 */
	SpecificDateItem getBySpecificDateItemNo( String companyId, SpecificDateItemNo specificDateItemNo );

}
