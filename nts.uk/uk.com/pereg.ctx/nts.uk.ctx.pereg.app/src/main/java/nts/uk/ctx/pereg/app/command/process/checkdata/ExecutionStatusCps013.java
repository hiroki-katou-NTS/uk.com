package nts.uk.ctx.pereg.app.command.process.checkdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExecutionStatusCps013 {

	// 0:完了
	DONE(0, "完了"),
	// 1:完了（エラーあり）
	DONE_WITH_ERROR(1, "完了（エラーあり）"),
	// 2:処理中
	PROCESSING(2, "実行中"),
	// 3:実行中止
	STOP_EXECUTION(3, "実行中止"),
	// 4:中断開始
	START_OF_INTERRUPTION(4, "中断開始"),
	// 5:中断終了
	END_OF_INTERRUPTION(5, "中断終了");

	public final int value;
	public final String name;
}
