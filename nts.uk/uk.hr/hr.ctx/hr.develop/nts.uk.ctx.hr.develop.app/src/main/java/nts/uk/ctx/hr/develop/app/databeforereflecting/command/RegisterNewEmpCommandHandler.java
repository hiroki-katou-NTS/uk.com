package nts.uk.ctx.hr.develop.app.databeforereflecting.command;

import java.time.LocalDate;
import java.time.Period;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;

@Stateless
public class RegisterNewEmpCommandHandler extends CommandHandler<DataBeforeReflectCommand> {

	@Override
	protected void handle(CommandHandlerContext<DataBeforeReflectCommand> context) {
		DataBeforeReflectCommand command = context.getCommand();

		// アルゴリズム[事前チェック]を実行する (THực hiện thuật toán [Check trước ] )
		preCheck(command);
		
		// truong hop bị sa thải
		if (command.retirementType == 3) {
			// アルゴリズム[警告チェック]を実行する(Thực hiện thuật toán [Warning check] )
			warningCheck(command);
		}	
		

	}

	private void warningCheck(DataBeforeReflectCommand command) {
		// [退職日] と [解雇予告日] 間の日数取得 (Get số ngày giứa [Ngay nghi huu] と [Ngày thông báo sa thải]  )
		GeneralDate retirementDate = GeneralDate.legacyDate(command.retirementDate.date()); // A222_12
		GeneralDate dismissalNoticeDate = GeneralDate.legacyDate(command.dismissalNoticeDate.date()); //A222_35
		
		LocalDate retirementDateLocalDate = LocalDate.of(retirementDate.year(), retirementDate.month(), retirementDate.day()); // A222_12
		LocalDate dismissalNoticeDateLocalDate = LocalDate.of(dismissalNoticeDate.year(), dismissalNoticeDate.month(), dismissalNoticeDate.day());//A222_35
		
		Period period = Period.between(dismissalNoticeDateLocalDate, retirementDateLocalDate);
	    int dayDifference = period.getDays();
	    
	    GeneralDate dismissalNoticeDateAllow = command.dismissalNoticeDateAllow; // A222_37
	    if (dismissalNoticeDateAllow == null) {
			if (dayDifference  < 30) {
				
			}
		}else{
			
			
		}
		
		
	}

	private boolean preCheck(DataBeforeReflectCommand command) {

		// [A222_12 退職日] と[A222_14 公開日]をチェックする ( Check [A222_14 Release date] và
		// [A222_12 Retirement date]
		
		GeneralDate retirementDate = GeneralDate.legacyDate(command.retirementDate.date()); // A222_12
		GeneralDate releaseDate = GeneralDate.legacyDate(command.releaseDate.date()); // A222_14
		GeneralDate dismissalNoticeDate = GeneralDate.legacyDate(command.dismissalNoticeDate.date()); //A222_35
		
		BundledBusinessException bundleExeption = BundledBusinessException.newInstance();
		int index = 0;
		
		if (retirementDate.beforeOrEquals(releaseDate)) {
			index++;
			bundleExeption.addMessage(new BusinessException("MsgJ_JCM007_2"));
		}
		/**
		 *  { value: 1, text: '退職' },
            { value: 2, text: '転籍' },
            { value: 3, text: '解雇' },
            { value: 4, text: '定年' }
		 */
		int retirementType = command.retirementType;
		if (retirementType == 3) {
			if (retirementDate.beforeOrEquals(dismissalNoticeDate)) {
				index++;
				bundleExeption.addMessage(new BusinessException("MsgJ_JCM007_3"));
			}
		}
		
		if (index > 0) {
			throw bundleExeption;
		}
		
		return true;
	}
}
