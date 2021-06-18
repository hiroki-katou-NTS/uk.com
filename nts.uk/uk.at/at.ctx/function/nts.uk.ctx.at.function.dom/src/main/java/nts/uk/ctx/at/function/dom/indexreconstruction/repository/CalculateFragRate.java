package nts.uk.ctx.at.function.dom.indexreconstruction.repository;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculateFragRate {

	private int indexId;

	/** 
	 * インデックス名
	 **/
	private String indexName;

	/** 
	 * テーブル物理名
	 **/
	private String tablePhysicalName;

	/** 
	 * 処理後の断片化率
	 **/
	private BigDecimal fragmentationRate;
}