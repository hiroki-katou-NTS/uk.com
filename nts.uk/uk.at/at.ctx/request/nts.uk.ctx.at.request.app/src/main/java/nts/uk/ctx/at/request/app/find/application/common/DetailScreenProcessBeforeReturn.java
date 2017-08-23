package nts.uk.ctx.at.request.app.find.application.common;

import nts.arc.error.BusinessException;

/**
 * 11-1.詳細画面差し戻し前の処理
 * @author tutk
 *
 */
public class DetailScreenProcessBeforeReturn {

	public boolean detailBeforeReturn() {
		boolean check = true;
		
		if(!check)
		{
			throw new BusinessException("Msg_197");
			//close KDL034
			//load lại màn hình
		}		
		return check;
	
	}
}
