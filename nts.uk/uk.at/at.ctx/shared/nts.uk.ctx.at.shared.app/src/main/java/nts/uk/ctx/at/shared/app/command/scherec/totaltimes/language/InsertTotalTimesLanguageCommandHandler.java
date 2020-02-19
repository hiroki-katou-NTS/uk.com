package nts.uk.ctx.at.shared.app.command.scherec.totaltimes.language;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLang;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLangRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class InsertTotalTimesLanguageCommandHandler extends CommandHandler<InsertTotalTimesLanguageCommand> {

	@Inject TotalTimesLangRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<InsertTotalTimesLanguageCommand> context) {
		String companyId = AppContexts.user().companyId();
		InsertTotalTimesLanguageCommand command = context.getCommand();
		
		TotalTimesLang timesLang = command.toDomain(command.getTotalCountNo(), command.getLangId(), command.getTotalTimesNameEn());
		// ドメインモデル「回数集計の他言語表示名」を取得する(Lấy dữ liệu từ domain 「回数集計の他言語表示名」)
		Optional<TotalTimesLang> optional = this.repo.findById(companyId, command.getTotalCountNo(), command.getLangId());
		
		if(optional.isPresent())
			//ドメインモデル「回数集計の他言語表示名」更新する (Update Domain "OtherLanguageCountDisplay")
			this.repo.update(timesLang);
		else
			//ドメインモデル「回数集計の他言語表示名」を登録する (Đăng ký Domain "OtherLanguageCountDisplay")
			this.repo.add(timesLang);
	}
}
