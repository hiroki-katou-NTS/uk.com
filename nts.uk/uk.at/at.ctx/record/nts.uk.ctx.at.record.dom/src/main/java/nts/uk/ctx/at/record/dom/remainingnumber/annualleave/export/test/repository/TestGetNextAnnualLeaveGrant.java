package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RepositoriesRequiredByRemNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrantProc;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrantProcKdm002;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

public class TestGetNextAnnualLeaveGrant implements GetNextAnnualLeaveGrant {

	/** 年休付与テーブル設定 */
	private YearHolidayRepository yearHolidayRepo;
	
	/** 勤続年数テーブル */
	private LengthServiceRepository lengthServiceRepo;
	
	/** 年休付与テーブル */
	private GrantYearHolidayRepository grantYearHolidayRepo;
	
	/** 次回年休付与を取得する(複数社員用) */
	private GetNextAnnualLeaveGrantProcKdm002 getNextAnnualLeaveGrantProcMulti;
	
	/**
	 * コンストラクタ
	 */
	public TestGetNextAnnualLeaveGrant(){
		
		/** 年休付与テーブル設定 */
		yearHolidayRepo = TestYearHolidayRepositoryFactory.create("1");
		
		/** 勤続年数テーブル */
		lengthServiceRepo = TestLengthServiceRepositoryFactory.create("1");
		
		/** 年休付与テーブル */
		grantYearHolidayRepo = TestGrantYearHolidayRepositoryFactory.create("1");
		
		/** 次回年休付与を取得する(複数社員用) */
		//getNextAnnualLeaveGrantProcMulti = 
		
	}
	
	/** 次回年休付与を取得する */
	@Override
	public List<NextAnnualLeaveGrant> algorithm(
			Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNumOpt,
			String companyId,
			String grantTableCode,
			GeneralDate entryDate,
			GeneralDate criteriaDate,
			DatePeriod period,
			boolean isSingleDay){

		GetNextAnnualLeaveGrantProc proc = new GetNextAnnualLeaveGrantProc(
				this.yearHolidayRepo,
				this.lengthServiceRepo,
				this.grantYearHolidayRepo,
				this.getNextAnnualLeaveGrantProcMulti);
		return proc.algorithm(
				repositoriesRequiredByRemNumOpt, companyId, grantTableCode, entryDate, criteriaDate, period, isSingleDay);
	}
	
	/** 次回年休付与を取得する */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NextAnnualLeaveGrant> algorithm(
			Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNumOpt, 
			String companyId, String grantTableCode, GeneralDate entryDate,
			GeneralDate criteriaDate, DatePeriod period, boolean isSingleDay,
			Optional<GrantHdTblSet> grantHdTblSet,
			Optional<List<LengthServiceTbl>> lengthServiceTbls, Optional<GeneralDate> closureStartDate) {
		
		GetNextAnnualLeaveGrantProc proc = new GetNextAnnualLeaveGrantProc(
				this.yearHolidayRepo,
				this.lengthServiceRepo,
				this.grantYearHolidayRepo,
				this.getNextAnnualLeaveGrantProcMulti);
		return proc.algorithm(repositoriesRequiredByRemNumOpt, companyId, grantTableCode, entryDate, criteriaDate, period, isSingleDay,
				grantHdTblSet, lengthServiceTbls, closureStartDate);
	}
}
