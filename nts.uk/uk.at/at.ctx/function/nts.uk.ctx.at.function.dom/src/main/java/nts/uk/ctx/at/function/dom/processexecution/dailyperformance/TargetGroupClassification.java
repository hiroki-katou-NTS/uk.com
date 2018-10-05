package nts.uk.ctx.at.function.dom.processexecution.dailyperformance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
//更新処理の日別処理対象者区分
@AllArgsConstructor
@Getter
@Setter
public class TargetGroupClassification {
	
	/*勤務種別者を再作成*/  //B15_3
	private boolean recreateTypeChangePerson;
	
	/* 途中入社は入社日からにする   ->  新入社員を再作成する */ //B8_5
	private boolean midJoinEmployee;
	
	/* 異動者を再作成する*/  //B15_2	
	private boolean recreateTransfer;  
}
