package nts.uk.ctx.sys.shared.dom.toppagealarmset;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.persistence.jpa.jpql.parser.ConcatExpression;

import nts.uk.shr.sample.domevent.SampleDomainEvent;

public class ToppageAlarmSetDESubscriber {
	@Inject
	private TopPageAlarmSetRepository toppageAlarmSetRepo;

	private static final String copyCompanyID = "1a";
	private static final String companyID = "000000000000-0000";

	protected void handle(SampleDomainEvent domainEvent) {
		// constant String copyCompanyID = '1a';

		boolean statusInit ;
		if (statusInit = true) {
			 copyInOverwriteMode(copyCompanyID);
		} else {
			copyAndInit(copyCompanyID);
		}
	}

	private void copyInOverwriteMode(String copyCompanyID) {
		
		// ドメインモデル「トップページアラーム設定」を削除する
		// Delete the domain model "top page alarm setting"
	//	toppageAlarmSetRepo.delete(copyCompanyID);
		// ドメインモデル「トップページアラーム設定」を0会社からコピーして新規登録する
		// (copy domain model từ 0 company, đăng ký tạo mới)
		if (companyID == copyCompanyID) {
			List<TopPageAlarmSet> listToppageAlarmSet = toppageAlarmSetRepo.getAll(companyID);
			for (TopPageAlarmSet topPageAlarmSet : listToppageAlarmSet) {
	//			toppageAlarmSetRepo.create(topPageAlarmSet);
			}
		}
	}

	private void copyAndInit(String copyCompanyID) {
		// ドメインモデル「トップページアラーム設定」を取得する(get domain model 「トップページアラーム設定」)
		// Acquire the domain model "top page alarm setting" (get domain model
		// "top page alarm setting")
		List<TopPageAlarmSet> listToppageAlarmSet = toppageAlarmSetRepo.getAll(copyCompanyID);
		if (listToppageAlarmSet.isEmpty()) {
			List<TopPageAlarmSet> listToppageAlarm = toppageAlarmSetRepo.getAll(companyID);
			for (TopPageAlarmSet topPageAlarmSet : listToppageAlarm) {
	//			toppageAlarmSetRepo.create(topPageAlarmSet);
			}
		}

	}
}
