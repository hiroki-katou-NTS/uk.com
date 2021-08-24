package nts.uk.ctx.sys.auth.app.find.grant.roleindividual;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDto;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDtoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.bs.employee.pub.jobtitle.AffJobTitleBasicExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.EmployeeJobHistExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.sys.auth.app.find.company.CompanyDto;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto.*;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonImport;
import nts.uk.ctx.sys.auth.dom.adapter.role.employment.*;
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireUserIDFromEmpIDService;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserName;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class RoleIndividualFinder {

    @Inject
    private RoleIndividualGrantRepository roleIndividualGrantRepo;

    @Inject
    private UserRepository userRepo;

    @Inject
    private PersonAdapter personAdapter;

    @Inject
    private CompanyAdapter companyAdapter;

    @Inject
    private RoleAdapter roleAdapter;

    @Inject
    private WorkplacePub workplacePub;

    @Inject
    private SyJobTitlePub syJobTitlePub;

    @Inject
    private EmployeeInfoPub employeeInfoPub;

    @Inject
    private AcquireUserIDFromEmpIDService userIDFromEmpIDService;


    private static final String COMPANY_ID_SYSADMIN = "000000000000-0000";

    public RoleIndividualDto findByCompanyAndRoleType(String companyID, int roleType) {
        String userName = "";
        // Get list RoleIndividualGrant
        if (roleType != RoleType.COMPANY_MANAGER.value)
            companyID = COMPANY_ID_SYSADMIN;

        List<RoleIndividualGrant> listRoleIndividualGrant = roleIndividualGrantRepo.findByCompanyIdAndRoleType(companyID, roleType);
        if (listRoleIndividualGrant.isEmpty()) {
            return new RoleIndividualDto(COMPANY_ID_SYSADMIN, new ArrayList<RoleIndividualGrantDto>());
        }

        // Get list User information
        List<String> listUserID = listRoleIndividualGrant.stream().map(c -> c.getUserId()).distinct().collect(Collectors.toList());

        List<User> listUser = userRepo.getByListUser(listUserID);

        List<String> listAssPersonID = listUser.stream().map(c -> c.getAssociatedPersonID().isPresent() ? c.getAssociatedPersonID().get() : "").distinct().collect(Collectors.toList());
        List<PersonImport> listPerson = personAdapter.findByPersonIds(listAssPersonID);

        // Build RoleIndividualGrantDto
        List<RoleIndividualGrantDto> listRoleIndividualGrantDto = new ArrayList<>();
        for (RoleIndividualGrant roleIndividualGrant : listRoleIndividualGrant) {
            // Filter get Users
            Optional<User> user = listUser.stream().filter(c -> c.getUserID().equals(roleIndividualGrant.getUserId())).findFirst();

            if (user.isPresent()) {
                if (user.get().getUserName().isPresent())
                    userName = user.get().getUserName().get().v();

                String loginID = user.get().getLoginID().v();
                // Filter get Person
                if (user.get().getAssociatedPersonID().isPresent()) {
                    Optional<PersonImport> optPerson = listPerson.stream().filter(c -> c.getPersonId().equals(user.get().getAssociatedPersonID().get())).findFirst();
                    if (optPerson.isPresent())
                        userName = optPerson.get().getPersonName();
                    else {
                        userName = "";
                    }
                }
                // Add to list
                RoleIndividualGrantDto dto = new RoleIndividualGrantDto(
                        roleIndividualGrant.getCompanyId(),
                        roleIndividualGrant.getRoleId(),
                        roleIndividualGrant.getRoleType().value,
                        loginID,
                        roleIndividualGrant.getUserId(),
                        userName,
                        roleIndividualGrant.getValidPeriod().start(),
                        roleIndividualGrant.getValidPeriod().end(),"","","");
                listRoleIndividualGrantDto.add(dto);
            }
        }
        listRoleIndividualGrantDto.sort((obj1, obj2) -> {
            return obj1.getLoginID().compareTo(obj2.getLoginID());
        });
        return new RoleIndividualDto(COMPANY_ID_SYSADMIN, listRoleIndividualGrantDto);

    }

    public RoleIndividualGrantMetaDto getCAS012Metadata() {
        LoginUserContext user = AppContexts.user();
        if (!user.roles().have().systemAdmin())
            return null;

        // Get List Enum RoleType
        // #117468 - no 6 remove 「グループ会社管理者」
        List<EnumConstant> enumRoleType = EnumAdaptor.convertToValueNameList(RoleType.class, RoleType.SYSTEM_MANAGER, RoleType.COMPANY_MANAGER);

        // Get list Company Information
        List<CompanyImport> listCompanyImport = companyAdapter.findAllCompany();

        return new RoleIndividualGrantMetaDto(enumRoleType, listCompanyImport);
    }

    public List<RoleTypeDto> getCAS013Metadata() {
        val user = AppContexts.user();
        if (!user.roles().have().companyAdmin()){
            throw  new BusinessException("Msg_1103");
        }
        // Get List Enum RoleType
        List<EnumConstant> enumRoleType = EnumAdaptor.convertToValueNameList(RoleType.class,
                RoleType.EMPLOYMENT, RoleType.SALARY, RoleType.HUMAN_RESOURCE,
                RoleType.OFFICE_HELPER, RoleType.MY_NUMBER, RoleType.PERSONAL_INFO);

        List<RoleTypeDto> roleTypeDtos = new ArrayList<>();
        for (EnumConstant r : enumRoleType) {
            roleTypeDtos.add(new RoleTypeDto(r.getValue(), r.getFieldName(), r.getLocalizedName()));
        }
        return roleTypeDtos;
    }

    public List<RoleIndividualGrantDto> getRoleGrants(String roleId) {
        String companyId = AppContexts.user().companyId();
        if (companyId == null)
            return null;
        List<RoleIndividualGrantDto> rGrants = new ArrayList<>();
        if (roleId == null)
            return rGrants;
        List<RoleIndividualGrant> ListRoleGrants = new ArrayList<>();
        ListRoleGrants = this.roleIndividualGrantRepo.findByCompanyRole(companyId, roleId);
        List<String> userId = ListRoleGrants.stream().map(RoleIndividualGrant::getUserId).distinct().collect(Collectors.toList());
        if (userId.size() == 0) {
            return rGrants;
        }
        Map<String,User> mapUser = userRepo.getByListUser(userId)
                .stream().collect(Collectors.toMap(User::getUserID, e->e));

        List<String> userIdRequest = mapUser.values().stream()
                .filter(User::hasAssociatedPersonID)
                .map(e->e.getAssociatedPersonID().get()).collect(Collectors.toList());
        Map<String,PersonImport> mapPerson = personAdapter.findByPersonIds(userIdRequest).stream()
                .filter(distinctByKey(PersonImport::getPersonId))
                .collect(Collectors.toMap(PersonImport::getPersonId,i->i));
        for (RoleIndividualGrant rGrant : ListRoleGrants) {
            String userName = "";
            String employeeName = "";
            User user = mapUser.getOrDefault(rGrant.getUserId(),null);
            if (user != null) {
                val pid = user.getAssociatedPersonID();
                if(pid.isPresent()){
                    val employeeDataMngInfoOptional = employeeInfoPub
                            .getEmployeeInfoByCidPid(rGrant.getCompanyId(),pid.get());
                    val person = mapPerson.get(pid.get());
                    if ( employeeDataMngInfoOptional.isPresent()){
                        val employeeDataMngInfo = employeeDataMngInfoOptional.get();
                        userName = user.getUserName().isPresent()? user.getUserName().get().v(): null ;
                        employeeName = person.getPersonName();
                        rGrants.add(RoleIndividualGrantDto.fromDomain(rGrant, userName, user.getLoginID().v(),
                                employeeDataMngInfo.getEmployeeId(),employeeDataMngInfo.getEmployeeCode(),employeeName));
                    }
                }
            }
        }
        return rGrants;
    }
    public RoleIndividualGrantDto getRoleGrant(String userId, String roleId,String companyId) {
        if (userId == null || roleId == null)
            return null;

        Optional<RoleIndividualGrant> rGrant = this.roleIndividualGrantRepo.findByKey(userId, companyId, roleId);
        if (!rGrant.isPresent()) {
            return null;
        }
        Optional<User> user = userRepo.getByUserID(rGrant.get().getUserId());
        val pid = user.get().getAssociatedPersonID();
        Optional<EmployeeInfoDto> employeeDataMngInfoOptional =  employeeInfoPub.getEmployeeInfoByCidPid(companyId, pid.get());

        List<RoleIndividualGrant> ListRoleGrants = new ArrayList<>();
        ListRoleGrants = this.roleIndividualGrantRepo.findByCompanyRole(companyId, roleId);

        List<String> uid = ListRoleGrants.stream().map(c -> c.getUserId()).distinct().collect(Collectors.toList());
        List<RoleExport> roleExportList = roleAdapter.getListRole(uid);
        val listPersonOptional = roleExportList.stream().filter( c -> c.getPersonId().equals(pid.get())).findFirst();
        String userName = "";
        String employeeName = "";
        if (user.get().getUserName().isPresent())
            userName = user.get().getUserName().get().v();
        employeeName = listPersonOptional.get().getPersonName();
        return RoleIndividualGrantDto.fromDomain(
                rGrant.get(),
                userName,
                user.get().getLoginID().v(),
                employeeDataMngInfoOptional.isPresent()? employeeDataMngInfoOptional.get().getEmployeeId() : null,
                employeeDataMngInfoOptional.isPresent()?employeeDataMngInfoOptional.get().getEmployeeCode() : null,
                employeeName);

    }
    public CompanyInfo getCompanyInfo() {
        val cid = AppContexts.user().companyId();
        CompanyImport companyInfo = companyAdapter.findCompanyByCid(cid);
        return new CompanyInfo(companyInfo.getCompanyCode(),
                companyInfo.getCompanyName(),
                companyInfo.getCompanyId(),
                companyInfo.getIsAbolition());
    }
    public WorkPlaceInfo GetWorkPlaceInfo(String employeeID) {
        String cid = AppContexts.user().companyId();

        GeneralDate baseDate = GeneralDate.today();

        Optional<SWkpHistExport> sWkpHistExport = workplacePub.findBySidNew(cid, employeeID, baseDate);
        if(sWkpHistExport.isPresent()){
            SWkpHistExport export = sWkpHistExport.get();
            WorkPlaceInfo workPlaceInfo = new WorkPlaceInfo(export.getWorkplaceCode(), export.getWorkplaceName());
            return workPlaceInfo;
        }
        return null;
    }

    public JobTitle GetJobTitle(String employeeID) {
        GeneralDate baseDate = GeneralDate.today();

        Optional<AffJobTitleBasicExport> affJobTitleBasicExport =  syJobTitlePub.getBySidAndBaseDate(employeeID, baseDate);
        if(affJobTitleBasicExport.isPresent()){
            AffJobTitleBasicExport affJobTitleBasicExport1 = affJobTitleBasicExport.get();
            JobTitle jobTitle = new JobTitle(affJobTitleBasicExport1.getJobTitleCode(), affJobTitleBasicExport1.getJobTitleName());
            return jobTitle;
        }
        return null;
    }

    public CompanyImport searchCompanyInfo(String cid) {
        if(cid == null) {
            return null;
        }
        CompanyImport companyInfo = companyAdapter.findCompanyByCid(cid);
        CompanyImport companyInfoDto = new CompanyImport(companyInfo.getCompanyCode(), companyInfo.getCompanyName(), companyInfo.getCompanyId(),companyInfo.getIsAbolition());
        return companyInfoDto;
    }

    public List<CompanyDto> getCompanyList() {
        List<CompanyImport> listCompany = new ArrayList<>();
        List<CompanyDto> listCompanyDTO = new ArrayList<CompanyDto>();
        LoginUserContext user = AppContexts.user();
        if (user.roles().forSystemAdmin() != null) {
            listCompany.addAll(companyAdapter.findAllCompanyImport());
        } else {
            // get company by cid
            listCompany.add(companyAdapter.findCompanyByCid(AppContexts.user().companyId()));
        }
        listCompany.stream().map(c -> {
            return listCompanyDTO.add(new CompanyDto(c.getCompanyCode(), c.getCompanyName(), c.getCompanyId()));
        }).collect(Collectors.toList());

        return listCompanyDTO;
    }
    public List<EmployeeBasicInfoDto> getListEmployeeInfo(String cid) {
        if (cid == null) {
            cid = AppContexts.user().companyId();
        }
        val rs = new ArrayList<EmployeeBasicInfoDto>();
        val basDate = GeneralDate.today();
        // 社員情報リストを取得する
        Map<String, EmployeeInfoDtoExport> employeeInfoDtoExportList = employeeInfoPub.getEmployeesAtWorkByBaseDate(cid, basDate)
                .stream().filter(distinctByKey(EmployeeInfoDtoExport::getEmployeeId)).collect(Collectors.toMap(EmployeeInfoDtoExport::getEmployeeId, i -> i));
        val mapPidSid =  employeeInfoPub.getEmployeesAtWorkByBaseDate(cid, basDate)
                .stream().filter(distinctByKey(EmployeeInfoDtoExport::getPersonId)).collect(Collectors.toMap(EmployeeInfoDtoExport::getEmployeeId, i -> i));
        List<String> listSid = new ArrayList<>(employeeInfoDtoExportList.keySet());
        List<String> listPid = new ArrayList<>(mapPidSid.keySet());
        Map<String, SWkpHistExport> wkpHistExportMap = workplacePub.findBySId(listSid, basDate)
                .stream().filter(distinctByKey(SWkpHistExport::getEmployeeId)).collect(Collectors.toMap( SWkpHistExport::getEmployeeId, i -> i));
        Map<String, EmployeeJobHistExport> jobHistExportMap = syJobTitlePub.findSJobHistByListSIdV2(listSid, basDate).stream()
                .filter(distinctByKey(EmployeeJobHistExport::getEmployeeId)).collect(Collectors.toMap(EmployeeJobHistExport::getEmployeeId, i -> i));
        val mapUidPerId = userIDFromEmpIDService.getUserIDByEmpID(listPid);
        for (val sid : listSid) {
            val employeeIf = employeeInfoDtoExportList.get(sid);
            val pid = employeeIf.getPersonId();
            val uId = mapUidPerId.get(pid);
            val jbInf = jobHistExportMap.get(sid);
            val wplInf = wkpHistExportMap.get(sid);
            rs.add(new EmployeeBasicInfoDto(
                    employeeIf.getPersonId(),
                    uId,
                    sid,
                    employeeIf.getEmployeeCode(),
                    employeeIf.getPerName(),
                    jbInf != null ? jbInf.getJobTitleID() : null,
                    jbInf != null ? jbInf.getJobTitleCode() : null,
                    jbInf != null ? jbInf.getJobTitleName() : null,
                    wplInf != null ? wplInf.getWorkplaceId() : null,
                    wplInf != null ? wplInf.getWorkplaceCode() : null,
                    wplInf != null ? wplInf.getWorkplaceName() : null,
                    wplInf != null ? wplInf.getWkpDisplayName() : null
            ));
        }
        return rs.stream().sorted(Comparator.comparing(e->e.employeeCode)).collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
