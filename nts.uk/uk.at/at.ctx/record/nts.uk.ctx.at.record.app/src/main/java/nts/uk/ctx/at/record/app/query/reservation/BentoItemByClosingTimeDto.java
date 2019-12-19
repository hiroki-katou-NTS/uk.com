package nts.uk.ctx.at.record.app.query.reservation;

import lombok.Data;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;

@Data
public class BentoItemByClosingTimeDto {
	private Integer frameNo;
	
	private String name;
	
	private Integer amount1;
	
	public BentoItemByClosingTimeDto() {
		super();
	}



	public BentoItemByClosingTimeDto(Integer frameNo, String name, Integer bentoAmount) {
		super();
		this.frameNo = frameNo;
		this.name = name;
		this.amount1 = bentoAmount;
	}
	
	public static BentoItemByClosingTimeDto fromDomain(BentoItemByClosingTime domain) {
		return new BentoItemByClosingTimeDto(domain.getFrameNo(), domain.getName().v(), domain.getAmount1().v());
	}
}
