package nts.uk.ctx.hr.notice.app.command.report;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItem;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItemRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * アルゴリズム「削除処理」を実行する (Thực hiện thuật toán "Xử lý delete")
 * @author lanlt
 *
 */
@Stateless
public class DeleteReportClassificationHandler extends CommandHandler<String>{

	@Inject
	private PersonalReportClassificationRepository reportClsRepo;
	
	@Inject
	private RegisterPersonalReportItemRepository reportItemRepo;
	
	@Override
	protected void handle(CommandHandlerContext<String> context) {
		
		int reportClsId = Integer.valueOf(context.getCommand()).intValue();
		String cid = AppContexts.user().companyId();
		Optional<PersonalReportClassification > reportClsOpt = this.reportClsRepo.getDetailReportClsByReportClsID(cid, Integer.valueOf(reportClsId).intValue());
		//個別届出種類IDをキーとしたドメイン「個別届出種類」.「廃止」=true、およびドメイン「個別届出の登録項目」.「廃止」=trueに設定する (set 「個別届出種類」.「廃止」=true and 「個別届出の登録項目」.「廃止」=true lấy với key là 個別届出種類ID)
		if(reportClsOpt.isPresent()) {
			reportClsOpt.get().setAbolition(true);
			this.reportClsRepo.update(reportClsOpt.get());
			List<RegisterPersonalReportItem> itemLst = this.reportItemRepo.getAllItemBy(cid, reportClsId);
			if(!CollectionUtil.isEmpty(itemLst)) {
				this.reportItemRepo.updateAll(itemLst.stream().map(c -> {
					c.setIsAbolition(Optional.of(true));
					return c;
				}).collect(Collectors.toList()));
			}
		}
	}

}
