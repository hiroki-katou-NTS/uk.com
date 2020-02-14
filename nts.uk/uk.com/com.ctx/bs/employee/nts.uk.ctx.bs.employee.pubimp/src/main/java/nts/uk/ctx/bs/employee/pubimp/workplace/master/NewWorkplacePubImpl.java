package nts.uk.ctx.bs.employee.pubimp.workplace.master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfiguration;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfigurationRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplaceInforExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class NewWorkplacePubImpl implements WorkplacePub {

	@Inject
	private WorkplaceExportService wkpExpService;

    @Inject
    private AffWorkplaceHistoryRepository affWkpHistRepo;

    @Inject
    private AffWorkplaceHistoryItemRepository affWkpHistItemRepo;
    
    @Inject
    private WorkplaceConfigurationRepository workplaceConfigurationRepository;
    
    @Inject
    private WorkplaceInformationRepository workplaceInformationRepository;

	@Override
	public List<WorkplaceInforExport> getWorkplaceInforByWkpIds(String companyId, List<String> listWorkplaceId,
			GeneralDate baseDate) {
		return wkpExpService.getWorkplaceInforFromWkpIds(companyId, listWorkplaceId, baseDate).stream()
				.map(i -> new WorkplaceInforExport(i.getWorkplaceId(), i.getHierarchyCode(), i.getWorkplaceCode(),
						i.getWorkplaceName(), i.getDisplayName(), i.getGenericName(), i.getExternalCode()))
				.collect(Collectors.toList());
	}

	@Override
	public List<WorkplaceInforExport> getAllActiveWorkplaceInfor(String companyId, GeneralDate baseDate) {
		return wkpExpService.getAllActiveWorkplace(companyId, baseDate).stream()
				.map(i -> new WorkplaceInforExport(i.getWorkplaceId(), i.getHierarchyCode().v(),
						i.getWorkplaceCode().v(), i.getWorkplaceName().v(), i.getWorkplaceDisplayName().v(),
						i.getWorkplaceGeneric().v(),
						i.getWorkplaceExternalCode().isPresent() ? i.getWorkplaceExternalCode().get().v() : null))
				.collect(Collectors.toList());
	}

	@Override
	public List<WorkplaceInforExport> getPastWorkplaceInfor(String companyId, String historyId,
			List<String> listWorkplaceId) {
		return wkpExpService.getPastWorkplaceInfor(companyId, historyId, listWorkplaceId).stream()
				.map(i -> new WorkplaceInforExport(i.getWorkplaceId(), i.getHierarchyCode(), i.getWorkplaceCode(),
						i.getWorkplaceName(), i.getDisplayName(), i.getGenericName(), i.getExternalCode()))
				.collect(Collectors.toList());
	}

    @Override
    public List<String> getAllChildrenOfWorkplaceId(String companyId, GeneralDate baseDate, String parentWorkplaceId) {
        return wkpExpService.getAllChildWorkplaceId(companyId, baseDate, parentWorkplaceId);
    }

    @Override
    public List<String> getWorkplaceIdAndChildren(String companyId, GeneralDate baseDate, String workplaceId) {
        return wkpExpService.getWorkplaceIdAndChildren(companyId, baseDate, workplaceId);
    }

    @Override
    public Optional<SWkpHistExport> findBySid(String employeeId, GeneralDate baseDate) {
        // get AffWorkplaceHistory
        Optional<AffWorkplaceHistory> affWrkPlc = affWkpHistRepo.getByEmpIdAndStandDate(employeeId,
                baseDate);
        if (!affWrkPlc.isPresent())
            return Optional.empty();

        // get AffWorkplaceHistoryItem
        String historyId = affWrkPlc.get().getHistoryItems().get(0).identifier();
        Optional<AffWorkplaceHistoryItem> affWrkPlcItem = affWkpHistItemRepo.getByHistId(historyId);
        if (!affWrkPlcItem.isPresent())
            return Optional.empty();

        // Get workplace info.
        String companyId = AppContexts.user().companyId();
        String workplaceId = affWrkPlcItem.get().getWorkplaceId();
        List<WorkplaceInforParam> lstWkpInfo = wkpExpService.getWorkplaceInforFromWkpIds(companyId, Arrays.asList(workplaceId), baseDate);

        // Check exist
        if (lstWkpInfo.isEmpty()) {
            return Optional.empty();
        }

        // Return workplace id
        WorkplaceInforParam param = lstWkpInfo.get(0);

        return Optional.of(SWkpHistExport.builder().dateRange(affWrkPlc.get().getHistoryItems().get(0).span())
                .employeeId(affWrkPlc.get().getEmployeeId()).workplaceId(param.getWorkplaceId())
                .workplaceCode(param.getWorkplaceCode()).workplaceName(param.getWorkplaceName())
                .wkpDisplayName(param.getDisplayName())
                .build());
    }

	@Override
	public AffWorkplaceHistoryItemExport getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date) {
		List<AffWorkplaceHistoryItem> itemLst = affWkpHistItemRepo.getAffWrkplaHistItemByEmpIdAndDate(date, employeeID);
		if(CollectionUtil.isEmpty(itemLst)) {
			return null;
		} else {
			return new AffWorkplaceHistoryItemExport(
					itemLst.get(0).getHistoryId(), 
					itemLst.get(0).getWorkplaceId(), 
					itemLst.get(0).getNormalWorkplaceId());
		}
	}

	@Override
	public List<String> getUpperWorkplace(String companyID, String workplaceID, GeneralDate date) {
		// ドメインモデル「職場構成」を取得する(lấy domain 「WorkplaceConfig」)
		Optional<WorkplaceConfiguration> opWorkplaceConfig = workplaceConfigurationRepository.findByDate(companyID, date);
		if(!opWorkplaceConfig.isPresent()) {
			throw new RuntimeException("error workplace config");
		}
		// ドメインモデル「職場情報」を取得する
		List<WorkplaceInformation> workplaceInforLst = workplaceInformationRepository.getAllActiveWorkplaceByCompany(
				companyID, 
				opWorkplaceConfig.get().items().get(0).identifier());
		// 取得した「職場情報」から基準となる職場の階層コードを求める
		WorkplaceInformation workplaceInfor = workplaceInforLst.stream().filter(x -> x.getWorkplaceId().equals(workplaceID)).findAny().get();
		// 求めた基準となる職場の階層コードから上位階層の職場を求める
		List<String> hierachyCDLst = new ArrayList<>();
		String sumCD = workplaceInfor.getHierarchyCode().toString();
		Integer index = 3;
		while(sumCD.length() - 3 >= index) {
			hierachyCDLst.add(sumCD.substring(0, index));
			index+=3;
		}
		Collections.reverse(hierachyCDLst);
		// 求めた上位階層の職場のIDをOutputする
		List<String> upperWkpIDLst = new ArrayList<>();
		for(String hierachyCD : hierachyCDLst) {
			upperWkpIDLst.add(workplaceInforLst.stream().filter(x -> x.getHierarchyCode().v().equals(hierachyCD)).findAny().get().getWorkplaceId());
		}
		return upperWkpIDLst;
	}

	@Override
	public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId) {
		List<String> lstResult = new ArrayList<>();
		lstResult.add(workplaceId);
		lstResult.addAll(this.getUpperWorkplace(companyId, workplaceId, baseDate));
		return lstResult;
	}

}
