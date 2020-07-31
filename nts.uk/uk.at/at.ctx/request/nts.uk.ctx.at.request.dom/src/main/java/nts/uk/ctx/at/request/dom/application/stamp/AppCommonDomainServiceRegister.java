package nts.uk.ctx.at.request.dom.application.stamp;


import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;

public interface AppCommonDomainServiceRegister {
//	打刻申請の新規登録
	/**
	 * Refactor4
	 * 打刻申請の新規登録
	 * UKDesign.UniversalK.就業.KAF_申請.KAF002_打刻申請.B：打刻申請（新規）→A・B画面.打刻申請（新規）共通アルゴリズム.打刻申請の新規登録
	 * @param application
	 * @param appStamp
	 * @param appRecordImage
	 * @param appStampOutput
	 * @param recoderFlag
	 * @return
	 */
	public ProcessResult registerAppStamp(Application application, AppStamp appStamp, AppRecordImage appRecordImage, AppStampOutput appStampOutput, Boolean recoderFlag);
	
}
