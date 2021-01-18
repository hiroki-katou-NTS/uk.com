package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessState;
import nts.uk.ctx.at.request.dom.application.overtime.OutDateApplication;

@AllArgsConstructor
@NoArgsConstructor
public class OutDateApplicationCommand {
	// ﾌﾚｯｸｽの超過状態
	public Integer flex;
	// 休出深夜時間
	public List<ExcessStateMidnightCommand> excessStateMidnight;
	// 残業深夜の超過状態
	public Integer overTimeLate;
	// 申請時間
	public List<ExcessStateDetailCommand> excessStateDetail;
	
	public OutDateApplication toDomain() {
		// dummy data
		if (flex == null) {
			flex = 0;
		}
		if (overTimeLate == null) {
			overTimeLate = 0;
		}
		return new OutDateApplication(
				EnumAdaptor.valueOf(flex, ExcessState.class),
				CollectionUtil.isEmpty(excessStateMidnight) ?
						Collections.emptyList() : 
						excessStateMidnight.stream()
										   .map(x -> x.toDomain())
										   .collect(Collectors.toList()),
				EnumAdaptor.valueOf(overTimeLate, ExcessState.class),
				CollectionUtil.isEmpty(excessStateDetail) ?
						Collections.emptyList() : 
							excessStateDetail.stream()
										   .map(x -> x.toDomain())
										   .collect(Collectors.toList()));
	}
}
