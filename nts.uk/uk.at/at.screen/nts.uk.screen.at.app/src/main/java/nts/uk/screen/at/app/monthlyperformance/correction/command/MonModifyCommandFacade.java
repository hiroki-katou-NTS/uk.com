package nts.uk.screen.at.app.monthlyperformance.correction.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.ContentApproval;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.ParamDayApproval;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.RegisterDayApproval;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm.ParamRegisterConfirmMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm.RegisterConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm.SelfConfirm;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.EditStateOfMonthlyPerformanceDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPItemCheckBox;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPItemDetail;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPItemParent;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class MonModifyCommandFacade {
	
	@Inject
	private MonthModifyCommandFacade monthModifyCommandFacade;

	@Inject
	private MonthlyPerformanceCorrectionUpdateCommand monthlyPerformanceCorrectionUpdateCommand;
	
	@Inject
	private RegisterConfirmationMonth registerConfirmationMonth;

	@Inject
	private RegisterDayApproval registerDayApproval;
	
	public Map<Integer, List<MPItemParent>> insertItemDomain(MPItemParent dataParent) {
		Map<String, List<MPItemDetail>> mapItemDetail = dataParent.getMPItemDetails().stream()
				.collect(Collectors.groupingBy(x -> x.getEmployeeId()));

		mapItemDetail.entrySet().forEach(item -> {
			List<MPItemDetail> rowDatas = item.getValue();
			monthModifyCommandFacade.handleUpdate(new MonthlyModifyQuery(rowDatas.stream().map(x -> {
				return ItemValue.builder().itemId(x.getItemId()).layout(x.getLayoutCode()).value(x.getValue())
						.valueType(ValueType.valueOf(x.getValueType())).withPath("");
			}).collect(Collectors.toList()), dataParent.getYearMonth(), item.getKey(), dataParent.getClosureId(),
					dataParent.getClosureDate()));
		});

		// new
		dataParent.getMPItemDetails().forEach(item -> {
			ClosureDateDto closureDate = dataParent.getClosureDate();
			EditStateOfMonthlyPerformanceDto editStateOfMonthlyPerformanceDto = new EditStateOfMonthlyPerformanceDto(
					item.getEmployeeId(), new Integer(item.getItemId()),
					new DatePeriod(dataParent.getStartDate(), dataParent.getEndDate()),
					dataParent.getYearMonth().intValue(), dataParent.getClosureId(),
					new nts.uk.screen.at.app.monthlyperformance.correction.dto.ClosureDateDto(
							closureDate.getClosureDay().intValue(),
							closureDate.getLastDayOfMonth().booleanValue() ? 1 : 0),
					new Integer(0));
			this.monthlyPerformanceCorrectionUpdateCommand.handleAddOrUpdate(editStateOfMonthlyPerformanceDto);
		});
		
		// insert sign
		this.insertSign(dataParent);

		
		List<MPItemCheckBox> listRegister = new ArrayList<>();
		List<MPItemCheckBox> listRemove = new ArrayList<>();
		for(MPItemCheckBox mpi :dataParent.getDataCheckApproval()) {
			if(mpi.isValue()) {
				listRegister.add(mpi);
			}else {
				listRemove.add(mpi);
			}
		}
		
		// insert approval
		this.insertApproval(listRegister,dataParent.getEndDate());
		// remove approval		
		this.removeMonApproval(listRemove,dataParent.getEndDate());

		return Collections.emptyMap();
	}
	
	private void insertSign(MPItemParent mPItemParent) {
		List<MPItemCheckBox> dataCheckSign = mPItemParent.getDataCheckSign();
		if(dataCheckSign.isEmpty()) return;
		List<SelfConfirm> selfConfirm = new ArrayList<>();
		ClosureDateDto closureDate = mPItemParent.getClosureDate();
		YearMonth ym = new YearMonth(mPItemParent.getYearMonth());
		dataCheckSign.stream().forEach(x -> {
			selfConfirm.add(new SelfConfirm(x.getEmployeeId(), x.isValue()));
		});
		ParamRegisterConfirmMonth param = new ParamRegisterConfirmMonth(ym, selfConfirm,
				mPItemParent.getClosureId(), closureDate.getLastDayOfMonth() ? ym.lastDateInMonth() : closureDate.getClosureDay(), GeneralDate.today());
		
		registerConfirmationMonth.registerConfirmationMonth(param);
	}

	private void insertApproval(List<MPItemCheckBox> dataCheckApprovals,GeneralDate endDate) {
		if(dataCheckApprovals.isEmpty()) return;
		Set<Pair<String, GeneralDate>> empAndDates = new HashSet<>();
		for(MPItemCheckBox dataCheckApproval : dataCheckApprovals) {
			empAndDates.add(Pair.of(dataCheckApproval.getEmployeeId(), endDate));
		}
		registerDayApproval.registerMonApproval(AppContexts.user().userId(), 
				new ArrayList<>(empAndDates), 2, AppContexts.user().companyId());
	}

	public void removeMonApproval(List<MPItemCheckBox> dataCheckApprovals,GeneralDate endDate) {
		if(dataCheckApprovals.isEmpty()) return;
		Set<Pair<String, GeneralDate>> empAndDates = new HashSet<>();
		for(MPItemCheckBox dataCheckApproval : dataCheckApprovals) {
			empAndDates.add(Pair.of(dataCheckApproval.getEmployeeId(), endDate));
		}
		registerDayApproval.removeMonApproval(AppContexts.user().userId(), 
				new ArrayList<>(empAndDates), 2, AppContexts.user().companyId());
	}
}
