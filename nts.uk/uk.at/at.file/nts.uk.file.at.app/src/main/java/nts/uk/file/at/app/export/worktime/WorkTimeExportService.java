package nts.uk.file.at.app.export.worktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.app.find.entranceexit.ManageEntryExitDto;
import nts.uk.ctx.at.shared.app.find.worktime.WorkTimeSettingInfoFinder;
import nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto.DiffTimeWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeSettingInfoDto;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.FixedWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.flexset.dto.FlexWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.flowset.dto.FlWorkSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.SimpleWorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeDisplayModeDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExit;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayModeRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingCondition;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class WorkTimeExportService extends ExportService<WorkTimExportDto> {

	private static final String COMPANY_ERROR = "Company is not found!!!!";
	
	@Inject
    private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
    private WorkTimeReportGenerator reportGenerator;

	@Inject
	private CompanyAdapter company;
	
	@Inject
    private WorkTimeDisplayModeRepository workTimeDisplayModeRepository;
	
	@Inject
    private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
	
	@Inject
    private ManageEntryExitRepository manageEntryExitRepository;
	
	@Inject
    private FixedWorkSettingRepository fixedWorkSettingRepository;
	
	@Inject
    private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;
	
	@Inject
    private FlowWorkSettingRepository flowWorkSettingRepository;
	
	@Inject
    private FlexWorkSettingRepository flexWorkSettingRepository;

	@Override
	protected void handle(ExportServiceContext<WorkTimExportDto> context) {
		LoginUserContext user = AppContexts.user();
		WorkTimExportDto workTimExportDto = context.getQuery();
		String programName = workTimExportDto.getProgramName();
		String cid = user.companyId();
		String companyName = user.companyCode() + " "
				+ company.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR)).getCompanyName();
		
		List<WorkTimeSettingInfoDto> normal = new ArrayList<>();
		List<WorkTimeSettingInfoDto> flow = new ArrayList<>();
		List<WorkTimeSettingInfoDto> flex = new ArrayList<>();
		
		// AnhNM: KMK003 MasterList: START
		WorkTimeSettingCondition condition = new WorkTimeSettingCondition(null, null, true);
		List<WorkTimeSetting> lstWorktimeSetting = workTimeSettingRepository.findWithCondition(cid, condition);
		List<SimpleWorkTimeSettingDto>lstSimpleWorktimeSetting = lstWorktimeSetting.stream().map(item -> {
            return SimpleWorkTimeSettingDto.builder().isAbolish(item.getAbolishAtr() == AbolishAtr.ABOLISH)
                    .worktimeCode(item.getWorktimeCode().v())
                    .workTimeName(item.getWorkTimeDisplayName().getWorkTimeName().v()).build();
        }).collect(Collectors.toList());
		
		this.bindDataSource(lstSimpleWorktimeSetting, normal, flow, flex);
		
		System.out.println(normal);
		// AnhNM: KMK003 MasterList: END

		val dataSource = new WorkTimeReportDatasource(programName, companyName, GeneralDateTime.now(), normal, flow, flex);
		reportGenerator.generate(context.getGeneratorContext(), dataSource);
	}

    private void bindDataSource(List<SimpleWorkTimeSettingDto>lstSimpleWorktimeSetting, List<WorkTimeSettingInfoDto> normal, List<WorkTimeSettingInfoDto> flow, List<WorkTimeSettingInfoDto> flex) {
        String companyId = AppContexts.user().companyId();
        
        for (SimpleWorkTimeSettingDto simpleWorkTime : lstSimpleWorktimeSetting) {
            String workTimeCode = simpleWorkTime.worktimeCode;
            
            WorkTimeSettingDto workTimeSettingDto = new WorkTimeSettingDto();
            WorkTimeDisplayModeDto displayModeDto = new WorkTimeDisplayModeDto();
            PredetemineTimeSettingDto predetemineTimeSettingDto = new PredetemineTimeSettingDto();
            FixedWorkSettingDto fixedWorkSettingDto = new FixedWorkSettingDto();
            DiffTimeWorkSettingDto diffTimeWorkSettingDto = new DiffTimeWorkSettingDto();
            FlWorkSettingDto flowWorkSettingDto = new FlWorkSettingDto();
            FlexWorkSettingDto flexWorkSettingDto = new FlexWorkSettingDto();
            ManageEntryExitDto manageEntryExitDto = new ManageEntryExitDto();
            
            Optional<WorkTimeSetting> workTimeSettingOp = workTimeSettingRepository.findByCode(companyId, workTimeCode);
            if (workTimeSettingOp.isPresent()) {
                WorkTimeSetting workTimeSetting = workTimeSettingOp.get();
                WorkTimeDisplayMode displayMode = this.workTimeDisplayModeRepository.findByKey(companyId, workTimeCode)
                        .orElse(null);
                // find predetemineTimeSettingRepository
                PredetemineTimeSetting predetemineTimeSetting = this.predetemineTimeSettingRepository
                        .findByWorkTimeCode(companyId, workTimeCode).get();
                ManageEntryExit manageEntryExit = this.manageEntryExitRepository.findByID(companyId).get();
                
                workTimeSetting.saveToMemento(workTimeSettingDto);
                if (displayMode != null) {
                    displayMode.saveToMemento(displayModeDto);
                }
                predetemineTimeSetting.saveToMemento(predetemineTimeSettingDto);
                manageEntryExit.saveToMemento(manageEntryExitDto);
                
                WorkTimeDivision workTimeDivision = workTimeSetting.getWorkTimeDivision();
                // check mode of worktime
                if (workTimeDivision.getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK) {
                    // workTimeSettingDto
                    // check WorkTimeMethodSet
                    
                    switch (workTimeDivision.getWorkTimeMethodSet()) {
                    case FIXED_WORK:
                        FixedWorkSetting fixedWorkSetting = this.fixedWorkSettingRepository
                        .findByKey(companyId, workTimeCode).get();
                        fixedWorkSetting.saveToMemento(fixedWorkSettingDto);
                        
                        normal.add(new WorkTimeSettingInfoDto(predetemineTimeSettingDto, workTimeSettingDto, displayModeDto,
                                flexWorkSettingDto, fixedWorkSettingDto, flowWorkSettingDto, diffTimeWorkSettingDto,
                                manageEntryExitDto, true));
                        break;
                    case DIFFTIME_WORK:
                        DiffTimeWorkSetting diffTimeWorkSetting = this.diffTimeWorkSettingRepository
                        .find(companyId, workTimeCode).get();
                        diffTimeWorkSetting.saveToMemento(diffTimeWorkSettingDto);
                        break;
                    case FLOW_WORK:
                        FlowWorkSetting flowWorkSetting = this.flowWorkSettingRepository.find(companyId, workTimeCode)
                        .get();
                        flowWorkSetting.saveToMemento(flowWorkSettingDto);
                        
                        flow.add(new WorkTimeSettingInfoDto(predetemineTimeSettingDto, workTimeSettingDto, displayModeDto,
                                flexWorkSettingDto, fixedWorkSettingDto, flowWorkSettingDto, diffTimeWorkSettingDto,
                                manageEntryExitDto, true));
                        break;
                    default:
                        break;
                    }
                } else// case FLEX_WORK
                {
                    FlexWorkSetting flexWorkSetting = this.flexWorkSettingRepository.find(companyId, workTimeCode).get();
                    // sort element by time
                    flexWorkSetting.getOffdayWorkTime().sortWorkTimeOfOffDay();
                    
                    flexWorkSetting.saveToMemento(flexWorkSettingDto);
                    
                    flex.add(new WorkTimeSettingInfoDto(predetemineTimeSettingDto, workTimeSettingDto, displayModeDto,
                            flexWorkSettingDto, fixedWorkSettingDto, flowWorkSettingDto, diffTimeWorkSettingDto,
                            manageEntryExitDto, true));
                }
            }
        }
    }

}
