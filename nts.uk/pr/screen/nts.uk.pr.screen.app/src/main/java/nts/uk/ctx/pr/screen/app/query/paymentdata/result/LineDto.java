package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import java.util.List;

import lombok.Value;

@Value
public class LineDto {

	private int categoryAtr;
	
	private int linePosition;

	private List<DetailItemDto> layoutMasterDetails;

	public static LineDto fromDomain(
		int categoryAtr,
		int linePosition,
		List<DetailItemDto> layoutMasterDetails){
	
	return new LineDto(categoryAtr,linePosition,  layoutMasterDetails);
	}
	
}
