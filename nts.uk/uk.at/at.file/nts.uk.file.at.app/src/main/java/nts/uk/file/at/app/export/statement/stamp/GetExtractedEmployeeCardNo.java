package nts.uk.file.at.app.export.statement.stamp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻カード.App.抽出した社員のカードNOを取得する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetExtractedEmployeeCardNo {

	@Inject
	private StampCardRepository stampCardRepo;
	
	@Inject
	private EmpEmployeeAdapter adapter;

	/**
	 * 
	 * @param input
	 * @return <List>打刻カード
	 */
	public List<QRStampCardDto> get(GetExtractedEmployeeCardNoInput input) {

		List<QRStampCardDto> result = new ArrayList<>();

		// sort 登録日付 DESC, 番号 ASC
		Comparator<StampCard> compareByDate = Comparator.comparing(StampCard::getRegisterDate).reversed()
				.thenComparing(number -> number.getStampNumber().v());

		for (String sId : input.getSIds()) {
			List<StampCard> stampCards = stampCardRepo.getLstStampCardBySidAndContractCd(input.getContractCode(), sId);

			Optional<StampCard> optStampCard = stampCards.stream().sorted(compareByDate).findFirst();

			if (optStampCard.isPresent()) {
				EmployeeImport emp = adapter.findByEmpId(sId);
				result.add(new QRStampCardDto(sId, optStampCard.get().getStampNumber().v(), emp.getEmployeeCode(), emp.getEmployeeName()));
			}

		}

		return result;
	}
}
