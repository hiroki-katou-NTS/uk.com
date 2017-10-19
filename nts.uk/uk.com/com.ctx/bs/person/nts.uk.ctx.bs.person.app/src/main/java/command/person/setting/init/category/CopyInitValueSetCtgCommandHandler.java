package command.person.setting.init.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSettingRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValueSettingCtg;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.bs.person.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class CopyInitValueSetCtgCommandHandler extends CommandHandler<CopyInitValueSetCtgCommand> {
	
	@Inject
	private PerInfoInitValueSettingRepository repoPerSet;
	@Inject
	private PerInfoInitValSetCtgRepository repoPerSetCtg;
	@Inject
	private PerInfoInitValueSetItemRepository repoPerSetItem;
	@Override
	protected void handle(CommandHandlerContext<CopyInitValueSetCtgCommand> context) {
		String companyId = AppContexts.user().companyId();
		CopyInitValueSetCtgCommand data = context.getCommand();
		boolean checkOverWrite = data.isOverWrite();
		String codeInput = data.getCodeInput();
		String valueSetIdSource = data.getIdSource();
		//複製元のドメインモデル「個人情報初期値設定」を取得する(Lấy Domain Model 「個人情報初期値設定」 của copy nguồn)
		Optional<PerInfoInitValueSetting> perSet = repoPerSet.getDetailInitValSetting(valueSetIdSource);
		//複製先のドメインモデル「個人情報初期値設定」を取得する (Lấy Domain Model ドメインモデル「個人情報初期値設定」 của copy đích)
		Optional<PerInfoInitValueSetting> perSetInput = repoPerSet.getDetailInitValSetting(companyId, codeInput);
		//複製先のドメインモデル「個人情報初期値設定」が存在するかチェックする (Kiểm tra Domain Model 「個人情報初期値設定」 của copy đích tồn tại hay không)
		if(perSetInput.isPresent()){
			//存在する場合(Tồn tại)
			//画面項目「上書き」のチェック状態を確認する (Xem 「上書き - Overwrite」 trên màn hình có đươc check không)
			if(!checkOverWrite){
				//エラーメッセージ（#Msg_3）を表示する (Hiển thị Error Message Msg_3)
				throw new BusinessException("Msg_3");
			}
			//入力さたコードが複製元のコードと同じかチェックする (Kiểm tra Code nhập vào có giống Code của copy nguồn không)
			if(codeInput.equals(perSet.get().getSettingCode())){
				//エラーメッセージ（#Msg_355）を表示する (Hiển thị Error Message Msg_355)
				throw new BusinessException("Msg_355");
			}
			//ドメインモデル「個人情報初期値設定」を削除する(Xóa Domain Model 「個人情報初期値設定」)
			String settingId = perSetInput.get().getInitValueSettingId();
			//delete item
			repoPerSetItem.deleteAllBySetId(settingId);
			//delete category
			repoPerSetCtg.delete(settingId);
			//delete per set
			repoPerSet.delete(companyId, settingId, codeInput);
		}
		//存在しない場合(Không tồn tại)
		//複製元の情報をもとに、ドメインモデル「個人情報初期値設定」を登録する (Đăng ký Domain Model 「個人情報初期値設定」 dựa vào Thông tin Copy nguồn)
		String initValueSettingId = IdentifierUtil.randomUniqueId();
		PerInfoInitValueSetting perSetInsert = PerInfoInitValueSetting.createFromJavaType(initValueSettingId, companyId, codeInput, data.getNameInput());
		//get all category by initValueSettingId
		List<PerInfoInitValueSettingCtg> lstCtgCopy = repoPerSetCtg.getAllCategoryBySetId(valueSetIdSource);
		//get all item by initValueSettingId
		List<PerInfoInitValueSetItem> lstItemCopy = new ArrayList<>();
		for (PerInfoInitValueSettingCtg perSetCtg : lstCtgCopy) {
			List<PerInfoInitValueSetItem> lstItem = repoPerSetItem.getAllInitValueItem(perSetCtg.getPerInfoCtgId(), valueSetIdSource);
			if(!lstItem.isEmpty()){
				lstItemCopy.addAll(lstItem);
			}
		}
		//copy category -> per set new
		if(!lstCtgCopy.isEmpty()){
			
		}
		//copy item -> per set new
		
		//insert per set new
		repoPerSet.insert(perSetInsert);
	}

}
