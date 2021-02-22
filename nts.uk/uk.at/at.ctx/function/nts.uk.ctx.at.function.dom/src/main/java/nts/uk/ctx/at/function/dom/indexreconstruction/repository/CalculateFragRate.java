package nts.uk.ctx.at.function.dom.indexreconstruction.repository;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculateFragRate {

	private int indexId;

	/** 
	 * インッ�クス�
	 **/
	private String indexName;

	/** 
	 * �ブル物琐� 
	 **/
	private String tablePhysicalName;

	/** 
	 * 処琉�の断牌��
	 **/
	private BigDecimal fragmentationRate;
}
