package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilyInsHis;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilyInsHisRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class EmpAddChangeInfoExportPDFService extends ExportService<NotificationOfLossInsExportQuery> {

    @Inject
    private SocialInsurNotiCrSetRepository socialInsurNotiCrSetRepository;

    @Inject
    private EmpAddChangeInfoRepository empAddChangeInfoRepository;

    @Inject
    private EmpAddChangeInfoFileGenerator empAddChangeInfoFileGenerator;

    @Inject
    private EmpFamilyInsHisRepository empFamilyInsHisRepository;

    @Override
    protected void handle(ExportServiceContext<NotificationOfLossInsExportQuery> exportServiceContext) {
        String userId = AppContexts.user().userId();
        String cid = AppContexts.user().companyId();
        GeneralDate start = exportServiceContext.getQuery().getStartDate();
        GeneralDate end = exportServiceContext.getQuery().getEndDate();
        int printPersonNumber = exportServiceContext.getQuery().getSocialInsurNotiCreateSet().getPrintPersonNumber();
        NotificationOfLossInsExport socialInsurNotiCreateSet = exportServiceContext.getQuery().getSocialInsurNotiCreateSet();

        SocialInsurNotiCreateSet domain = new SocialInsurNotiCreateSet(userId, cid,
                socialInsurNotiCreateSet.getOfficeInformation(),
                socialInsurNotiCreateSet.getBusinessArrSymbol(),
                socialInsurNotiCreateSet.getOutputOrder(),
                socialInsurNotiCreateSet.getPrintPersonNumber(),
                socialInsurNotiCreateSet.getSubmittedName(),
                socialInsurNotiCreateSet.getInsuredNumber(),
                socialInsurNotiCreateSet.getFdNumber(),
                socialInsurNotiCreateSet.getTextPersonNumber(),
                socialInsurNotiCreateSet.getOutputFormat(),
                socialInsurNotiCreateSet.getLineFeedCode()
        );
        socialInsurNotiCrSetRepository.update(domain);

        List<String> empIds = exportServiceContext.getQuery().getEmpIds();
        if(end.before(start)) {
            throw new BusinessException("Msg_812");
        }

        if (printPersonNumber == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER.value || printPersonNumber == PersonalNumClass.DO_NOT_OUTPUT.value){
            List<EmpAddChangeInfo> empAddChangeInfoList = empAddChangeInfoRepository.getListEmpAddChange(empIds);
            EmpAddChangeInformation empAddChangeInformation = null;
            for (int i = 0; i < empIds.size(); i++) {

                //1. アルゴリズム「社員毎被保険者住所変更届対象データ判定処理」を実行する

                empAddChangeInformation = new EmpAddChangeInformation();
                empAddChangeInformation.setEmpId(empIds.get(i));

                //Imported（給与）「個人現住所」PersonCurrentAddress
                PersonCurrentAddress personCurrentAddress = new PersonCurrentAddress();
                if ( personCurrentAddress != null) {
                    empAddChangeInformation.setPersonAddChangeDate(personCurrentAddress.getStartDate());
                }
                //Imported（給与）「家族情報」
                FamilyInformation familyInformation = new FamilyInformation();
                if (familyInformation != null ){
                    //Imported（給与）「家族現住所」
                    FamilyCurrentAddress familyCurrentAddress = new FamilyCurrentAddress();
                    if(familyCurrentAddress != null ) {
                        empAddChangeInformation.setSpouseAddChangeDate(familyCurrentAddress.getStartDate());
                        Optional<EmpFamilyInsHis> empFamilyInsHis = empFamilyInsHisRepository.getListEmFamilyHis(empIds.get(i), Integer.parseInt(familyInformation.getFamilyId()));
                    }
                }

            }

            EmpAddChangeInforData empAddChangeInforData = new EmpAddChangeInforData();
            List<EmpAddChangeInfoExport> empAddChangeInfoExportList = new ArrayList<>();
            EmpAddChangeInfoExport empAddChangeInfoExport = new EmpAddChangeInfoExport(
                    "1233",
                    "1233",
                    "1233",
                    "1233",
                    "1234567",
                    "nameKana",
                    "fullNamePs",
                    start,
                    "1233121",
                    "add1KanaPs",
                    "add2KanaPs",
                    "add1Ps",
                    "add2Ps",
                    start,
                    "7654321",
                    1,
                    1,
                    1,
                    1,
                    "otherReason",
                    1,
                    1,
                    1,
                    1,
                    "spouseOtherReason",
                    start,
                    "nameKanaF",
                    "fullNameF",
                    "1233234",
                    "add1KanaF",
                    "add2KanaF",
                    "add1F",
                    "add2F",
                    start,
                    "add1BeforeChange",
                    "add2BeforeChange",
                    "address1",
                    "address2",
                    "businessName",
                    "referenceName",
                    "phoneNumber",
                    true,
                    start,
                    start
            );
            empAddChangeInfoExportList.add(empAddChangeInfoExport);

            //condition for print
            // empid tương ứng
            if (empAddChangeInformation.getPersonAddChangeDate() != null  )  {
                //条件を満たす対象者のデータをもとに「被保険者住所変更届」を印刷する
            }
            // empid tương ứng
            if ( empAddChangeInformation.getSpouseAddChangeDate() != null && empAddChangeInformation.isEmpPenInsurance() ) {
                //条件を満たす対象者のデータをもとに「国民年金第３号被保険者住所変更届」を印刷する
            }

            empAddChangeInfoFileGenerator.generate(exportServiceContext.getGeneratorContext(), empAddChangeInforData);
        }
    }
}
