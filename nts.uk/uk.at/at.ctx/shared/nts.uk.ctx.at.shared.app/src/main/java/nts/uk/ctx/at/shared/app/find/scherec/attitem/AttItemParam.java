package nts.uk.ctx.at.shared.app.find.scherec.attitem;

import java.util.List;

import lombok.Data;

@Data
public class AttItemParam {
	private String roleId;
	private List<Integer> attendanceItemIds;
	private List<Integer> itemAtrs;
}
