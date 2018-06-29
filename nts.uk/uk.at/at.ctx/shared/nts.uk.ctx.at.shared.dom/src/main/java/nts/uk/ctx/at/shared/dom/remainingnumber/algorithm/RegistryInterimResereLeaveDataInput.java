package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistryInterimResereLeaveDataInput {
	private String cid;
	private String sid;
	private GeneralDate baseDate;
	private List<InterimRemain> lstBeforeData;
	private List<InterimRemain> lstCreateData;
	private DailyInterimRemainMngData earchData;
	private RemainType remainType;
}
