/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.retiredismissalregulation.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RetirementRelatedInfoDto {
	
	/** 処理結果 */
	public Boolean processingResult;
	
	/** 公開日 */
	public GeneralDate releaseDate;
	
	/** 解雇予告日アラーム */
	public Boolean dismissalNoticeAlerm;
	
	/** 解雇予告日チェック処理 */
	public Boolean dismissalNoticeDateCheckProcess;
	
	public String errorMessageId;
	
	/** 解雇手当有無  tro cap thoi viec */
	public Boolean dismissalAllowance;
	
	/** 解雇予告日条件 */
	public DismissalNoticeDateCondition dismissalNoticeDateCondition;
	
	/** 解雇予告日 */
	public GeneralDate dismissalNoticeDate;
	
}
