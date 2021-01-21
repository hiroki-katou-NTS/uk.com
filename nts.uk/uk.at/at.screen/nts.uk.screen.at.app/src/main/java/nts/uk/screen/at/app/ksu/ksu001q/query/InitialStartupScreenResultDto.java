package nts.uk.screen.at.app.ksu.ksu001q.query;

import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * 初期起動Dto
 * 
 * @author thanhlv
 *
 */
@Data
public class InitialStartupScreenResultDto {

	/** 組織名称 */
	@Getter
	private String orgName;

	/** 外部予算実績項目 */
	@Getter
	private List<ExternalBudgetItem> externalBudgetItems;

}
