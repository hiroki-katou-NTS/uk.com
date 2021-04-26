//package nts.uk.cnv.screen.app.query;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nemunoki.oruta.dom.schema.snapshot.SchemaSnapshot;
//import nemunoki.oruta.dom.schema.snapshot.SnapshotRepository;
//import nts.arc.error.BusinessException;
//import nts.arc.error.RawErrorMessage;
//import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
//import nts.uk.cnv.screen.app.query.dto.Cnv001BLoadDataDto;
//import nts.uk.cnv.screen.app.query.dto.Cnv001BLoadParamDto;
//
//@Stateless
//public class Cnv001BService {
//
//	@Inject
//	SnapshotRepository ssRepo;
//
//	@Inject
//	ConversionCategoryTableRepository repository;
//
//	public Cnv001BLoadDataDto loadData(Cnv001BLoadParamDto param) {
//
//		List<String> conversionTableCategories = repository.get(param.getCategory()).stream()
//				.map(cate -> cate.getTablename())
//				.collect(Collectors.toList());
//
//		SchemaSnapshot sss = ssRepo.getSchemaLatest()
//				.orElseThrow(() -> new BusinessException(new RawErrorMessage("スキーマスナップショットがありません")));
//		List<String> tables = ssRepo.getTableList(sss.getSnapshotId()).getList().stream()
//				.map(ti -> ti.getName())
//				.collect(Collectors.toList());
//
//		tables.removeAll(conversionTableCategories);
//
//		return new Cnv001BLoadDataDto(conversionTableCategories, tables);
//	}
//}
