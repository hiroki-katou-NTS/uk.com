package nts.uk.screen.at.app.cmm040.worklocation;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.worklocation.InsertUpdateWorkLocationCmd;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPlaceOfWorkOutput {
	private String companyId;
	
	private List<InsertUpdateWorkLocationCmd> listWorkLocationDto = new ArrayList<>();
}
