package nts.uk.ctx.at.record.app.query.reservation;

import lombok.Data;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;

@Data
public class BentoItemByClosingTimeDto {
	private Integer framNo;
	
	private String name;
	
	private Integer amount1;
	


	public BentoItemByClosingTimeDto() {
		super();
	}



	public BentoItemByClosingTimeDto(Integer framNo, String name, Integer bentoAmount) {
		super();
		this.framNo = framNo;
		this.name = name;
		this.amount1 = bentoAmount;
	}
	
	public static BentoItemByClosingTimeDto fromDomain(BentoItemByClosingTime domain) {
		return new BentoItemByClosingTimeDto(domain.getFrameNo(), domain.getName().v(), domain.getAmount1().v());
	}
}
