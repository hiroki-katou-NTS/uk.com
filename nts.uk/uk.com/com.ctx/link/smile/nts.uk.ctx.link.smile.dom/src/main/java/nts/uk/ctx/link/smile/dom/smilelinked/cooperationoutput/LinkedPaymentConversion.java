package nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 連動支払変換
 *
 */
@Getter
@AllArgsConstructor
public class LinkedPaymentConversion extends AggregateRoot {
	/**
	 * Name: 支払コード					
	 * Type: 支払区分	
	 */
	private final PaymentCategory paymentCode;
	/**
	 * Name: 選択雇用コード					
	 * Type: List 雇用と連動月設定 								
	 */
	private List<EmploymentAndLinkedMonthSetting> selectiveEmploymentCodes;
}
