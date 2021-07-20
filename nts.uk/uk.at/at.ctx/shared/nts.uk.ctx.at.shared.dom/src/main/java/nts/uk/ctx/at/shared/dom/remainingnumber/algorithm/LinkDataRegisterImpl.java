package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg.LinkDataRegister;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;

@Stateless
public class LinkDataRegisterImpl {

	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepo;

	@Inject
	private InterimBreakDayOffMngRepository interimBreakDayOffMngRepo;

	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepo;

	@Inject
	private LeaveManaDataRepository leaveManaDataRepo;

	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepo;

	@Inject
	private InterimRecAbasMngRepository interimRecAbasMngRepo;

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo;

	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepo;

	public LinkDataRegisterRequireImpl createImpl() {
		return new LinkDataRegisterRequireImpl();
	}

	public class LinkDataRegisterRequireImpl implements LinkDataRegister.Require {
		@Override
		public void deleteDayoffLinkWithPeriod(String sid, DatePeriod period) {
			leaveComDayOffManaRepo.deleteWithPeriod(sid, period);
		}

		@Override
		public void insertDayOffLinkList(List<LeaveComDayOffManagement> lstDomain) {
			leaveComDayOffManaRepo.insertList(lstDomain);
		}

		@Override
		public void deleteBreakOffMngWithPeriod(String sid, DatePeriod period) {
			interimBreakDayOffMngRepo.deleteBreakoffWithPeriod(sid, period);

		}

		@Override
		public void insertBreakoffMngList(List<InterimBreakMng> lstDomain) {
			interimBreakDayOffMngRepo.insertBreakoffList(lstDomain);
		}

		@Override
		public void deleteDayoffWithPeriod(String sid, DatePeriod period) {
			interimBreakDayOffMngRepo.deleteDayoffWithPeriod(sid, period);
		}

		@Override
		public void insertDayoffList(List<InterimDayOffMng> lstDomain) {
			interimBreakDayOffMngRepo.insertDayoffList(lstDomain);
		}

		@Override
		public List<LeaveComDayOffManagement> getDigestOccByListComId(String sid, DatePeriod period) {
			return leaveComDayOffManaRepo.getDigestOccByListComId(sid, period);
		}

		@Override
		public List<InterimDayOffMng> getTempDayOffBySidPeriod(String sid, DatePeriod period) {
			return interimBreakDayOffMngRepo.getDayOffBySidPeriod(sid, period);
		}

		@Override
		public List<CompensatoryDayOffManaData> getFixByDayOffDatePeriod(String sid, DatePeriod dateData) {
			return comDayOffManaDataRepo.getByDayOffDatePeriod(sid, dateData);
		}

		@Override
		public List<InterimBreakMng> getTempBreakBySidPeriod(String sid, DatePeriod period) {
			return interimBreakDayOffMngRepo.getBySidPeriod(sid, period);
		}

		@Override
		public List<LeaveManagementData> getFixLeavByDayOffDatePeriod(String sid, DatePeriod dateData) {
			return leaveManaDataRepo.getByDayOffDatePeriod(sid, dateData);
		}

		@Override
		public void deletePayoutWithPeriod(String sid, DatePeriod period) {
			payoutSubofHDManaRepo.deletePayoutWithPeriod(sid, period);
		}

		@Override
		public void insertPayoutList(List<PayoutSubofHDManagement> lstDomain) {
			payoutSubofHDManaRepo.insertPayoutList(lstDomain);
		}

		@Override
		public void deleteRecMngWithPeriod(String sid, DatePeriod period) {
			interimRecAbasMngRepo.deleteRecMngWithPeriod(sid, period);
		}

		@Override
		public void insertRecMngList(List<InterimRecMng> lstDomain) {
			interimRecAbasMngRepo.insertRecMngList(lstDomain);
		}

		@Override
		public void deleteAbsMngWithPeriod(String sid, DatePeriod period) {
			interimRecAbasMngRepo.deleteAbsMngWithPeriod(sid, period);
		}

		@Override
		public void insertAbsMngList(List<InterimAbsMng> lstDomain) {
			interimRecAbasMngRepo.insertAbsMngList(lstDomain);
		}

		@Override
		public List<PayoutSubofHDManagement> getOccDigetByListSid(String sid, DatePeriod date) {
			return payoutSubofHDManaRepo.getOccDigetByListSid(sid, date);
		}

		@Override
		public List<InterimAbsMng> getAbsBySidDatePeriod(String sid, DatePeriod period) {
			return interimRecAbasMngRepo.getAbsBySidDatePeriod(sid, period);
		}

		@Override
		public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String sid, DatePeriod period) {
			return substitutionOfHDManaDataRepo.getBySidAndDatePeriod(sid, period);
		}

		@Override
		public List<InterimRecMng> getRecBySidDatePeriod(String sid, DatePeriod period) {
			return interimRecAbasMngRepo.getRecBySidDatePeriod(sid, period);
		}

		@Override
		public List<PayoutManagementData> getPayoutMana(String sid, DatePeriod dateData) {
			return payoutManagementDataRepo.getBySidAndDatePeriod(sid, dateData);
		}

	}
}
