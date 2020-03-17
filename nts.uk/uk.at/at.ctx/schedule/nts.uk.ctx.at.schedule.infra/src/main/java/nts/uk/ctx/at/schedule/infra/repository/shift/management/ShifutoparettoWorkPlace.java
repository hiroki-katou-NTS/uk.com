package nts.uk.ctx.at.schedule.infra.repository.shift.management;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShifutoparettoWorkPlace {
	
	public int targetUnit;
	public String targetId;
	public int page;
	public int position;
	public int positionOrder;
	public String shiftMasterCd;

}
