package nts.uk.ctx.bs.employee.app.command.employee.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistService;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfo;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfoRepository;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateAffCompanyHistoryListCommandHandler extends CommandHandler<List<UpdateAffCompanyHistoryCommand>>
implements PeregUpdateListCommandHandler<UpdateAffCompanyHistoryCommand>{
	@Inject
	private AffCompanyHistRepository affCompanyHistRepository;
	
	@Inject
	private AffCompanyInfoRepository affCompanyInfoRepository;
	
	@Inject
	private AffCompanyHistService affCompanyHistService;
	@Override
	public String targetCategoryCd() {
		return "CS00003";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateAffCompanyHistoryCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<List<UpdateAffCompanyHistoryCommand>> context) {
		List<UpdateAffCompanyHistoryCommand> command = context.getCommand();
		List<AffCompanyInfo> affCompanyInfoLst = new ArrayList<>();
		List<AffCompanyHistItem> affCompanyHistItems = new ArrayList<>();
		// sidsPidsMap
		List<String> sids = command.parallelStream().map(c -> c.getSId()).collect(Collectors.toList());
		// In case of date period are exist in the screen, do thiết lập ẩn hiển cho cùng
		// một công ty nên tất item của các nhân viên được hiển thị giống nhau
		// vì vậy mà mình sẽ kiểm tra startDate của nhân viên đầu tiên trong list để
		// check xem có hiển thị trên màn hình ko?
		UpdateAffCompanyHistoryCommand updateFirst = command.get(0);
		if (updateFirst.getStartDate() != null) {
			Map<String, List<AffCompanyHist>> histLstMap = this.affCompanyHistRepository
					.getAffCompanyHistoryOfEmployees(sids).parallelStream()
					.collect(Collectors.groupingBy(c -> c.getPId()));
			
			command.parallelStream().forEach(c -> {
				List<AffCompanyHist> listHist = histLstMap.get(c.getPId());
				AffCompanyHistByEmployee itemToBeAdded = null;
				if (listHist != null) {
					if (listHist.size() > 0) {
						itemToBeAdded = listHist.get(0).getAffCompanyHistByEmployee(c.getSId());
						Optional<AffCompanyHistItem> itemToBeUpdated = itemToBeAdded.getLstAffCompanyHistoryItem()
								.stream().filter(h -> h.identifier().equals(c.getHistoryId())).findFirst();
						if (!itemToBeUpdated.isPresent()) {
							throw new RuntimeException("Invalid AffCompanyHist");
						}
						itemToBeAdded.changeSpan(itemToBeUpdated.get(), new DatePeriod(c.getStartDate(),
								c.getEndDate() != null ? c.getEndDate() : ConstantUtils.maxDate()));
						affCompanyHistItems.add(itemToBeUpdated.get());

					}

				}

				AffCompanyInfo histItem = AffCompanyInfo.createFromJavaType(c.getSId(), c.getHistoryId(),
						c.getRecruitmentClassification(), c.getAdoptionDate(), c.getRetirementAllowanceCalcStartDate());
				affCompanyInfoLst.add(histItem);

			});
		}
		affCompanyHistService.updateAll(affCompanyHistItems);
		affCompanyInfoRepository.updateAll(affCompanyInfoLst);

	}

}
