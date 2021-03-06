package nts.uk.ctx.pereg.app.command.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import command.person.contact.AddPerContactCommand;
import command.person.contact.DeletePerContactCommand;
import command.person.contact.UpdatePerContactCommand;
import command.person.info.AddPersonCommand;
import command.person.info.UpdatePersonCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.AddBusinessWokrTypeOfHistoryCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.DeleteBusinessWorkTypeOfHistoryCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype.UpdateBusinessWorkTypeOfHistoryCommand;
import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.delete.DeleteStampCardCommand;
import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.add.AddStampCardCommand;
import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.update.UpdateStampCardCommand;
import nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle.AddEmpMedicalWorkCommand;
import nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle.UpdateEmpMedicalWorkCommand;
import nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle.DeleteEmpMedicalWorkCommand;
import nts.uk.ctx.at.shared.app.command.dailyattdcal.empunitpricehistory.AddEmployeeUnitPriceCommand;
import nts.uk.ctx.at.shared.app.command.dailyattdcal.empunitpricehistory.UpdateEmployeeUnitPriceCommand;
import nts.uk.ctx.at.shared.app.command.dailyattdcal.empunitpricehistory.DeleteEmployeeUnitPriceCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annleagrtremnum.AddAnnLeaGrantRemnNumPeregCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave.AddAnnuaLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave.DeleteAnnuaLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annualeave.UpdateAnnuaLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave10informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave11informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave12informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave13informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave14informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave15informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave16informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave17informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave18informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave19informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave1informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave20informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave2informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave3informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave4informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave5informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave6informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave7informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave8informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add.AddSpecialleave9informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave10informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave11informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave12informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave13informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave14informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave15informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave16informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave17informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave18informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave19informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave1informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave20informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave2informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave3informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave4informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave5informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave6informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave7informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave8informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update.UpdateSpecialleave9informationCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave.AddCareLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave.UpdateCareLeaveCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.otherhdinfo.AddOtherHolidayInfoCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.otherhdinfo.DeleteOtherHolidayInfoCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.otherhdinfo.UpdateOtherHolidayInfoCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.rervleagrtremnum.AddResvLeaRemainPeregCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant10Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant11Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant12Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant13Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant14Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant15Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant16Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant17Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant18Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant19Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant1Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant20Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant2Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant3Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant4Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant5Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant6Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant7Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant8Command;
import nts.uk.ctx.at.shared.app.command.remainingnumber.specialleavegrant.add.AddSpecialLeaveGrant9Command;
import nts.uk.ctx.at.shared.app.command.shortworktime.AddShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.shortworktime.DeleteShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.shortworktime.UpdateShortWorkTimeCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.AddWorkingConditionCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.DeleteWorkingConditionCommand;
import nts.uk.ctx.at.shared.app.command.workingcondition.UpdateWorkingCondition2Command;
import nts.uk.ctx.at.shared.app.command.workingcondition.UpdateWorkingConditionCommand;
import nts.uk.ctx.bs.employee.app.command.classification.affiliate.AddAffClassificationCommand;
import nts.uk.ctx.bs.employee.app.command.classification.affiliate.DeleteAffClassificationCommand;
import nts.uk.ctx.bs.employee.app.command.classification.affiliate.UpdateAffClassificationCommand;
import nts.uk.ctx.bs.employee.app.command.department.AddAffiliationDepartmentCommand;
import nts.uk.ctx.bs.employee.app.command.department.DeleteAffiliationDepartmentCommand;
import nts.uk.ctx.bs.employee.app.command.department.UpdateAffiliationDepartmentCommand;
import nts.uk.ctx.bs.employee.app.command.employee.contact.AddEmployeeInfoContactCommand;
import nts.uk.ctx.bs.employee.app.command.employee.contact.DeleteEmployeeInfoContactCommand;
import nts.uk.ctx.bs.employee.app.command.employee.contact.UpdateEmployeeInfoContactCommand;
import nts.uk.ctx.bs.employee.app.command.employee.history.AddAffCompanyHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employee.history.DeleteAffCompanyHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employee.history.UpdateAffCompanyHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employee.mngdata.AddEmployeeDataMngInfoCommand;
import nts.uk.ctx.bs.employee.app.command.employee.mngdata.UpdateEmployeeDataMngInfoCommand;
import nts.uk.ctx.bs.employee.app.command.employment.history.AddEmploymentHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employment.history.DeleteEmploymentHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.employment.history.UpdateEmploymentHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.AddAffJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.DeleteAffJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate.UpdateAffJobTitleMainCommand;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.AddTemporaryAbsenceCommand;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.DeleteTemporaryAbsenceCommand;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.UpdateTemporaryAbsenceCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.affiliate.AddAffWorkplaceHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.affiliate.DeleteAffWorkplaceHistoryCommand;
import nts.uk.ctx.bs.employee.app.command.workplace.affiliate.UpdateAffWorkplaceHistoryCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis.AddEmpCorpHealthOffHisCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis.DeleteEmpCorpHealthOffHisCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis.UpdateEmpCorpHealthOffHisCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo.AddEmpHealInsQualifiInfoCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo.DeleteEmpHealInsQualifiInfoCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo.UpdateEmpHealInsQualifiInfoCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis.AddEmpSocialInsGradeInforCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis.DeleteEmpSocialInsGradeInforCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis.UpdateEmpSocialInsGradeInforCommand;
import nts.uk.ctx.sys.gateway.app.command.login.password.userpassword.UpdateEmpLoginPasswordCommand;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregCommandHandlerCollector;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
@SuppressWarnings("serial")
public class PeregCommandHandlerCollectorImpl implements PeregCommandHandlerCollector {

