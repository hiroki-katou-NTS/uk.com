package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.algorithm.service.MatchingElapsedYearsTblWithNumDaysGrantedTblService;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 *
 *	UKDesign.UniversalK.就業.KDW_日別実績.KMF_休暇マスタ.KMF004_特別休暇情報の登録.D：付与テーブルの設定.アルゴリズム.付与テーブル登録時○.付与テーブル登録時
 * @author tanlv
 *
 */
@Stateless
public class AddGrantDateTblCommandHandler extends CommandHandlerWithResult<GrantDateTblCommand, List<String>> {
	@Inject
	private GrantDateTblRepository grantDateTblRepository;
	
	@Inject
	private ElapseYearRepository elapseYearRepository;

	@Override
	protected List<String> handle(CommandHandlerContext<GrantDateTblCommand> context) {
		List<String> errList = new ArrayList<String>();
		GrantDateTblCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Require require = new Require(grantDateTblRepository);
		
		// check exists code
		Optional<GrantDateTbl> grantDateTbl = grantDateTblRepository.findByCode(companyId, command.getSpecialHolidayCode(), command.getGrantDateCode());
		if (grantDateTbl.isPresent()) {
			addMessage(errList, "Msg_3");
		}

		GrantDateTbl domainGrantDateTbl = GrantDateTblCommand.toGrantDateTblDomain(command, companyId);
		ElapseYear domainElapseYear     = GrantDateTblCommand.toElapseYearDomain(command, companyId);

		// check error before register
		errList.addAll(domainElapseYear.validateInput()); 

		List<GrantDateTbl> listGrantDateTbl = MatchingElapsedYearsTblWithNumDaysGrantedTblService.consistentGrantDaysTbl(require, domainElapseYear, domainGrantDateTbl);
		
		if (errList.isEmpty()) {
			// 画面項目「D3_8 規定」をチェック
			if (domainGrantDateTbl.isSpecified()) {
				elapseYearRepository.update(domainElapseYear);
				listGrantDateTbl.forEach(e -> grantDateTblRepository.update(e, domainGrantDateTbl));
			} else {
				listGrantDateTbl.forEach(e -> {
					if (e.getCompanyId().equals(domainGrantDateTbl.getCompanyId()) 
							&& e.getSpecialHolidayCode().v() == domainGrantDateTbl.getSpecialHolidayCode().v()
							&& e.getGrantDateCode().v().equals(domainGrantDateTbl.getGrantDateCode().v())) {
						grantDateTblRepository.update(e, domainGrantDateTbl);
					}
				});
			}
		}

		return errList;
	}
	
	@AllArgsConstructor
	public static class Require implements MatchingElapsedYearsTblWithNumDaysGrantedTblService.Require {

		private GrantDateTblRepository repository;
		
		@Override
		public List<GrantDateTbl> findBySphdCd(String companyId, int specialHolidayCode) {
			// TODO Auto-generated method stub
			return repository.findBySphdCd(companyId, specialHolidayCode);
		}
		
	}

	/**
	 * Add exception message
	 *
	 * @param exceptions
	 * @param messageId
	 */
	private void addMessage(List<String> errorsList, String messageId) {
		if (!errorsList.contains(messageId)) {
			errorsList.add(messageId);
		}
	}
}
