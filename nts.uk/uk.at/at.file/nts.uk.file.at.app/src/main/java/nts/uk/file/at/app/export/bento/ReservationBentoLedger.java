package nts.uk.file.at.app.export.bento;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class ReservationBentoLedger {
	
	private Integer bentoNumber;
	
	/**
	 * 弁当名
	 */
	private String bentoName;
	
	/**
	 * 数量小計
	 */
	private Integer totalQuantity;
	
	/**
	 * 金額１小計
	 */
	private Integer totalAmount1;
	
	/**
	 * 金額２小計
	 */
	private Integer totalAmount2;
	
	private Map<GeneralDate, Integer> quantityDateMap;
	
}
