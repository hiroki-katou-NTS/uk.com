package command.person.setting.init.category;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
@Stateless
public class CopyInitValueSetCtgCommandHandler extends CommandHandler<CopyInitValueSetCtgCommand> {

	@Override
	protected void handle(CommandHandlerContext<CopyInitValueSetCtgCommand> context) {
		CopyInitValueSetCtgCommand data = context.getCommand();
		boolean checkCopy = data.isCopy();
		String codeNew = data.getCodeNew();
		String codeOld = data.getId();
		//画面項目「上書き」のチェック状態を確認する (Xem 「上書き」 trên màn hình có đươc check không)
		if(!checkCopy){
			//エラーメッセージ（#Msg_3）を表示する (Hiển thị Error Message Msg_3)
			throw new BusinessException("Msg_3");
		}
		//入力さたコードが複製元のコードと同じかチェックする (Kiểm tra Code nhập vào có giống Code của copy nguồn không)
		if(codeOld.equals(codeNew)){
			//エラーメッセージ（#Msg_355）を表示する (Hiển thị Error Message Msg_355)
			throw new BusinessException("Msg_355");
		}
	}

}
