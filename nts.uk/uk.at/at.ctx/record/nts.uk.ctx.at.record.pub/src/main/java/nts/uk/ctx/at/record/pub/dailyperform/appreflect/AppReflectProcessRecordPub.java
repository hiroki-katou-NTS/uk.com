package nts.uk.ctx.at.record.pub.dailyperform.appreflect;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback.GobackReflectPubParameter;

/**
 * 反映状況によるチェック
 * @author do_dt
 *
 */
public interface AppReflectProcessRecordPub {
	//チェック処理(Xử lý check)
	public boolean appReflectProcess(AppCommonPara para);
	//事前申請の処理(Xử lý xin trước) 　直行直帰
	public AppReflectPubOutput preGobackReflect(GobackReflectPubParameter para);
}
