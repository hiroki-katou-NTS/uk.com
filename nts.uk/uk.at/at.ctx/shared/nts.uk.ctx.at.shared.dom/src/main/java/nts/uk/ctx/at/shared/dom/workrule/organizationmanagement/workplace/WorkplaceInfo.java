package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 職場情報一覧
 * @author Hieult
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceInfo {
		/**職場ID **/
	private String workplaceId;
	
	/**職場コード **/
	private Optional<String> workplaceCd;
	
	/**職場名称 **/
	private Optional<String> workplaceName;
	
	/** 職場外部コード**/
	private Optional<String> outsideWkpCd;
	
	/** 職場総称 **/
	private Optional<String> wkpGenericName;
	
	/** 職場表示名 **/
	private Optional<String> wkpDisplayName;
	
	/** 階層コード**/
	private Optional<String> tierCd; 
	


}
