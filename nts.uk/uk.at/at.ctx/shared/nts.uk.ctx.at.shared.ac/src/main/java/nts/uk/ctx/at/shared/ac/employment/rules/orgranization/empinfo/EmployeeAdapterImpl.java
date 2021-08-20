
package nts.uk.ctx.at.shared.ac.employment.rules.orgranization.empinfo;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.ClassificationImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.DepartmentImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.EmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.EmployeeInfoQueryDto;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.EmployeeInformationImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.EmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.FacePhotoFileImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.PositionImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.WorkplaceImport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class EmployeeAdapterImpl implements EmployeeAdapter {

    /**
     * The employment.
     */
    @Inject
    public SyEmploymentPub employment;

    @Inject
    private EmployeeInformationPub employeeInformationPub;

    @Inject
    private SyEmployeePub employeePub;


    /**
     * [1] 社員コードから社員IDを取得する
     *
     * @param companyId 会社ID
     * @param empCodes  List<社員コード>
     * @return Map<社員コード                                                                                                                               ,                                                                                                                               社員ID>	                                                               ,                               S               t               r               ing>
     */
    @Override
    public Map<String, String> getEmploymentMapCodeName(String companyId, List<String> empCodes) {
        return employment.getEmploymentMapCodeName(companyId, empCodes);
    }

    /**
     * [2] 社員IDリストから社員コードと表示名を取得する
     *
     * @param sIds List<社員ID>
     * @return List<社員コードと表示名Imported                                                                                                                                >
     */
    @Override
    public List<EmployeeInfoImport> getByListSid(List<String> sIds) {
        return employeePub.getByListSid(sIds).stream()
                .map(x -> new EmployeeInfoImport(
                        x.getSid(),
                        x.getScd(),
                        x.getBussinessName()
                )).collect(Collectors.toList());
    }

    /**
     * アルゴリズム.<<Public>> 社員の情報を取得する( 社員IDリスト, 基準日, 取得したい社員情報 )
     *
     * @param employeeIds   List<社員ID>
     * @param referenceDate 年月日
     * @param param         取得したい社員情報
     * @return List<EmployeeInformationImport>
     */
    @Override
    public List<EmployeeInformationImport> find(List<String> employeeIds, GeneralDate referenceDate, EmployeeInfoQueryDto param) {
        EmployeeInformationQueryDto empInfo = EmployeeInformationQueryDto.builder()
                .employeeIds(employeeIds)
                .referenceDate(referenceDate)
                .toGetWorkplace(param.isToGetWorkplace())
                .toGetDepartment(param.isToGetDepartment())
                .toGetPosition(param.isToGetPosition())
                .toGetEmployment(param.isToGetEmployment())
                .toGetClassification(param.isToGetClassification())
                .toGetEmploymentCls(param.isToGetEmploymentCls()).build();
        return employeeInformationPub.find(empInfo).stream().map(x -> {
                    val workPlace = x.getWorkplace();
                    val classification = x.getClassification();
                    val department = x.getDepartment();
                    val position = x.getPosition();
                    val employment = x.getEmployment();
                    val avatarFile = x.getAvatarFile();
                    return new EmployeeInformationImport(
                            x.getEmployeeId(),
                            x.getEmployeeCode(),
                            x.getBusinessName(),
                            x.getBusinessNameKana(),
                            new WorkplaceImport(
                                    workPlace != null ? workPlace.getWorkplaceId() : "",
                                    workPlace != null ? workPlace.getWorkplaceId() : "",
                                    workPlace != null ? workPlace.getWorkplaceId() : "",
                                    workPlace != null ? workPlace.getWorkplaceId() : ""
                            ),
                            new ClassificationImport(
                                    classification != null ? classification.getClassificationCode() : "",
                                    classification != null ? classification.getClassificationName() : ""

                            ),
                            new DepartmentImport(
                                    department != null ? department.getCompanyId() : "",
                                    department != null ? department.isDeleteFlag() : false,
                                    department != null ? department.getDepartmentHistoryId() : "",
                                    department != null ? department.getDepartmentId() : "",
                                    department != null ? department.getDepartmentCode() : "",
                                    department != null ? department.getDepartmentName() : "",
                                    department != null ? department.getDepartmentGeneric() : "",
                                    department != null ? department.getDepartmentDisplayName() : "",
                                    department != null ? department.getHierarchyCode() : "",
                                    department != null ? department.getDepartmentExternalCode() : ""
                            ),
                            new PositionImport(
                                    position != null ? position.getPositionId() : "",
                                    position != null ? position.getPositionCode() : "",
                                    position != null ? position.getPositionName() : ""
                            ),
                            new EmploymentImport(
                                    employment != null ? employment.getEmploymentCode() : "",
                                    employment != null ? employment.getEmploymentName() : ""
                            ),
                            x.getEmploymentCls(),
                            x.getPersonID(),
                            x.getEmployeeName(),
                            new FacePhotoFileImport(
                                    avatarFile != null ? avatarFile.getThumbnailFileID() : "",
                                    avatarFile != null ? avatarFile.getFacePhotoFileID() : ""
                            ),
                            x.getBirthday(),
                            x.getAge(),
                            x.getGender()
                    );
                }
        ).collect(Collectors.toList());
    }
}
