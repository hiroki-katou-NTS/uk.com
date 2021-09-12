package nts.uk.file.com.app.equipment.data;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.office.dom.equipment.data.EquipmentData;
import nts.uk.ctx.office.dom.equipment.data.EquipmentDataRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EquipmentDataExportFileQueryImpl implements EquipmentDataExportFileQuery {

	@Inject
	private EquipmentDataExportGenerator generator;
	
	@Inject
	private EquipmentDataRepository equipmentDataRepository;

	@Inject
	private CompanyRepository companyRepository;
	
	@Override
	public void handle(ExportServiceContext<EquipmentDataQuery> context, EquipmentDataQuery query) {
		EquipmentDataExportDataSource dataSource = this.getData(query);
		this.generator.generate(context.getGeneratorContext(), dataSource);
	}

	@Override
	public EquipmentDataExportDataSource getData(EquipmentDataQuery query) {
		// Init input
		Optional<String> optEquipmentClsCode = Optional.ofNullable(query.getEquipmentClsCode());
		Optional<String> optEquipmentCode = Optional.ofNullable(query.getEquipmentCode());
		YearMonth ym = YearMonth.of(query.getYm());

		// $対象期間 = 期間# 1ヶ月の期間を作る(INPUT「年月」)
		DatePeriod period = DatePeriod.daysFirstToLastIn(ym);

		// 1.期間、設備コード、設備分類コードから実績データを取得する(ログイン会社ID、$対象期間、Optional<設備コード>、Optional<設備分類コード>)
		List<EquipmentData> equipmentDatas = this.equipmentDataRepository.findByPeriodAndOptionalInput(
				AppContexts.user().companyId(), period, optEquipmentCode, optEquipmentClsCode);
		// 2.[設備利用実績データ<List> IS NULL]
		if (equipmentDatas.isEmpty()) {
			throw new BusinessException("Msg_2240");
		}
		// 3.<call>(ログイン会社ID)
		Optional<Company> optCompanyInfo = this.companyRepository
				.getComanyNotAbolitionByCid(AppContexts.user().companyId());
		// TODO
		return null;
	}
}
