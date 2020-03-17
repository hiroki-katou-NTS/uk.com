package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.notice.app.find.report.PersonalReportClassificationDto;
import nts.uk.ctx.hr.notice.app.find.report.PersonalReportClassificationFinder;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachPersonReportFileRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class RegistrationPersonReportFinder {
	
	@Inject
	private RegistrationPersonReportRepository repo;
	
	@Inject
	private PersonalReportClassificationFinder reportClsFinder;
	
	@Inject
	private PersonalReportClassificationRepository PerReportClassRepo;

	
	// lay ra danh sach report hien thi trong gird  owr manf JHN001.A
	// アルゴリズム「起動処理」を実行する (Thực hiện thuật toán 「Xử lý khởi động」)
	public List<PersonalReportDto> getListReport(String cid) {

		List<PersonalReportDto> result = new ArrayList<PersonalReportDto>();

		// danh sách report
		List<RegistrationPersonReport> listReport = repo.getListByCid(cid);

		// danh sách report bên màn hình JHN011.
		
		List<PersonalReportClassificationDto> listReportJhn011 = this.reportClsFinder.getAllReportClsForJHN001(false);

		
		if (!listReportJhn011.isEmpty()) {
			listReportJhn011.stream().forEach(rp -> {
				PersonalReportDto dto = new PersonalReportDto();
				Optional<RegistrationPersonReport> report = listReport.stream().filter(rpt -> rpt.getReportLayoutID() == rp.getReportClsId()).findFirst();
				if (report.isPresent()) {
					dto.setReportID(report.get().getReportID());
					dto.setSendBackComment(report.get().getSendBackComment());
					dto.setRootSateId(report.get().getRootSateId());
					dto.setRegStatus(report.get().getRegStatus().value);
					dto.setAprStatus(report.get().getAprStatus().value);
					rp.setReportName(report.get().getReportName());
					dto.setClsDto(rp);
					result.add(dto);
				} else {
					dto.setReportID(null);
					dto.setSendBackComment("");
					dto.setRegStatus(null);
					dto.setAprStatus(null);
					dto.setClsDto(rp);
					result.add(dto);
				}
			});
			
			return result;
		}
		return new ArrayList<>();
	};

	public List<RegistrationPersonReportDto> findPersonReport(PersonReportQuery query) {

		String cId = AppContexts.user().companyId();

		String sId = AppContexts.user().employeeId();
		
		List<RegistrationPersonReport> regisList = this.repo.findByJHN003(cId, sId, query.getAppDate().getStartDate(),
				query.getAppDate().getEndDate(), query.getReportId(), query.getApprovalStatus(), query.getInputName(),
				query.isApprovalReport()).stream()
				.sorted(Comparator.comparing(RegistrationPersonReport::getAppDate)).collect(Collectors.toList());

		if ((regisList.size() > 99 && query.isApprovalReport())
				|| (regisList.size() > 999 && !query.isApprovalReport())) {
			throw new BusinessException("MsgJ_46");
		}

		return regisList
				.stream().map(x -> RegistrationPersonReportDto.fromDomain(x, query.isApprovalReport())).collect(Collectors.toList());
	}
	
	
	// 下書き保存の届出がある場合にアルゴリズム「起動処理」を実行する
	// (Thực hiện thuật toán "Xử lýkhởi động" khi có report lưu bản nháp)
	public List<RegistrationPersonReportSaveDraftDto> getListReportSaveDraft(String cid) {
		List<RegistrationPersonReport> listDomain = repo.getListReportSaveDraft(cid);
		if (listDomain.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<RegistrationPersonReportSaveDraftDto> result = new ArrayList<>();
		result = listDomain.stream().map(dm -> {
			
			RegistrationPersonReportSaveDraftDto dto = new RegistrationPersonReportSaveDraftDto();
			dto.setReportID(dm.getReportID());
			dto.setReportCode(dm.getReportCode());
			dto.setReportName(dm.getReportName());
			dto.setMissingDocName(dm.getMissingDocName());
			dto.setDraftSaveDate(dm.getDraftSaveDate().toDate().toString());
			return dto;
		}).collect(Collectors.toList());
		
		return result;
	};
	
	RegistrationPersonReport getDomain(String cid, Integer reportLayoutID){
		Optional<RegistrationPersonReport> result = repo.getDomain(cid, reportLayoutID);
		return result.isPresent() ? result.get() : null;
	}

	/**
	 * 起動処理
	 * 
	 * @return List<個別届出種類>
	 */
	public List<PersonalReportClassificationDto> startPage() {
		// アルゴリズム [個別届出種類を取得する] を実行する
		// do trong thuật toán "個別届出種類を取得する" chỉ có 1 xử lý "個別届出種類" nên viết nó
		// ra ngoài luôn chứ không tạo function riêng cho "個別届出種類を取得する"

		// ドメインモデル [個別届出種類] を取得する

		return this.PerReportClassRepo.getAllByCid(AppContexts.user().companyId(), false).stream().map(c -> {
			return PersonalReportClassificationDto.fromDomain(c);
		}).sorted(Comparator.comparing(PersonalReportClassificationDto::getReportName)).collect(Collectors.toList());
	};
	
	
}
