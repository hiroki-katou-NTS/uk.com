package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatSheet;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatSheetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteAuthFormatBySheetcmdHandler extends CommandHandler<DeleteAuthFormatBySheetcmd> {

	@Inject
	private AuthorityFormatSheetRepository repo;

	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteAuthFormatBySheetcmd> context) {
		String companyID = AppContexts.user().companyId();
		DeleteAuthFormatBySheetcmd command = context.getCommand();
		if(repo.find(companyID, new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()), command.getSheetNo()).isPresent()) {
			repo.deleteBySheetNo(companyID, command.getDailyPerformanceFormatCode(), command.getSheetNo());
			//listSheet after remove
			List<AuthorityFormatSheet> listAuthFormatSheet = repo.findByCode(companyID,command.getDailyPerformanceFormatCode());
			//sort
			Collections.sort(listAuthFormatSheet, Comparator.comparing(AuthorityFormatSheet::getSheetNo));
			int size = listAuthFormatSheet.size();
			List<AuthorityFormatSheet> listAuthFormatSheetClone = listAuthFormatSheet.stream().map(c->c.clone()).collect(Collectors.toList());
			
			
			//listItem after remove
			List<AuthorityFomatDaily> listAuthFomatDaily = authorityFormatDailyRepo.getAuthorityFormatDaily(companyID,
					new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));
			
			List<AuthorityFomatDaily> listAuthFomatDailyClone = listAuthFomatDaily.stream().map(c->c.clone()).collect(Collectors.toList());
			//delete all
			for(int i = command.getSheetNo().intValue()-1;i<size;i++) {
				repo.deleteBySheetNo(companyID, command.getDailyPerformanceFormatCode(),listAuthFormatSheetClone.get(i).getSheetNo());
			}
			//insert
			for(int i = command.getSheetNo().intValue()-1;i<size;i++) {
				int sheetNoOld = listAuthFormatSheetClone.get(i).getSheetNo().intValue();
				listAuthFormatSheetClone.get(i).setSheetNo(BigDecimal.valueOf(i+1));
				repo.add(listAuthFormatSheetClone.get(i));
				List<AuthorityFomatDaily> listAuthorityFomatDailyNew = new ArrayList<>();
				for(AuthorityFomatDaily authFomatDaily :listAuthFomatDailyClone) {
					if(authFomatDaily.getSheetNo().intValue() == sheetNoOld) {
						authFomatDaily.setSheetNo(BigDecimal.valueOf(i+1));
						listAuthorityFomatDailyNew.add(authFomatDaily);
					}
				}
				authorityFormatDailyRepo.add(listAuthorityFomatDailyNew);
				
				
			}
			
		}
	}

}
