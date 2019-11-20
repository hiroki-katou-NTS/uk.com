package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppListParamFilter {

	private AppListExtractConditionDto condition;
	private boolean spr;
	private int extractCondition;
	//デバイス：PC = 0 or スマートフォン = 1
	private int device;
	//対象申請種類List
	private List<Integer> lstAppType;
}
