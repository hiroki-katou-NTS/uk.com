package nts.uk.ctx.exio.dom.exi.condset;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StdAcceptCondSetCopyParam {
	
	private String cId;
	/**
	 * システム種類
	 */
	private int systemType;
	
	/**
	 * 外部受入条件コード
	 */
	private String sourceCondSetCode;
	
	/**
	 * 外部受入条件コード
	 */
	private String destCondSetCode;

	/**
	 * 外部受入条件名称
	 */
	private String destCondSetName;
	
	private boolean override;
	
	
}
