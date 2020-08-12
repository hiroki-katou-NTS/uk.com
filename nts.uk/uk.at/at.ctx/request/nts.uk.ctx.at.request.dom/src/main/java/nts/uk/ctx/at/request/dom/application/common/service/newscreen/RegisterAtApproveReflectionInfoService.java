package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import nts.uk.ctx.at.request.dom.application.Application;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.2-2.新規画面登録時承認反映情報の整理(register: reflection info setting)
 * @author Doan Duy Hung
 *
 */
public interface RegisterAtApproveReflectionInfoService {
	/**
	 * 2-2.新規画面登録時承認反映情報の整理
	 * @param empID
	 * @param application
	 */
	public void newScreenRegisterAtApproveInfoReflect(String empID, Application application);

}
