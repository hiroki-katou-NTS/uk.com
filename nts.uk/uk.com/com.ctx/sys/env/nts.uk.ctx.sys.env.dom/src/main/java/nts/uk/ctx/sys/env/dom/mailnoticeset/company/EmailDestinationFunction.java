package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;

import java.util.List;

/**
 * メール送信先機能
 */
@Getter
@Setter
@Builder
public class EmailDestinationFunction {
	/**
	 * メール分類
	 */
	private EmailClassification emailClassification;

	/**
	 * 機能ID
	 */
	private List<FunctionId> functionIds;
}