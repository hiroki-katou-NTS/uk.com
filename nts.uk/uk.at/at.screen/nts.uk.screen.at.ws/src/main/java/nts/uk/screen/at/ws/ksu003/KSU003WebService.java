package nts.uk.screen.at.ws.ksu003;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.TargetOrgIdenInforDto;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.ksu003.changeworktype.ChangeWorkType;
import nts.uk.screen.at.app.ksu003.changeworktype.ChangeWorkTypeDto;
import nts.uk.screen.at.app.ksu003.getempworkfixedworkkinfo.EmpWorkFixedWorkInfoDto;
import nts.uk.screen.at.app.ksu003.getempworkfixedworkkinfo.GetEmpWorkFixedWorkInfoSc;
import nts.uk.screen.at.app.ksu003.sortemployee.SortEmployeeParam;
import nts.uk.screen.at.app.ksu003.sortemployee.SortEmployeesSc;
import nts.uk.screen.at.app.ksu003.start.DisplayWorkInfoByDateSc;
import nts.uk.screen.at.app.ksu003.start.GetFixedWorkInformation;
import nts.uk.screen.at.app.ksu003.start.GetInfoInitStartKsu003;
import nts.uk.screen.at.app.ksu003.start.dto.DisplayWorkInfoByDateDto;
import nts.uk.screen.at.app.ksu003.start.dto.DisplayWorkInfoParam;
import nts.uk.screen.at.app.ksu003.start.dto.FixedWorkInformationDto;
import nts.uk.screen.at.app.ksu003.start.dto.GetInfoInitStartKsu003Dto;

/**
 * 
 * @author phongtq
 *
 */

@Path("screen/at/schedule")
@Produces("application/json")
public class KSU003WebService extends WebService{
	@Inject
	private GetInfoInitStartKsu003 infoInitStartKsu003;
	
	@Inject
	private GetFixedWorkInformation fixedWorkInformation;
	
	@Inject
	private DisplayWorkInfoByDateSc displayWorkInfoByDateSc;
	
	@Inject
	private SortEmployeesSc sortEmployeesSc;
	
	@Inject
	private GetEmpWorkFixedWorkInfoSc fixedWorkInfoSc;
	
	@Inject ChangeWorkType changeWorkType;
	
	@POST
	@Path("getinfo-initstart")
	// 初期起動の情報取得
	public GetInfoInitStartKsu003Dto getDataStartScreen(TargetOrgIdenInforDto targetOrgDto){
		TargetOrgIdenInfor targetOrg = new TargetOrgIdenInfor(TargetOrganizationUnit.valueOf(targetOrgDto.getUnit()), 
				Optional.of(targetOrgDto.getWorkplaceId()), Optional.of(targetOrgDto.getWorkplaceGroupId()));
		GetInfoInitStartKsu003Dto data = infoInitStartKsu003.getData(targetOrg);
		return data;
	}
	
	@POST
	@Path("getfixedworkinfo")
	// 勤務固定情報を取得する
	public FixedWorkInformationDto getFixedWorkInformation(WorkInformationDto information){
		FixedWorkInformationDto data = fixedWorkInformation.getFixedWorkInfo(information);
		return data;
	}
	
	@POST
	@Path("displayDataKsu003")
	// 日付別勤務情報で表示する
	public List<DisplayWorkInfoByDateDto> displayDataKsu003(DisplayWorkInfoParam param){
		List<DisplayWorkInfoByDateDto> data = displayWorkInfoByDateSc.displayDataKsu003(param);
		return data;
	}
	
	@POST
	@Path("sortEmployee")
	// 社員を並び替える
	public List<String> sortEmployee(SortEmployeeParam param){
		List<String> data = sortEmployeesSc.sortEmployee(param);
		return data;
	}
	
	@POST
	@Path("getEmpWorkFixedWorkInfo")
	// 社員勤務予定と勤務固定情報を取得する
	public EmpWorkFixedWorkInfoDto getEmpWorkFixedWorkInfo(WorkInformationDto information){
		EmpWorkFixedWorkInfoDto data = fixedWorkInfoSc.getEmpWorkFixedWorkInfo(information);
		return data;
	}
	
	@POST
	@Path("changeWorkType")
	// 勤務種類を変更する
	public ChangeWorkTypeDto changeWorkType(WorkInformationDto information){
		ChangeWorkTypeDto data = changeWorkType.changeWorkType(information);
		return data;
	}
	
}