	/** Add handlers */
	private static final List<TypeLiteral<?>> ADD_HANDLER_CLASSES = Arrays.asList(
			//CS00001	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddEmployeeDataMngInfoCommand>>() {
			},
			//CS00002	??????????????????
			new TypeLiteral<PeregAddCommandHandler<AddPersonCommand>>() {
			},
			//CS00003	??????????????????
			new TypeLiteral<PeregAddCommandHandler<AddAffCompanyHistoryCommand>>() {
			},
			//CS00015	????????????
			new TypeLiteral<PeregAddCommandHandler<AddAffiliationDepartmentCommand>>() {
			},
			//CS00004	?????????
			new TypeLiteral<PeregAddCommandHandler<AddAffClassificationCommand>>() {
			},
			//CS00014	??????
			new TypeLiteral<PeregAddCommandHandler<AddEmploymentHistoryCommand>>() {
			},
			//CS00016	????????????
			new TypeLiteral<PeregAddCommandHandler<AddAffJobTitleMainCommand>>() {
			},
			//CS00017	??????
			new TypeLiteral<PeregAddCommandHandler<AddAffWorkplaceHistoryCommand>>() {
			},
			//CS00018	????????????
			new TypeLiteral<PeregAddCommandHandler<AddTemporaryAbsenceCommand>>() {
			},
			//CS00019	???????????????
			new TypeLiteral<PeregAddCommandHandler<AddShortWorkTimeCommand>>() {
			},
			//CS00020	????????????
			new TypeLiteral<PeregAddCommandHandler<AddWorkingConditionCommand>>() {
			},
			//CS00021	????????????
			new TypeLiteral<PeregAddCommandHandler<AddBusinessWokrTypeOfHistoryCommand>>() {
			},
			//CS00022	???????????????
			new TypeLiteral<PeregAddCommandHandler<AddPerContactCommand>>() {
			},
			//CS00023	???????????????
			new TypeLiteral<PeregAddCommandHandler<AddEmployeeInfoContactCommand>>() {
			},
			//CS00024	????????????
			new TypeLiteral<PeregAddCommandHandler<AddAnnuaLeaveCommand>>() {
			},
			//CS00025	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave1informationCommand>>() {
			},
			//CS00026	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave2informationCommand>>() {
			},
			//CS00027	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave3informationCommand>>() {
			},
			//CS00028	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave4informationCommand>>() {
			},
			//CS00029	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave5informationCommand>>() {
			},
			//CS00030	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave6informationCommand>>() {
			},
			//CS00031	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave7informationCommand>>() {
			},
			//CS00032	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave8informationCommand>>() {
			},
			//CS00033	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave9informationCommand>>() {
			},
			//CS00034	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave10informationCommand>>() {
			},
			//CS00035	?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddOtherHolidayInfoCommand>>() {
			},
			//CS00036	?????????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddCareLeaveCommand>>() {
			},
			//CS00037	??????????????????
			new TypeLiteral<PeregAddCommandHandler<AddAnnLeaGrantRemnNumPeregCommand>>() {
			},
			//CS00038	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddResvLeaRemainPeregCommand>>() {
			},
			//CS00039	???????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant1Command>>() {
			},
			//CS00040	???????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant2Command>>() {
			},
			//CS00041	???????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant3Command>>() {
			},
			//CS00042	???????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant4Command>>() {
			},
			//CS00043	???????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant5Command>>() {
			},
			//CS00044	???????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant6Command>>() {
			},
			//CS00045	???????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant7Command>>() {
			},
			//CS00046	???????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant8Command>>() {
			},
			//CS00047	???????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant9Command>>() {
			},
			//CS00048	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant10Command>>() {
			},
			//CS00049	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave11informationCommand>>() {
			},
			//CS00050	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave12informationCommand>>() {
			},
			//CS00051	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave13informationCommand>>() {
			},
			//CS00052	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave14informationCommand>>() {
			},
			//CS00053	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave15informationCommand>>() {
			},
			//CS00054	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave16informationCommand>>() {
			},
			//CS00055	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave17informationCommand>>() {
			},
			//CS00056	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave18informationCommand>>() {
			},
			//CS00057	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave19informationCommand>>() {
			},
			//CS00058	????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialleave20informationCommand>>() {
			},
			//CS00059	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant11Command>>() {
			},
			//CS00060	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant12Command>>() {
			},
			//CS00061	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant13Command>>() {
			},
			//CS00062	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant14Command>>() {
			},
			//CS00063	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant15Command>>() {
			},
			//CS00064	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant16Command>>() {
			},
			//CS00065	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant17Command>>() {
			},
			//CS00066	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant18Command>>() {
			},
			//CS00067	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant19Command>>() {
			},
			//CS00068	??????????????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddSpecialLeaveGrant20Command>>() {
			},
			//CS00069 ?????????????????????
			new TypeLiteral<PeregAddCommandHandler<AddStampCardCommand>>() {
			},
			//CS00075
			new TypeLiteral<PeregAddCommandHandler<AddEmpCorpHealthOffHisCommand>>() {
			},
			// CS00082
			new TypeLiteral<PeregAddCommandHandler<AddEmpHealInsQualifiInfoCommand>>() {
			},
			// CS00092
			new TypeLiteral<PeregAddCommandHandler<AddEmpSocialInsGradeInforCommand>>() {
			},
			// CS00097 ??????
			new TypeLiteral<PeregAddCommandHandler<AddEmployeeUnitPriceCommand>>() {
			},
			// CS00098 ??????
			new TypeLiteral<PeregAddCommandHandler<AddEmpMedicalWorkCommand>>() {
			}
	);
	/** Update handlers */
	private static final List<TypeLiteral<?>> UPDATE_HANDLER_CLASSES = Arrays.asList(
			//CS00001	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmployeeDataMngInfoCommand>>() {
			},
			//CS00002	??????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdatePersonCommand>>() {
			},
			//CS00003	??????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffCompanyHistoryCommand>>() {
			},
			//CS00004	?????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffClassificationCommand>>() {
			},
			//CS00014	??????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmploymentHistoryCommand>>() {
			},
			//CS00015	????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffiliationDepartmentCommand>>() {
			},
			//CS00016	????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffJobTitleMainCommand>>() {
			},
			//CS00017	??????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAffWorkplaceHistoryCommand>>() {
			},
			//CS00018	????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateTemporaryAbsenceCommand>>() {
			},
			//CS00019	???????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateShortWorkTimeCommand>>() {
			},
			//CS00020	????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateWorkingConditionCommand>>() {
			},
			//CS00021	????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateBusinessWorkTypeOfHistoryCommand>>() {
			},
			//CS00022	???????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdatePerContactCommand>>() {
			},
			//CS00023	???????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmployeeInfoContactCommand>>() {
			},
			//CS00024	????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateAnnuaLeaveCommand>>() {
			},
			//CS00025	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave1informationCommand>>() {
			},
			//CS00026	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave2informationCommand>>() {
			},
			//CS00027	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave3informationCommand>>() {
			},
			//CS00028	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave4informationCommand>>() {
			},
			//CS00029	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave5informationCommand>>() {
			},
			//CS00030	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave6informationCommand>>() {
			},
			//CS00031	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave7informationCommand>>() {
			},
			//CS00032	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave8informationCommand>>() {
			},
			//CS00033	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave9informationCommand>>() {
			},
			//CS00034	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave10informationCommand>>() {
			},
			//CS00035	?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateOtherHolidayInfoCommand>>() {
			},
			//CS00036	?????????????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateCareLeaveCommand>>() {
			},
			//CS00049	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave11informationCommand>>() {
			},
			//CS00050	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave12informationCommand>>() {
			},
			//CS00051	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave13informationCommand>>() {
			},
			//CS00052	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave14informationCommand>>() {
			},
			//CS00053	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave15informationCommand>>() {
			},
			//CS00054	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave16informationCommand>>() {
			},
			//CS00055	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave17informationCommand>>() {
			},
			//CS00056	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave18informationCommand>>() {
			},
			//CS00057	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave19informationCommand>>() {
			},
			//CS00058	????????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateSpecialleave20informationCommand>>() {
			},
			//CS00069 ?????????????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateStampCardCommand>>() {
			},
			//CS00070  ????????????2
			new TypeLiteral<PeregUpdateCommandHandler<UpdateWorkingCondition2Command>>() {
			},
			//CS00075
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmpCorpHealthOffHisCommand>>() {
			},
			// CS00082
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmpHealInsQualifiInfoCommand>>() {
			},
			// CS00092
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmpSocialInsGradeInforCommand>>() {
			},
			// CS00097 ??????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmployeeUnitPriceCommand>>() {
			},
			// CS00098 ??????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmpMedicalWorkCommand>>() {
			},
			// CS00100 ???????????????
			new TypeLiteral<PeregUpdateCommandHandler<UpdateEmpLoginPasswordCommand>>() {
			}
			
	);
	
	
	/** Delete handlers */
	private static final List<TypeLiteral<?>> DELETE_HANDLER_CLASSES = Arrays.asList(
			//CS00003	??????????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffCompanyHistoryCommand>>() {
			},
			//CS00004	?????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffClassificationCommand>>() {
			},
			//CS00014	??????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmploymentHistoryCommand>>() {
			},
			//CS00015	????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffiliationDepartmentCommand>>() {
			},
			//CS00016	????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffJobTitleMainCommand>>() {
			},
			//CS00017	??????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAffWorkplaceHistoryCommand>>() {
			},
			//CS00018	????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteTemporaryAbsenceCommand>>() {
			},
			//CS00019	???????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteShortWorkTimeCommand>>() {
			},
			//CS00020	????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteWorkingConditionCommand>>() {
			},
			//CS00021	????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteBusinessWorkTypeOfHistoryCommand>>() {
			},
			//CS00022	???????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeletePerContactCommand>>() {
			},
			//CS00023	???????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmployeeInfoContactCommand>>() {
			},
			//CS00024	????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteAnnuaLeaveCommand>>() {
			},
			//CS00035	?????????????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteOtherHolidayInfoCommand>>() {
			},
			//CS00069 ?????????????????????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteStampCardCommand>>(){},
			//CS00075
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmpCorpHealthOffHisCommand>>() {
			},
			// CS00082
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmpHealInsQualifiInfoCommand>>() {
			},
			// CS00092
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmpSocialInsGradeInforCommand>>() {
			},
			// CS00097 ??????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmployeeUnitPriceCommand>>() {
			},
			// CS00098 ??????
			new TypeLiteral<PeregDeleteCommandHandler<DeleteEmpMedicalWorkCommand>>() {
			}
	);
	
	@Override
	public Set<PeregAddCommandHandler<?>> collectAddHandlers() {
		return ADD_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregAddCommandHandler<?>)obj)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<PeregUpdateCommandHandler<?>> collectUpdateHandlers() {
		return UPDATE_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregUpdateCommandHandler<?>)obj)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<PeregDeleteCommandHandler<?>> collectDeleteHandlers() {
		return DELETE_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregDeleteCommandHandler<?>)obj)
				.collect(Collectors.toSet());
	}

}
